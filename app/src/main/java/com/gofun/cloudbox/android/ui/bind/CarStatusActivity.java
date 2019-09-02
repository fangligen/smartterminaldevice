package com.gofun.cloudbox.android.ui.bind;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.bin.david.form.core.SmartTable;
import com.bin.david.form.data.format.bg.IBackgroundFormat;
import com.bin.david.form.utils.DensityUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.gofun.cloudbox.android.R;
import com.gofun.cloudbox.android.base.BaseActivity;
import com.gofun.cloudbox.android.entity.CarRemoteStatus;
import com.gofun.cloudbox.android.entity.CarStatus;
import com.gofun.cloudbox.android.utils.HttpConstant;
import com.gofun.cloudbox.android.utils.IHttpResponse;
import com.gofun.cloudbox.android.utils.RequestUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CarStatusActivity extends BaseActivity {

    @BindView(R.id.activity_car_status_table)
    SmartTable smartTable;
    @BindView(R.id.carLicense)
    TextView carLicense;
    @BindView(R.id.carVin)
    TextView carVinText;
    @BindView(R.id.carDeviceNo)
    TextView carDeviceNo;
    private String carNum, deviceNo,carVin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        carNum = getIntent().getStringExtra("carNum");
        deviceNo = getIntent().getStringExtra("deviceNo");
        carVin = getIntent().getStringExtra("carVin");
        setContentView(R.layout.activity_car_status);
        ButterKnife.bind(this);
        initTitle(true, false);
        setTitleTxt("车辆信息");
        carLicense.setText(carNum);
        carDeviceNo.setText(deviceNo);
        carVinText.setText(carVin);
        initTable(null);
        getStatus();
        findViewById(R.id.clickContent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getStatus();
            }
        });
    }


    private CarStatus handleStatusData(String str, boolean b) {
        CarStatus carStatus = new CarStatus();
        switch (str) {
            case "acc":
                carStatus.setTitle("acc");
                carStatus.setValue(b ? "开" : "关");
                break;
            case "door1":
                carStatus.setTitle("左前门");
                carStatus.setValue(b ? "开" : "关");
                break;
            case "door2":
                carStatus.setTitle("右前门");
                carStatus.setValue(b ? "开" : "关");
                break;

            case "door3":
                carStatus.setTitle("左后门");
                carStatus.setValue(b ? "开" : "关");
                break;
            case "door4":
                carStatus.setTitle("右后门");
                carStatus.setValue(b ? "开" : "关");
                break;
            case "door5":
                carStatus.setTitle("后备箱");
                carStatus.setValue(b ? "开" : "关");
                break;
            case "acCharge":
                carStatus.setTitle("acCharge");
                carStatus.setValue(b ? "开" : "关");
                break;
            case "dcCharge":
                carStatus.setTitle("dcCharge");
                carStatus.setValue(b ? "开" : "关");
                break;
            case "headlight":
                carStatus.setTitle("车灯状态");
                carStatus.setValue(b ? "开" : "关");
                break;
            case "poweron":
                carStatus.setTitle("poweron");
                carStatus.setValue(b ? "开" : "关");
                break;
            case "hightPower":
                carStatus.setTitle("电源状态");
                carStatus.setValue(b ? "开" : "关");
                break;
            case "footBrake":
                carStatus.setTitle("footBrake");
                carStatus.setValue(b ? "开" : "关");
                break;
            case "handBrake":
                carStatus.setTitle("handBrake");
                carStatus.setValue(b ? "开" : "关");
                break;
            case "engine":
                carStatus.setTitle("engine");
                carStatus.setValue(b ? "开" : "关");
                break;
        }
        return carStatus;
    }

    private void initTable(CarRemoteStatus.FullStatus status) {
        List<CarStatus> carStatuses = new ArrayList<>();
        if (null == status) {
            String[] arrays = getResources().getStringArray(R.array.car_status_table_title_array);
            for (String title : arrays) {
                CarStatus carStatus = new CarStatus();
                carStatus.setTitle(title);
                carStatus.setValue("未知");
                carStatuses.add(carStatus);
            }
        } else {
            Field[] fields = status.getClass().getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                Field f = fields[i];
                f.setAccessible(true);
                try {
                    carStatuses.add(handleStatusData(f.getName(), (Boolean) f.get(status)));
                } catch (Exception e) {
                }
            }
        }

        int minWidth = ScreenUtils.getScreenWidth() - DensityUtils.dp2px(this, getResources().getDimension(R.dimen.dp_8));
        smartTable.setData(carStatuses);
        smartTable.getConfig().setShowYSequence(false);
        smartTable.getConfig().setMinTableWidth(minWidth);
        smartTable.getConfig().setShowTableTitle(false);
        smartTable.getConfig().setShowXSequence(false);
        smartTable.getConfig().setColumnTitleBackground(new IBackgroundFormat() {
            @Override
            public void drawBackground(Canvas canvas, Rect rect, Paint paint) {
                paint.setColor(Color.GREEN);
                canvas.drawRect(rect, paint);
            }
        });
    }

    @OnClick(R.id.activity_car_status_next)
    void goNext() {
        Bundle b = new Bundle();
        b.putString("deviceNo",deviceNo);
        b.putString("carNum",carNum);
        b.putString("carVin",carVin);
        launchActivity(CarControlActivity.class,b);
        finish();
    }

    private void getStatus() {
        Map<String, Object> paras = new HashMap<>();
        paras.put("deviceNo", deviceNo);
        paras.put("action", 1);
        RequestUtil requestUtil = new RequestUtil();
        requestUtil.requestGetByParmarts(HttpConstant.REMOTE_POWER, paras, new IHttpResponse() {
            @Override
            public void response(boolean success, String result) {
                if (success) {
                    if (!TextUtils.isEmpty(result)) {
                        CarRemoteStatus carRemoteStatus = JSONObject.parseObject(result, new TypeReference<CarRemoteStatus>() {
                        });
                        if (carRemoteStatus.getDetails() != null && carRemoteStatus.getDetails().getFullStatus() != null) {
                            initTable(carRemoteStatus.getDetails().getFullStatus());
                        }
                    }
                } else {
                }
            }
        });
    }
}
