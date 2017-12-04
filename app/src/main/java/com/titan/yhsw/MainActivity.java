package com.titan.yhsw;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.titan.model.Pest;
import com.titan.yhsw.adapter.MyFragmentPagerAdapter;
import com.titan.yhsw.fragment.JzswFragment;
import com.titan.yhsw.fragment.WhzzFragment;
import com.titan.yhsw.fragment.YhswFragment;
import com.titan.yhsw.fragment.infoFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 主页面
 */
public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, RadioGroup.OnCheckedChangeListener, infoFragment.OnFragmentInteractionListener {

    @BindView(R.id.tv_title)
    TextView mTv_title;
    @BindView(R.id.rg_identify)
    RadioGroup mRg_identify;
    @BindView(R.id.rb_yhsw)
    RadioButton mRb_yhsw;
    @BindView(R.id.rb_whzz)
    RadioButton mRb_whzz;
    @BindView(R.id.rb_jzsw)
    RadioButton mRb_jzsw;
    @BindView(R.id.vp_identify)
    ViewPager mVp_identify;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_back)
    TextView tvBack;


    public List<Pest> getQueryPests() {
        return queryPests;
    }

    //查看结果
    private List<Pest> queryPests = new ArrayList<>();
    //查询类型 0：林业有害生物 1：寄主植物 2：常见林业有害生物
    private int type = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        initView();
    }


    private void initView() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.finish();
            }
        });
        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.finish();
            }
        });


        List<Fragment> fmList = new ArrayList<>();
        fmList.add(new WhzzFragment()); // 有害生物查询
        fmList.add(new JzswFragment()); // 寄主植物查询
        fmList.add(new YhswFragment()); // 常见有害生物
        fmList.add(new infoFragment()); // 系统简介

        //mVp_identify.addOnPageChangeListener(this);
        mVp_identify.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), fmList));
        //mRg_identify.setOnCheckedChangeListener(this);

        type = (int) getIntent().getExtras().get("type");
        switch (type) {
            case 0:
                mTv_title.setText(R.string.whzz);
                mVp_identify.setCurrentItem(0);
                break;
            case 1:
                mTv_title.setText(R.string.jzsw);
                mVp_identify.setCurrentItem(1);
                break;
            case 2:
                mTv_title.setText(R.string.yhsw);
                mVp_identify.setCurrentItem(2);
                break;
            case 3:
                mTv_title.setText(R.string.info);
                mVp_identify.setCurrentItem(3);
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                mTv_title.setText(R.string.yhsw);
                mRb_yhsw.setChecked(true);
                break;
            case 1:
                mTv_title.setText(R.string.whzz);
                mRb_whzz.setChecked(true);
                break;
            case 2:
                mTv_title.setText(R.string.jzsw);
                mRb_jzsw.setChecked(true);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i) {
            case R.id.rb_yhsw:
                mTv_title.setText(R.string.yhsw);
                mVp_identify.setCurrentItem(0, false);
                break;
            case R.id.rb_whzz:
                mTv_title.setText(R.string.whzz);
                mVp_identify.setCurrentItem(1, false);
                break;
            case R.id.rb_jzsw:
                mTv_title.setText(R.string.jzsw);
                mVp_identify.setCurrentItem(2, false);
                break;
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public interface MyTouchListener {
        public void onTouchEvent(MotionEvent event);
    }

    /**
     * 保存MyTouchListener接口的列表
     */
    private ArrayList<MyTouchListener> myTouchListeners = new ArrayList<>();

    /**
     * 提供给Fragment通过getActivity()方法来注册自己的触摸事件的方法
     */
    public void registerMyTouchListener(MyTouchListener listener) {
        myTouchListeners.add(listener);
    }

    /**
     * 提供给Fragment通过getActivity()方法来取消注册自己的触摸事件的方法
     */
    public void unRegisterMyTouchListener(MyTouchListener listener) {
        myTouchListeners.remove(listener);
    }

    /**
     * 分发触摸事件给所有注册了MyTouchListener的接口
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        for (MyTouchListener listener : myTouchListeners) {
            listener.onTouchEvent(ev);
        }
        return super.dispatchTouchEvent(ev);
    }
}
