package com.android.personalityquiz;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.VideoView;

public class SecondActivity extends AppCompatActivity {

    private VideoView videoView;
    private ImageView imgSkip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        videoView = (VideoView) findViewById(R.id.videoView);
        imgSkip = (ImageView) findViewById(R.id.imgSkip);

        String path = "android.resource://" + getPackageName() + "/" + R.raw.first_video;
        Uri video = Uri.parse(path);
        videoView.setVideoURI(video);
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                videoView.start();
            }
        });

        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Intent intent = new Intent(SecondActivity.this, MainActivity.class);
                intent.putExtra("user", (User) getIntent().getSerializableExtra("user"));
                Functions.fireIntent(SecondActivity.this, intent, true);
                finish();
            }
        });

        imgSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SecondActivity.this, MainActivity.class);
                intent.putExtra("user", (User) getIntent().getSerializableExtra("user"));
                Functions.fireIntent(SecondActivity.this, intent, true);
                finish();
            }
        });

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) videoView.getLayoutParams();
        params.width = metrics.widthPixels;
        params.height = metrics.heightPixels;
        params.leftMargin = 0;
        videoView.setLayoutParams(params);

    }
}
