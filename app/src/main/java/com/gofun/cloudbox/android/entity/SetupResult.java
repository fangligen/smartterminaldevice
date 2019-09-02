package com.gofun.cloudbox.android.entity;

public class SetupResult {
    private String deviceNo;
    private int actionSeq;
    private String responseHexCode;
    private String message;
    private int spendTime;
    private int status;
    private String currentVersion;
    private String targetVersion;
    private boolean success;

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    public int getActionSeq() {
        return actionSeq;
    }

    public void setActionSeq(int actionSeq) {
        this.actionSeq = actionSeq;
    }

    public String getResponseHexCode() {
        return responseHexCode;
    }

    public void setResponseHexCode(String responseHexCode) {
        this.responseHexCode = responseHexCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getSpendTime() {
        return spendTime;
    }

    public void setSpendTime(int spendTime) {
        this.spendTime = spendTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCurrentVersion() {
        return currentVersion;
    }

    public void setCurrentVersion(String currentVersion) {
        this.currentVersion = currentVersion;
    }

    public String getTargetVersion() {
        return targetVersion;
    }

    public void setTargetVersion(String targetVersion) {
        this.targetVersion = targetVersion;
    }


    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
