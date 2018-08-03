package com.android.personalityquiz;

import android.Manifest;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SoundEffectConstants;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TextView txtSubmit;
    private EditText edtEmail;
    private EditText edtMob;
    private EditText edtName;
    private TextView txtAge1;
    private TextView txtAge2;
    private TextView txtAge3;
    private TextView txtLabelEmail;
    private TextView txtLabelMob;
    private TextView txtLabelName;
    private TextView txtTitle;
    private Button btnSubmit;
    private String ageGroup;
    private MediaPlayer mMediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }


        txtTitle = (TextView) findViewById(R.id.txtTitle);
        txtLabelName = (TextView) findViewById(R.id.txtLabelName);
        txtLabelMob = (TextView) findViewById(R.id.txtLabelMob);
        txtLabelEmail = (TextView) findViewById(R.id.txtLabelEmail);
        txtAge1 = (TextView) findViewById(R.id.txtAge1);
        txtAge2 = (TextView) findViewById(R.id.txtAge2);
        txtAge3 = (TextView) findViewById(R.id.txtAge3);
        edtName = (EditText) findViewById(R.id.edtName);
        edtMob = (EditText) findViewById(R.id.edtMob);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        txtSubmit = (TextView) findViewById(R.id.txtSubmit);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        btnSubmit.setText("أبدأ الاختبار");
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtName.getText().toString().trim().length() == 0) {
                    Toast.makeText(MainActivity.this, "يرجى إدخال الاسم", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (edtMob.getText().toString().trim().length() == 0) {
                    Toast.makeText(MainActivity.this, "يرجى إدخال المحمول", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (edtEmail.getText().toString().trim().length() == 0 && !Functions.emailValidation(edtEmail.getText().toString().trim())) {
                    Toast.makeText(MainActivity.this, "الرجاء إدخال معرف البريد الإلكتروني الصحيح", Toast.LENGTH_SHORT).show();
                    return;
                }
                User user = (User) getIntent().getSerializableExtra("user");
                user.setName(edtName.getText().toString().trim());
                user.setMob(edtMob.getText().toString().trim());
                user.setEmail(edtEmail.getText().toString().trim());

                Intent intent = new Intent(MainActivity.this, IntroVideoActivity.class);
                intent.putExtra("user", user);
                Functions.fireIntent(MainActivity.this, intent, true);
                finish();
            }
        });

        mMediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.tick);
        txtTitle.setText("تسجيل بيانات");
        txtLabelName.setText("الاسم");
        txtLabelMob.setText("التليفون المحمول");
        txtLabelEmail.setText("الايميل");
        txtSubmit.setText("الفئة العمريه");

        txtTitle.setTypeface(Functions.getRegularFont(MainActivity.this));
        txtLabelName.setTypeface(Functions.getRegularFont(MainActivity.this));
        txtLabelMob.setTypeface(Functions.getRegularFont(MainActivity.this));
        txtLabelEmail.setTypeface(Functions.getRegularFont(MainActivity.this));
//        txtAge1.setTypeface(Functions.getRegularFont(MainActivity.this));
//        txtAge2.setTypeface(Functions.getRegularFont(MainActivity.this));
//        txtAge3.setTypeface(Functions.getRegularFont(MainActivity.this));
//        edtName.setTypeface(Functions.getRegularFont(MainActivity.this));
//        edtMob.setTypeface(Functions.getRegularFont(MainActivity.this));
//        edtEmail.setTypeface(Functions.getRegularFont(MainActivity.this));
        txtSubmit.setTypeface(Functions.getRegularFont(MainActivity.this));
        btnSubmit.setTypeface(Functions.getRegularFont(MainActivity.this));

        txtAge1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMediaPlayer.start();
                ageGroup = "18-25";
            }
        });
        txtAge2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMediaPlayer.start();
                ageGroup = "26-35";
            }
        });
        txtAge3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMediaPlayer.start();
                ageGroup = "36+";
            }
        });

        TedPermission.with(this).setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                .setPermissionListener(new PermissionListener() {
                                           @Override
                                           public void onPermissionGranted() {

                                           }

                                           @Override
                                           public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                                               Toast.makeText(MainActivity.this, "You have denied service", Toast.LENGTH_SHORT).show();
                                               finish();
                                           }
                                       }
                ).check();

    }
}
