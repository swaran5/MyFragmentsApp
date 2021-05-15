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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.BitSet;

public class PDFViewerActivity extends AppCompatActivity {
    ImageView imageView;
    PdfRenderer pdfRenderer;
    PdfRenderer.Page currentPage;
    ParcelFileDescriptor parcelFileDescriptor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfviewer);

        imageView = findViewById(R.id.pdfImage);
    }

    @Override
    protected void onStart() {
        super.onStart();
        initRender();
        renderPage();
    }
   public void initRender(){
       try {
           File file = new File( getExternalFilesDir("MyPDF"),"TaxInvoice.pdf");
           parcelFileDescriptor = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY);
           pdfRenderer = new PdfRenderer(parcelFileDescriptor);

       } catch (FileNotFoundException e) {
           e.printStackTrace();
       } catch (IOException e) {
           e.printStackTrace();
       }
   }

    public void renderPage(){

        currentPage = pdfRenderer.openPage(0);
        Bitmap bitmap = Bitmap.createBitmap(currentPage.getWidth(), currentPage.getHeight(), Bitmap.Config.ARGB_8888);
        currentPage.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);
        imageView.setImageBitmap(bitmap);

    }

    @Override
    protected void onPause() {
        if(isFinishing()) {
            if(currentPage != null){
                currentPage.close();
            }
            try {
                parcelFileDescriptor.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            pdfRenderer.close();
        }
        super.onPause();
    }
}
