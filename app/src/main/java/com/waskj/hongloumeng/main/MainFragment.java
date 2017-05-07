package com.waskj.hongloumeng.main;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.waskj.hongloumeng.R;
import com.waskj.hongloumeng.common.MyScrollView;
import com.waskj.hongloumeng.common.RenderScriptBlur;
import com.waskj.hongloumeng.common.ScrollViewListener;

/**
 * Created by Administrator on 2016/8/24 0024.
 */
public class MainFragment extends Fragment implements ScrollViewListener {
    public static final String TAG = "MainFragment";

    public static final String INDEX = "current_index";
    public static final String SCROLL_Y = "share_scroll_y";
    public static final String TITLE = "frag_title";
    public static final String CONTENT = "frag_content";

    SharedPreferences.Editor spEditor;

    long timer;

    View view;
    MyScrollView scroll;
    TextView titleText;
    TextView contentText;
    ImageView upTopImage;

    protected int index;
    protected int scrollY;

    Handler handler  = new Handler(Looper.getMainLooper());

    public static Fragment newInstance(int index, int title, int content){
        MainFragment fragment = new MainFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(INDEX, index);
        bundle.putInt(TITLE, title);
        bundle.putInt(CONTENT, content);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        spEditor = MyApplication.sharedPrefs.edit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_main, container, false);
        scroll = (MyScrollView)view.findViewById(R.id.frag_scroll);
        scroll.setScrollViewListener(this);
        titleText = (TextView)view.findViewById(R.id.frag_title);
        contentText = (TextView)view.findViewById(R.id.frag_content);
        upTopImage = (ImageView)view.findViewById(R.id.up_top_image);

        index = getArguments().getInt(INDEX, 0);
        titleText.setText(getArguments().getInt(TITLE, 0));
        contentText.setText(getArguments().getInt(CONTENT, 0));

        titleText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RenderScriptBlur.blurImage(getActivity(), MainActivity.blurImage);
                TitleActivity.actionStart(getActivity(), 1, index);
            }
        });

        //向上滑动到顶按钮
        upTopImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scroll.smoothScrollTo(0, 0);
            }
        });

        return view;
    }

    @Override
    public void onResume(){
        super.onResume();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                int y = MyApplication.sharedPrefs.getInt(SCROLL_Y + index, 0);
                Log.d(TAG, "Set ScrollY. index= " + index + "  scrollY= " + y );
                scroll.setScrollY(y);// 改变滚动条的位置
            }
        }, 50);
    }

    @Override
    public void onPause(){

        super.onPause();
    }

    @Override
    public void onScrollChanged(MyScrollView scrollView, int x, int y, int oldx, int oldy) {
        if(System.currentTimeMillis() - timer > 120){
            if(scrollY > y && View.INVISIBLE == upTopImage.getVisibility()){
                upTopImage.setVisibility(View.VISIBLE);
                handler.removeCallbacks(upTopRunnable);
                handler.postDelayed(upTopRunnable, 2500);
            }
            if(scrollY < y && View.VISIBLE == upTopImage.getVisibility())upTopImage.setVisibility(View.INVISIBLE);
            scrollY = y;
            handler.post(editorRunnable);
            timer = System.currentTimeMillis();
        }

        //Log.d("Scroll", " " + x + " " + y + " " + oldx + " " + oldy + " " + scrollView.getChildAt(0).getMeasuredHeight() + scrollView.getHeight());
        //标题随动效果
        if(y > 0 && y < scrollView.getChildAt(0).getMeasuredHeight() - scrollView.getHeight() &&
                !((titleText.getTranslationY() == 0 && y < oldy)
                        || (titleText.getTranslationY() == -titleText.getHeight() && y > oldy))
                ){
            float transY = titleText.getTranslationY() + (oldy-y);
            if(transY > 0) transY = 0;
            if(transY < -titleText.getHeight()) transY = -titleText.getHeight();
            titleText.setTranslationY(transY);
        }
    }

    /**
     * 存储当前浏览位置
     */
    Runnable editorRunnable = new Runnable() {
        @Override
        public void run() {
            spEditor.putInt(SCROLL_Y + index, scrollY);
            //Log.d(TAG, "Save Reading Position. SCROLL_Y + index=  " + SCROLL_Y + index + "  scrollY= " + scrollY );
            spEditor.commit();
        }
    };

    /**
     * 滑动到顶按钮的自动消失
     */
    Runnable upTopRunnable = new Runnable() {
        @Override
        public void run() {
            upTopImage.setVisibility(View.INVISIBLE);
        }
    };


}
