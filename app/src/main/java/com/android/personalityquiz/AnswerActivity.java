package com.android.personalityquiz;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.RawResourceDataSource;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class AnswerActivity extends AppCompatActivity {

    private TextView txtTitle;
    private TextView txtResult;
    private Button btnSubmit;
    private String ans;
    private User user;
    private MediaPlayer mMediaPlayer;

    SimpleExoPlayerView videoView;
    SimpleExoPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        txtResult = (TextView) findViewById(R.id.txtResult);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);

        txtTitle.setText("اﻟﻨﺘﺎﺋﺞ");
        btnSubmit.setText("اﺑﺪأ ﻣﻦ ﺟﺪﻳﺪ");

        txtTitle.setTypeface(Functions.getRegularFont(this));
        txtResult.setTypeface(Functions.getRegularFont(this));
        btnSubmit.setTypeface(Functions.getRegularFont(this));

        ans = getIntent().getStringExtra("ans");
        user = (User) getIntent().getSerializableExtra("user");

        if (ans.equalsIgnoreCase("a")) {
            txtTitle.setText(" النتيجة: أنت شخصية طموحة");
            txtResult.setText("تدل مؤشرات نتائجك على انك شخصيه طموحه،خيالها واسعه،لاتخاف المستحيل،تحب الطبيعه ترتاح بها،قوية،شامخة ");
        } else if (ans.equalsIgnoreCase("b")) {
            txtTitle.setText("النتيجة: أنت شخصية مرحة و ضحوكه");
            txtResult.setText("تدل مؤشرات نتائجك على أنك شخصيه مرحه و ضحوكه،محبة للفرفشة والراحة والانبساط، وتسعى لها اينما كانت،واسعة الخيال،طيبه.");
        } else if (ans.equalsIgnoreCase("c")) {
            txtTitle.setText(" النتيجة: أنت شخصية قيادية");
            txtResult.setText("تدل مؤشرات نتائجك على أنك شخصية قيادية، ويبدو في سلوكك المظاهر القيادية وهي التي تنعكس من خلال تصرفاتك وردود أفعالك تجاه المواقف المُختلفة.. أنت لا تريدين أن يتحكم أحد بك، بل تحبين أنت أن تكوني ذات اليد القائدة.");
        } else if (ans.equalsIgnoreCase("d")) {
            txtTitle.setText(" النتيجة: أنت شخصية شجاعة");
            txtResult.setText("تدل مؤشرات نتائجك على أنك شخصية شجاعة ومندفعة تهوى المغامرات لا تخاف من شيءٍ، تحب أنْ تجرّب الأشياء الجديدة، ولا تفكر أبداً في عواقب ما تفعل من أمور");
        } else if (ans.equalsIgnoreCase("e")) {
            txtTitle.setText("النتيجة: أنت شخصية عاطفية");
            txtResult.setText("تدل مؤشرات نتائجك على أنك شخصية عاطفية، لكنك تحسنين التحكم في مشاعرك، فأنت إنسانةُ متزنةُ بطبعك؛ لذلك يلجأ إليك الأصدقاء في أوقات الشدة والضعف لأنك تكونين خير عونٍ لهم بكلماتك الداعمة وعقلك المستنير");
        }
        user.setResult(txtResult.getText().toString().trim());
        mMediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.tick);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMediaPlayer.start();
                addDataToDb();
                Intent intent = new Intent(AnswerActivity.this, FirstActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                Functions.fireIntent(AnswerActivity.this, intent, true);
                finish();
            }
        });


        loadPlayer(R.raw.result_video);
    }

    private void addDataToDb() {
        String data = "";
        if (user.getQuestion() != null && user.getQuestion().trim().length() > 0) {
            data = data + user.getQuestion() + "\n\n\n";
        }
        data = data + user.getName() + "\n\n\n";
        data = data + user.getMob() + "\n\n\n";
        data = data + user.getEmail() + "\n\n\n";
//        data = data + user.getAgeGroup() + "\n\n\n";
        data = data + user.getResult();
        generateNoteOnSD(AnswerActivity.this, user.getName() + "_" + System.currentTimeMillis() + ".text", data);
    }

    public void generateNoteOnSD(Context context, String sFileName, String sBody) {
        try {
            File root = new File(Environment.getExternalStorageDirectory(), "PersonalityResult");
            if (!root.exists()) {
                root.mkdirs();
            }
            File gpxfile = new File(root, sFileName);
            FileWriter writer = new FileWriter(gpxfile);
            writer.append(sBody);
            writer.flush();
            writer.close();
            Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void loadPlayer(int raw) {

        videoView = (SimpleExoPlayerView) findViewById(R.id.videoView);

        try {

            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
            TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);

            player = ExoPlayerFactory.newSimpleInstance(this, trackSelector);
            videoView.setPlayer(player);
            videoView.setUseController(false);

            DataSpec dataSpec = new DataSpec(RawResourceDataSource.buildRawResourceUri(raw));
            final RawResourceDataSource rawResourceDataSource = new RawResourceDataSource(this);
            rawResourceDataSource.open(dataSpec);
            DataSource.Factory factory = new DataSource.Factory() {
                @Override
                public DataSource createDataSource() {
                    return rawResourceDataSource;
                }
            };

            MediaSource videoSource = new ExtractorMediaSource(rawResourceDataSource.getUri(), factory, new DefaultExtractorsFactory(), null, null);
            //LoopingMediaSource loopingMediaSource = new LoopingMediaSource(videoSource);
            player.prepare(videoSource);

            DisplayMetrics metrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metrics);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) videoView.getLayoutParams();
            params.width = metrics.widthPixels;
            params.height = metrics.heightPixels;
            params.leftMargin = 0;
            videoView.setLayoutParams(params);

        } catch (RawResourceDataSource.RawResourceDataSourceException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (player != null){
            player.setPlayWhenReady(true);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (player != null){
            player.stop();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (player != null){
            player.release();
        }
    }
}
