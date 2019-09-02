package com.gofun.cloudbox.android.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;

import java.util.regex.Pattern;

public class CommonUtils {

    /**
     * Build.BOARD // 主板
     * Build.BRAND // android系统定制商
     * Build.CPU_ABI // cpu指令集
     * Build.DEVICE // 设备参数
     * Build.DISPLAY // 显示屏参数
     * Build.FINGERPRINT // 硬件名称
     * Build.HOST
     * Build.ID // 修订版本列表
     * Build.MANUFACTURER // 硬件制造商
     * Build.MODEL // 版本
     * Build.PRODUCT // 手机制造商
     * Build.TAGS // 描述build的标签
     * Build.TIME
     * Build.TYPE // builder类型
     * Build.USER
     * Build.VERSION.CODENAME// 当前开发代号
     * Build.VERSION.INCREMENTAL// 源码控制版本号
     * Build.VERSION.RELEASE// 版本字符串
     * Build.VERSION.SDK// 版本号
     * Build.VERSION.SDK_INT// 版本号
     */
    public static final int getSDKCode() {
        return Build.VERSION.SDK_INT;
    }

    public static String getAppVersion(Context context) {
        //获取包管理者对象
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo info = pm.getPackageInfo(context.getPackageName(), 0);
            return String.valueOf(info.versionCode);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getOsVersion() {
        return Build.VERSION.RELEASE;
    }

    public static String getDeviceId() {
//    String SerialNumber = null;
//    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//      SerialNumber = Build.getSerial();
//    } else {
//      SerialNumber = Build.SERIAL;
//    }
        return "001007100999";
    }

    public static String getSDpath() {
        return Environment.getExternalStorageDirectory().getPath();
    }

    public static boolean checkPlateNumberFormat(String content) {
        if (TextUtils.isEmpty(content)){
            return false;
        }
        String pattern = "([京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼]{1}(([A-HJ-Z]{1}[A-HJ-NP-Z0-9]{5})|([A-HJ-Z]{1}(([DF]{1}[A-HJ-NP-Z0-9]{1}[0-9]{4})|([0-9]{5}[DF]{1})))|([A-HJ-Z]{1}[A-D0-9]{1}[0-9]{3}警)))|([0-9]{6}使)|((([沪粤川云桂鄂陕蒙藏黑辽渝]{1}A)|鲁B|闽D|蒙E|蒙H)[0-9]{4}领)|(WJ[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼·•]{1}[0-9]{4}[TDSHBXJ0-9]{1})|([VKHBSLJNGCE]{1}[A-DJ-PR-TVY]{1}[0-9]{5})";
        return Pattern.matches(pattern, content);
    }
}
