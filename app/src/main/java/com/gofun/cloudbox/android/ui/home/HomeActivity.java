package com.gofun.cloudbox.android.ui.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.bumptech.glide.Glide;
import com.gofun.cloudbox.android.R;
import com.gofun.cloudbox.android.base.BaseActivity;
import com.gofun.cloudbox.android.entity.BannerData;
import com.gofun.cloudbox.android.ui.bind.BindCarActivity;
import com.gofun.cloudbox.android.ui.unbind.UnBindCarActivity;
import com.gofun.cloudbox.android.widget.BindDialog;
import com.stx.xhb.xbanner.XBanner;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends BaseActivity {
  @BindView(R.id.activity_home_banner) XBanner xBanner;

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_home);
    ButterKnife.bind(this);
    initTitle(false, true);
    init();
  }

  private void init() {
    String[] urls = {
        "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1564983999173&di=c389f4d0af843300068be140ceaf8b59&imgtype=0&src=http%3A%2F%2Fzkres2.myzaker.com%2F201903%2F5c7d2dab6227689c98007614_1024.jpg",
        "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1564983999170&di=156ec999ff21f27fad2dd85c068ca22e&imgtype=0&src=http%3A%2F%2Fn.sinaimg.cn%2Fsinacn%2Fw1600h1000%2F20180119%2F6d42-fyqtwzu8328432.jpg",
        "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1564984049078&di=94512f7e2857854086bedc1a4642e755&imgtype=0&src=http%3A%2F%2Fn.sinaimg.cn%2Fsinacn%2Fw1280h652%2F20180119%2F9c03-fyqtwzu8327534.jpg"
    };
    List<BannerData> bannerDatas = new ArrayList<>();
    for (String url : urls) {
      BannerData bannerData = new BannerData();
      bannerData.setImgUrl(url);
      bannerData.setTitle("车机平台化");
      bannerDatas.add(bannerData);
    }
    xBanner.setBannerData(bannerDatas);
    xBanner.loadImage(new XBanner.XBannerAdapter() {
      @Override public void loadBanner(XBanner banner, Object model, View view, int position) {
        Glide.with(HomeActivity.this)
            .load(((BannerData) model).getImgUrl())
            .placeholder(R.drawable.round_bg_white_normal)
            .error(R.drawable.round_bg_white_normal)
            .into((ImageView) view);
      }
    });
  }

  @OnClick(R.id.activity_home_bind)
  void goBind(){
    launchActivity(BindCarActivity.class);
  }

  @OnClick(R.id.activity_home_unbind)
  void goUnBind(){

    launchActivity(UnBindCarActivity.class);
  }
}
