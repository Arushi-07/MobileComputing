package com.example.mobilecomputing;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

public class ImageSaveFinal {

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void saveFile(Bitmap myBitmap, Context ctx, int predictedNum)
    {
        File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "mobile_computing/"+predictedNum);
        if(!dir.exists()){
            dir.mkdirs();
        } else {
            Log.d("debug", "dir exists");
        }



        try {
            String filePath = dir.getPath() + "/"+ System.currentTimeMillis() + ".jpg";
            ByteArrayOutputStream outstream = new ByteArrayOutputStream();
            myBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outstream);
            byte[] byteArray = outstream.toByteArray();
            Log.d("filePath", filePath);
            File image = new File(filePath);
            if(image.createNewFile()) {
                Log.d("debug", "file successfully created");
                FileOutputStream stream = new FileOutputStream(image);
                stream.write(byteArray);
                stream.flush();
                stream.close();
                Toast.makeText(ctx, "Save Completed", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(ctx, "File Not Saved", Toast.LENGTH_SHORT).show();
                Log.d("debug", "file not created");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static byte[] bitmapToByte(Bitmap bmp) {
        final ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }
}
