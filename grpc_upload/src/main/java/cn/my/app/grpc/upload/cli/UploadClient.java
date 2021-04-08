package cn.my.app.grpc.upload.cli;

import io.grpc.Channel;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import cn.my.app.grpc.upload.Chunk;
import cn.my.app.grpc.upload.UploadRequest;
import cn.my.app.grpc.upload.UploadServiceGrpc;
import cn.my.app.grpc.upload.UploadServiceGrpc.UploadServiceBlockingStub;
import cn.my.app.grpc.upload.UploadServiceGrpc.UploadServiceStub;
import cn.my.app.grpc.upload.UploadStatus;
import cn.my.app.grpc.util.ZipUtils;

import com.google.protobuf.ByteString;

/**
 * Sample client code that makes gRPC calls to the server.
 */
public class UploadClient
{
    private static final Logger logger = Logger.getLogger(UploadClient.class.getName());

    private final UploadServiceBlockingStub blockingStub;
    private final UploadServiceStub asyncStub;

    public UploadClient(Channel channel)
    {
        blockingStub = UploadServiceGrpc.newBlockingStub(channel);
        asyncStub = UploadServiceGrpc.newStub(channel);
    }

    public Chunk buildChunk(byte[] b)
    {
        return Chunk.newBuilder().setContent(ByteString.copyFrom(b)).build();
    }

    public void safeClose(InputStream is)
    {
        if (is != null)
        {
            try
            {
                is.close();
            }
            catch (IOException e)
            {
            }
        }
    }

    public void safeClose(OutputStream os)
    {
        if (os != null)
        {
            try
            {
                os.close();
            }
            catch (IOException e)
            {
            }
        }
    }

    public void upload(File f, int chunkSize) throws InterruptedException
    {
        final CountDownLatch finishLatch = new CountDownLatch(1);
        StreamObserver<UploadStatus> responseObserver = new StreamObserver<UploadStatus>()
        {
            @Override
            public void onNext(UploadStatus value)
            {
                UploadClient.logger.log(Level.INFO, "[Client] onNext, value=" + value.getCode() + ", " + value.getMessage());
            }

            @Override
            public void onError(Throwable t)
            {
                finishLatch.countDown();

                UploadClient.logger.log(Level.WARNING, "[Client] onError, " + t.getMessage());
                t.printStackTrace();
            }

            @Override
            public void onCompleted()
            {
                finishLatch.countDown();
                UploadClient.logger.log(Level.INFO, "[Client] onCompleted, upload finished.");
            }
        };

        StreamObserver<UploadRequest> requestObserver = asyncStub.upload(responseObserver);
        FileInputStream is = null;
        ByteArrayOutputStream bos = null;

        try
        {
            is = new FileInputStream(f);
            bos = new ByteArrayOutputStream(chunkSize);
            byte[] b = new byte[chunkSize];
            int n;
            while ((n = is.read(b)) != -1)
            {
                if (n == chunkSize)
                {
                    requestObserver.onNext(UploadRequest.newBuilder()
                            .setName(f.getName())
                            .setChunk(buildChunk(b))
                            .build());
                }
                else
                {
                    bos.write(b, 0, n);
                    requestObserver.onNext(UploadRequest.newBuilder()
                            .setName(f.getName())
                            .setChunk(buildChunk(bos.toByteArray()))
                            .build());
                }

                if (finishLatch.getCount() == 0)
                {
                    // RPC completed or errored before we finished sending.
                    // Sending further requests won't error, but they will just be thrown away.
                    return;
                }
            }
        }
        catch (RuntimeException e)
        {
            // Cancel RPC
            requestObserver.onError(e);
            UploadClient.logger.log(Level.SEVERE, e.getMessage());
            throw e;
        }
        catch (Exception e)
        {
            UploadClient.logger.log(Level.SEVERE, e.getMessage());
            e.printStackTrace();
        }
        finally
        {
            safeClose(is);
            safeClose(bos);
        }

        // Mark the end of requests
        requestObserver.onCompleted();
        UploadClient.logger.log(Level.INFO, "Mark the end of requests.");

        // Receiving happens asynchronously
        if (!finishLatch.await(1, TimeUnit.MINUTES))
        {
            UploadClient.logger.log(Level.INFO, "upload can not finish within 1 minutes");
        }
    }

    /** Issues several different requests and then exits. */
    public static void main(String[] args) throws InterruptedException
    {
        // String target = "localhost:8980";
        String target = "47.117.69.74:8980";
        try
        {
            // ZipUtils.compress(new String[] { "/data/dicomImage/100166155827", "/data/dicomImage/100165745707/" },
            // "dicomImage.zip");
            ZipUtils.compress(new String[] { "/data/dicomImage/100166155827" },
                    "dicomImage.zip");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        System.out.println("zip completed.");
        String filePath = "./dicomImage.zip";
        // String filePath = "/home/wf/bak/dcm4che5.23.2.tar.gz";
        // if (args.length > 0)
        // {
        // if ("--help".equals(args[0]))
        // {
        // System.err.println("Usage: [target]");
        // System.err.println("");
        // System.err.println("  target  The server to connect to. Defaults to " + target);
        // System.exit(1);
        // }
        // target = args[0];
        // filePath = args[1];
        // }

        ManagedChannel channel = ManagedChannelBuilder.forTarget(target).usePlaintext().build();
        System.out.println("channel=" + channel);
        try
        {
            UploadClient client = new UploadClient(channel);
            System.out.println("client=" + client);
            // client.upload(new File("/home/wf/OC_64R_128S_0.20.x_20201209.tar.gz"), 1 * 1024 * 1024);
            client.upload(new File(filePath), 1 * 1024 * 1024);
        }
        finally
        {
            channel.shutdownNow().awaitTermination(120, TimeUnit.SECONDS);
        }
    }
}