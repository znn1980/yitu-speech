package com.yitutech.speech;

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
 * <pre>
 * 音频流识别服务。
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.32.1)",
    comments = "Source: YituAudioSpeech.proto")
public final class SpeechRecognitionGrpc {

  private SpeechRecognitionGrpc() {}

  public static final String SERVICE_NAME = "SpeechRecognition";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<StreamingSpeechRequest,
      StreamingSpeechResponse> getRecognizeStreamMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "RecognizeStream",
      requestType = StreamingSpeechRequest.class,
      responseType = StreamingSpeechResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
  public static io.grpc.MethodDescriptor<StreamingSpeechRequest,
      StreamingSpeechResponse> getRecognizeStreamMethod() {
    io.grpc.MethodDescriptor<StreamingSpeechRequest, StreamingSpeechResponse> getRecognizeStreamMethod;
    if ((getRecognizeStreamMethod = SpeechRecognitionGrpc.getRecognizeStreamMethod) == null) {
      synchronized (SpeechRecognitionGrpc.class) {
        if ((getRecognizeStreamMethod = SpeechRecognitionGrpc.getRecognizeStreamMethod) == null) {
          SpeechRecognitionGrpc.getRecognizeStreamMethod = getRecognizeStreamMethod =
              io.grpc.MethodDescriptor.<StreamingSpeechRequest, StreamingSpeechResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "RecognizeStream"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  StreamingSpeechRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  StreamingSpeechResponse.getDefaultInstance()))
              .setSchemaDescriptor(new SpeechRecognitionMethodDescriptorSupplier("RecognizeStream"))
              .build();
        }
      }
    }
    return getRecognizeStreamMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static SpeechRecognitionStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<SpeechRecognitionStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<SpeechRecognitionStub>() {
        @Override
        public SpeechRecognitionStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new SpeechRecognitionStub(channel, callOptions);
        }
      };
    return SpeechRecognitionStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static SpeechRecognitionBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<SpeechRecognitionBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<SpeechRecognitionBlockingStub>() {
        @Override
        public SpeechRecognitionBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new SpeechRecognitionBlockingStub(channel, callOptions);
        }
      };
    return SpeechRecognitionBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static SpeechRecognitionFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<SpeechRecognitionFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<SpeechRecognitionFutureStub>() {
        @Override
        public SpeechRecognitionFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new SpeechRecognitionFutureStub(channel, callOptions);
        }
      };
    return SpeechRecognitionFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * 音频流识别服务。
   * </pre>
   */
  public static abstract class SpeechRecognitionImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * 传入metadata "x-api-key"作为验证。
     * </pre>
     */
    public io.grpc.stub.StreamObserver<StreamingSpeechRequest> recognizeStream(
        io.grpc.stub.StreamObserver<StreamingSpeechResponse> responseObserver) {
      return asyncUnimplementedStreamingCall(getRecognizeStreamMethod(), responseObserver);
    }

    @Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getRecognizeStreamMethod(),
            asyncBidiStreamingCall(
              new MethodHandlers<
                StreamingSpeechRequest,
                StreamingSpeechResponse>(
                  this, METHODID_RECOGNIZE_STREAM)))
          .build();
    }
  }

  /**
   * <pre>
   * 音频流识别服务。
   * </pre>
   */
  public static final class SpeechRecognitionStub extends io.grpc.stub.AbstractAsyncStub<SpeechRecognitionStub> {
    private SpeechRecognitionStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected SpeechRecognitionStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new SpeechRecognitionStub(channel, callOptions);
    }

    /**
     * <pre>
     * 传入metadata "x-api-key"作为验证。
     * </pre>
     */
    public io.grpc.stub.StreamObserver<StreamingSpeechRequest> recognizeStream(
        io.grpc.stub.StreamObserver<StreamingSpeechResponse> responseObserver) {
      return asyncBidiStreamingCall(
          getChannel().newCall(getRecognizeStreamMethod(), getCallOptions()), responseObserver);
    }
  }

  /**
   * <pre>
   * 音频流识别服务。
   * </pre>
   */
  public static final class SpeechRecognitionBlockingStub extends io.grpc.stub.AbstractBlockingStub<SpeechRecognitionBlockingStub> {
    private SpeechRecognitionBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected SpeechRecognitionBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new SpeechRecognitionBlockingStub(channel, callOptions);
    }
  }

  /**
   * <pre>
   * 音频流识别服务。
   * </pre>
   */
  public static final class SpeechRecognitionFutureStub extends io.grpc.stub.AbstractFutureStub<SpeechRecognitionFutureStub> {
    private SpeechRecognitionFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected SpeechRecognitionFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new SpeechRecognitionFutureStub(channel, callOptions);
    }
  }

  private static final int METHODID_RECOGNIZE_STREAM = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final SpeechRecognitionImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(SpeechRecognitionImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }

    @Override
    @SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_RECOGNIZE_STREAM:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.recognizeStream(
              (io.grpc.stub.StreamObserver<StreamingSpeechResponse>) responseObserver);
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class SpeechRecognitionBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    SpeechRecognitionBaseDescriptorSupplier() {}

    @Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return StreamingProtos.getDescriptor();
    }

    @Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("SpeechRecognition");
    }
  }

  private static final class SpeechRecognitionFileDescriptorSupplier
      extends SpeechRecognitionBaseDescriptorSupplier {
    SpeechRecognitionFileDescriptorSupplier() {}
  }

  private static final class SpeechRecognitionMethodDescriptorSupplier
      extends SpeechRecognitionBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    SpeechRecognitionMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (SpeechRecognitionGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new SpeechRecognitionFileDescriptorSupplier())
              .addMethod(getRecognizeStreamMethod())
              .build();
        }
      }
    }
    return result;
  }
}
