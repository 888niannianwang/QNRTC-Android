package com.qiniu.droid.rtc.demo.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.os.ResultReceiver;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.qiniu.droid.rtc.demo.R;
import com.qiniu.droid.rtc.demo.event.TaskEvent;
import com.qiniu.droid.rtc.demo.model.TestField;
import com.qiniu.droid.rtc.demo.service.TaskScheduleService;
import com.qiniu.droid.rtc.demo.utils.Config;
import com.qiniu.droid.rtc.demo.utils.EventBusBase;
import com.qiniu.droid.rtc.demo.utils.PermissionChecker;
import com.qiniu.droid.rtc.demo.utils.ToastUtils;


import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;


public class UserConfigActivity extends AppCompatActivity {

    private static final String TAG = "UserConfigActivity";
    private static final String ACTION_SAVE_NICK_NAME = "com.qiniu.droid.rtc.demo.activity.UserConfigActivity.Save";
    private static final String ACTION_INPUT_NICK_NAME = "com.qiniu.droid.rtc.demo.activity.UserConfigActivity.InputUser";
    private InternelReceiver mReceiver;
    private EditText mUsernameEditText;
    private static Handler mHandler;
    private static final int MSG_START = 1;
    private List<TestField> testFields = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_config);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        mUsernameEditText = (EditText) findViewById(R.id.user_name_edit_text);
        registerInternelReceiver();
        mHandler = new Handler(getMainLooper(), mCallBack);
    }

    @Override
    protected void onResume() {
        super.onResume();
        EventBusBase.getInstance().register(this);
        Log.e("TAG", "EventBusBase register");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unRegisterInternelReceiver();
        EventBusBase.getInstance().unregister(this);
        Log.e("TAG", "EventBusBase unregister");
    }


    private Handler.Callback mCallBack = new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what){
                case MSG_START:
                    saveUserName();
            }
            return false;
        }
    };


    public void onClickNext(View view) {
        saveUserName();
    }

    @Override
    public void onBackPressed() {
        saveUserName();
    }


    private boolean isPermissionOK() {
        PermissionChecker checker = new PermissionChecker(this);
        boolean isPermissionOK = Build.VERSION.SDK_INT < Build.VERSION_CODES.M || checker.checkPermission();
        if (!isPermissionOK) {
            ToastUtils.l(this, "Some permissions is not approved !!!");
        }
        return isPermissionOK;
    }

    private void inputUserName(String value){
        mUsernameEditText.setText(value);
    }

    private void saveUserName() {
        final String userName = mUsernameEditText.getText().toString();
        if (userName == null || userName.isEmpty()) {
            ToastUtils.s(this, getString(R.string.null_user_name_toast));
            return;
        }
        if (!MainActivity.isUserNameOk(userName)) {
            ToastUtils.s(this, getString(R.string.wrong_user_name_toast));
            return;
        }
        if (!isPermissionOK()) {
            return;
        }

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(Config.USER_NAME, userName);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    private void registerInternelReceiver(){
        if (mReceiver == null){
            mReceiver = new InternelReceiver();
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(ACTION_SAVE_NICK_NAME);
            this.registerReceiver(mReceiver, intentFilter);
        }
    }
    private void unRegisterInternelReceiver(){
        if (mReceiver != null){
            this.unregisterReceiver(mReceiver);
            mReceiver = null;
        }
    }

    private class InternelReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
//            if (intent != null && !intent.getAction().equals("")) {
                String action = intent.getAction();
                if (action.equals(ACTION_SAVE_NICK_NAME)){
                    Message.obtain(mHandler,MSG_START).sendToTarget();
                    Toast.makeText(UserConfigActivity.this, "hi hi hi ！！！", Toast.LENGTH_LONG).show();
                }
//            }
        }
    }

    public void onEventMainThread(TaskEvent e){
        Log.e("TAG", "EventBusBase onEventMainThread, event name = " + e.getStep().getStepName());
        if (ACTION_INPUT_NICK_NAME.equals(e.getStep().getStepName())) {
            testFields = e.getStep().getTestFields();
            inputUserName(testFields.get(0).getFieldValue());
            Log.e("TAG", "input user name = " + testFields.get(0).getFieldValue());
            notifyStatus(true);
        }else if (ACTION_SAVE_NICK_NAME.equals(e.getStep().getStepName())){
            saveUserName();
            Log.e("TAG", "Click save button");
            notifyStatus(true);
        }
    }

    private void notifyStatus(boolean status){
        ResultReceiver receiver = getIntent().getParcelableExtra(TaskScheduleService.KEY_RECEIVER);
        Bundle resultData = new Bundle();
        resultData.putBoolean(TaskScheduleService.KEY_MESSAGE, status);
        receiver.send(TaskScheduleService.RESULT_OK, resultData);
    }
}
