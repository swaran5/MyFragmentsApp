package com.northerly.myfragmentsapp.View.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.northerly.myfragmentsapp.R;
import com.northerly.myfragmentsapp.View.Fragments.HomeFragment;
import com.northerly.myfragmentsapp.View.Fragments.UserFragment;

public class GoBackDialog extends AppCompatDialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Go To Home")
                .setMessage("Please Select a User From Home")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }
                );
   return builder.create(); }
}
