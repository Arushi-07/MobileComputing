package com.example.mobilecomputing;

import android.graphics.Bitmap;
import android.os.AsyncTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UploadRequest extends AsyncTask<Object, String, String> {

    @Override
    protected String doInBackground(Object... params) {
        final Bitmap bitmap = (Bitmap)params[1];
        OkHttpClient client = new OkHttpClient();
        UUID uuid = UUID.randomUUID();
        final String fileName = uuid.toString() + ".jpeg";
        final RequestBody imageUploadBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", fileName,
                        RequestBody.create(MediaType.parse("image/jpg"), bitmapToByte(bitmap)))
                .build();
        final Request request = new Request.Builder().url(params[0].toString()).post(imageUploadBody).build();
        String responseString = "";
        try {
            Response response = client.newCall(request).execute();
            if(response.isSuccessful()) {
                responseString = "success";
            } else {
                responseString = "error";
            }
            response.close();

        } catch (IOException e) {
            e.printStackTrace();
            responseString = "error";
        }
        return responseString;
    }

    public byte[] bitmapToByte(Bitmap bmp) {
        final ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        return stream.toByteArray();
    }
}