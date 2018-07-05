package com.tetris.arnaud.tetris;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import com.tetris.arnaud.tetris.Models.Block;
import java.util.ArrayList;
import java.util.Arrays;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    public int map[];

    public ImageAdapter(Context c, int[] map) {
        mContext = c;
        this.map = map;
    }

    public int getCount() {
        return this.map.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(80, 80));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageResource(this.map[position]);
        return imageView;
    }
}