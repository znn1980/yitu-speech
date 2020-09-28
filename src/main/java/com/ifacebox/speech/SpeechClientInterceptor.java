package com.ifacebox.speech;

import io.grpc.*;

public class SpeechClientInterceptor implements ClientInterceptor {
    private static final Metadata.Key<String> X_DEV_ID = Metadata.Key.of("x-dev-id", Metadata.ASCII_STRING_MARSHALLER);
    private static final Metadata.Key<String> X_REQUEST_SEND_TIMESTAMP = Metadata.Key.of("x-request-send-timestamp", Metadata.ASCII_STRING_MARSHALLER);
    private static final Metadata.Key<String> X_SIGNATURE = Metadata.Key.of("x-signature", Metadata.ASCII_STRING_MARSHALLER);
    private static final Metadata.Key<String> X_API_KEY = Metadata.Key.of("x-api-key", Metadata.ASCII_STRING_MARSHALLER);
    private AudioSpeechConfig audioSpeechConfig;

    public SpeechClientInterceptor(AudioSpeechConfig audioSpeechConfig) {
        this.audioSpeechConfig = audioSpeechConfig;
    }

    @Override
    public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(MethodDescriptor<ReqT, RespT> method, CallOptions callOptions, Channel next) {
        return new ForwardingClientCall.SimpleForwardingClientCall<ReqT, RespT>(next.newCall(method, callOptions)) {
            @Override
            public void start(Listener<RespT> responseListener, Metadata headers) {
                long ts = audioSpeechConfig.getRequestSendTimestamp();
                headers.put(X_DEV_ID, audioSpeechConfig.getDevId());
                headers.put(X_REQUEST_SEND_TIMESTAMP, String.valueOf(ts));
                headers.put(X_SIGNATURE, audioSpeechConfig.getSignature(ts));
                headers.put(X_API_KEY, audioSpeechConfig.getApiKey(ts));
                System.err.println("验证信息：" + headers.toString());
                super.start(new ForwardingClientCallListener.SimpleForwardingClientCallListener<RespT>(responseListener) {
                    @Override
                    public void onHeaders(Metadata headers) {
                        super.onHeaders(headers);
                    }
                }, headers);
            }
        };
    }
}
