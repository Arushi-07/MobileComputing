package com.example.mobilecomputing;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class RequestTask extends AsyncTask<Object, String, String> {

    @Override
    protected String doInBackground(Object... uri) {
        URL url = null;
        try {
            url = new URL((String)uri[0]);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                ((Bitmap) uri[1]).compress(Bitmap.CompressFormat.PNG, 100, stream);
                urlConnection.setDoOutput(true);
                urlConnection.setChunkedStreamingMode(0);
                OutputStream out = new BufferedOutputStream(urlConnection.getOutputStream());
                //out.write(bitmapToByte((Bitmap) uri[1]));
                //out.flush();
//            //writeStream(out);
//
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                Log.d("http_debug", in.toString());
                Log.d("http_debug", "response code: " + urlConnection.getResponseCode());

                //readStream(in);
            } finally {
                urlConnection.disconnect();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";

    }
    public byte[] bitmapToByte(Bitmap bmp) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        Log.d("http_debug", "" + byteArray.length);
        return byteArray;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        //Do anything with response..
    }
}