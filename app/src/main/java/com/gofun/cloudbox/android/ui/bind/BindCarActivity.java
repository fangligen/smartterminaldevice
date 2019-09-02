package com.gofun.cloudbox.android.ui.bind;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.gofun.cloudbox.android.R;
import com.gofun.cloudbox.android.base.BaseActivity;
import com.gofun.cloudbox.android.entity.BindDetail;
import com.gofun.cloudbox.android.entity.SetupResult;
import com.gofun.cloudbox.android.ui.unbind.DeviceActivity;
import com.gofun.cloudbox.android.ui.unbind.UnBindCarActivity;
import com.gofun.cloudbox.android.utils.CommonUtils;
import com.gofun.cloudbox.android.utils.HttpConstant;
import com.gofun.cloudbox.android.utils.IHttpResponse;
import com.gofun.cloudbox.android.utils.RequestUtil;
import com.gofun.cloudbox.android.utils.ToastUtils;
import com.gofun.cloudbox.android.widget.BindDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BindCarActivity extends BaseActivity {
    @BindView(R.id.activity_bind_car_car_num_input)
    EditText carNumInput;
    @BindView(R.id.activity_bind_car_device_id)
    EditText deviceIdInput;
    @BindView(R.id.activity_bind_car_brand)
    TextView carBrandText;
    @BindView(R.id.activity_bind_car_type)
    TextView carTypeText;

    private String carModelId;


    private String dictCode, carBrandCode;
    private List<String> serialNoList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_car);
        initTitle(true, false);
        setTitleTxt(R.string.str_bind_car);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.activity_bind_car_next)
    void goNext() {
        boolean result = CommonUtils.checkPlateNumberFormat(carNumInput.getText().toString());
        if (!result) {
            ToastUtils.showLongToast("请输入正确的车牌号");
            return;
        }
        getData();
    }

    @OnClick(R.id.activity_bind_car_brand)
    void selectBrand() {
        Intent intent = new Intent(this, CarModelActivity.class);
        startActivityForResult(intent, 1);
    }

    @OnClick(R.id.activity_bind_car_type)
    void selectType() {

        Intent intent = new Intent(this, CarDictActivity.class);
        intent.putExtra("brandId", carBrandCode);
        startActivityForResult(intent, 2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 1) {
            String carBrand = data.getStringExtra("dictName");
            carBrandCode = data.getStringExtra("dictCode");
            carBrandText.setText(carBrand);
            carTypeText.setText("");
            carModelId = "";
        } else if (requestCode == 2 && resultCode == 2) {
            String dictName = data.getStringExtra("dictName");
            carModelId = data.getStringExtra("dictCode");
            carTypeText.setText(dictName);
        }
    }


    private void getData() {
        Map<String, Object> paras = new HashMap<>();
        paras.put("deviceSerialNum", deviceIdInput.getText().toString());
        paras.put("carPlateNumber", carNumInput.getText().toString());
        RequestUtil requestUtil = new RequestUtil();
        requestUtil.requestGetByParmarts(HttpConstant.QUERY_BIND_DETAIL, paras, new IHttpResponse() {
            @Override
            public void response(boolean success, String result) {
                if (success) {
                    if (!TextUtils.isEmpty(result)) {
                        BindDetail detail = JSONObject.parseObject(result, new TypeReference<BindDetail>() {
                        });
                        if (null != detail) {
                            if (detail.getStatus() != 1) {
                                deviceUpdate();
                            } else {
                                BindDialog dialog = new BindDialog(BindCarActivity.this);
                                dialog.show();
                                dialog.setType(BindDialog.TYPE_BINDED);
                                dialog.setListener(new BindDialog.DisMissListener() {
                                    @Override
                                    public void onDisMiss() {
                                        Bundle b = new Bundle();
                                        b.putString("deviceNo", deviceIdInput.getText().toString());
                                        b.putString("carNum", carNumInput.getText().toString());
                                        launchActivity(UnBindCarActivity.class, b);
                                        finish();
                                    }
                                });
                            }
                        }
                    }
                }
            }
        });
    }

    private void deviceUpdate() {
        if (TextUtils.isEmpty(carNumInput.getText().toString())) {
            ToastUtils.showLongToast("请选择车型！");
            return;
        }
        Map<String, Object> paras = new HashMap<>();
        paras.put("serialNumber", deviceIdInput.getText().toString());
        paras.put("carModelId", carModelId);//车型id
        paras.put("carPlateNumber", carNumInput.getText().toString());//int	车牌号	用户输入
        RequestUtil requestUtil = new RequestUtil();
        requestUtil.requestPost(HttpConstant.DEVICE_BIND_CAR_INFO, paras, new IHttpResponse() {
            @Override
            public void response(boolean success, String result) {
                if (success) {
                    if (!TextUtils.isEmpty(result)) {
                        Bundle b = new Bundle();
                        b.putString("deviceNo", deviceIdInput.getText().toString());
                        b.putString("carNum", carNumInput.getText().toString());
                        launchActivity(BindStatusActivity.class, b);
                        finish();
                    }
                } else {
                    ToastUtils.showLongToast(result);
                }
            }
        });
    }

}
