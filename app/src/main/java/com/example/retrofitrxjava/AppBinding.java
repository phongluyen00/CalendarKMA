package com.example.retrofitrxjava;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AppBinding {

    @BindingAdapter("thumb")
    public static void setThumb(ImageView im, String img) {
        Glide.with(im)
                .load(img)
                .into(im);
    }

    @BindingAdapter("time")
    public static void setTime(TextView tv, int time) {
        SimpleDateFormat format = new SimpleDateFormat("mm:ss");
        tv.setText(format.format(new Date(time)));
    }
}
