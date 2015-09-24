package com.jerry.switcher;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jerry.switcher.cutomview.GalleryViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jerry on 2015/9/22.
 */
public class ImagePagerActivity extends Activity {
    LinearLayout pagerContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_pager);
        initView();
        initData();
    }

    void initView(){
        pagerContainer = (LinearLayout) findViewById(R.id.image_pager_container);
    }

    void initData(){
        List<Integer> resList = new ArrayList<>();
        resList.add(R.drawable.one);
        resList.add(R.drawable.two);
        resList.add(R.drawable.three);
        resList.add(R.drawable.four);
        resList.add(R.drawable.five);
        resList.add(R.drawable.six);

        GalleryViewPager.init(this, pagerContainer, resList, true, 2000, 1000);
    }
}