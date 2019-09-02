package com.gofun.cloudbox.android.ui.bind;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.gofun.cloudbox.android.R;
import com.gofun.cloudbox.android.base.BaseActivity;
import com.gofun.cloudbox.android.entity.DeviceStatus;
import com.gofun.cloudbox.android.entity.SetupResult;
import com.gofun.cloudbox.android.utils.HttpConstant;
import com.gofun.cloudbox.android.utils.IHttpResponse;
import com.gofun.cloudbox.android.utils.RequestUtil;
import com.gofun.cloudbox.android.utils.ToastUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BindStatusActivity extends BaseActivity {
    @BindView(R.id.btn)
    Button btn;
    private String carNum, deviceNo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        carNum = getIntent().getStringExtra("carNum");
        deviceNo = getIntent().getStringExtra("deviceNo");
        setContentView(R.layout.activity_bind_status);
        initTitle(true, false);
        setTitleTxt("安装车机");
        ButterKnife.bind(this);
        btn.setEnabled(false);
        queryDevicesStatus();
    }

    @OnClick(R.id.btn)
    void next() {
        btn.setEnabled(false);
        setUp();
    }

    /**
     * DEVICE_INACTIVE(1,"挂起") ,
     * DEVICE_ONLINE(2,"在线"),
     * DEVICE_OFFLINE(3,"离线"),
     * DEVICE_DISABLE(4,"注销") ;
     */
    private void queryDevicesStatus() {
        Map<String, Object> paras = new HashMap<>();
        paras.put("deviceNo", deviceNo);
        paras.put("serialNum", deviceNo);
        RequestUtil requestUtil = new RequestUtil();
        requestUtil.requestGetByParmarts(HttpConstant.QUERY_DEVICE_STATUS, paras, new IHttpResponse() {
            @Override
            public void response(boolean success, String result) {
                btn.setEnabled(true);
                if (success) {
                    if (!TextUtils.isEmpty(result)) {
                        DeviceStatus status = JSONObject.parseObject(result, new TypeReference<DeviceStatus>() {
                        });
                        if (status != null) {
                            //DEVICE_INACTIVE(1,"挂起") ,
                            // DEVICE_ONLINE(2,"在线"),
                            // DEVICE_OFFLINE(3,"离线"),
                            // DEVICE_DISABLE(4,"注销") ;
                            switch (status.getStats()) {
                                case 1:
                                    ToastUtils.showLongToast("车机已挂起");
                                case 2:
                                    btn.setEnabled(true);
                                    return;
                                case 3:
                                    ToastUtils.showLongToast("车机已离线");
                                    break;
                                case 4:
                                    ToastUtils.showLongToast("车机已注销");
                                    break;
                            }
                        }
                    }
                }
                handler.removeMessages(1);
                handler.sendEmptyMessageDelayed(1, 5000);
            }
        });
    }

    private void setUp() {
        Map<String, Object> paras = new HashMap<>();
        paras.put("deviceNo", deviceNo);
        paras.put("targetVersion", "");//String	固件版本号 如果不传, 默认找该设备支持的车型的最新版本
        RequestUtil requestUtil = new RequestUtil();
        requestUtil.requestPostByParmarts(HttpConstant.REMOTE_SETUP, paras, new IHttpResponse() {
            @Override
            public void response(boolean success, String result) {
                if (success) {
                    String targetVersion = "";
                    if (!TextUtils.isEmpty(result)) {
                        SetupResult setupResult = JSONObject.parseObject(result, new TypeReference<SetupResult>() {
                        });
                        targetVersion = setupResult.getTargetVersion();
                        if (!setupResult.isSuccess()) {
                            ToastUtils.showLongToast(setupResult.getMessage());
                        }
                    }
                    Bundle b = new Bundle();
                    b.putString("deviceNo", deviceNo);
                    b.putString("carNum", carNum);
                    b.putString("targetVersion", targetVersion);
                    launchActivity(WaitingActivity.class, b);
                    finish();
                } else {
                    launchActivity(WaitingActivity.class);
                    finish();
                }
            }
        });
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                queryDevicesStatus();
            }
        }
    };
}
