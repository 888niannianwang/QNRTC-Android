package com.qiniu.droid.rtc.demo.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TestCases {

    @SerializedName("cases_id")
    private int CasesId;

    @SerializedName("cases")
    private List<TestCase> Cases;

    @SerializedName("cases_description")
    private String CasesDescription;

    public int getCasesId() {
        return CasesId;
    }

    public void setCasesId(int casesId) {
        CasesId = casesId;
    }

    public List<TestCase> getCases() {
        return Cases;
    }

    public void setCases(List<TestCase> cases) {
        Cases = cases;
    }

    public String getCasesDescription() {
        return CasesDescription;
    }

    public void setCasesDescription(String casesDescription) {
        CasesDescription = casesDescription;
    }

    @Override
    public String toString() {
        return "TestCase {  CasesId: " + CasesId +
                ", CasesDescription: " + CasesDescription
                + " }";
    }
}
