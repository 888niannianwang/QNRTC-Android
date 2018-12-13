package com.qiniu.droid.rtc.demo.event;


import android.view.View;

import com.qiniu.droid.rtc.demo.model.TestStep;


public class TaskEvent {
    private TestStep step;
    private View view;

    public TaskEvent(TestStep step) {
        this.step = step;
    }

    public TaskEvent(TestStep step, View view) {
        this.step = step;
        this.view = view;
    }


    public TestStep getStep() {
        return step;
    }

    public void setStep(TestStep step) {
        this.step = step;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    @Override
    public String toString() {
        return "TestStep { stepName : " + step.getStepName() + ", stepProperty : " + step.getStepProperty() + " }";
    }
}

