package com.yuntao.ui.login;

import android.app.Dialog;
import android.content.Context;

import com.yuntao.R;

public class SimpleProgressDialog extends Dialog {

    public SimpleProgressDialog(Context context, int theme) {
        super(context, theme);
        this.setContentView(R.layout.progressdialog);
    }

}
