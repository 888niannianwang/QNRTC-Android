package com.qiniu.droid.rtc.demo.model;

import com.google.gson.annotations.SerializedName;

public class TestField {

    @SerializedName("filed_id")
    private int FiledId;

    @SerializedName("field_name")
    private  String FieldName;

    @SerializedName("field_value")
    private  String FieldValue;

    @SerializedName("field_description")
    private  String FieldDescription;

    public int getFiledId() {
        return FiledId;
    }

    public void setFiledId(int filedId) {
        FiledId = filedId;
    }

    public String getFieldName() {
        return FieldName;
    }

    public void setFieldName(String fieldName) {
        FieldName = fieldName;
    }

    public String getFieldValue() {
        return FieldValue;
    }

    public void setFieldValue(String fieldValue) {
        FieldValue = fieldValue;
    }

    public String getFieldDescription() {
        return FieldDescription;
    }

    public void setFieldDescription(String fieldDescription) {
        FieldDescription = fieldDescription;
    }

    @Override
    public String toString() {
        return "TestField{  FiledId: " + FiledId +
                ", FieldName: " + FieldName +
                ", FieldValue: " + FieldValue +
                ", FieldDescription: " + FieldDescription
                + " }";
    }
}
