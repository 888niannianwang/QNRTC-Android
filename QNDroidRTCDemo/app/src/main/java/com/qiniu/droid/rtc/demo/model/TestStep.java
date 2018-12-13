package com.qiniu.droid.rtc.demo.model;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TestStep implements Comparable<TestStep>{

    @SerializedName("step_id")
    private int StepId;

    @SerializedName("step_property")
    private String StepProperty;

    @SerializedName("step_name")
    private String StepName;

    @SerializedName("step_description")
    private String StepDescription;

    @SerializedName("step_runtime_s")
    private int StepRunTime;

    @SerializedName("test_asserts")
    private List<TestAssert> TestAsserts;

    @SerializedName("test_fields")
    private List<TestField> TestFields;

    public int getStepId() {
        return StepId;
    }

    public void setStepId(int stepId) {
        StepId = stepId;
    }

    public String getStepName() {
        return StepName;
    }

    public void setStepName(String stepName) {
        StepName = stepName;
    }

    public String getStepDescription() {
        return StepDescription;
    }

    public void setStepDescription(String stepDescription) {
        StepDescription = stepDescription;
    }

    public int getStepRunTime() {
        return StepRunTime;
    }

    public void setStepRunTime(int stepRunTime) {
        StepRunTime = stepRunTime;
    }

    public List<TestAssert> getTestAsserts() {
        return TestAsserts;
    }

    public void setTestAsserts(List<TestAssert> testAsserts) {
        TestAsserts = testAsserts;
    }

    public List<TestField> getTestFields() {
        return TestFields;
    }

    public void setTestFields(List<TestField> testFields) {
        TestFields = testFields;
    }

    public String getStepProperty() {
        return StepProperty;
    }

    public void setStepProperty(String stepProperty) {
        StepProperty = stepProperty;
    }

    @Override
    public String toString() {
        return "TestStep{ StepId: " + StepId +
                ", StepProperty: " + StepProperty +
                ", StepDescription: " + StepDescription +
                ", StepRunTime: " + StepRunTime
                + " }";
    }

    @Override
    public int compareTo(@NonNull TestStep s) {
        if (this.StepId > s.getStepId()){
            return 1;
        }else {
            return -1;
        }
    }
}
