package com.qiniu.droid.rtc.demo.service;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.ResultReceiver;
import android.util.Log;

import com.qiniu.droid.rtc.demo.activity.MainActivity;
import com.qiniu.droid.rtc.demo.activity.RoomActivity;
import com.qiniu.droid.rtc.demo.activity.SettingActivity;
import com.qiniu.droid.rtc.demo.activity.UserConfigActivity;
import com.qiniu.droid.rtc.demo.activity.WelcomeActivity;
import com.qiniu.droid.rtc.demo.event.TaskEvent;
import com.qiniu.droid.rtc.demo.model.TestCase;
import com.qiniu.droid.rtc.demo.model.TestCases;
import com.qiniu.droid.rtc.demo.model.TestStep;
import com.qiniu.droid.rtc.demo.utils.EventBusBase;
import com.qiniu.droid.rtc.demo.utils.GetTestCasesTask;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import de.greenrobot.event.EventBus;

import static com.qiniu.droid.rtc.demo.utils.GetTestCasesTask.clearDataAsync;
import static com.qiniu.droid.rtc.demo.utils.GetTestCasesTask.testCases;

public class TaskScheduleService extends Service {

    private ExecutorService executor;
    private TestCases cases;
    private GetTestCasesTask.GetTestCasesThread taskT;
    private List<TestCase> caseList = new ArrayList<>();
    public static final int RESULT_OK = -1;
    public static final String KEY_RECEIVER = "KEY_RECEIVER";
    public static final String KEY_MESSAGE = "KEY_MESSAGE";
    private static boolean stepCompletedTag;
    private static boolean GetTaskFlag = false;
    private static boolean ScheduleTaskFlag = false;
    private Thread GetTaskThread;
    private Thread ScheduleTaskThread;


    @Override
    public void onCreate() {
        super.onCreate();
        executor = Executors.newFixedThreadPool(1);
        taskT = new GetTestCasesTask.GetTestCasesThread();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("TaskScheduleService", "TaskScheduleService Start");
        GetTaskThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
//                    while (!GetTaskFlag && !Thread.currentThread().isInterrupted()) {
                    while (true) {
                        if (!GetTaskFlag){
                            execute();
                            Thread.sleep(10 * 1000);
                            if (testCases.getCasesId() != 0){
                                Log.d("TAG", "TaskScheduleService , size = " + testCases.getCases().size());
                                GetTaskFlag = true;
                                ScheduleTaskFlag = true;
                                Log.d("TAG", "GetTaskThread , set ScheduleTaskFlag = " + ScheduleTaskFlag);
                            }
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "GetTaskThread");

        ScheduleTaskThread = new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d("TAG", "ScheduleTaskThread , Waiting for execute TestCases, ScheduleTaskFlag = " + ScheduleTaskFlag);
                while (true) {
                    if (ScheduleTaskFlag){
                        try {
                            schedule();
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, "ScheduleThread");

        GetTaskThread.start();
        ScheduleTaskThread.start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        GetTaskThread.interrupt();
        ScheduleTaskThread.interrupt();
        GetTaskFlag = true;
        ScheduleTaskFlag = false;
    }

    private void stop(){
        clearDataAsync();
    }

    private void execute() throws InterruptedException {
//        FutureTask<TestCases> futureTask = new FutureTask<>(taskT);
//        executor.submit(futureTask);
//        testCases = futureTask.get();
        GetTestCasesTask.getDataAsync();
        Log.d("TAG", "Get TestCases, " + testCases.getCasesId());
//        executor.shutdown();
    }

    private void schedule() throws InterruptedException {
            caseList = testCases.getCases();
            Log.e("TAG", "schedule TestCases Id = " + caseList.get(0).getCaseName());
            for(TestCase tc : caseList){
                Log.e("TAG", "schedule Execute TestCase, Name = " + tc.getCaseName());
                Collections.sort(tc.getTestSteps());
                for(TestStep ts : tc.getTestSteps()){
                    stepCompletedTag = false;
                    String actual = obtainActualMessage();
                    Log.d("TAG", "schedule Execute TestStep, " + ts.toString());
                    if (ts.getStepProperty().equals("activity")){
                        Log.e("TAG", "schedule jumpToActivity, " + ts.toString());
                        jumpToActivity(ts.getStepName());
                    }else {
                        Log.e("TAG", "schedule Send TestStep Event, Name = " + ts.toString());
                        sendEvent(new TaskEvent(ts));
                    }
                    verifyMessage(ts, actual);
                    Log.e("TAG", "TestStep completed status = " + stepCompletedTag);
                    Thread.sleep(1000 * 2);
                    if (stepCompletedTag){
                        continue;
                    }else {
                        Log.e("TAG", "TestStep completed status = " + stepCompletedTag + ", use default time " + ts.getStepRunTime() + " s");
                        Thread.sleep(1000 * ts.getStepRunTime());
                    }
                }
                Log.e("TAG", "schedule TestCase execute completed, Name = " + tc.toString());
            }
            GetTaskFlag = false;
            ScheduleTaskFlag  = false;
            stop();
    }


    private String obtainActualMessage(){
        return "Expecteted";
    }

    private boolean verifyMessage(TestStep ts,String actual){
//        String expected = ts.getTestAsserts().get(0).getActualValue();
        String expected = "Expecteted";
        if (actual.equals(expected)){
            Log.e("TAG", "TestCase step verify, Result = true, Expected value = " +  expected + ", Actual Value = " + actual);
            return true;
        }
        return false;
    }

    //把打开Activity作为一步，不需要考虑复杂的acvity跳转场景
    //把Activity是特殊步骤，需要在json中标记
    private void jumpToActivity(String name){
        switch (name){
            case "UserConfigActivity":
                startMessageActivity(UserConfigActivity.class);
                break;
            case "MainActivity" :
                startMessageActivity(MainActivity.class);
                break;
            case "RoomActivity":
                startMessageActivity(RoomActivity.class);
                break;
        }
    }

    private void startMessageActivity(Class clazz){
        Intent intent = new Intent(this, clazz);
        intent.putExtra(KEY_RECEIVER, new MessageReceiver());
        Log.e("TAG", "Start activity =  " + clazz.getName());
        startActivity(intent);
    }

    class MessageReceiver extends ResultReceiver{

        public MessageReceiver() {
            super(null);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            if (resultCode != RESULT_OK){
                return;
            }
            stepCompletedTag = resultData.getBoolean(KEY_MESSAGE, false);
        }
    }

    public void sendEvent(TaskEvent event){
        Log.e("TAG", "sendEvent Event  = " + event.toString());
        EventBusBase.getInstance().postSticky(event);
    }

    Comparator<TestStep> comparator = new Comparator<TestStep>() {
        @Override
        public int compare(TestStep t1, TestStep t2) {
            if (t1.getStepId() < t2.getStepId()){
                return 1;
            }else {
                return 0;
            }
        }
    };
}
