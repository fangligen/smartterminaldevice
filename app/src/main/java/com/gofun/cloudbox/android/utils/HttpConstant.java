package com.gofun.cloudbox.android.utils;

public class HttpConstant {

  //===================url===========================
  public static final String BASE_URL = "http://172.17.10.19:9771/";
  public static final String LOGIN_URL = BASE_URL + "accessToken";
  public static final String CAR_MODEL_LIST_URL = BASE_URL + "api/carModel/carBrandDict";
  public static final String CAR_MODEL_DICT_URL = BASE_URL + "api/carModel/carModelDict";
  public static final String REMOTE_CONTROL = BASE_URL + "api/remote/control";
  public static final String REMOTE_POWER = BASE_URL + "api/remote/power";
  public static final String REMOTE_SETUP = BASE_URL + "api/remote/setup";
  public static final String DEVICE_BIND_CAR_INFO = BASE_URL + "api/app/deviceOperate/bindCarInfo";
  public static final String QUERY_BIND_DETAIL= BASE_URL + "api/app/deviceOperate/queryDeviceBindCarDetail";
  public static final String DEVICE_CART_URL = BASE_URL + "api/device/getDeviceCartypeByDeviceNo";
  public static final String UNBIND_DEVICE_CAR = BASE_URL + "api/app/deviceOperate/unbindCarInfo";
  public static final String QUERY_DEVICE_STATUS = BASE_URL + "api/app/deviceOperate/queryDeviceStatus";


  public static final String FILE_UPLOAD_SERVICE = BASE_URL + "upload/fileUpload";
  //=====================end===========================

  //==================common parameter===========
  /**
   * 通用参数：
   *
   * deviceId     设备唯一编号
   *
   * appVersion       软件版本
   *
   * osVersion         操作系统版本
   *
   * timestamp        时间戳
   */
  public static final String PARAMETER_DEVICE_ID = "deviceId";
  public static final String PARAMETER_APP_VERSION = "appVersion";
  public static final String PARAMETER_OS_VERSION = "osVersion";
  public static final String PARAMETER_TIMESTAMP = "timestamp";

  public static final String HEARDER_TOKEN = "OSAUTHORIZATION";
  //======================end=========================

  //=====================CODE=========================
  public static final int CODE_OK = 200;
  public static final String DESC_UNKNOW = "未知错误";
  //======================END=========================
}
