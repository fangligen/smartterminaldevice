package com.gofun.cloudbox.android.ui.usercenter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.gofun.cloudbox.android.R;
import com.gofun.cloudbox.android.base.BaseActivity;
import com.gofun.cloudbox.android.widget.RecyclerViewDivider;

public class InstallListActivity extends BaseActivity {

  @BindView(R.id.activity_install_list_recycler_view) RecyclerView installListView;
  InstallListAdapter adapter;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_install_list);
    ButterKnife.bind(this);
    initTitle(true, false);
    init();
  }

  private void init(){
    adapter=new InstallListAdapter(this);
    installListView.setLayoutManager(new LinearLayoutManager(this));
    installListView.addItemDecoration(new RecyclerViewDivider(this, LinearLayoutManager.HORIZONTAL));
    installListView.setAdapter(adapter);
  }

}
