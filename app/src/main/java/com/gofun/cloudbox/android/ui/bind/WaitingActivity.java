package com.gofun.cloudbox.android.ui.bind;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.gofun.cloudbox.android.R;
import com.gofun.cloudbox.android.base.BaseActivity;
import com.gofun.cloudbox.android.entity.BindDetail;
import com.gofun.cloudbox.android.entity.DeviceVersion;
import com.gofun.cloudbox.android.entity.SetupResult;
import com.gofun.cloudbox.android.service.GoFunAlarmService;
import com.gofun.cloudbox.android.utils.HttpConstant;
import com.gofun.cloudbox.android.utils.IHttpResponse;
import com.gofun.cloudbox.android.utils.RequestUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

public class WaitingActivity extends BaseActivity {

    private String carNum, deviceNo, targetVersion;
    TextView tv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        carNum = getIntent().getStringExtra("carNum");
        deviceNo = getIntent().getStringExtra("deviceNo");
        targetVersion = getIntent().getStringExtra("targetVersion");
        if (targetVersion == null) {
            targetVersion = "";
        }
        setContentView(R.layout.activity_waiting);
        startService(new Intent(this, GoFunAlarmService.class));
        handler.sendEmptyMessageDelayed(1, 0);
        tv = findViewById(R.id.tv);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeMessages(1);
    }

    private void getStatus() {
        Map<String, Object> paras = new HashMap<>();
        paras.put("deviceNo", deviceNo);
        RequestUtil requestUtil = new RequestUtil();
        requestUtil.requestGetByParmarts(HttpConstant.DEVICE_CART_URL, paras, new IHttpResponse() {
            @Override
            public void response(boolean success, String result) {
                if (success) {
                    if (!TextUtils.isEmpty(result)) {
                        DeviceVersion version = JSONObject.parseObject(result, new TypeReference<DeviceVersion>() {
                        });
                        if (version != null) {
                            // currentVersion == lastVersion 升级成功,  currentVersion < lastVersion 升级中
                            if (targetVersion.equals(version.getCurrentVersion())) {
                                Bundle b = new Bundle();
                                b.putString("deviceNo", deviceNo);
                                b.putString("carNum", carNum);
                                launchActivity(CarStatusActivity.class, b);
                                finish();
                                return;
                            }
                        }
                    }
                }
                handler.sendEmptyMessageDelayed(1, 5000);
            }
        });
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                getStatus();
//
            }
        }
    };
}
