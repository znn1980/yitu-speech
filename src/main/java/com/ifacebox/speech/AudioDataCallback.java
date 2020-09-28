package com.ifacebox.speech;

public interface AudioDataCallback {
    void onText(boolean isFinal, String text);

    void onError(Throwable t);
}
