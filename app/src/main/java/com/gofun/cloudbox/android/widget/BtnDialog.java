package com.gofun.cloudbox.android.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.gofun.cloudbox.android.R;
import com.gofun.cloudbox.android.utils.ToastUtils;

public class BtnDialog extends Dialog {
    public Context context;
    private OnOkClickListener listener;

    private TextView title;
    private Button btnCancel, btnOk;

    public BtnDialog(Context context) {
        super(context);
        this.context = context;
    }

    public BtnDialog(Context context, int theme) {
        super(context, theme);
        this.context = context;
    }

    public BtnDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = View.inflate(context, R.layout.dialog_button, null);
        setContentView(view);
        title = view.findViewById(R.id.title);
        btnCancel = view.findViewById(R.id.btnCancel);
        btnOk = view.findViewById(R.id.btnOk);
        setCanceledOnTouchOutside(false);
        Window win = getWindow();
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.height = ToastUtils.dip2px(context, 180);
        lp.width = ToastUtils.dip2px(context, 300);
        win.setAttributes(lp);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick();
                }
                dismiss();
            }
        });
    }

    public void setTitle(String s) {
        title.setText(s);
    }

    public void setListener(OnOkClickListener l) {
        listener = l;
    }

    public interface OnOkClickListener {
        void onClick();
    }
}
