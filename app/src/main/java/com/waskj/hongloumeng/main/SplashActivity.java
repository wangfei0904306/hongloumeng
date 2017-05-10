package com.waskj.hongloumeng.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import java.util.Calendar;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.waskj.hongloumeng.R;
import com.waskj.hongloumeng.common.BitmapCache;

public class SplashActivity extends BaseActivity implements View.OnClickListener {

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, SplashActivity.class);
        ((Activity) context).startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_old);
        RequestQueue mQueue = Volley.newRequestQueue(this);
        ImageLoader imageLoader = new ImageLoader(mQueue, new BitmapCache());
        NetworkImageView adImage = (NetworkImageView)findViewById(R.id.image_ad);
        adImage.setImageUrl("http://image.tianjimedia.com/uploadImages/2013/297/QPD99T7KCO3D.jpg",
                imageLoader);
        (new Handler()).postDelayed(finishRunnable, 3000);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            default:
                break;
        }

    }

    Runnable finishRunnable = new Runnable() {
        @Override
        public void run() {
            finish();
        }
    };

    @Override
    public void finish(){
        super.finish();
        overridePendingTransition(R.anim.nothing, R.anim.alpha_out); //不加这一句退出动画不行
    }


}
