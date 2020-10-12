package com.example.dohaumen;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyReceiver extends BroadcastReceiver {
    public interface OnScreenOn {
        void onScereenOn();
    }

    private OnScreenOn onScreenOn;

    public void setOnScreenOn(OnScreenOn onScreenOn) {
        this.onScreenOn = onScreenOn;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
            onScreenOn.onScereenOn();
        }
    }
}
