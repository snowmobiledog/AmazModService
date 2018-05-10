package com.edotassi.amazmodcompanionservice.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.edotassi.amazmodcompanionservice.R;
import com.edotassi.amazmodcompanionservice.R2;
import com.edotassi.amazmodcompanionservice.notifications.NotificationSpec;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NotificationActivity extends Activity {

    @BindView(R2.id.notification_title)
    TextView title;
    @BindView(R2.id.notification_text)
    TextView text;
    @BindView(R2.id.notification_icon)
    ImageView icon;

    private Handler handler;

    private NotificationSpec notificationSpec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |
                WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON);

        setContentView(R.layout.activity_notification);

        ButterKnife.bind(this);

        Intent intent = getIntent();

        notificationSpec = intent.getExtras().getParcelable(NotificationSpec.EXTRA);

        title.setText(notificationSpec.getTitle());
        text.setText(notificationSpec.getText());
        icon.setImageBitmap(notificationSpec.getIcon());

        if (notificationSpec.isDeviceLocked()) {
            handler = new Handler();

            handler.postDelayed(new Runnable() {
                public void run() {
                    finish();
                }
            }, notificationSpec.getTimeoutRelock());
        }

        if (notificationSpec.getVibration() > 0) {
            Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(notificationSpec.getVibration());
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        findViewById(R.id.notification_root_layout).dispatchTouchEvent(event);
        return false;
    }

    @OnClick(R2.id.activity_notification_button_close)
    public void clickClose() {
        finish();
    }

    @OnClick(R2.id.activity_notification_button_reply)
    public void clickReply() {
        Toast.makeText(this, "not_implented", Toast.LENGTH_SHORT).show();
    }
}
