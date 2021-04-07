package cn.my.app.grpc.upload;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.34.0)",
    comments = "Source: upload.proto")
public final class UploadServiceGrpc {

  private UploadServiceGrpc() {}

  public static final String SERVICE_NAME = "upload.UploadService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<cn.my.app.grpc.upload.UploadRequest,
      cn.my.app.grpc.upload.UploadStatus> getUploadMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Upload",
      requestType = cn.my.app.grpc.upload.UploadRequest.class,
      responseType = cn.my.app.grpc.upload.UploadStatus.class,
      methodType = io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
  public static io.grpc.MethodDescriptor<cn.my.app.grpc.upload.UploadRequest,
      cn.my.app.grpc.upload.UploadStatus> getUploadMethod() {
    io.grpc.MethodDescriptor<cn.my.app.grpc.upload.UploadRequest, cn.my.app.grpc.upload.UploadStatus> getUploadMethod;
    if ((getUploadMethod = UploadServiceGrpc.getUploadMethod) == null) {
      synchronized (UploadServiceGrpc.class) {
        if ((getUploadMethod = UploadServiceGrpc.getUploadMethod) == null) {
          UploadServiceGrpc.getUploadMethod = getUploadMethod =
              io.grpc.MethodDescriptor.<cn.my.app.grpc.upload.UploadRequest, cn.my.app.grpc.upload.UploadStatus>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Upload"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  cn.my.app.grpc.upload.UploadRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  cn.my.app.grpc.upload.UploadStatus.getDefaultInstance()))
              .setSchemaDescriptor(new UploadServiceMethodDescriptorSupplier("Upload"))
              .build();
        }
      }
    }
    return getUploadMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static UploadServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<UploadServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<UploadServiceStub>() {
        @java.lang.Override
        public UploadServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new UploadServiceStub(channel, callOptions);
        }
      };
    return UploadServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static UploadServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<UploadServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<UploadServiceBlockingStub>() {
        @java.lang.Override
        public UploadServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new UploadServiceBlockingStub(channel, callOptions);
        }
      };
    return UploadServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static UploadServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<UploadServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<UploadServiceFutureStub>() {
        @java.lang.Override
        public UploadServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new UploadServiceFutureStub(channel, callOptions);
        }
      };
    return UploadServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class UploadServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public io.grpc.stub.StreamObserver<cn.my.app.grpc.upload.UploadRequest> upload(
        io.grpc.stub.StreamObserver<cn.my.app.grpc.upload.UploadStatus> responseObserver) {
      return asyncUnimplementedStreamingCall(getUploadMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getUploadMethod(),
            asyncClientStreamingCall(
              new MethodHandlers<
                cn.my.app.grpc.upload.UploadRequest,
                cn.my.app.grpc.upload.UploadStatus>(
                  this, METHODID_UPLOAD)))
          .build();
    }
  }

  /**
   */
  public static final class UploadServiceStub extends io.grpc.stub.AbstractAsyncStub<UploadServiceStub> {
    private UploadServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected UploadServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new UploadServiceStub(channel, callOptions);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<cn.my.app.grpc.upload.UploadRequest> upload(
        io.grpc.stub.StreamObserver<cn.my.app.grpc.upload.UploadStatus> responseObserver) {
      return asyncClientStreamingCall(
          getChannel().newCall(getUploadMethod(), getCallOptions()), responseObserver);
    }
  }

  /**
   */
  public static final class UploadServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<UploadServiceBlockingStub> {
    private UploadServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected UploadServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new UploadServiceBlockingStub(channel, callOptions);
    }
  }

  /**
   */
  public static final class UploadServiceFutureStub extends io.grpc.stub.AbstractFutureStub<UploadServiceFutureStub> {
    private UploadServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected UploadServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new UploadServiceFutureStub(channel, callOptions);
    }
  }

  private static final int METHODID_UPLOAD = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final UploadServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(UploadServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_UPLOAD:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.upload(
              (io.grpc.stub.StreamObserver<cn.my.app.grpc.upload.UploadStatus>) responseObserver);
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class UploadServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    UploadServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return cn.my.app.grpc.upload.Upload.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("UploadService");
    }
  }

  private static final class UploadServiceFileDescriptorSupplier
      extends UploadServiceBaseDescriptorSupplier {
    UploadServiceFileDescriptorSupplier() {}
  }

  private static final class UploadServiceMethodDescriptorSupplier
      extends UploadServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    UploadServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (UploadServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new UploadServiceFileDescriptorSupplier())
              .addMethod(getUploadMethod())
              .build();
        }
      }
    }
    return result;
  }
}
