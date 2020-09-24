package com.ifacebox.speech;

public class AudioSpeechConfig {
	private String ip;
	private int port;
	private String devId;
	private String devKey;
	private int sampleRate;

	public AudioSpeechConfig() {
		this.ip = "stream-asr-prod.yitutech.com";
		this.port = 50051;
		this.devId = "21501";
		this.devKey = "NGE3ZTQ3MDBjNmU5NDhhZTgyMDJmMjNjOTI4NzhlY2U=";
		this.sampleRate = 16000;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getDevId() {
		return devId;
	}

	public void setDevId(String devId) {
		this.devId = devId;
	}

	public String getDevKey() {
		return devKey;
	}

	public void setDevKey(String devKey) {
		this.devKey = devKey;
	}

	public int getSampleRate() {
		return sampleRate;
	}

	public void setSampleRate(int sampleRate) {
		this.sampleRate = sampleRate;
	}

	public long getRequestSendTimestamp() {
		return System.currentTimeMillis() / 1000L;
	}

	public String getSignature(long ts) {
		return HmacUtils.hmacSha256Hex(this.getDevKey(), this.getDevId() + ts);
	}

	public String getApiKey(long ts) {
		return this.getDevId() + "," + ts + "," + this.getSignature(ts);
	}

}
