package com.gofun.cloudbox.android.ui.usercenter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.gofun.cloudbox.android.R;
import com.gofun.cloudbox.android.base.BaseActivity;

public class UserCenterActivity extends BaseActivity {

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_user_center);
    ButterKnife.bind(this);
    initTitle(true, false);
  }

  @OnClick(R.id.activity_user_center_install_list) void goInstallList() {
    launchActivity(InstallListActivity.class);
  }

  @OnClick(R.id.activity_user_center_logout) void logout() {
    finish();
  }
}
