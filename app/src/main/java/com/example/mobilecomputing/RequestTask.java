package com.example.mobilecomputing;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RequestTask extends AsyncTask<Object, String, String> {

    @Override
    protected String doInBackground(Object... uri) {
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", "file_name",
                        RequestBody.create(MediaType.parse("text/plain"), bitmapToByte((Bitmap)uri[1])))
                .addFormDataPart("tag", uri[2].toString())
                .build();
        Request request = new Request.Builder().url(uri[0].toString()).post(formBody).build();
        try {
            Response response = client.newCall(request).execute();
            Log.d("http_debug", "response_message: " + response.message());
            if(response.isSuccessful()) {
                Log.d("http_debug", "successful");
            } else {
                Log.d("http_debug", "not successful");
            }
            response.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
    public byte[] bitmapToByte(Bitmap bmp) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
    }
}