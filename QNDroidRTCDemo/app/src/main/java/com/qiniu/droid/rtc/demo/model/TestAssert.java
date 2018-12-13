package com.qiniu.droid.rtc.demo.model;

import com.google.gson.annotations.SerializedName;

public class TestAssert {

    @SerializedName("assert_id")
    private int AssertId;
    @SerializedName("assert_name")
    private String AssertName;
    @SerializedName("expect_value")
    private String ExpectValue;
    @SerializedName("actual_value")
    private String ActualValue;
    @SerializedName("assert_result")
    private String AssertResult;

    public int getAssertId() {
        return AssertId;
    }

    public void setAssertId(int assertId) {
        AssertId = assertId;
    }

    public String getAssertName() {
        return AssertName;
    }

    public void setAssertName(String assertName) {
        AssertName = assertName;
    }

    public String getExpectValue() {
        return ExpectValue;
    }

    public void setExpectValue(String expectValue) {
        ExpectValue = expectValue;
    }

    public String getActualValue() {
        return ActualValue;
    }

    public void setActualValue(String actualValue) {
        ActualValue = actualValue;
    }

    public String getAssertResult() {
        return AssertResult;
    }

    public void setAssertResult(String assertResult) {
        AssertResult = assertResult;
    }

    @Override
    public String toString() {
        return "TestAssert{  AssertId: " + AssertId +
                ", AssertName: " + AssertName +
                ", ExpectValue: " + ExpectValue +
                ", ActualValue: " + ActualValue +
                ", AssertResult:  " + AssertResult
                + " }";
    }
}
