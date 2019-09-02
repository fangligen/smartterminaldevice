package com.gofun.cloudbox.android.base;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import com.gofun.cloudbox.android.R;
import com.gofun.cloudbox.android.ui.usercenter.UserCenterActivity;
import com.gofun.cloudbox.android.utils.StatusBarUtil;
import com.qincis.slideback.SlideBack;
import de.hdodenhof.circleimageview.CircleImageView;

public abstract class BaseActivity extends FragmentActivity {
  protected static final int PICK_PHOTO = 8;
  protected TextView titleTxt;
  protected View backBtn;
  protected CircleImageView avatarImgView;
  protected boolean openSlideBack=true;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Window window = getWindow();
    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    window.getDecorView()
        .setSystemUiVisibility(
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
    //先将状态栏调整为透明
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      window.setStatusBarColor(Color.TRANSPARENT);
    }
    StatusBarUtil.StatusBarLightMode(this, false);
    StatusBarUtil.setStatusBarColor(this, getResources().getColor(R.color.color_title_bar_bg));
    if(openSlideBack) {
      //开启滑动返回
      SlideBack.create().attachToActivity(this);
    }
  }

  @Override public void finish() {
    super.finish();
  }

  protected void launchActivity(Class<?> cls) {
    launchActivity(cls, null);
  }

  protected void launchActivity(Class<?> cls, Bundle bundle) {
    Intent intent = new Intent(this, cls);
    if (bundle != null) {
      intent.putExtras(bundle);
    }
    startActivity(intent);
    overridePendingTransition(R.anim.ani_slid_in, R.anim.ani_slid_out);
  }

  protected void initTitle(boolean showBack, boolean showAvatar) {
    titleTxt = findViewById(R.id.title_bar_text);
    backBtn = findViewById(R.id.title_bar_back);
    avatarImgView = findViewById(R.id.title_bar_avatar);
    if (showBack) {
      backBtn.setVisibility(View.VISIBLE);
      backBtn.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View view) {
          finish();
        }
      });
    } else {
      backBtn.setVisibility(View.INVISIBLE);
    }
    avatarImgView.setVisibility(showAvatar ? View.VISIBLE : View.INVISIBLE);
    avatarImgView.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        launchActivity(UserCenterActivity.class);
      }
    });
  }

  protected void setTitleTxt(int sid) {
    if (titleTxt != null) {
      titleTxt.setText(sid);
    }
  }
  protected void setTitleTxt(String title) {
    if (titleTxt != null&&!TextUtils.isEmpty(title)) {
      titleTxt.setText(title);
    }
  }

  protected void openImagePicker() {
    Intent intent = new Intent();
    intent.setAction(Intent.ACTION_PICK);
    intent.setType("image/*");
    startActivityForResult(intent, PICK_PHOTO);
  }
}
