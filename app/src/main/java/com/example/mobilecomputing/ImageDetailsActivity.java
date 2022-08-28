package com.example.mobilecomputing;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ImageDetailsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(this, "in image details", Toast.LENGTH_LONG).show();
        setContentView(R.layout.image_details);

        Bundle extras = getIntent().getExtras();
        String encodedImage = "";
        if (extras != null) {
            encodedImage = extras.getString("image_encoded");
            //The key argument here must match that used in the other activity
        }
        Log.d("image base64", encodedImage);
        byte[] imageAsBytes = Base64.decode(encodedImage.getBytes(), Base64.DEFAULT);
        ImageView imageView = findViewById(R.id.image_preview);
        imageView.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));
    }
}
