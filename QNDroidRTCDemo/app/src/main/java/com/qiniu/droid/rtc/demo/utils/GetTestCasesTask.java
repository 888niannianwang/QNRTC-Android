package com.qiniu.droid.rtc.demo.utils;

import android.util.Log;

import com.google.gson.Gson;
import com.qiniu.droid.rtc.demo.event.TaskEvent;
import com.qiniu.droid.rtc.demo.model.TestCase;
import com.qiniu.droid.rtc.demo.model.TestCases;
import com.qiniu.droid.rtc.demo.model.TestStep;

import junit.framework.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import de.greenrobot.event.EventBus;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GetTestCasesTask {

    private static Gson gson = new Gson();
    public static TestCases testCases = new TestCases();
    private static List<TestCase> caseList = new ArrayList<>();
    private int casesIdTag;
    private boolean taskStart;


    public static void getDataAsync(){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://10.0.2.2:8888/get")
//                .url("http://100.100.34.46:8888/get")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("GetTestCasesTask err", e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()){
                    testCases = gson.fromJson(response.body().charStream(), TestCases.class);
                    Log.e("TAG","response.body() " + testCases.getCasesId());
                }else {
                    Log.e("TAG","Response err, response.body().string()" + response.body().string());
                }
            }
        });
    }


    public static void clearDataAsync(){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://10.0.2.2:8888/clear")
//                .url("http://100.100.34.46:8888/clear")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("GetTestCasesTask err", e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()){
                    testCases = gson.fromJson(response.body().charStream(), TestCases.class);
                    Log.e("TAG","response.body() " + testCases.getCasesId());
                }else {
                    Log.e("TAG","Response err, response.body().string()" + response.body().string());
                }
            }
        });
    }


    public static class GetTestCasesThread implements Callable<TestCases>{

//        @Override
//        public void run() {
//            for (;;){
//                try {
//                    Log.d("GetTestCasesTask","Start GetTestCases loop tasks");
//                    if (! Thread.currentThread().isInterrupted()){
//                        getDataAsync();
//                    }
//                    Thread.sleep(10 * 1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }

        @Override
        public TestCases call() throws Exception {
            getDataAsync();
            Log.d("GetTestCasesTask","callable " + testCases.getCasesId());
            return testCases;
        }
    }

//    private void executeTask(List<TestCase> caseList) throws InterruptedException {
//        for(TestCase tc : caseList){
//            for (TestStep ts : tc.getTestSteps()){
//                String stepName = ts.getStepName();
//                int stepDefaultTime = ts.getStepRunTime();
//                Thread.sleep(stepDefaultTime * 1000);
//                sendEvent(new TaskEvent(ts));
//            }
//        }
//    }
//
//    public void sendEvent(TaskEvent event){
//        EventBus.getDefault().post(event);
//    }
//
//    public void beforeEvent(String name){
//        switch (name){
//            case "FormatTestingActivity" :
//
//                break;
//            case "":
//                break;
//
//        }
//    }
}
