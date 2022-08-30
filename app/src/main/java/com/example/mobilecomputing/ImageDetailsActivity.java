package com.example.mobilecomputing;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.security.NetworkSecurityPolicy;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImageDetailsActivity extends AppCompatActivity {
    public static final String REQUEST_URL = "http://192.168.0.8:5000/upload_image";
    Bitmap imageBitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(this, "in image details", Toast.LENGTH_LONG).show();
        setContentView(R.layout.image_details);

        Bundle extras = getIntent().getExtras();
        Bitmap encodedImage ;
        if (extras != null) {
            encodedImage = (Bitmap)extras.get("image_encoded");
            imageBitmap = encodedImage;
            ImageView imageView = findViewById(R.id.image_preview);
            imageView.setImageBitmap(encodedImage);
            //The key argument here must match that used in the other activity
        }
        Spinner spinner = (Spinner) findViewById(R.id.tag_spinner);
        spinner.setOnItemSelectedListener(new SpinnerActivity());

    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void uploadImage(View view) throws IOException {
        Spinner spinner = (Spinner) findViewById(R.id.tag_spinner);
        String tag = spinner.getSelectedItem().toString();
        new RequestTask().execute(REQUEST_URL, imageBitmap, tag);

    }


}
