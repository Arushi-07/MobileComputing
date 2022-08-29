package com.example.mobilecomputing;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

public class SpinnerActivity extends Activity implements AdapterView.OnItemSelectedListener {

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
        Log.d("tag", "" + parent.getItemAtPosition(pos));
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
        Toast.makeText(this, "nothing selected", Toast.LENGTH_LONG).show();

    }
}
