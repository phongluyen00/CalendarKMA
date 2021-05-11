package com.example.retrofitrxjava;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.net.Uri;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.example.retrofitrxjava.utils.AppUtils;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;

public class AppBinding {

    @BindingAdapter("thumb")
    public static void setThumb(ImageView im, String img) {
        Glide.with(im)
                .load(img)
                .error(R.drawable.ic_baseline_warning_24)
                .into(im);
    }

    @BindingAdapter("imgType")
    public static void setImage(ImageView img, String score) {
        if (!TextUtils.isEmpty(score)){
            float scoreAccount = Float.parseFloat(score);
            if (scoreAccount >= 2.5 && scoreAccount < 3.2) {
                img.setImageResource(R.drawable.custom_botron_kha);
            } else if (scoreAccount >= 3.2) {
                img.setImageResource(R.drawable.custom_botron_gioi);
            } else {
                img.setImageResource(R.drawable.custom_botron_tb);
            }
        }
    }

    @BindingAdapter("score")
    public static void setScore(AppCompatTextView tv, String score) {
        score = score.trim();
        if (!AppUtils.isNullOrEmpty(score)) {
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
            tv.setBackground(null);
            tv.setTextColor(Color.BLACK);
            tv.setText("0");
        }
    }

    @SuppressLint("ResourceAsColor")
    @BindingAdapter("study")
    public static void StatusStudy(TextView tv, String status){
        if (!status.equals("DAT")){
            tv.setTextColor(Color.parseColor("#ff3300"));
        }else {
            tv.setTextColor(Color.parseColor("#1494A5"));
        }
        tv.setText(status.trim());
    }

    @SuppressLint("SetTextI18n")
    @BindingAdapter("status")
    public static void setStatus(TextView tv, String score) {
        if (!AppUtils.isNullOrEmpty(score)){
            float scoreAccount = Float.parseFloat(score);
            if (scoreAccount >= 2.5 && scoreAccount < 3.2) {
                tv.setTextColor(Color.parseColor("#FD05B0F4"));
            } else if (scoreAccount >= 3.2) {
                tv.setTextColor(Color.parseColor("#FF9800"));
            } else {
                tv.setTextColor(Color.parseColor("#BAFA0133"));
            }
            tv.setText(score+"");
        }else {
            tv.setText("0");
        }
    }

    @BindingAdapter("name")
    public static void setName(TextView tv, String name) {
        String nameAccount = "Hi, " + "<b>" + name + "</b>";
        tv.setText(Html.fromHtml(nameAccount));
    }

    @BindingAdapter("hide")
    public static void checkHideView(ImageView img, String score) {
        if (!score.equals("")) {
            float scoreAccount = Float.parseFloat(score);
            if (scoreAccount >= 2.5 && scoreAccount < 3.2) {
                img.setVisibility(View.VISIBLE);
            } else if (scoreAccount >= 3.2) {
                img.setVisibility(View.VISIBLE);
            } else {
                img.setVisibility(View.GONE);
            }
        } else {
            img.setVisibility(View.GONE);
        }
    }

    @BindingAdapter("avatar")
    public static void setAvatar(CircleImageView img, File file){
        if (file != null){
            img.setImageURI(Uri.fromFile(new File(file+"")));
        }else {
            img.setImageResource(R.drawable.ebba92202bbad4e48dab);
        }
    }

}
