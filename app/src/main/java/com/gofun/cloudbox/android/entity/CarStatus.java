package com.gofun.cloudbox.android.entity;

import com.bin.david.form.annotation.SmartColumn;
import com.bin.david.form.annotation.SmartTable;

@SmartTable public class CarStatus {

  @SmartColumn(id = 0, name = "项目") String title;
  @SmartColumn(id = 1, name = "状态") String value;

  public void setTitle(String title) {
    this.title = title;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public String getTitle() {
    return title;
  }

  public String getValue() {
    return value;
  }
}
