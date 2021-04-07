package cn.my.app.grpc.download;

import io.grpc.Channel;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import cn.my.app.grpc.download.DownloadServiceGrpc.DownloadServiceBlockingStub;
import cn.my.app.grpc.download.DownloadServiceGrpc.DownloadServiceStub;

/**
 * Sample client code that makes gRPC calls to the server.
 */
public class DownloadClient
{
    private static final Logger logger = Logger.getLogger(DownloadClient.class.getName());

    private final DownloadServiceBlockingStub blockingStub;
    private final DownloadServiceStub asyncStub;

    public DownloadClient(Channel channel)
    {
        blockingStub = DownloadServiceGrpc.newBlockingStub(channel);
        asyncStub = DownloadServiceGrpc.newStub(channel);
    }

    /**
     * Blocking server-streaming example. Calls download with a file name. Prints each response as it arrives.
     */
    public void download(String filename)
    {
        long lo1 = System.currentTimeMillis();
        DownloadRequest req = DownloadRequest.newBuilder().setName(filename).build();
        try
        {
            Iterator<DownloadResponse> iter = blockingStub.download(req);

            // save into download/...
            File downloadDir = new File("download" + File.separator + String.valueOf(lo1));
            downloadDir.mkdirs();
            File downloadFile = new File(downloadDir, filename);

            try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(downloadFile, true)))
            {
                for (int i = 1; iter.hasNext(); i++)
                {
                    DownloadResponse resp = iter.next();
                    if (resp.getDownloadStatus().getCode() != -1)
                    {
                        DownloadClient.logger.log(Level.INFO, "Result #" + i + ": {0},  BlockSeq: {1}, {2}, {3}",
                                new Object[] {
                                        resp.getName(),
                                        resp.getChunk().getBlockSeq(),
                                        resp.getDownloadStatus().getCode(),
                                        resp.getDownloadStatus().getMessage()
                                });
                        byte[] bytes = resp.getChunk().getContent().toByteArray();
                        bos.write(bytes);
                    }
                    else
                    { // No more data
                        DownloadClient.logger.log(Level.INFO, "Result #" + i + ": {0},  BlockSeq: {1}, {2}, {3}",
                                new Object[] {
                                        resp.getName(),
                                        resp.getChunk().getBlockSeq(),
                                        resp.getDownloadStatus().getCode(),
                                        resp.getDownloadStatus().getMessage()
                                });
                    }
                }
            }
            catch (Exception e)
            {
                DownloadClient.logger.log(Level.WARNING, "get error. " + e.getMessage());
            }
        }
        catch (StatusRuntimeException e)
        {
            DownloadClient.logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
        }
        System.out.println((System.currentTimeMillis() - lo1) / 1000f + "s");
    }

    public void downloadAsync(String filename) throws InterruptedException
    {
        System.out.println("BEGIN downloadAsync");

        final CountDownLatch finishLatch = new CountDownLatch(1);

        long lo1 = System.currentTimeMillis();
        DownloadRequest req = DownloadRequest.newBuilder().setName(filename).build();

        // save into download/...
        File downloadDir = new File("downloadAsync" + File.separator + String.valueOf(lo1));
        downloadDir.mkdirs();
        File downloadFile = new File(downloadDir, filename);

        asyncStub.download(req, new StreamObserver<DownloadResponse>()
        {
            @Override
            public void onNext(DownloadResponse resp)
            {
                try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(downloadFile, true)))
                {
                    if (resp.getDownloadStatus().getCode() != -1)
                    {
                        DownloadClient.logger.log(Level.INFO, "Result #" + ": {0},  BlockSeq: {1}, {2}, {3}",
                                new Object[] {
                                        resp.getName(),
                                        resp.getChunk().getBlockSeq(),
                                        resp.getDownloadStatus().getCode(),
                                        resp.getDownloadStatus().getMessage()
                                });
                        byte[] bytes = resp.getChunk().getContent().toByteArray();
                        bos.write(bytes);
                    }
                    else
                    { // No more data
                        DownloadClient.logger.log(Level.INFO, "Result #" + ": {0},  BlockSeq: {1}, {2}, {3}",
                                new Object[] {
                                        resp.getName(),
                                        resp.getChunk().getBlockSeq(),
                                        resp.getDownloadStatus().getCode(),
                                        resp.getDownloadStatus().getMessage()
                                });
                    }
                }
                catch (Exception e)
                {
                    DownloadClient.logger.log(Level.WARNING, "get error. " + e.getMessage());
                    finishLatch.countDown();
                }
            }

            @Override
            public void onError(Throwable t)
            {
                DownloadClient.logger.log(Level.WARNING, "downloadAsync, get error. " + t.getMessage());
                finishLatch.countDown();
            }

            @Override
            public void onCompleted()
            {
                DownloadClient.logger.log(Level.INFO, "downloadAsync, completed.");
                System.out.println((System.currentTimeMillis() - lo1) / 1000f + "s");
                finishLatch.countDown();
            }
        });

        System.out.println("download request...");
        if (!finishLatch.await(1, TimeUnit.MINUTES))
        {
            DownloadClient.logger.log(Level.INFO, "downloadAsync can not finish within 1 minutes");
        }
        System.out.println("END downloadAsync");
    }

    /** Issues several different requests and then exits. */
    public static void main(String[] args) throws InterruptedException
    {
        String target = "localhost:8980";
        if (args.length > 0)
        {
            if ("--help".equals(args[0]))
            {
                System.err.println("Usage: [target]");
                System.err.println("");
                System.err.println("  target  The server to connect to. Defaults to " + target);
                System.exit(1);
            }
            target = args[0];
        }

        CountDownLatch latch = new CountDownLatch(2);
        ManagedChannel channel = ManagedChannelBuilder.forTarget(target).usePlaintext().build();
        try
        {
            DownloadClient client = new DownloadClient(channel);
            // TestCase1
            // client.download("OC_64R_128S_0.20.x_20201209.tar.gz");

            // TestCase2
            // ExecutorService pool = Executors.newCachedThreadPool();
            // for (int i = 0; i < 2; i++)
            // {
            // pool.execute(() -> {
            // client.download("OC_64R_128S_0.20.x_20201209.tar.gz");
            // latch.countDown();
            // });
            // }
            // System.out.println("Started threads.");
            // if (!latch.await(1, TimeUnit.MINUTES))
            // {
            // DownloadClient.logger.log(Level.INFO, "download can not finish within 1 minutes");
            // }
            // pool.shutdown();

            // TestCase3
            client.downloadAsync("OC_64R_128S_0.20.x_20201209.tar.gz");
            System.out.println("--Finished--");
        }
        finally
        {
            channel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
        }
        System.out.println("--Finished Main--");
    }

}