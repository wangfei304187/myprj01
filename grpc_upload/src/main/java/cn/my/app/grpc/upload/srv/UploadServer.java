package cn.my.app.grpc.upload.srv;

import static java.util.concurrent.TimeUnit.NANOSECONDS;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import cn.my.app.grpc.upload.UploadRequest;
import cn.my.app.grpc.upload.UploadServiceGrpc;
import cn.my.app.grpc.upload.UploadStatus;
import cn.my.app.grpc.util.ZipUtils;

/**
 * A sample gRPC server that serve the RouteGuide (see route_guide.proto) service.
 */
public class UploadServer
{
    private static final Logger logger = Logger.getLogger(UploadServer.class.getName());

    private static SimpleDateFormat DateFmt = new SimpleDateFormat("yyyyMMdd_HHmmss.S");

    private final int port;
    private final Server server;

    public UploadServer(int port) throws IOException
    {
        this.port = port;
        ServerBuilder<?> serverBuilder = ServerBuilder.forPort(port);
        server = serverBuilder.addService(new UploadService())
                .build();
    }

    /** Start serving requests. */
    public void start() throws IOException
    {
        server.start();
        UploadServer.logger.info("Server started, listening on " + port);
        Runtime.getRuntime().addShutdownHook(new Thread()
        {
            @Override
            public void run()
            {
                // Use stderr here since the logger may have been reset by its JVM shutdown hook.
                System.err.println("*** shutting down gRPC server since JVM is shutting down");
                try
                {
                    UploadServer.this.stop();
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
        UploadServer server = new UploadServer(8980);
        server.start();
        server.blockUntilShutdown();
    }

    private static class UploadService extends UploadServiceGrpc.UploadServiceImplBase
    {

        @Override
        public StreamObserver<UploadRequest> upload(StreamObserver<UploadStatus> responseObserver)
        {
            return new StreamObserver<UploadRequest>()
            {
                final long startTime = System.nanoTime();
                int count = 1;
                // String filename = UUID.randomUUID().toString();
                String timestampStr = "_" + UploadServer.DateFmt.format(new Date(System.currentTimeMillis()));
                String filename = null;

                @Override
                public void onNext(UploadRequest uploadRequest)
                {
                    filename = uploadRequest.getName() + timestampStr;
                    UploadServer.logger.info("process chunk-" + count + ", filename=" + filename);

                    File file = new File(filename);
                    try (BufferedOutputStream bos = new BufferedOutputStream(
                            new FileOutputStream(file, true)))
                    {
                        byte[] bytes = uploadRequest.getChunk().getContent().toByteArray();
                        bos.write(bytes);
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                    UploadServer.logger.info("chunk-" + String.valueOf(count) + " finish");
                    count++;
                }

                @Override
                public void onError(Throwable t)
                {
                    UploadServer.logger.log(Level.WARNING, "upload cancelled, " + t.getMessage());
                }

                @Override
                public void onCompleted()
                {
                    long seconds = NANOSECONDS.toSeconds(System.nanoTime() - startTime);
                    responseObserver.onNext(UploadStatus.newBuilder()
                            .setCode(1)
                            .setMessage("upload ok.")
                            .build());
                    UploadServer.logger.info("[server] onCompleted, elapse time: " + seconds + "s");

                    // --> unzip
                    try
                    {
                        ZipUtils.decompress(new File(filename), "/data/dicomImage");
                    }
                    catch (IOException e)
                    {
                    }
                    // <--

                    responseObserver.onCompleted();
                }
            };
        }
    } // end UploadService
}