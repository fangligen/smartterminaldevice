package com.gofun.cloudbox.android.ui.usercenter;

import android.content.Context;
import com.donkingliang.groupedadapter.adapter.GroupedRecyclerViewAdapter;
import com.donkingliang.groupedadapter.holder.BaseViewHolder;
import com.gofun.cloudbox.android.R;

public class InstallListAdapter extends GroupedRecyclerViewAdapter {

  public InstallListAdapter(Context context) {
    super(context);
  }

  @Override public int getGroupCount() {
    return 1;
  }

  @Override public int getChildrenCount(int groupPosition) {
    return 10;
  }

  @Override public boolean hasHeader(int groupPosition) {
    return true;
  }

  @Override public boolean hasFooter(int groupPosition) {
    return false;
  }

  @Override public int getHeaderLayout(int viewType) {
    return R.layout.item_install_list_header;
  }

  @Override public int getFooterLayout(int viewType) {
    return 0;
  }

  @Override public int getChildLayout(int viewType) {
    return R.layout.item_install_list_child;
  }

  @Override public void onBindHeaderViewHolder(BaseViewHolder holder, int groupPosition) {

  }

  @Override public void onBindFooterViewHolder(BaseViewHolder holder, int groupPosition) {

  }

  @Override public void onBindChildViewHolder(BaseViewHolder holder, int groupPosition, int childPosition) {

  }
}
