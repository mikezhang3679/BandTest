package com.example.bandtest.utils;

import android.app.ProgressDialog;
import android.content.Context;


public class MyProgressDialog {

    public static ProgressDialog dialog=null;

    public MyProgressDialog(Context context)
    {
            dialog=new ProgressDialog(context);

    }

    public void showDialog(String msg)
    {
        dialog.setMessage(msg);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    public void hideDialog()
    {
        if(dialog!=null)
            dialog.dismiss();
    }
}
