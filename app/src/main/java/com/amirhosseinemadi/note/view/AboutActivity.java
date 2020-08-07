package com.amirhosseinemadi.note.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.amirhosseinemadi.note.R;

public class AboutActivity extends AppCompatActivity {

    AppCompatTextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        textView = findViewById(R.id.ver);

        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(),0);
            String ver = packageInfo.versionName;
            textView.setText("v : "+ver);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }


    }
}
