package com.gofun.cloudbox.android.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.gofun.cloudbox.android.R;
import com.gofun.cloudbox.android.utils.ToastUtils;

public class BindDialog extends Dialog {
    public Context context;
    public static int TYPE_BIND = 0;
    public static int TYPE_UNBIND = 1;
    public static int TYPE_BINDED = 2;

    private int timeCount = 3;
    private ImageView img;
    private ImageView close;
    private TextView title, content;
    private DisMissListener listener;

    public BindDialog(Context context) {
        super(context);
        this.context = context;
    }

    public BindDialog(Context context, int theme) {
        super(context, theme);
        this.context = context;
    }

    public BindDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.context = context;
    }

    public void setType(int type) {
        if (type == TYPE_BIND) {
            title.setText("车机绑定成功");
            close.setVisibility(View.GONE);
            handler.sendEmptyMessageDelayed(0, 1000);
        } else if (type == TYPE_UNBIND) {
            title.setText("车机已解绑");
            img.setVisibility(View.GONE);
            close.setVisibility(View.INVISIBLE);
            handler.sendEmptyMessageDelayed(0, 1000);
        } else if (type == TYPE_BINDED) {
            title.setText("该设备已绑定");
            img.setVisibility(View.GONE);
            content.setText("如需要重新绑定，请解绑改车机！");
        }

    }

    public void setListener(DisMissListener listener) {
        this.listener = listener;
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                if (timeCount == 0) {
                    closeDialog();
                    handler.removeMessages(0);
                    return;
                }
                timeCount--;
                content.setText(timeCount + "s后自动跳回到首页");
                handler.sendEmptyMessageDelayed(0, 1000);
            }
        }
    };

    private void closeDialog(){
        BindDialog.this.dismiss();
        if (null != listener) {
            listener.onDisMiss();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = View.inflate(context, R.layout.dialog_bind_car, null);
        setContentView(view);
        title = view.findViewById(R.id.title);
        content = view.findViewById(R.id.content);
        img = view.findViewById(R.id.img);
        close = view.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeDialog();
            }
        });
        setCanceledOnTouchOutside(false);
        Window win = getWindow();
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.height = ToastUtils.dip2px(context, 260);
        lp.width = ToastUtils.dip2px(context, 300);
        win.setAttributes(lp);


    }

    public interface DisMissListener {
        void onDisMiss();
    }
}
