package com.example.retrofitrxjava.b;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.retrofitrxjava.R;
import com.example.retrofitrxjava.model.Advertisement;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BannerAdapter extends PagerAdapter {

    private ArrayList<Advertisement> data;
    private LayoutInflater inflater;

    public BannerAdapter(Context context, ArrayList<Advertisement> data) {
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        View v = inflater.inflate(R.layout.item_banner, container, false);
        ImageView img = v.findViewById(R.id.img_banner);
        Picasso.with(container.getContext())
                .load(data.get(position).getImage())
                .into(img);
        container.addView(v);
        return v;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }


}
