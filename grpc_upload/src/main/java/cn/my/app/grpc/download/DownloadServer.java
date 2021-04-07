package cn.my.app.grpc.download;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import com.google.protobuf.ByteString;

/**
 * A sample gRPC server that serve the RouteGuide (see route_guide.proto) service.
 */
public class DownloadServer
{
    private static final Logger logger = Logger.getLogger(DownloadServer.class.getName());

    private final int port;
    private final Server server;

    public DownloadServer(int port) throws IOException
    {
        this.port = port;
        ServerBuilder<?> serverBuilder = ServerBuilder.forPort(port);
        server = serverBuilder.addService(new DownloadService())
                .build();
    }

    /** Start serving requests. */
    public void start() throws IOException
    {
        server.start();
        DownloadServer.logger.info("Server started, listening on " + port);
        Runtime.getRuntime().addShutdownHook(new Thread()
        {
            @Override
            public void run()
            {
                // Use stderr here since the logger may have been reset by its JVM shutdown hook.
                System.err.println("*** shutting down gRPC server since JVM is shutting down");
                try
                {
                    DownloadServer.this.stop();
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace(System.err);
                }
                System.err.println("*** server shut down");
            }
        });
    }

    /** Stop serving requests and shutdown resources. */
    public void stop() throws InterruptedException
    {
        if (server != null)
        {
            server.shutdown().awaitTermination(30, TimeUnit.SECONDS);
        }
    }

    /**
     * Await termination on the main thread since the grpc library uses daemon threads.
     */
    private void blockUntilShutdown() throws InterruptedException
    {
        if (server != null)
        {
            server.awaitTermination();
        }
    }

    /**
     * Main method. This comment makes the linter happy.
     */
    public static void main(String[] args) throws Exception
    {
        DownloadServer server = new DownloadServer(8980);
        server.start();
        server.blockUntilShutdown();
    }

    private static class DownloadService extends DownloadServiceGrpc.DownloadServiceImplBase
    {
        DownloadService()
        {
        }

        @Override
        public void download(DownloadRequest request, StreamObserver<DownloadResponse> responseObserver)
        {
            String filename = request.getName();
            DownloadServer.logger.info("rcvd request to download " + filename);

            File f = new File("/home/wf/OC_64R_128S_0.20.x_20201209.tar.gz");

            FileInputStream is = null;
            ByteArrayOutputStream bos = null;
            int chunkSize = 1 * 1024 * 1024; // 1M

            int seq = 1;
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
                        responseObserver.onNext(DownloadResponse.newBuilder()
                                .setName(filename)
                                .setChunk(Chunk.newBuilder()
                                        .setBlockSeq(seq++)
                                        .setContent(ByteString.copyFrom(b))
                                        .build())
                                .setDownloadStatus(DownloadStatus.newBuilder()
                                        .setCode(1) // 0--unknown, 1--ok, 2--failure
                                        .setMessage("send chunk ok")
                                        .build())
                                .build());
                    }
                    else
                    {
                        bos.write(b, 0, n);
                        responseObserver.onNext(DownloadResponse.newBuilder()
                                .setName(filename)
                                .setChunk(Chunk.newBuilder()
                                        .setBlockSeq(seq++)
                                        .setContent(ByteString.copyFrom(bos.toByteArray()))
                                        .build())
                                .setDownloadStatus(DownloadStatus.newBuilder()
                                        .setCode(1) // 0--unknown, 1--ok, 2--failure
                                        .setMessage("send chunk ok")
                                        .build())
                                .build());
                    }
                }

                responseObserver.onNext(DownloadResponse.newBuilder()
                        .setName(filename)
                        .setDownloadStatus(DownloadStatus.newBuilder()
                                .setCode(-1) // -1--No more data, 0--unknown, 1--ok, 2--failure
                                .setMessage("send all ok")
                                .build())
                        .build());
            }
            catch (Exception e)
            {
                DownloadServer.logger.warning("get error. " + e.getMessage());
                e.printStackTrace();
            }
            finally
            {
                responseObserver.onCompleted();
            }

        }
    }
}