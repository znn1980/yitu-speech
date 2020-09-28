package com.ifacebox.speech;

public class AudioSpeechTest {

    public static void main(String[] args) throws Exception {
        // TODO Auto-generated method stub
        new AudioSpeechTransfer(new AudioSpeechConfig(), new AudioDataCallback() {
            @Override
            public void onText(boolean isFinal, String text) {
                System.out.println(text);
            }

            @Override
            public void onError(Throwable t) {
                System.err.println(t);
            }
        }).start();
    }

}
