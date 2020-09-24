package com.ifacebox.speech;

import java.io.IOException;
import java.io.InputStream;

import com.google.protobuf.ByteString;
import com.yitutech.speech.AudioConfig;
import com.yitutech.speech.SpeechConfig;
import com.yitutech.speech.SpeechRecognitionGrpc;
import com.yitutech.speech.StreamingSpeechConfig;
import com.yitutech.speech.StreamingSpeechRequest;
import com.yitutech.speech.StreamingSpeechResponse;
import com.yitutech.speech.StreamingSpeechResult;
import com.yitutech.speech.StreamingSpeechStatus;
import com.yitutech.speech.StreamingTranscription;
import com.yitutech.speech.SpeechRecognitionGrpc.SpeechRecognitionStub;
import io.grpc.ForwardingClientCall.SimpleForwardingClientCall;
import io.grpc.ForwardingClientCallListener.SimpleForwardingClientCallListener;
import io.grpc.CallOptions;
import io.grpc.Channel;
import io.grpc.ClientCall;
import io.grpc.ClientInterceptor;
import io.grpc.ClientInterceptors;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Metadata;
import io.grpc.MethodDescriptor;
import io.grpc.stub.StreamObserver;

public class AudioSpeechServer {
	private static final Metadata.Key<String> X_DEV_ID = Metadata.Key.of("x-dev-id", Metadata.ASCII_STRING_MARSHALLER);
	private static final Metadata.Key<String> X_REQUEST_SEND_TIMESTAMP = Metadata.Key.of("x-request-send-timestamp", Metadata.ASCII_STRING_MARSHALLER);
	private static final Metadata.Key<String> X_SIGNATURE = Metadata.Key.of("x-signature", Metadata.ASCII_STRING_MARSHALLER);
	private static final Metadata.Key<String> X_API_KEY = Metadata.Key.of("x-api-key", Metadata.ASCII_STRING_MARSHALLER);
	private ManagedChannel channel;
	private SpeechRecognitionStub stub;
	private StreamObserver<StreamingSpeechRequest> requestObserver;
	private AudioDataCallback callback;
	private AudioSpeechConfig config;
	private volatile boolean isRunning = false;

	public AudioSpeechServer(AudioSpeechConfig config, AudioDataCallback callback) {
		this.config = config;
		this.callback = callback;
	}

	public boolean isRunning() {
		return isRunning;
	}

	public void setAudioSpeechConfig(AudioSpeechConfig config) {
		this.config = config;
	}

	public AudioSpeechConfig getAudioSpeechConfig() {
		return config;
	}

	public void start() {
		isRunning = true;
		channel = ManagedChannelBuilder.forAddress(config.getIp(), config.getPort()).usePlaintext().build();
		stub = SpeechRecognitionGrpc.newStub(ClientInterceptors.intercept(channel, new ClientInterceptor() {
			@Override
			public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(MethodDescriptor<ReqT, RespT> method, CallOptions callOptions, Channel next) {
				return new SimpleForwardingClientCall<ReqT, RespT>(next.newCall(method, callOptions)) {
					@Override
					public void start(Listener<RespT> responseListener, Metadata headers) {
						long ts = config.getRequestSendTimestamp();
						headers.put(X_DEV_ID, config.getDevId());
						headers.put(X_REQUEST_SEND_TIMESTAMP, String.valueOf(ts));
						headers.put(X_SIGNATURE, config.getSignature(ts));
						headers.put(X_API_KEY, config.getApiKey(ts));
						System.err.println("验证信息：" + headers.toString());
						super.start(new SimpleForwardingClientCallListener<RespT>(responseListener) {
							@Override
							public void onHeaders(Metadata headers) {
								super.onHeaders(headers);
							}
						}, headers);
					}
				};
			}
		}));
		requestObserver = stub.recognizeStream(new StreamObserver<StreamingSpeechResponse>() {
			@Override
			public void onNext(StreamingSpeechResponse response) {
				StreamingSpeechStatus status = response.getStatus();
				System.err.println("当前音频处理进行到的时间点（音频开始时间为0）：" + status.getProcessedTimestamp());
				StreamingSpeechResult result = response.getResult();
				System.err.println("此识别结果是否为最终结果：" + result.getIsFinal());
				StreamingTranscription transcription = result.getBestTranscription();
				System.err.println("转写结果：" + transcription.getTranscribedText());
				if (callback != null && !transcription.getTranscribedText().isEmpty()) {
					callback.setText(result.getIsFinal(), transcription.getTranscribedText());
				}
			}

			@Override
			public void onError(Throwable t) {
				System.err.println("异常：" + t);
			}

			@Override
			public void onCompleted() {
				System.err.println("完成：");
			}
		});
		requestObserver.onNext(StreamingSpeechRequest.newBuilder().setStreamingSpeechConfig(getConfig()).build());
	}

	public void stop() {
		isRunning = false;
		channel.shutdown();
	}

	public void setAudioData(byte[] bytes, int offset, int size) {
		requestObserver.onNext(StreamingSpeechRequest.newBuilder().setAudioData(ByteString.copyFrom(bytes, offset, size)).build());
	}

	public void setAudioData(InputStream is) throws IOException {
		requestObserver.onNext(StreamingSpeechRequest.newBuilder().setAudioData(ByteString.readFrom(is)).build());
	}

	public StreamingSpeechConfig getConfig() {
		SpeechConfig.Builder speechConfig = SpeechConfig.newBuilder();
		speechConfig.setLang(SpeechConfig.Language.MANDARIN);
		speechConfig.setScene(SpeechConfig.Scene.GENERALSCENE);
		speechConfig.setRecognizeType(SpeechConfig.RecognizeType.ALL);
		AudioConfig.Builder audioConfig = AudioConfig.newBuilder();
		audioConfig.setAue(AudioConfig.AudioEncoding.PCM);
		audioConfig.setSampleRate(config.getSampleRate());
		StreamingSpeechConfig.Builder streamingSpeechConfig = StreamingSpeechConfig.newBuilder();
		streamingSpeechConfig.setSpeechConfig(speechConfig.build());
		streamingSpeechConfig.setAudioConfig(audioConfig.build());
		return streamingSpeechConfig.build();
	}
}
