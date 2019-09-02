package com.gofun.cloudbox.android.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

public class BaseFragment extends Fragment {
  protected void launchActivity(Context context, Class<?> cls) {
    context.startActivity(new Intent(context, cls));
    ((Activity) context).overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
  }
}
