package com.example.mobilecomputing;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.ExecutionException;

public class ImageDetailsActivity extends AppCompatActivity {
    public static final String REQUEST_URL = "http://192.168.0.8:5000/upload_image";
    Bitmap imageBitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_details);
        Bundle data = getIntent().getExtras();
        if (data != null) {
            this.imageBitmap = (Bitmap)data.get("image_bitmap");
            ImageView imageView = findViewById(R.id.image_preview);
            imageView.setImageBitmap(this.imageBitmap);
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void uploadImage(View view) throws ExecutionException, InterruptedException {
        Spinner spinner = (Spinner) findViewById(R.id.tag_spinner);
        String tag = spinner.getSelectedItem().toString();
        String response = new UploadRequest().execute(REQUEST_URL, imageBitmap, tag).get();
        if(response.equalsIgnoreCase("success")) {
            Toast.makeText(this, "Upload Successful!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Upload Unsuccessful, Please try again!", Toast.LENGTH_LONG).show();
        }

    }


}
