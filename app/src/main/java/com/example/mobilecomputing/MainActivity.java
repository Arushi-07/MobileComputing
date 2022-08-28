package com.example.mobilecomputing;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Base64;
import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
public class MainActivity extends AppCompatActivity {
    private static final int CAMERA_REQUEST = 1888;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkPermission();
        //requestPermissions(new String[]{Manifest.permission.CAMERA},MY_CAMERA_PERMISSION_CODE);
        setContentView(R.layout.activity_main);
    }

    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            Log.d("permission", "NOT granted");
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_CAMERA_PERMISSION_CODE);
        }else {
            Log.d("permission", "granted");
        }
    }

    /** Called when the user taps the Send button */
    public void openCameraActivity(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, CameraActivity.class);
//        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
//        intent.putExtra("facing", "CAMERA_FACING_FRONT");
        startActivityForResult(intent, CAMERA_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap image;
        String encodedImage;
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            image = (Bitmap) data.getExtras().get("data");
            encodedImage = getEncodedString(image);
            Intent intent = new Intent(MainActivity.this, ImageDetailsActivity.class);
            intent.putExtra("image_encoded", encodedImage);
            startActivity(intent);
            //setDataToDataBase();
        }

    }
    private String getEncodedString(Bitmap bitmap) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
        byte[] imageArr = os.toByteArray();
        return Base64.encodeToString(imageArr, Base64.URL_SAFE);
    }

    /** Check if this device has a camera */
    private boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            }
            else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }
}