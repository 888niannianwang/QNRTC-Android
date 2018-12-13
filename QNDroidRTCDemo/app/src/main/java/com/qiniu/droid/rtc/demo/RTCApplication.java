package com.qiniu.droid.rtc.demo;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

import com.qiniu.droid.rtc.QNLogLevel;
import com.qiniu.droid.rtc.QNRTCEnv;
import com.qiniu.droid.rtc.demo.service.TaskScheduleService;

public class RTCApplication extends Application {
    private Intent intent;
    @Override
    public void onCreate() {
        super.onCreate();
        QNRTCEnv.setLogLevel(QNLogLevel.INFO);
        /**
         * init must be called before any other func
         */
        QNRTCEnv.init(getApplicationContext());
        QNRTCEnv.setLogFileEnabled(true);

        Log.d("RTCApplication", "Prepare TaskScheduleService Start");
        intent = new Intent(this, TaskScheduleService.class);
        startService(intent);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        stopService(intent);
    }
}
