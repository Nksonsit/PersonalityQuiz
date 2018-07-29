package com.android.personalityquiz;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SoundEffectConstants;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.VideoView;

import java.util.ArrayList;
import java.util.List;

public class QuizActivity extends AppCompatActivity {

    private NonSwipeableViewPager viewPager;
    private List<Quiz> list;
    private QuizAdapter adpter;
    private String answer;
    private User user;
    private boolean isNextBtnAvailable = false;
    private MediaPlayer mMediaPlayer;
    private VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        viewPager = (NonSwipeableViewPager) findViewById(R.id.viewPager);

        mMediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.tick);

        user = (User) getIntent().getSerializableExtra("user");
        list = new ArrayList<>();

        list.add(new Quiz("يصفك الناس بأنك:", "طيبه", "شجاعة ومسؤولة", "حيوية ولطيفة", ""));
        list.add(new Quiz("ما هو اللون المفضل لك ؟", "اخضر", "أسود", "احمر", ""));
        list.add(new Quiz("هل ترين نفسك منفتحة على التجارب الجديدة المعقدة؟", "من وقت ل اخر", "نعم دون شك ", "لا", ""));
        list.add(new Quiz("إلى أي مدى تعتبرين نفسك شخصا مزاجيا ؟", "علي الإطلاق", "الي اقصي الحدود", "حسب الظروف", ""));
        list.add(new Quiz("ما هي نقطة الضعف في شخصيتك؟", "الخجل الشديد", "الجرأة الزائدة", "المزاجيه ", ""));
        list.add(new Quiz("ماذا يشكل الطعام في حياتك؟", "لذة يومية", "أساسي في حياتنا", "وسيلة للشبع", ""));
        list.add(new Quiz("اذا كنت تحضرين وصفة ووجدت انّ هناك مكون ينقصك... ماذا تفعلين؟", "استبدله بمكون آخر", "ابحث عن المكون البديل في الإنترنت", "أوقف الوصفة", ""));
        list.add(new Quiz("أكثر ما تحبين تحضيره", "الاطباق الرئيسية", "السلطات", "الحلويات", ""));
        list.add(new Quiz("هل تحرصين على تزيين أطباقك قبل تقديمها؟", "من وقت ل اخر", "نعم دون شك", "لا", ""));
        list.add(new Quiz("هل سبق ان حضرتي Sushi في بيتك", "لا ولكن ممكن في المستقبل", "لا", "نعم", ""));
        list.add(new Quiz("أي من هذه الأحذية تفضلين:", "الصنادل", "الكعب العالي", "الأحذية الرياضية", ""));
        list.add(new Quiz("ماهي طريقتك المثالية لقضاء عطلة نهاية الأسبوع؟", "مع الأصدقاء", "على الشاطئ بمفردي", "مع العائلة", ""));
        list.add(new Quiz("كيف تصفين منزلك؟", "مستوحى من اماكن بالعالم", "يبعث على الشعور بالسعادة", "عملي ومريح", ""));
        list.add(new Quiz("كم عدد ساعات العمل في اليوم؟", "8+", "6-8", "4-6", ""));
        list.add(new Quiz("كم عدد مرة ذهابك للنادي في الأسبوع؟", "6 مرات", "3 مرات", "مره واحده", ""));

        adpter = new QuizAdapter(this, list, new QuizAdapter.OnAnsClick() {
            @Override
            public void onAnsClick(String ans) {
                Log.e("ans", ans);
                if (answer != null && answer.trim().length() > 0) {
                    if (!answer.equalsIgnoreCase("c")) {
                        answer = ans;
                    }
                } else {
                    answer = ans;
                }
            }

            @Override
            public void enableNextBtn() {
                isNextBtnAvailable = true;
                /*if (!isNextBtnAvailable) {
                    Toast.makeText(QuizActivity.this, "يرجى اختيار الجواب", Toast.LENGTH_SHORT).show();
                    return;
                }*/
                mMediaPlayer.start();
                isNextBtnAvailable = false;
                if (viewPager.getCurrentItem() != list.size() - 1) {
                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                } else {
                    Intent intent = new Intent(QuizActivity.this, AnswerActivity.class);
                    intent.putExtra("user", user);
                    intent.putExtra("ans", answer);
                    Functions.fireIntent(QuizActivity.this, intent, true);
                    finish();
                }
            }
        });
        viewPager.setAdapter(adpter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (viewPager.getCurrentItem() == list.size() - 1) {
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });



        videoView = (VideoView) findViewById(R.id.videoView);

        String path = "android.resource://" + getPackageName() + "/" + R.raw.bg_video;
        Uri video = Uri.parse(path);
        videoView.setVideoURI(video);
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
                videoView.start();
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
