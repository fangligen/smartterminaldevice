package com.gofun.cloudbox.android.entity;

public class DeviceVersion {
    private String deviceNo;
    private String carModelId;
    private String carTypeCode;
    private String carBrandName;
    private String lastFirmWareId;
    private String currentVersion;
    private String lastVersion;

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    public String getCarModelId() {
        return carModelId;
    }

    public void setCarModelId(String carModelId) {
        this.carModelId = carModelId;
    }

    public String getCarTypeCode() {
        return carTypeCode;
    }

    public void setCarTypeCode(String carTypeCode) {
        this.carTypeCode = carTypeCode;
    }

    public String getCarBrandName() {
        return carBrandName;
    }

    public void setCarBrandName(String carBrandName) {
        this.carBrandName = carBrandName;
    }

    public String getLastFirmWareId() {
        return lastFirmWareId;
    }

    public void setLastFirmWareId(String lastFirmWareId) {
        this.lastFirmWareId = lastFirmWareId;
    }

    public String getCurrentVersion() {
        return currentVersion;
    }

    public void setCurrentVersion(String currentVersion) {
        this.currentVersion = currentVersion;
    }

    public String getLastVersion() {
        return lastVersion;
    }

    public void setLastVersion(String lastVersion) {
        this.lastVersion = lastVersion;
    }
}
