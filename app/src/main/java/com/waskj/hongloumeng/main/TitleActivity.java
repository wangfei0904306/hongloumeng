package com.waskj.hongloumeng.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import com.waskj.hongloumeng.R;
import com.waskj.hongloumeng.common.MyRecyclerAdapter;

public class TitleActivity extends BaseActivity implements View.OnClickListener, View.OnTouchListener {
    public static final String TAG = "TitleActivity";

    private RecyclerView recyclerView;
    private List<String> titles;
    private MyRecyclerAdapter recycleAdapter;

    public static void actionStart(Context context, int requestCode, int index) {
        Intent intent = new Intent(context, TitleActivity.class);
        intent.putExtra("index", index);
        ((Activity) context).startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        initData();
        recycleAdapter= new MyRecyclerAdapter(TitleActivity.this , titles);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //设置布局管理器
        recyclerView.setLayoutManager(layoutManager);
        //设置布局方向
        layoutManager.setOrientation(OrientationHelper.HORIZONTAL);
        //设置Adapter
        recyclerView.setAdapter( recycleAdapter);
        //设置增加或删除条目的动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.scrollToPosition(getIntent().getIntExtra("index", 40));
        recycleAdapter.setOnItemClickListener(new MyRecyclerAdapter.OnRecyclerViewItemClickListener(){
            @Override
            public void onItemClick(View view , int position){
                //Toast.makeText(TitleActivity.this, position, Toast.LENGTH_LONG).show();
                onBackResult(position);
            }
        });

        ((Button)findViewById(R.id.button_reread)).setOnClickListener(this);
        ((LinearLayout)findViewById(R.id.lina_top)).setOnTouchListener(this);
        ((LinearLayout)findViewById(R.id.lina_bottom)).setOnTouchListener(this);
    }

    private void initData() {
        titles = new ArrayList<String>();
        for (int i = MainActivity.CHAPTER_MIN; i <= MainActivity.CHAPTER_MAX; i++) {
            int id = getResources().getIdentifier("title" + i, "string", getPackageName());
            String titleStr = getResources().getString(id);
            titles.add(titleStr);
        }
    }

    public void onBackResult(int id) {
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        intent.putExtra("index", id);
        finish();
    }

    @Override
    public void finish(){
        super.finish();
        overridePendingTransition(R.anim.nothing,R.anim.alpha_out); //不加这一句退出动画不行
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()){
            case R.id.lina_top:
                finish();
                break;
            case R.id.lina_bottom:
                finish();
                break;
            default:
                break;
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_reread:
                spEditor.clear();
                spEditor.commit();
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                intent.putExtra("index", 0);
                finish();
                break;
            default:
                break;
        }
    }
}
