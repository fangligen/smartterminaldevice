package com.gofun.cloudbox.android.ui.unbind;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.gofun.cloudbox.android.R;
import com.gofun.cloudbox.android.base.BaseActivity;
import com.gofun.cloudbox.android.entity.BindDetail;
import com.gofun.cloudbox.android.ui.bind.CarStatusActivity;
import com.gofun.cloudbox.android.utils.CommonUtils;
import com.gofun.cloudbox.android.utils.HttpConstant;
import com.gofun.cloudbox.android.utils.IHttpResponse;
import com.gofun.cloudbox.android.utils.RequestUtil;
import com.gofun.cloudbox.android.utils.ToastUtils;

import java.util.HashMap;
import java.util.Map;

public class UnBindCarActivity extends BaseActivity {

    @BindView(R.id.activity_bind_car_car_num_input)
    EditText activity_bind_car_car_num_input;
    @BindView(R.id.activity_bind_car_device_id)
    EditText activity_bind_car_device_id;
    private String carNum, deviceNo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        carNum = getIntent().getStringExtra("carNum");
        deviceNo = getIntent().getStringExtra("deviceNo");
        setContentView(R.layout.activity_unbind_car);
        ButterKnife.bind(this);
        initTitle(true, false);
        if (!TextUtils.isEmpty(carNum)) {
            activity_bind_car_car_num_input.setText(carNum);
            activity_bind_car_device_id.setText(deviceNo);
        }
    }

    @OnClick(R.id.activity_unbind_car_next)
    void goNext() {
        if (!TextUtils.isEmpty(activity_bind_car_car_num_input.getText().toString())) {
            boolean result = CommonUtils.checkPlateNumberFormat(activity_bind_car_car_num_input.getText().toString());
            if (!result) {
                ToastUtils.showLongToast("请输入正确的车牌号");
                return;
            }
        }
        Bundle b = new Bundle();
        b.putString("deviceNo", activity_bind_car_device_id.getText().toString());
        b.putString("carNum", activity_bind_car_car_num_input.getText().toString());
        launchActivity(DeviceActivity.class, b);
        finish();
    }


}
