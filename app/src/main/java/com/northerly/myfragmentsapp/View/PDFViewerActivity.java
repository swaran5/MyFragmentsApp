package com.northerly.myfragmentsapp.View;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.pdf.PdfRenderer;
import android.os.Binder;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.northerly.myfragmentsapp.R;

import java.io.File;
import java.io.IOException;
import java.util.BitSet;

public class PDFViewerActivity extends AppCompatActivity {
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfviewer);

        imageView = findViewById(R.id.pdfImage);
        int reqWidth = imageView.getMaxWidth();
        int reqHeight = imageView.getMaxHeight();

        Bitmap.Config conf = Bitmap.Config.ARGB_8888; // see other conf types
        Bitmap bmp = Bitmap.createBitmap(reqWidth, reqHeight, conf);

//        Bitmap bmp = Bitmap.createBitmap(imageView.getDrawingCache());

        File file = new File(getExternalFilesDir("MyPDF"),"/TaxInvoice.pdf");

        try {
            PdfRenderer  pdfRenderer = new PdfRenderer(ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY));
            Matrix m = imageView.getImageMatrix();
            Rect rect = new Rect(0,  0, reqWidth, reqHeight);
            pdfRenderer.openPage(1).render(bmp, rect, m, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);
            imageView.setImageMatrix(m);
            imageView.setImageBitmap(bmp);
            imageView.invalidate();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
