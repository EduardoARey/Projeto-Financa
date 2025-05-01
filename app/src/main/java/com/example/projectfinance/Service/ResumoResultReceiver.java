package com.example.projectfinance.Service;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

public class ResumoResultReceiver extends ResultReceiver {
    private Receiver receiver;

    public ResumoResultReceiver(Handler handler) {
        super(handler);
    }

    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {
        if (receiver != null) {
            receiver.onReceiveResult(resultCode, resultData);
        }
    }

    public interface Receiver {
        void onReceiveResult(int resultCode, Bundle resultData);
    }
}
