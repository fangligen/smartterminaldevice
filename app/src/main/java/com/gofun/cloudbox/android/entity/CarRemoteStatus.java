package com.gofun.cloudbox.android.entity;

public class CarRemoteStatus {
    private String deviceNo;
    private int actionCode;
    private String actionName;
    private int actionSeq;
    private String  responseHexCode;
    private String  message;
    private int  spendTime;
    private int  status;
    private Details details;

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    public int getActionCode() {
        return actionCode;
    }

    public void setActionCode(int actionCode) {
        this.actionCode = actionCode;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
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

    public Details getDetails() {
        return details;
    }

    public void setDetails(Details details) {
        this.details = details;
    }

    public static class Details{
        private int  replySeq;
        private int  result;
        private int  power;
        private float  voltage;
        private int  mileage;
        private int  totalMileage;
        private long time;
        private int gpsLen;
        private String gps ;
        private FullStatus fullStatus;

        public int getReplySeq() {
            return replySeq;
        }

        public void setReplySeq(int replySeq) {
            this.replySeq = replySeq;
        }

        public int getResult() {
            return result;
        }

        public void setResult(int result) {
            this.result = result;
        }

        public int getPower() {
            return power;
        }

        public void setPower(int power) {
            this.power = power;
        }

        public float getVoltage() {
            return voltage;
        }

        public void setVoltage(float voltage) {
            this.voltage = voltage;
        }

        public int getMileage() {
            return mileage;
        }

        public void setMileage(int mileage) {
            this.mileage = mileage;
        }

        public int getTotalMileage() {
            return totalMileage;
        }

        public void setTotalMileage(int totalMileage) {
            this.totalMileage = totalMileage;
        }

        public long getTime() {
            return time;
        }

        public void setTime(long time) {
            this.time = time;
        }

        public int getGpsLen() {
            return gpsLen;
        }

        public void setGpsLen(int gpsLen) {
            this.gpsLen = gpsLen;
        }

        public String getGps() {
            return gps;
        }

        public void setGps(String gps) {
            this.gps = gps;
        }

        public FullStatus getFullStatus() {
            return fullStatus;
        }

        public void setFullStatus(FullStatus fullStatus) {
            this.fullStatus = fullStatus;
        }
    }

    public static class FullStatus{
        private boolean acc;
        private boolean door1;
        private boolean door2;
        private boolean door3;
        private boolean door4;
        private boolean door5;
        private boolean poweron;
        private boolean hightPower;
        private boolean acCharge;
        private boolean dcCharge;
        private boolean headlight;
        private boolean footBrake;
        private boolean handBrake;
        private boolean engine;

        public boolean isAcc() {
            return acc;
        }

        public void setAcc(boolean acc) {
            this.acc = acc;
        }

        public boolean isDoor1() {
            return door1;
        }

        public void setDoor1(boolean door1) {
            this.door1 = door1;
        }

        public boolean isDoor2() {
            return door2;
        }

        public void setDoor2(boolean door2) {
            this.door2 = door2;
        }

        public boolean isDoor3() {
            return door3;
        }

        public void setDoor3(boolean door3) {
            this.door3 = door3;
        }

        public boolean isDoor4() {
            return door4;
        }

        public void setDoor4(boolean door4) {
            this.door4 = door4;
        }

        public boolean isDoor5() {
            return door5;
        }

        public void setDoor5(boolean door5) {
            this.door5 = door5;
        }

        public boolean isPoweron() {
            return poweron;
        }

        public void setPoweron(boolean poweron) {
            this.poweron = poweron;
        }

        public boolean isHightPower() {
            return hightPower;
        }

        public void setHightPower(boolean hightPower) {
            this.hightPower = hightPower;
        }

        public boolean isAcCharge() {
            return acCharge;
        }

        public void setAcCharge(boolean acCharge) {
            this.acCharge = acCharge;
        }

        public boolean isDcCharge() {
            return dcCharge;
        }

        public void setDcCharge(boolean dcCharge) {
            this.dcCharge = dcCharge;
        }

        public boolean isHeadlight() {
            return headlight;
        }

        public void setHeadlight(boolean headlight) {
            this.headlight = headlight;
        }

        public boolean isFootBrake() {
            return footBrake;
        }

        public void setFootBrake(boolean footBrake) {
            this.footBrake = footBrake;
        }

        public boolean isHandBrake() {
            return handBrake;
        }

        public void setHandBrake(boolean handBrake) {
            this.handBrake = handBrake;
        }

        public boolean isEngine() {
            return engine;
        }

        public void setEngine(boolean engine) {
            this.engine = engine;
        }
    }
}
