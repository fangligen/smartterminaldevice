package com.gofun.cloudbox.android.ui.login;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.gofun.cloudbox.android.R;
import com.gofun.cloudbox.android.base.BaseActivity;
import com.gofun.cloudbox.android.ui.bind.CarControlActivity;
import com.gofun.cloudbox.android.ui.home.HomeActivity;
import com.gofun.cloudbox.android.utils.HttpConstant;
import com.gofun.cloudbox.android.utils.IHttpResponse;
import com.gofun.cloudbox.android.utils.RequestUtil;
import com.gofun.cloudbox.android.utils.SPConstant;
import com.gofun.cloudbox.android.utils.SPUtils;
import com.gofun.cloudbox.android.utils.ToastUtils;
import com.gofun.cloudbox.android.widget.BindDialog;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        openSlideBack = false;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initTitle(false, false);
    }

    @OnClick(R.id.activity_login_btn)
    void login() {
        Map<String, Object> paras = new HashMap<>();
        paras.put("username", "keesh");
        paras.put("password", "Cc");
        RequestUtil requestUtil = new RequestUtil();
        requestUtil.requestPostByParmarts(HttpConstant.LOGIN_URL, paras, new IHttpResponse() {
            @Override
            public void response(boolean success, String result) {
                if (success && !TextUtils.isEmpty(result)) {
                    SPUtils.put(SPConstant.SP_TOKEN, result);
                    launchActivity(HomeActivity.class);
                    finish();
                }
            }
        });
    }

}
