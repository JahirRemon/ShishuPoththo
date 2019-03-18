package com.example.mdjahirulislam.shishupoththo.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.mdjahirulislam.shishupoththo.R;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_sign_up );
    }

    public void skipBTN(View view) {
        startActivity( new Intent( this,BabyHomeActivity.class ) );
    }
}
