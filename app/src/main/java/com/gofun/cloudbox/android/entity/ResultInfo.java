package com.gofun.cloudbox.android.entity;

public class ResultInfo {
  private int code;
  private String result;
  private String message;


  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }


  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getResult() {
    return result;
  }

  public void setResult(String result) {
    this.result = result;
  }
}
