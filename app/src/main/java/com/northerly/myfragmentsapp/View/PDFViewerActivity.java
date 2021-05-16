package com.northerly.myfragmentsapp.View;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.fonts.Font;
import android.graphics.pdf.PdfRenderer;
import android.os.Binder;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.DashedBorder;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.northerly.myfragmentsapp.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.BitSet;

import uk.co.senab.photoview.PhotoViewAttacher;

public class PDFViewerActivity extends AppCompatActivity {
    PhotoViewAttacher pAttacher;
    ImageView imageView;
    PdfRenderer pdfRenderer;
    PdfRenderer.Page currentPage;
    ParcelFileDescriptor parcelFileDescriptor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfviewer);

        imageView = findViewById(R.id.pdfImage);
        pAttacher = new PhotoViewAttacher(imageView);
        pAttacher.update();

        try {
            createpdf();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void createpdf() throws FileNotFoundException {
        File file = new File(getExternalFilesDir("MyPDF"),"/TaxInvoice.pdf");
        OutputStream outputStream = new FileOutputStream(file);
        PdfWriter pdfWriter = new PdfWriter(file);
        PdfDocument pdfDocument = new PdfDocument(pdfWriter);
        Document document = new Document(pdfDocument);


        document.setMargins(0f,33f, 0f, 33f);
        document.setFontSize(8);
        float[] columnWidth1 = {80, 400, 100};
//Table 1
        Table table1 = new Table(columnWidth1);

//row1
        Drawable d = getDrawable(R.drawable.kimobitlity);
        Bitmap bitmap = ((BitmapDrawable)d).getBitmap();
        ByteArrayOutputStream stream = new com.itextpdf.io.source.ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] bitmapData = stream.toByteArray();
        ImageData imageData = ImageDataFactory.create(bitmapData);
        Image image = new Image(imageData);
        image.setWidth(70).setHeight(60);

        table1.addCell((new Cell(4,1).add(image).setBorder(Border.NO_BORDER)));
        table1.addCell((new Cell().add(new Paragraph("\n").setFontSize(4)).setBorder(Border.NO_BORDER)));

         d = getDrawable(R.drawable.tvs);
         bitmap = ((BitmapDrawable)d).getBitmap();
         stream = new com.itextpdf.io.source.ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
         bitmapData = stream.toByteArray();
         imageData = ImageDataFactory.create(bitmapData);
         image = new Image(imageData);
        image.setWidth(90).setHeight(30);

        table1.addCell((new Cell(2,1).add(image).setBorder(Border.NO_BORDER)));

//row2
//        table1.addCell((new Cell().add(new Paragraph("Ki Mobility Solutions Private Limited,"))));
        table1.addCell((new Cell(3,1).add(new Paragraph("Ki Mobility Solutions Private Limited,\n" +
                "(Subsidiary of TVS Automobile Solutions PVT Ltd.,)\n" +
                "B18, FIRST MAIN ROAD, AMBATTUR INDUSTRIAL ESTATE\n" +
                "Chennai,Tamil Nadu,6000058,7338747970").setFontSize(10).setFixedLeading(12f)).setBorder(Border.NO_BORDER).setPadding(0)));
//        table1.addCell((new Cell().add(new Paragraph("B18, FIRST MAIN ROAD, AMBATTUR INDUSTRIAL ESTATE"))));
//row3
//        table1.addCell((new Cell().add(new Paragraph("Ki Mobility Solutions Private Limited,"))));
//        table1.addCell((new Cell().add(new Paragraph("B18, FIRST MAIN ROAD, AMBATTUR INDUSTRIAL ESTATE").setFontSize(10))));
//        table1.addCell((new Cell().add(new Paragraph(""))));
//row4
//        table1.addCell((new Cell().add(new Paragraph("Ki Mobility Solutions Private Limited,"))));
//        table1.addCell((new Cell().add(new Paragraph("Chennai,Tamil Nadu,6000058,7338747970").setFontSize(10))));
//        table1.addCell((new Cell().add(new Paragraph(""))));


        float[] columnWidth2 = {100, 10, 120, 125, 100, 140};
//Table 2
        Table table2 = new Table(columnWidth2);
//row1
        table2.addCell((new Cell().add(new Paragraph("Pan\n" + "GSTIN\n" + "CIN").setFontSize(8)).setPadding(0).setBorder(Border.NO_BORDER)));
        table2.addCell((new Cell().add(new Paragraph(":\n" + ":\n" + ":").setFontSize(8)).setPadding(0).setBorder(Border.NO_BORDER)));
        table2.addCell((new Cell().add(new Paragraph("\n33AAGCM0329K1ZM").setTextAlignment(TextAlignment.CENTER).setFontSize(8)).setPadding(0).setBorder(Border.NO_BORDER)));
        table2.addCell((new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER)));
        table2.addCell((new Cell().add(new Paragraph("Job Card no\n Bill Date\n Bill Type").setTextAlignment(TextAlignment.RIGHT).setFontSize(8)).setPadding(0).setBorder(Border.NO_BORDER)));
        table2.addCell((new Cell().add(new Paragraph(": RJC-KAMB122-000402\n"+": 11/05/2021\n: CASH BILL").setFontSize(8)).setPadding(0).setPaddingLeft(20f).setBorder(Border.NO_BORDER)));

//row2
        table2.addCell((new Cell().add(new Paragraph("Invoice Number").setFontSize(8)).setPadding(0).setBorder(Border.NO_BORDER)));
        table2.addCell((new Cell().add(new Paragraph(":").setFontSize(8)).setPadding(0).setBorder(Border.NO_BORDER)));
        table2.addCell((new Cell().add(new Paragraph("CLS-KAMB122-00374").setFontSize(8).setTextAlignment(TextAlignment.CENTER)).setPadding(0).setBorder(Border.NO_BORDER)));
        table2.addCell((new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER)));
        table2.addCell((new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER)));
        table2.addCell((new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER)));

//row3
        table2.addCell((new Cell().add(new Paragraph("IRN Number").setFontSize(8)).setPadding(0).setBorder(Border.NO_BORDER)));
        table2.addCell((new Cell().add(new Paragraph(":").setFontSize(8)).setPadding(0).setBorder(Border.NO_BORDER)));
        table2.addCell((new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER)));
        table2.addCell((new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER)));
        table2.addCell((new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER)));
        table2.addCell((new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER)));

//row4
        table2.addCell((new Cell().add(new Paragraph("IRN Ack Date").setFontSize(8)).setPadding(0).setBorder(Border.NO_BORDER)));
        table2.addCell((new Cell().add(new Paragraph(":").setFontSize(8)).setPadding(0).setBorder(Border.NO_BORDER)));
        table2.addCell((new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER)));
        table2.addCell((new Cell().add(new Paragraph("\n" +
                "Tax Invoice").setFontSize(8).setBold().setUnderline().setTextAlignment(TextAlignment.CENTER)).setPadding(0).setBorder(Border.NO_BORDER)));
        table2.addCell((new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER)));
        table2.addCell((new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER)));

        float[] columnWidth3 = {150,150,150,150};
//Table 3
        Table table3 = new Table(columnWidth3);
//        Border b1 = new SolidBorder(2);
//row1
        table3.addCell((new Cell(1,2).add(new Paragraph("BILLING TO").setTextAlignment(TextAlignment.CENTER).setBold()).setPadding(0)));
//        table3.addCell((new Cell().add(new Paragraph(""))));
        table3.addCell((new Cell(1,2).add(new Paragraph("SHIP TO / Place of supply").setTextAlignment(TextAlignment.CENTER).setBold()).setPadding(0)));
//        table3.addCell((new Cell().add(new Paragraph(""))));
//row2
        table3.addCell((new Cell().add(new Paragraph("Insurance/Cust code")).setPadding(0)));
        table3.addCell((new Cell().add(new Paragraph("KAMB1-1576")).setPadding(0)));
        table3.addCell((new Cell().add(new Paragraph("Customer Code")).setPadding(0)));
        table3.addCell((new Cell().add(new Paragraph("KAMB1-1576")).setPadding(0)));
//row3
        table3.addCell((new Cell().add(new Paragraph("Insurance/Cust name")).setPadding(0)));
        table3.addCell((new Cell().add(new Paragraph("Velu prasanth Mr")).setPadding(0)));
        table3.addCell((new Cell().add(new Paragraph("Customer Name")).setPadding(0)));
        table3.addCell((new Cell().add(new Paragraph("Velu prasanth Mr")).setPadding(0)));
//row4
        table3.addCell((new Cell().add(new Paragraph("Insurer/Cust GSTIN")).setPadding(0)));
        table3.addCell((new Cell().add(new Paragraph(""))));
        table3.addCell((new Cell().add(new Paragraph("Customer GSTIN")).setPadding(0)));
        table3.addCell((new Cell().add(new Paragraph(""))));
//row5
        table3.addCell((new Cell().add(new Paragraph("Billing Address")).setPadding(0)));
        table3.addCell((new Cell().add(new Paragraph("anna nagar,karajar street,5th street,Pattabiram, Chennai\n" +
                "\n" +
                "Chennai, Tamil Nadu 600054")).setPadding(0)));
        table3.addCell((new Cell().add(new Paragraph("Delivery Address")).setPadding(0)));
        table3.addCell((new Cell().add(new Paragraph("anna nagar,karajar street,5th street,Pattabiram, Chennai\n" +
                "\n" +
                "Chennai, Tamil Nadu 600054\n" +
                "9751159962")).setPadding(0)));


        float[] columnWidth4 = {150,150,150,150};
//Table 4
        Table table4 = new Table(columnWidth4);
//row1
        table4.addCell((new Cell(1,2).add(new Paragraph("VEH/ENG DETAILS").setTextAlignment(TextAlignment.CENTER).setBold()).setPadding(0)));
//        table4.addCell((new Cell().add(new Paragraph(""))));
        table4.addCell((new Cell(1,2).add(new Paragraph("JOBCARD DETAILS").setTextAlignment(TextAlignment.CENTER).setBold()).setPadding(0)));
//        table4.addCell((new Cell().add(new Paragraph(""))));
//row2
        table4.addCell((new Cell().add(new Paragraph("Vehicle")).setPadding(0)));
        table4.addCell((new Cell().add(new Paragraph("OTHERS PETROL / DIESEL ALL\n" +
                "TYPES")).setPadding(0)));
        table4.addCell((new Cell().add(new Paragraph("Creation")).setPadding(0)));
        table4.addCell((new Cell().add(new Paragraph("2021-05-10 23:06:48")).setPadding(0)));
//row3
        table4.addCell((new Cell().add(new Paragraph("Registration No")).setPadding(0)));
        table4.addCell((new Cell().add(new Paragraph("TN06X6845")).setPadding(0)));
        table4.addCell((new Cell().add(new Paragraph("Repair Type")).setPadding(0)));
        table4.addCell((new Cell().add(new Paragraph("Paid Service")).setPadding(0)));
//row4
        table4.addCell((new Cell().add(new Paragraph("Chassis No")).setPadding(0)));
        table4.addCell((new Cell().add(new Paragraph("TN06X6845")).setPadding(0)));
        table4.addCell((new Cell().add(new Paragraph("Service Type")).setPadding(0)));
        table4.addCell((new Cell().add(new Paragraph("Running Repair")).setPadding(0)));
//row5
        table4.addCell((new Cell().add(new Paragraph("Engine No")).setPadding(0)));
        table4.addCell((new Cell().add(new Paragraph("TN06X6845")).setPadding(0)));
        table4.addCell((new Cell().add(new Paragraph("Km Reading")).setPadding(0)));
        table4.addCell((new Cell().add(new Paragraph("12000")).setPadding(0)));
//row6
        table4.addCell((new Cell().add(new Paragraph("Contact person")).setPadding(0)));
        table4.addCell((new Cell().add(new Paragraph(""))));
        table4.addCell((new Cell().add(new Paragraph("Customer state code")).setPadding(0)));
        table4.addCell((new Cell().add(new Paragraph("33")).setPadding(0)));
//row6
        table4.addCell((new Cell().add(new Paragraph("Service Engineer")).setPadding(0)));
        table4.addCell((new Cell().add(new Paragraph("MOHAN RAJ .")).setPadding(0)));
        table4.addCell((new Cell().add(new Paragraph("Is reverse charge apply")).setPadding(0)));
        table4.addCell((new Cell().add(new Paragraph("No")).setPadding(0)));

        float[] columnWidth5 = {8, 130, 33, 23, 53, 33, 53, 23, 43, 23, 43, 23, 43, 63};
//Table 5
        Table table5 = new Table(columnWidth5);
//row1
        table5.addCell((new Cell().add(new Paragraph("SN\nO").setBold().setTextAlignment(TextAlignment.CENTER)).setPadding(0)));
        table5.addCell((new Cell().add(new Paragraph("DESCRIPTION").setBold().setTextAlignment(TextAlignment.CENTER)).setPadding(0)));
        table5.addCell((new Cell().add(new Paragraph("HSN/SAC").setBold().setTextAlignment(TextAlignment.CENTER)).setPadding(0)));
        table5.addCell((new Cell().add(new Paragraph("QTY").setBold().setTextAlignment(TextAlignment.CENTER)).setPadding(0)));
        table5.addCell((new Cell().add(new Paragraph("TOT VAL").setBold().setTextAlignment(TextAlignment.CENTER)).setPadding(0)));
        table5.addCell((new Cell().add(new Paragraph("DISC").setBold().setTextAlignment(TextAlignment.CENTER)).setPadding(0)));
        table5.addCell((new Cell().add(new Paragraph("Tax Val after DISC").setBold().setTextAlignment(TextAlignment.CENTER)).setPadding(0)));
        table5.addCell((new Cell().add(new Paragraph("CGS\nT %").setBold().setTextAlignment(TextAlignment.CENTER)).setPadding(0)));
        table5.addCell((new Cell().add(new Paragraph("CGST").setBold().setTextAlignment(TextAlignment.CENTER)).setPadding(0)));
        table5.addCell((new Cell().add(new Paragraph("SGS\nT%").setBold().setTextAlignment(TextAlignment.CENTER)).setPadding(0)));
        table5.addCell((new Cell().add(new Paragraph("SGST").setBold().setTextAlignment(TextAlignment.CENTER)).setPadding(0)));
        table5.addCell((new Cell().add(new Paragraph("IGS\nT%").setBold().setTextAlignment(TextAlignment.CENTER)).setPadding(0)));
        table5.addCell((new Cell().add(new Paragraph("IGST").setBold().setTextAlignment(TextAlignment.CENTER)).setPadding(0)));
        table5.addCell((new Cell().add(new Paragraph("AMOUNT").setBold().setTextAlignment(TextAlignment.CENTER)).setPadding(0)));
//row2
        table5.addCell((new Cell().add(new Paragraph("1").setTextAlignment(TextAlignment.CENTER)).setPadding(0)));
        table5.addCell((new Cell().add(new Paragraph("General Service").setTextAlignment(TextAlignment.CENTER)).setPadding(0)));
        table5.addCell((new Cell().add(new Paragraph("998729").setTextAlignment(TextAlignment.CENTER)).setPadding(0)));
        table5.addCell((new Cell().add(new Paragraph("1.00").setTextAlignment(TextAlignment.RIGHT)).setPadding(0)));
        table5.addCell((new Cell().add(new Paragraph("999.00").setTextAlignment(TextAlignment.RIGHT)).setPadding(0)));
        table5.addCell((new Cell().add(new Paragraph("24.80").setTextAlignment(TextAlignment.RIGHT)).setPadding(0)));
        table5.addCell((new Cell().add(new Paragraph("974.20").setTextAlignment(TextAlignment.RIGHT)).setPadding(0)));
        table5.addCell((new Cell().add(new Paragraph("9%").setTextAlignment(TextAlignment.CENTER)).setPadding(0)));
        table5.addCell((new Cell().add(new Paragraph("87.68").setTextAlignment(TextAlignment.RIGHT)).setPadding(0)));
        table5.addCell((new Cell().add(new Paragraph("9%").setTextAlignment(TextAlignment.CENTER)).setPadding(0)));
        table5.addCell((new Cell().add(new Paragraph("87.68").setTextAlignment(TextAlignment.RIGHT)).setPadding(0)));
        table5.addCell((new Cell().add(new Paragraph("0%").setTextAlignment(TextAlignment.CENTER)).setPadding(0)));
        table5.addCell((new Cell().add(new Paragraph("0.00").setTextAlignment(TextAlignment.RIGHT)).setPadding(0)));
        table5.addCell((new Cell().add(new Paragraph("1,149.56").setTextAlignment(TextAlignment.RIGHT)).setPadding(0)));
//row3
        table5.addCell((new Cell(1,3).add(new Paragraph("SUB TOTAL").setBold().setTextAlignment(TextAlignment.RIGHT)).setPadding(0)));
        table5.addCell((new Cell().add(new Paragraph("1.00").setTextAlignment(TextAlignment.RIGHT)).setPadding(0)));
        table5.addCell((new Cell().add(new Paragraph("").setTextAlignment(TextAlignment.RIGHT)).setPadding(0)));
        table5.addCell((new Cell().add(new Paragraph("").setTextAlignment(TextAlignment.RIGHT)).setPadding(0)));
        table5.addCell((new Cell().add(new Paragraph("974.20").setTextAlignment(TextAlignment.RIGHT)).setPadding(0)));
        table5.addCell((new Cell().add(new Paragraph("").setTextAlignment(TextAlignment.CENTER)).setPadding(0)));
        table5.addCell((new Cell().add(new Paragraph("87.68").setTextAlignment(TextAlignment.RIGHT)).setPadding(0)));
        table5.addCell((new Cell().add(new Paragraph("").setTextAlignment(TextAlignment.CENTER)).setPadding(0)));
        table5.addCell((new Cell().add(new Paragraph("87.68").setTextAlignment(TextAlignment.RIGHT)).setPadding(0)));
        table5.addCell((new Cell().add(new Paragraph("").setTextAlignment(TextAlignment.CENTER)).setPadding(0)));
        table5.addCell((new Cell().add(new Paragraph("0.00").setTextAlignment(TextAlignment.RIGHT)).setPadding(0)));
        table5.addCell((new Cell().add(new Paragraph("1,149.56").setTextAlignment(TextAlignment.RIGHT)).setPadding(0)));
//row4
        table5.addCell((new Cell(1,13).add(new Paragraph("Total GST").setBold().setTextAlignment(TextAlignment.RIGHT)).setPadding(0)));
        table5.addCell((new Cell().add(new Paragraph("175.36").setTextAlignment(TextAlignment.RIGHT)).setPadding(0)));
//row5
        table5.addCell((new Cell(1,13).add(new Paragraph("Round off").setBold().setTextAlignment(TextAlignment.RIGHT)).setPadding(0)));
        table5.addCell((new Cell().add(new Paragraph("0.44").setTextAlignment(TextAlignment.RIGHT)).setPadding(0)));
//row6
        table5.addCell((new Cell(1,13).add(new Paragraph("Grand Total of Labours").setBold().setTextAlignment(TextAlignment.RIGHT)).setPadding(0)));
        table5.addCell((new Cell().add(new Paragraph("1,150.00").setTextAlignment(TextAlignment.RIGHT)).setPadding(0)));

        float[] columnWidth6 = {8, 130, 23, 23, 10, 53, 33, 53, 23, 43, 23, 43, 23, 43, 63};
//Table 6
        Table table6 = new Table(columnWidth6);
//row1
        table6.addCell((new Cell().add(new Paragraph("SN\nO").setBold().setTextAlignment(TextAlignment.CENTER)).setPadding(0)));
        table6.addCell((new Cell().add(new Paragraph("PART NAME").setBold().setTextAlignment(TextAlignment.CENTER)).setPadding(0)));
        table6.addCell((new Cell().add(new Paragraph("HSN CODE").setBold().setTextAlignment(TextAlignment.CENTER)).setPadding(0)));
        table6.addCell((new Cell().add(new Paragraph("QTY").setBold().setTextAlignment(TextAlignment.CENTER)).setPadding(0)));
        table6.addCell((new Cell().add(new Paragraph("PER QTY RATE").setBold().setTextAlignment(TextAlignment.CENTER)).setPadding(0)));
        table6.addCell((new Cell().add(new Paragraph("Taxable Value").setBold().setTextAlignment(TextAlignment.CENTER)).setPadding(0)));
        table6.addCell((new Cell().add(new Paragraph("DISC").setBold().setTextAlignment(TextAlignment.CENTER)).setPadding(0)));
        table6.addCell((new Cell().add(new Paragraph("Tax Val after DISC").setBold().setTextAlignment(TextAlignment.CENTER)).setPadding(0)));
        table6.addCell((new Cell().add(new Paragraph("CG ST\n%").setBold().setTextAlignment(TextAlignment.CENTER)).setPadding(0)));
        table6.addCell((new Cell().add(new Paragraph("CGST").setBold().setTextAlignment(TextAlignment.CENTER)).setPadding(0)));
        table6.addCell((new Cell().add(new Paragraph("SG ST\n%").setBold().setTextAlignment(TextAlignment.CENTER)).setPadding(0)));
        table6.addCell((new Cell().add(new Paragraph("SGST").setBold().setTextAlignment(TextAlignment.CENTER)).setPadding(0)));
        table6.addCell((new Cell().add(new Paragraph("IG ST\n%").setBold().setTextAlignment(TextAlignment.CENTER)).setPadding(0)));
        table6.addCell((new Cell().add(new Paragraph("IGST").setBold().setTextAlignment(TextAlignment.CENTER)).setPadding(0)));
        table6.addCell((new Cell().add(new Paragraph("AMOUNT").setBold().setTextAlignment(TextAlignment.CENTER)).setPadding(0)));
//row3
        table6.addCell((new Cell(1,3).add(new Paragraph("SUB TOTAL").setBold().setTextAlignment(TextAlignment.RIGHT)).setPadding(0)));
        table6.addCell((new Cell().add(new Paragraph("0.00").setTextAlignment(TextAlignment.RIGHT)).setPadding(0)));
        table6.addCell((new Cell().add(new Paragraph("").setTextAlignment(TextAlignment.RIGHT)).setPadding(0)));
        table6.addCell((new Cell().add(new Paragraph("").setTextAlignment(TextAlignment.RIGHT)).setPadding(0)));
        table6.addCell((new Cell().add(new Paragraph("").setTextAlignment(TextAlignment.RIGHT)).setPadding(0)));
        table6.addCell((new Cell().add(new Paragraph("0.00").setTextAlignment(TextAlignment.CENTER)).setPadding(0)));
        table6.addCell((new Cell().add(new Paragraph("").setTextAlignment(TextAlignment.RIGHT)).setPadding(0)));
        table6.addCell((new Cell().add(new Paragraph("0.00").setTextAlignment(TextAlignment.CENTER)).setPadding(0)));
        table6.addCell((new Cell().add(new Paragraph("").setTextAlignment(TextAlignment.RIGHT)).setPadding(0)));
        table6.addCell((new Cell().add(new Paragraph("0.00").setTextAlignment(TextAlignment.CENTER)).setPadding(0)));
        table6.addCell((new Cell().add(new Paragraph("").setTextAlignment(TextAlignment.RIGHT)).setPadding(0)));
        table6.addCell((new Cell().add(new Paragraph("0.00").setTextAlignment(TextAlignment.RIGHT)).setPadding(0)));
        table6.addCell((new Cell().add(new Paragraph("0.00").setTextAlignment(TextAlignment.RIGHT)).setPadding(0)));
//row4
        table6.addCell((new Cell(1,14).add(new Paragraph("Total GST").setBold().setTextAlignment(TextAlignment.RIGHT)).setPadding(0)));
        table6.addCell((new Cell().add(new Paragraph("0.00").setTextAlignment(TextAlignment.RIGHT)).setPadding(0)));
//row5
        table6.addCell((new Cell(1,14).add(new Paragraph("Round off").setBold().setTextAlignment(TextAlignment.RIGHT)).setPadding(0)));
        table6.addCell((new Cell().add(new Paragraph("0.00").setTextAlignment(TextAlignment.RIGHT)).setPadding(0)));
//row6
        table6.addCell((new Cell(1,14).add(new Paragraph("Grand Total of Spares").setBold().setTextAlignment(TextAlignment.RIGHT)).setPadding(0)));
        table6.addCell((new Cell().add(new Paragraph("0.00").setTextAlignment(TextAlignment.RIGHT)).setPadding(0)));
//row6
        table6.addCell((new Cell(1,14).add(new Paragraph("GRAND TOTAL").setBold().setTextAlignment(TextAlignment.RIGHT)).setPadding(0)));
        table6.addCell((new Cell().add(new Paragraph("1,150.00").setTextAlignment(TextAlignment.RIGHT)).setPadding(0)));

        float[] columnWidth7 = {300,300};
//Table 7
        Table table7 = new Table(columnWidth7);
//row1
        table7.addCell((new Cell().add(new Paragraph("ADVICE").setTextAlignment(TextAlignment.CENTER)).setPadding(0)));
        table7.addCell((new Cell().add(new Paragraph("SERVICE ENGINEER REMARKS").setTextAlignment(TextAlignment.CENTER)).setPadding(0)));
//row2
        table7.addCell((new Cell().add(new Paragraph("General Service 999").setTextAlignment(TextAlignment.CENTER)).setPadding(0)));
        table7.addCell((new Cell().add(new Paragraph(""))));

        float[] columnWidth8 = {100, 400, 100};
//Table 8
        Table table8 = new Table(columnWidth8);
        table8.setFixedPosition(33, 0, 529);
//row1
         d = getDrawable(R.drawable.tvs_round);
         bitmap = ((BitmapDrawable)d).getBitmap();
         stream = new com.itextpdf.io.source.ByteArrayOutputStream();
         bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
         bitmapData = stream.toByteArray();
         imageData = ImageDataFactory.create(bitmapData);
         image = new Image(imageData);
         image.setWidth(20).setHeight(20);

        table8.addCell((new Cell().add(image.setHorizontalAlignment(HorizontalAlignment.CENTER)).setBorder(Border.NO_BORDER)));
        table8.addCell((new Cell().add(new Paragraph("Regd. Off. No. 10, Jawahar Road, Madurai – 625 002\n" +
                "Mail id: enquiry@tvs.in, Website: www.tvsautomobilesolutions.com\n" +
                "ki Mobility Solutions Private Limited (formerly Peninsula Auto Parts Private Limited)").setFixedLeading(8f).setTextAlignment(TextAlignment.CENTER)).setPadding(0).setBorder(Border.NO_BORDER)));
        table8.addCell((new Cell().add(new Paragraph("1/1")).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER)));








        document.add(table1);

        document.add(new Paragraph("\n").setFontSize(3f));
        PdfCanvas canvas = new PdfCanvas(pdfDocument.getPage(1));
        canvas.moveTo(33, 775)
                .lineTo(562,775)
                .closePathStroke();

        document.add(table2);
        document.add(table3);
        document.add(new Paragraph("\n").setFontSize(3f));
        document.add(table4);
        document.add(new Paragraph("\n").setFontSize(3f));
        document.add(table5);
        document.add(new Paragraph("\n").setFontSize(3f));
        document.add(table6);
        document.add(new Paragraph("\n").setFontSize(3f));
        document.add(new Paragraph("AMOUNT IN WORDS: Rupees One Thousand One Hundred Fifty Only").setBold().setMargin(0));
        document.add(new Paragraph("E & O.E\n" +
                "I hereby accept to pay the above mentioned amount by Cash / DD before taking delivery of the vehicle.\n" +
                "Certified that goods covered by this bill suffered taxes at our hand/already. Pl turnover for the condition of sale. Goods once sold will not taken back.").setFixedLeading(8f).setMargin(0));
        document.add(new Paragraph("\n").setFontSize(3f));
        document.add(table7);

        canvas
//                .setStrokeColor(ColorConstants.BLACK)
//                .setLineWidth(10f)
                .moveTo(33, 90)
                .lineTo(133, 90)
                .closePathStroke();
        canvas.moveTo(562, 90)
                .lineTo(462,90)
                .closePathStroke();
//        float f = pdfDocument.getPage(1).getPageSize().getHeight();
        document.add(new Paragraph("Customer Signature").setFixedPosition(33,75,200)
                            .setBold());
        document.add(new Paragraph("Authorized Signature").setFixedPosition(485,75,200)
                            .setBold());

        canvas.moveTo(33, 30)
                .lineTo(562,30)
                .closePathStroke();

        document.add(table8);

        document.close();
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
