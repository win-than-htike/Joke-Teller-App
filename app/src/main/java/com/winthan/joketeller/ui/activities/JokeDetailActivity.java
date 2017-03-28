package com.winthan.joketeller.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.winthan.joketeller.R;
import com.winthan.joketeller.data.vos.JokeVO;

import butterknife.BindView;
import butterknife.ButterKnife;

public class JokeDetailActivity extends AppCompatActivity {

    private static final String JOKE = "JOKE";

    @BindView(R.id.tv_joke_name)
    TextView mTvJokeName;

    @BindView(R.id.iv_joke_image)
    ImageView mIvJokeImage;

    @BindView(R.id.tv_joke_desc)
    TextView mTvJokeDesc;

    public static Intent newInstance(Context context, JokeVO jokeVO){
        Intent jokedetailIntent = new Intent(context, JokeDetailActivity.class);
        jokedetailIntent.putExtra(JOKE, jokeVO);
        return jokedetailIntent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke_detail);
        ButterKnife.bind(this, this);

        if (getIntent().getExtras() != null){

            JokeVO jokeVO = (JokeVO) getIntent().getSerializableExtra(JOKE);

            if (jokeVO != null){

                mTvJokeName.setText(jokeVO.getName());
                mTvJokeDesc.setText(jokeVO.getDescription());

                if (jokeVO.getImage_url() == null){
                    mIvJokeImage.setVisibility(View.GONE);
                }else {
                    mIvJokeImage.setVisibility(View.VISIBLE);
                    Glide.with(getApplicationContext())
                            .load(jokeVO.getImage_url())
                            .crossFade()
                            .centerCrop()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(mIvJokeImage);
                }

            }
        }

    }
}
