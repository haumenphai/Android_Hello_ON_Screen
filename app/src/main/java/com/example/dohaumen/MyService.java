package com.example.dohaumen;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import androidx.core.app.NotificationCompat;

import com.example.dohaumen.util.OnRun;
import com.example.dohaumen.util.TimeDelayUlti;

import java.util.Random;

public class MyService extends Service {
    MyReceiver myReceiver = new MyReceiver();
    View floatingView;
    WindowManager windowManager;
    ImageView img;
    int[] imgArr;
    Random random = new Random();

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_SCREEN_ON);
        intentFilter.addAction(Intent.ACTION_SCREEN_OFF);
        registerReceiver(myReceiver, intentFilter);

        floatingView = LayoutInflater.from(this).inflate(R.layout.floating_view, null);
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        windowManager.addView(floatingView, getParam());

        img = floatingView.findViewById(R.id.img);
        imgArr = new int[]{
                R.drawable.hoa_tuyet_do_1, R.drawable.hoa_tuyet_do_2, R.drawable.hoa_tuyet_do_3,
                R.drawable.hoa_tuyet_do_4, R.drawable.hoa_tuyet_do_6,
                R.drawable.hoa_tuyet_do_7, R.drawable.hoa_tuyet_do_8, R.drawable.hoa_tuyet_do_9,

                R.drawable.hoa_tuyet_xanh_1, R.drawable.hoa_tuyet_xanh_2, R.drawable.hoa_tuyet_xanh_3,
                R.drawable.hoa_tuyet_xanh_4, R.drawable.hoa_tuyet_xanh_6,
                R.drawable.hoa_tuyet_xanh_7, R.drawable.hoa_tuyet_xanh_8, R.drawable.hoa_tuyet_xanh_9,
        };
    }


    WindowManager.LayoutParams getParam() {
        int LAYOUT_FLAG;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_PHONE;
        }
        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                LAYOUT_FLAG,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);
        return params;
    }




    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Notification notification = new NotificationCompat.Builder(this, "chanel_ididid")
                .setContentTitle("My app so fun")
                .setContentText("dohaumen")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .build();
        startForeground(123, notification);

        myReceiver.setOnScreenOn(() -> {
            floatingView.setVisibility(View.VISIBLE);
            int index = random.nextInt(imgArr.length);
            img.setImageResource(imgArr[index]);
            int todegress = random.nextInt(1000-180) + 180;

            RotateAnimation rotateAnimation = new RotateAnimation(0,todegress,img.getPivotX(),img.getPivotY());
            rotateAnimation.setDuration(200);


            Animation anim1 = AnimationUtils.loadAnimation(MyApplication.getContext(), R.anim.anim_chinh_xac);

            AnimationSet animationSet = new AnimationSet(false);
            animationSet.addAnimation(rotateAnimation);
            animationSet.addAnimation(anim1);
            animationSet.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    floatingView.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });


            img.startAnimation(animationSet);
        });


        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(myReceiver);
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    "chanel_ididid",
                    "Foreground Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }

    }

}
