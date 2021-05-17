package com.northerly.myfragmentsapp.View.Dialog;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.UserHandle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.northerly.myfragmentsapp.Model.RoomDB.User;
import com.northerly.myfragmentsapp.R;
import com.northerly.myfragmentsapp.View.PDFViewerActivity;

public class PdfBottomSheet extends BottomSheetDialogFragment {
    TextView view;
    TextView download;
    TextView share;
    LoadingDialog loadingDialog;
    String phone;
    boolean chk = false;

    public PdfBottomSheet(String phone) {
        this.phone = phone;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bottom_sheet_pdf, container, false);

        view = v.findViewById(R.id.pdfView);
        download = v.findViewById(R.id.pdfDownload);
        share = v.findViewById(R.id.pdfShare);


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingDialog = new LoadingDialog(getActivity());
                loadingDialog.startLoadingDialog();
                chk = true;
                Intent intent = new Intent(getActivity(), PDFViewerActivity.class);
                intent.putExtra("pnumber", phone );
                startActivity(intent);
            }
        });

        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return v;}

    @Override
    public void onStop() {
        super.onStop();
        if(chk) {
            loadingDialog.dismissDialog();
        }
    }
}
