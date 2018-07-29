package com.android.personalityquiz;

import android.content.Context;
import android.media.MediaPlayer;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class QuizAdapter extends PagerAdapter {

    private final MediaPlayer mMediaPlayer;
    private OnAnsClick onAnsClick;
    private Context context;
    private List<Quiz> list;

    public QuizAdapter(Context context, List<Quiz> list, OnAnsClick onAnsClick) {
        this.context = context;
        this.list = list;
        this.onAnsClick = onAnsClick;
        mMediaPlayer = MediaPlayer.create(context, R.raw.tick);

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.item_quiz, container, false);
        TextView txtTitle = (TextView) view.findViewById(R.id.txtTitle);
        TextView txtOptionA = (TextView) view.findViewById(R.id.txtOptionA);
        TextView txtOptionB = (TextView) view.findViewById(R.id.txtOptionB);
        TextView txtOptionC = (TextView) view.findViewById(R.id.txtOptionC);

        txtTitle.setText(list.get(position).getQuestion());
        txtOptionA.setText(list.get(position).getOptionA());
        txtOptionB.setText(list.get(position).getOptionB());
        txtOptionC.setText(list.get(position).getOptionC());

        txtTitle.setTypeface(Functions.getRegularFont(context));
        txtOptionA.setTypeface(Functions.getRegularFont(context));
        txtOptionB.setTypeface(Functions.getRegularFont(context));
        txtOptionC.setTypeface(Functions.getRegularFont(context));

        txtOptionA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMediaPlayer.start();
                list.get(position).setAnswer("a");
                if (position == 0) {
                    onAnsClick.onAnsClick("e");
                }
                onAnsClick.enableNextBtn();
            }
        });
        txtOptionB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMediaPlayer.start();
                list.get(position).setAnswer("b");
                if (position == 0) {
                    onAnsClick.onAnsClick("d");
                } else if (position == 4) {
                    onAnsClick.onAnsClick("c");
                }
                onAnsClick.enableNextBtn();

            }
        });
        txtOptionC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMediaPlayer.start();
                list.get(position).setAnswer("c");
                if (position == 0) {
                    onAnsClick.onAnsClick("b");
                } else if (position == 12) {
                    onAnsClick.onAnsClick("a");
                }
                onAnsClick.enableNextBtn();

            }
        });

        container.addView(view);
        return view;
    }

    public interface OnAnsClick {
        void onAnsClick(String ans);

        void enableNextBtn();
    }
}
