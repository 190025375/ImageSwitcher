package com.jerry.switcher.cutomview.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jerry.switcher.R;

import java.util.List;

/**
 * Created by jerry on 2015/9/22.
 */
public class GalleryPageAdapter extends PagerAdapter {
    Context context;
    List<Integer> resList;

    public GalleryPageAdapter(Context context, List<Integer> resList){
        this.context = context;
        this.resList = resList;
    }

    @Override
    public int getCount() {
        return resList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == o;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView img = new ImageView(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        img.setImageResource(resList.get(position));
        img.setLayoutParams(params);
        img.setScaleType(ImageView.ScaleType.CENTER_CROP);
        container.addView(img);
        return img;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
       container.removeView((View)object);
    }
}