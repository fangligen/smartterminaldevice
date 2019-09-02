package com.gofun.cloudbox.android.ui.bind;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.gofun.cloudbox.android.R;
import com.gofun.cloudbox.android.base.BaseActivity;
import com.gofun.cloudbox.android.entity.CarBrandList;
import com.gofun.cloudbox.android.utils.HttpConstant;
import com.gofun.cloudbox.android.utils.IHttpResponse;
import com.gofun.cloudbox.android.utils.RequestUtil;
import com.gofun.cloudbox.android.widget.BindDialog;

import java.util.HashMap;
import java.util.Map;

public class CarControlActivity extends BaseActivity {

    @BindView(R.id.activity_car_control_btn_group_top)
    LinearLayout topLinearLayout;
    @BindView(R.id.activity_car_control_btn_group_bottom)
    LinearLayout bottomLinearLayout;
    @BindView(R.id.activity_car_control_next)
    Button nextBtn;
    @BindView(R.id.carNum)
    TextView carNumText;
    @BindView(R.id.deviceNoView)
    TextView deviceNoView;
    @BindView(R.id.carVivText)
    TextView carVivText;
    private String carNum, deviceNo, carVin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        carNum = getIntent().getStringExtra("carNum");
        deviceNo = getIntent().getStringExtra("deviceNo");
        carVin = getIntent().getStringExtra("carVin");
        setContentView(R.layout.activity_car_control);
        ButterKnife.bind(this);
        nextBtn.setEnabled(false);
        carNumText.setText(carNum);
        deviceNoView.setText(deviceNo);
        carVivText.setText(carVin);
    }

    @OnClick({
            R.id.activity_car_control_lock, R.id.activity_car_control_unlock, R.id.activity_car_control_light,
            R.id.activity_car_control_di, R.id.activity_car_control_power_on, R.id.activity_car_control_power_off
    })
    void carControl(Button button) {
        if (button.getId() == R.id.activity_car_control_lock) {
            remoteControl(button.getId(), 2);
        } else if (button.getId() == R.id.activity_car_control_unlock) {
            remoteControl(button.getId(), 3);
        } else if (button.getId() == R.id.activity_car_control_light) {
            remoteControl(button.getId(), 12);
        } else if (button.getId() == R.id.activity_car_control_di) {
            remoteControl(button.getId(), 1);
        } else if (button.getId() == R.id.activity_car_control_power_on) {
            remoteControl(button.getId(), 4);
        } else if (button.getId() == R.id.activity_car_control_power_off) {
            remoteControl(button.getId(), 5);
        }
    }

    private void handleViewResult(int id) {
        findViewById(id).setEnabled(false);
        boolean canNext = checkButtonStatus();
        nextBtn.setEnabled(canNext);
    }

    @OnClick(R.id.activity_car_control_next)
    void goNext() {
        BindDialog dialog = new BindDialog(this);
        dialog.show();
        dialog.setType(BindDialog.TYPE_BIND);
        dialog.setListener(new BindDialog.DisMissListener() {
            @Override
            public void onDisMiss() {
               CarControlActivity.this.finish();
            }
        });
    }

    boolean checkButtonStatus() {
        int topChildCount = topLinearLayout.getChildCount();
        int bottomChildCount = bottomLinearLayout.getChildCount();
        for (int i = 0; i < topChildCount; i++) {
            Button child = (Button) topLinearLayout.getChildAt(i);
            if (child.isEnabled()) {
                return false;
            }
        }
        for (int i = 0; i < bottomChildCount; i++) {
            Button child = (Button) bottomLinearLayout.getChildAt(i);
            if (child.isEnabled()) {
                return false;
            }
        }
        return true;
    }

    /**
     * @param action VOICE((short)1, "鸣笛"),
     *               OPEN_DOOR((short)2, "开门"),
     *               LOCK_DOOR((short)3, "锁门"),
     *               POWEROFF((short)4, "断电"),
     *               POWERON((short)5, "供电"),
     *               HIGHTPOWER((short)6, "高功耗模式"),
     *               LOWPOWER((short)7, "低功耗模式"),
     *               UN_SUPPORT((short)99, "UN_SUPPORT");
     */
    private void remoteControl(final int buttonId, int action) {
        Map<String, Object> paras = new HashMap<>();
        paras.put("deviceNo", deviceNo);
        paras.put("action", action);
        RequestUtil requestUtil = new RequestUtil();
        requestUtil.requestGetByParmarts(HttpConstant.REMOTE_CONTROL, paras, new IHttpResponse() {
            @Override
            public void response(boolean success, String result) {
                if (success && !TextUtils.isEmpty(result)) {
                    handleViewResult(buttonId);
                }
            }
        });
    }
}
