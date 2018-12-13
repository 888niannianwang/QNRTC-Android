package com.qiniu.droid.rtc.demo.model;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.List;

public class TestCase {

    @SerializedName("case_id")
    private int CaseId;

    @SerializedName("case_name")
    private String CaseName;

    @SerializedName("case_description")
    private String CaseDescription;

    @SerializedName("default_runtime_s")
    private int DefaultRunTimeS;

    @SerializedName("test_steps")
    private List<TestStep> TestSteps;

    @SerializedName("extra")
    private String Extra;

    public int getCaseId() {
        return CaseId;
    }

    public void setCaseId(int caseId) {
        CaseId = caseId;
    }

    public String getCaseName() {
        return CaseName;
    }

    public void setCaseName(String caseName) {
        CaseName = caseName;
    }

    public String getCaseDescription() {
        return CaseDescription;
    }

    public void setCaseDescription(String caseDescription) {
        CaseDescription = caseDescription;
    }

    public int getDefaultRunTimeS() {
        return DefaultRunTimeS;
    }

    public void setDefaultRunTimeS(int defaultRunTimeS) {
        DefaultRunTimeS = defaultRunTimeS;
    }

    public List<TestStep> getTestSteps() {
        return TestSteps;
    }

    public void setTestSteps(List<TestStep> testSteps) {
        TestSteps = testSteps;
    }

    public String getExtra() {
        return Extra;
    }

    public void setExtra(String extra) {
        Extra = extra;
    }

    @Override
    public String toString() {
        return "TestCase {  CaseId: " + CaseId +
                ", CaseName: " + CaseName +
                ", CaseDescription: " + CaseDescription +
                ", DefaultRunTimeS: " + DefaultRunTimeS +
                ", extra: " + Extra
                + " }";
    }
}
