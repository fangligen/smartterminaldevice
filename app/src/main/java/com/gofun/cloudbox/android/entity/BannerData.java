package com.gofun.cloudbox.android.entity;

import com.stx.xhb.xbanner.entity.SimpleBannerInfo;

public class BannerData extends SimpleBannerInfo {

  private String imgUrl;
  private String title;

  public void setImgUrl(String imgUrl) {
    this.imgUrl = imgUrl;
  }

  public String getImgUrl() {
    return imgUrl;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getTitle() {
    return title;
  }

  @Override public String getXBannerTitle() {
    return title;
  }

  @Override public Object getXBannerUrl() {
    return imgUrl;
  }
}
