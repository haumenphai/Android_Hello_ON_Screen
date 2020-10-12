package com.example.dohaumen.util;

import android.os.CountDownTimer;

public class TimeDelayUlti {


    private static TimeDelayUlti INSTANCE;
    private static long milisecond;

    public static TimeDelayUlti setTime(long milisecondDelay) {
        if (INSTANCE == null) {
            INSTANCE = new TimeDelayUlti();
        }
        milisecond = milisecondDelay;
        return INSTANCE;
    }


    public void runAfterMilisecond(OnRun onRun) {
        new CountDownTimer(milisecond, milisecond) {

            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                onRun.run();
            }
        }.start();
    }
}
