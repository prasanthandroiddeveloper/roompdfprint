package com.sss.rentalapp;

import android.Manifest;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class PdfActivity extends AppCompatActivity {

    String name,price;
    Bundle bundle;
    TextView tvname,tvprice;
    final static int REQUEST_CODE = 1232;
    Button btnCreatePDF;
    Button btnXMLtoPDF;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);
        tvname=findViewById(R.id.name);
        tvprice=findViewById(R.id.price);
        btnCreatePDF = findViewById(R.id.createPDF);
        btnCreatePDF.setOnClickListener(v -> createPDF());
        btnXMLtoPDF = findViewById(R.id.generatePDF);
        btnXMLtoPDF.setOnClickListener(v -> convertXmlToPdf());

        askPermissions();


        bundle=new Bundle();
        bundle=getIntent().getExtras();
        name=bundle.getString("name");
        price=bundle.getString("price");
        tvname.setText(name);
        tvprice.setText(price);

        Toast.makeText(this, name+"/n"+price, Toast.LENGTH_SHORT).show();
    }

    private void askPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
    }

    private void createPDF() {
        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(1080, 1920, 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);

        Canvas canvas = page.getCanvas();

        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setTextSize(42);

       // String text = name;
        float x = 500;
        float y = 900;

        canvas.drawText(name+""+price, x, y, paint);
      //  canvas.drawText(price, x, y, paint);
        document.finishPage(page);

        File downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        String fileName = "example.pdf";
        File file = new File(downloadsDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            document.writeTo(fos);
            document.close();
            fos.close();
            Toast.makeText(this, "Written Successfully!!!", Toast.LENGTH_SHORT).show();


            File files = new File(Environment.getExternalStorageDirectory(), "Download/example.pdf");
            Log.d("pdfFIle", "" + file);

            // Get the URI Path of file.
            Uri uriPdfPath = FileProvider.getUriForFile(this,  BuildConfig.APPLICATION_ID + ".provider", files);
            Log.d("pdfPath", "" + uriPdfPath);

            // Start Intent to View PDF from the Installed Applications.
            Intent pdfOpenIntent = new Intent(Intent.ACTION_VIEW);
            pdfOpenIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            pdfOpenIntent.setClipData(ClipData.newRawUri("", uriPdfPath));
            pdfOpenIntent.setDataAndType(uriPdfPath, "application/pdf");
            pdfOpenIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION |  Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            startActivity(pdfOpenIntent);

        } catch (FileNotFoundException e) {
            Log.d("mylog", "Error while writing " + e.toString());
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void convertXmlToPdf() {
        // Inflate the XML layout file
        View view = LayoutInflater.from(this).inflate(R.layout.activity_pdf, null);
        DisplayMetrics displayMetrics = new DisplayMetrics();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            this.getDisplay().getRealMetrics(displayMetrics);
        } else
            this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        view.measure(View.MeasureSpec.makeMeasureSpec(displayMetrics.widthPixels, View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(displayMetrics.heightPixels, View.MeasureSpec.EXACTLY));
        Log.d("mylog", "Width Now " + view.getMeasuredWidth());
        view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        // Create a new PdfDocument instance
        PdfDocument document = new PdfDocument();

        // Obtain the width and height of the view
        //int viewWidth = view.getMeasuredWidth();
        //int viewHeight = view.getMeasuredHeight();
        int viewWidth = 1080;
        int viewHeight = 1920;
        //Log.d("mylog", "Width: " + viewWidth);
        // Create a PageInfo object specifying the page attributes
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(viewWidth, viewHeight, 1).create();

        // Start a new page
        PdfDocument.Page page = document.startPage(pageInfo);

        // Get the Canvas object to draw on the page
        Canvas canvas = page.getCanvas();

        // Create a Paint object for styling the view
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);

        // Draw the view on the canvas
        view.draw(canvas);

        // Finish the page
        document.finishPage(page);

        // Specify the path and filename of the output PDF file
        File downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        String fileName = "exampleXML.pdf";
        File filePath = new File(downloadsDir, fileName);

        try {
            // Save the document to a file
            FileOutputStream fos = new FileOutputStream(filePath);
            document.writeTo(fos);
            document.close();
            fos.close();
            // PDF conversion successful
            Toast.makeText(this, "XML to PDF Conversion Successful", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
            // Error occurred while converting to PDF
        }
    }


}