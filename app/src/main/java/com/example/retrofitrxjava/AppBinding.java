package com.example.retrofitrxjava;

import android.graphics.Color;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;

public class AppBinding {

    @BindingAdapter("thumb")
    public static void setThumb(ImageView im, String img) {
        Glide.with(im)
                .load(img)
                .into(im);
    }

    @BindingAdapter("imgType")
    public static void setImage(ImageView img, String score) {
        float scoreAccount = Float.parseFloat(score);
        if (scoreAccount >= 2.5 && scoreAccount < 3.2) {
            img.setImageResource(R.drawable.custom_botron_kha);
        } else if (scoreAccount >= 3.2) {
            img.setImageResource(R.drawable.custom_botron_gioi);
        } else {
            img.setImageResource(R.drawable.custom_botron_tb);
        }
    }

    @BindingAdapter("score")
    public static void setScore(AppCompatTextView tv, String score) {
        if (!score.equals("")) {
            Float scoreAccount = Float.parseFloat(score);
            if (scoreAccount >= 2.5 && scoreAccount <= 3.20) {
                tv.setBackgroundResource(R.drawable.custom_botron_kha);
                tv.setTextColor(Color.parseColor("#FFFFFFFF"));
            } else if (scoreAccount >= 3.20) {
                tv.setBackgroundResource(R.drawable.custom_botron_gioi);
                tv.setTextColor(Color.parseColor("#FFFFFFFF"));
            } else {
                tv.setBackgroundResource(R.drawable.custom_botron_tb);
            }
            tv.setTextColor(Color.parseColor("#FFFFFFFF"));
            tv.setText(score);
        } else {
            tv.setTextColor(Color.BLACK);
            tv.setText(R.string.chua_co);
        }
    }

    @BindingAdapter("status")
    public static void setStatus(TextView tv, String score) {
        float scoreAccount = Float.parseFloat(score);
        if (scoreAccount >= 2.5 && scoreAccount < 3.2) {
            tv.setText(R.string.kha);
        } else if (scoreAccount >= 3.2) {
            tv.setText(R.string.gioi);
        } else {
            tv.setText(R.string.trung_binh);
        }
    }

    @BindingAdapter("name")
    public static void setName(TextView tv, String name) {
        String nameAccount = "Xin chào, " + "<b>" + name + "</b>";
        tv.setText(Html.fromHtml(nameAccount));
    }

    @BindingAdapter("hide")
    public static void checkHideView(ImageView img, String score) {
        if (!score.equals("")) {
            float scoreAccount = Float.parseFloat(score);
            if (scoreAccount >= 2.5 && scoreAccount < 3.2) {
                img.setVisibility(View.VISIBLE);
                img.setImageResource(R.drawable.ic_two);
            } else if (scoreAccount >= 3.2) {
                img.setVisibility(View.VISIBLE);
                img.setImageResource(R.drawable.ic_one);
            } else {
                img.setVisibility(View.GONE);
            }
        } else {
            img.setVisibility(View.GONE);
        }
    }

}
