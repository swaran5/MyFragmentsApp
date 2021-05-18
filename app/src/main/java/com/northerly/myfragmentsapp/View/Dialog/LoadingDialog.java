package com.northerly.myfragmentsapp.View.Dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

import com.northerly.myfragmentsapp.R;
import com.northerly.myfragmentsapp.View.Fragments.DBFragment;

public class LoadingDialog {
    public Activity activity;
    public AlertDialog dialog;

    public LoadingDialog(Activity activity) {
        this.activity = activity;
    }

    public void startLoadingDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        builder.setView(LayoutInflater.from(activity).inflate(R.layout.custom_dailog, null));
        builder.setCancelable(true);

        dialog = builder.create();
        dialog.show();
    }

    public void dismissDialog(){
        dialog.dismiss();
    }
}
