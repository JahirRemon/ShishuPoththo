package com.example.mdjahirulislam.shishupoththo.Activity;

import android.Manifest;
import android.graphics.Bitmap;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mdjahirulislam.shishupoththo.R;
import com.example.mdjahirulislam.shishupoththo.screenShort.FileUtil;
import com.example.mdjahirulislam.shishupoththo.screenShort.ScreenshotUtil;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.ArrayList;
import java.util.Calendar;

public class PrescriptionViewActivity extends AppCompatActivity {

    private static final String TAG = PrescriptionViewActivity.class.getSimpleName();


    private Bitmap bitmap;
    private LinearLayout parentView;
    private TextView nameTV;
    private TextView ageTV;
    private ListView drugsLV;
    private ImageView showImageIV;

    ArrayList<String> medicineList=new ArrayList();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription_view);

        parentView = findViewById(R.id.parentView);
        nameTV = findViewById(R.id.presNameTV);
        ageTV = findViewById(R.id.presAgeTV);
        drugsLV = findViewById(R.id.presDrugsLV);
        showImageIV = findViewById(R.id.showImage);

        nameTV.setText("Patient Name: "+getIntent().getStringExtra("name"));
        ageTV.setText("Patient Age: "+getIntent().getStringExtra("age"));

        medicineList = getIntent().getStringArrayListExtra("list");


        final ArrayAdapter<String> arrayAdapter =new ArrayAdapter<String>(PrescriptionViewActivity.this,android.R.layout.simple_list_item_1,
                medicineList);
        drugsLV.setAdapter(arrayAdapter);

    }

    private void requestPermissionAndSave() {

        Dexter.withActivity(this)
                .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {

                        if (bitmap != null) {
                            Calendar calendar = Calendar.getInstance();
                            String path = Environment.getExternalStorageDirectory().toString() + "/ShishuPoththo/"+calendar.getTimeInMillis()+".png";
                            FileUtil.getInstance().storeBitmap(bitmap, path);
                            Log.d(TAG, "onPermissionGranted: path "+ path);
                            Toast.makeText(PrescriptionViewActivity.this, getString(R.string.toast_message_screenshot_success) + " " + path, Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(PrescriptionViewActivity.this, getString(R.string.toast_message_screenshot), Toast.LENGTH_LONG).show();
                        }

                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        // check for permanent denial of permission
                        if (response.isPermanentlyDenied()) {
                            Toast.makeText(PrescriptionViewActivity.this, getString(R.string.settings_message), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    public void takeScreenShort(View view) {

        bitmap = ScreenshotUtil.getInstance().takeScreenshotForView(parentView); // Take ScreenshotUtil for any view
        requestPermissionAndSave();
        showImageIV.setImageBitmap(bitmap);
    }
}
