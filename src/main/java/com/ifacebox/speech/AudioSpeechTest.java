package com.ifacebox.speech;

public class AudioSpeechTest {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		AudioSpeechServer server = new AudioSpeechServer(new AudioSpeechConfig(), new AudioDataCallback() {
			@Override
			public void setText(boolean isFinal, String text) {
				System.out.println(text);
			}
		});
		server.start();
		new AudioSpeechTransfer(server).start();
	}

}
