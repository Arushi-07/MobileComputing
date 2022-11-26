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
import java.io.FileWriter;
import java.nio.charset.Charset;

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

        Log.d("dir_path", dir.getPath());

        try {
            String data1 = dir.getPath() + "/"+ System.currentTimeMillis();
            Log.d("data1 path: " , data1);

            ByteArrayOutputStream outstream = new ByteArrayOutputStream();
            myBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outstream);
            byte[] byteArray = outstream.toByteArray();

            Charset charset = Charset.forName("UTF-8");

            File gpxfile = new File(dir.getPath(), "hello.png");
            FileWriter writer = new FileWriter(gpxfile);
            writer.append(new String(bitmapToByte(myBitmap), charset));
            writer.flush();
            writer.close();
            Log.d("debug", "image saved");
            Log.d("debug",gpxfile.getAbsolutePath());
            Toast.makeText(ctx, "Save Completed", Toast.LENGTH_SHORT).show();
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
