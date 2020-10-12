package com.example.dohaumen;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static final int SYSTEM_ALERT_WINDOW_PERMISSION = 2084;
    Button btnOn, btnOFF;
    static Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (intent == null) {
            intent = new Intent(this, MyService.class);
        }
        btnOFF = findViewById(R.id.btnOFF);
        btnOn = findViewById(R.id.btnON);
        
        btnOn.setOnClickListener(v -> {
           startService();
        });

        btnOFF.setOnClickListener(v -> {
            stopService(intent);
        });

        startService();

    }

    private void startService() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            startService(intent);
        } else if (Settings.canDrawOverlays(this)) {
            startService(intent);
        } else {
            Toast.makeText(this, "Bạn cần cho phép quyền vẽ trên ứng dụng khác", Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "Bạn cần cho phép quyền vẽ trên ứng dụng khác", Toast.LENGTH_SHORT).show();
            new AlertDialog.Builder(this)
                    .setTitle("Cho phép quyền")
                    .setMessage("Bạn cần cho phép quyền hiển thị trên ứng dụng khác")
                    .setNegativeButton("OK", (dialog, which) -> {
                        askPermission_SYSTEM_ALERT_WINDOW();
                    }).show();
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void askPermission_SYSTEM_ALERT_WINDOW() {
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:" +this.getPackageName()));
        startActivityForResult(intent, SYSTEM_ALERT_WINDOW_PERMISSION);
    }


}