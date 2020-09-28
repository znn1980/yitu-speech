package com.ifacebox.speech;

import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.TargetDataLine;

public class AudioSpeechTransfer implements Runnable {
    private static final int AUDIO_SPEECH_TIME = 1000;
    private volatile boolean isRunning = false;
    private TargetDataLine targetDataLine = null;
    private Thread transferThread = null;
    private AudioSpeechServer audioSpeechServer;
    private AudioFormat audioFormat;

    public AudioSpeechTransfer(AudioSpeechConfig audioSpeechConfig, AudioDataCallback audioDataCallback) {
        this.audioSpeechServer = new AudioSpeechServer(audioSpeechConfig, audioDataCallback);
        this.audioFormat = new AudioFormat(audioSpeechConfig.getSampleRate(), 16, 1, true, false);
    }

    public void start() {
        System.out.println("开始录音...");
        isRunning = true;
        transferThread = new Thread(this);
        transferThread.start();
    }

    public void stop() {
        System.out.println("停止录音...");
        isRunning = false;
        audioFormat = null;
        if (targetDataLine != null) {
            targetDataLine.close();
            targetDataLine = null;
        }
        if (transferThread != null) {
            transferThread.interrupt();
            transferThread = null;
        }
        if (audioSpeechServer != null) {
            audioSpeechServer.stop();
            audioSpeechServer = null;
        }
    }

    @Override
    public void run() {
        byte[] bytes = new byte[1024];
        ByteBuffer buffer = new ByteBuffer();
        audioSpeechServer.start();
        try {
            targetDataLine = (TargetDataLine) AudioSystem.getLine(new DataLine.Info(TargetDataLine.class, audioFormat));
            targetDataLine.open(audioFormat);
            targetDataLine.start();
            long ms = System.currentTimeMillis();
            while (isRunning) {
                int len = new AudioInputStream(targetDataLine).read(bytes);
                if (Math.abs(System.currentTimeMillis() - ms) < AUDIO_SPEECH_TIME) {
                    byte[] data = new byte[len];
                    System.arraycopy(bytes, 0, data, 0, len);
                    buffer.put(data);
                } else {
                    byte[] data = buffer.array();
                    System.out.println("间隔[" + AUDIO_SPEECH_TIME + "]毫秒发送数据：" + data.length);
                    audioSpeechServer.setAudioData(data, 0, data.length);
                    buffer.clear();
                    ms = System.currentTimeMillis();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            stop();
        }
    }

    static class ByteBuffer {
        private List<byte[]> buffer;
        private int length;

        public ByteBuffer() {
            buffer = new ArrayList<byte[]>();
            length = 0;
        }

        public void put(byte[] bytes) {
            length = length + bytes.length;
            buffer.add(bytes);
        }

        public void clear() {
            length = 0;
            buffer.clear();
        }

        public byte[] array() {
            byte[] bytes = new byte[length];
            for (int i = 0; i < buffer.size(); i++) {
                byte[] data = buffer.get(i);
                System.arraycopy(data, 0, bytes, i * data.length, data.length);
            }
            return bytes;
        }
    }

}
