package com.jerry.switcher.cutomview;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jerry.switcher.R;
import com.jerry.switcher.cutomview.adapter.GalleryPageAdapter;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by jerry on 2015/9/22.
 */
public class GalleryViewPager extends FrameLayout implements ViewPager.OnPageChangeListener, Runnable{
    Context context;
    ViewPager viewPager;
    LinearLayout pointContainer;    //导航点的容器
    Handler handler;
    int index = 0;                  //当前活动页面索引
    int millis;                    //每次滑动之间的时间间隔
    int duration;                  //滑动动画速度
    boolean isAutoSliding = true;

    public GalleryViewPager(Context context) {
        super(context);
    }

    public GalleryViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * @param context       context
     * @param container     导航点的容器
     * @param viewList      填充 ViewPager 的View项
     * @param isAutoSliding 是否自动滑动
     * @param millis        每次滑动之间的间隔
     * @param duration      滑动速度
     */
    public static void init( Context context, LinearLayout container, final List<Integer> viewList, boolean isAutoSliding, int millis, int duration){
        final GalleryViewPager galleryViewPager= new GalleryViewPager(context);
        galleryViewPager.context = context;
        galleryViewPager.millis = millis;
        galleryViewPager.duration = duration;
        galleryViewPager.isAutoSliding = isAutoSliding;

        galleryViewPager.viewPager = new ViewPager(context);
        galleryViewPager.viewPager.setAdapter(new GalleryPageAdapter(context, viewList));
        galleryViewPager.viewPager.addOnPageChangeListener(galleryViewPager);
        ViewPagerScroller scroller = new ViewPagerScroller(context);
        scroller.initViewPagerScroll(galleryViewPager.viewPager, duration);                 //自定义滑动时间

        //修饰导航点的 容器样式
        galleryViewPager.pointContainer = new LinearLayout(context);
        galleryViewPager.pointContainer.setBackgroundColor(Color.argb(60, 0, 0, 0));
        galleryViewPager.pointContainer.setPadding(0, 20, 0, 20);
        galleryViewPager.pointContainer.setGravity(Gravity.CENTER);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.BOTTOM;
        galleryViewPager.pointContainer.setLayoutParams(params);

        //添加点到容器，数量与ViewPager项相等
        for(int i = 0; i < viewList.size(); i++) {
            ImageView img = new ImageView(context);
            img.setImageResource(i == 0 ? R.drawable.radio_yes : R.drawable.radio_no);
            galleryViewPager.pointContainer.addView(img);
        }
        galleryViewPager.addView(galleryViewPager.viewPager);
        galleryViewPager.addView(galleryViewPager.pointContainer);

        container.addView(galleryViewPager);

        galleryViewPager.handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if(galleryViewPager.index == viewList.size() - 1) {
                    galleryViewPager.viewPager.setCurrentItem(0);
                }else{
                    galleryViewPager.viewPager.setCurrentItem(galleryViewPager.index + 1);
                }
            }
        };
        new Thread(galleryViewPager).start();
    }

    void changePointDirect(int position){
        int count = pointContainer.getChildCount();
        for(int i = 0; i < count; i++) {
            ((ImageView) pointContainer.getChildAt(i)).setImageResource(i == position ? R.drawable.radio_yes : R.drawable.radio_no);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        index = position;
        changePointDirect(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void run() {
        try {
            while (isAutoSliding) {
                Thread.sleep(millis);
                handler.sendEmptyMessage(0);
            }
        }catch (InterruptedException e){
            Log.e("GalleryViewPager error", "GalleryViewPager run error:" + e.getMessage());
        }
    }
}