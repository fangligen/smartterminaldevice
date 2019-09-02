package com.gofun.cloudbox.android.ui.unbind;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.gofun.cloudbox.android.R;
import com.gofun.cloudbox.android.base.BaseActivity;
import com.gofun.cloudbox.android.entity.BindDetail;
import com.gofun.cloudbox.android.utils.HttpConstant;
import com.gofun.cloudbox.android.utils.IHttpResponse;
import com.gofun.cloudbox.android.utils.RequestUtil;
import com.gofun.cloudbox.android.utils.TimeUtil;
import com.gofun.cloudbox.android.widget.BindDialog;
import com.gofun.cloudbox.android.widget.BtnDialog;

import java.util.HashMap;
import java.util.Map;

public class DeviceActivity extends BaseActivity {

    @BindView(R.id.carNumTV)
    TextView carNumTV;
    @BindView(R.id.carVinTV)
    TextView carVinTV;
    @BindView(R.id.deviceNoTV)
    TextView deviceNoTV;
    @BindView(R.id.statusTV)
    TextView statusTV;
    @BindView(R.id.installTimeTV)
    TextView installTimeTV;
    @BindView(R.id.activity_device_info_unbind)
    Button activity_device_info_unbind;
    private String deviceNo, carNum;
    private BindDetail detail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        deviceNo = getIntent().getStringExtra("deviceNo");
        carNum = getIntent().getStringExtra("carNum");
        setContentView(R.layout.activity_device_info);
        ButterKnife.bind(this);
        initTitle(true, false);
        getData();
        activity_device_info_unbind.setEnabled(false);
    }

    @OnClick(R.id.activity_device_info_unbind)
    void unBind() {
        BtnDialog dialog = new BtnDialog(this);
        dialog.setListener(new BtnDialog.OnOkClickListener() {
            @Override
            public void onClick() {
                unbindDevice();
            }
        });
        dialog.show();
        dialog.setTitle("确定解绑车机？");
    }

    private void unbindDevice() {
        Map<String, Object> paras = new HashMap<>();
        paras.put("deviceSerialNum", deviceNo);
        RequestUtil requestUtil = new RequestUtil();
        requestUtil.requestGetByParmarts(HttpConstant.UNBIND_DEVICE_CAR, paras, new IHttpResponse() {
            @Override
            public void response(boolean success, String result) {
                if (success) {
                    showDialog();
                }
            }
        });
    }

    private void showDialog() {
        BindDialog dialog = new BindDialog(this);
        dialog.show();
        dialog.setType(BindDialog.TYPE_UNBIND);
        dialog.setListener(new BindDialog.DisMissListener() {
            @Override
            public void onDisMiss() {
                finish();
            }
        });
    }

    private void getData() {
        Map<String, Object> paras = new HashMap<>();
        paras.put("deviceSerialNum", deviceNo);
        paras.put("carPlateNumber", carNum);
        RequestUtil requestUtil = new RequestUtil();
        requestUtil.requestGetByParmarts(HttpConstant.QUERY_BIND_DETAIL, paras, new IHttpResponse() {
            @Override
            public void response(boolean success, String result) {
                if (success) {
                    if (!TextUtils.isEmpty(result)) {
                        BindDetail detail = JSONObject.parseObject(result, new TypeReference<BindDetail>() {
                        });
                        if (null != detail) {
                            carNumTV.setText(detail.getCarPlate());
                            carVinTV.setText(detail.getCarVin());
                            deviceNoTV.setText(detail.getDeviceSerialNum());
                            statusTV.setText(detail.getStatus() == 1 ? "绑定" : "未绑定");
                            if (detail.getModifyTime() > 0) {
                                installTimeTV.setText(TimeUtil.getFormatDateTime(detail.getModifyTime()));
                            }
                            if (detail.getStatus() == 1) {
                                activity_device_info_unbind.setEnabled(true);
                            }
                        }
                    }
                }
            }
        });
    }
}
