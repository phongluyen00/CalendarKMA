package com.example.retrofitrxjava.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.example.retrofitrxjava.NetworkUtils;
import com.example.retrofitrxjava.R;
import com.example.retrofitrxjava.base.BaseAdapter;
import com.example.retrofitrxjava.base.BaseFragment;
import com.example.retrofitrxjava.base.ItemOnclickListener;
import com.example.retrofitrxjava.databinding.LayoutHomeBindingImpl;
import com.example.retrofitrxjava.home.adapter.BannerAdapter;
import com.example.retrofitrxjava.home.model.Advertisement;
import com.example.retrofitrxjava.main.MainViewModel;
import com.example.retrofitrxjava.model.Article;
import com.example.retrofitrxjava.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HomeFrg extends BaseFragment<LayoutHomeBindingImpl> implements ItemOnclickListener<Article> {

    private android.os.Handler handler;
    private int currentItem;
    private Runnable runnable;
    List<Article> articles;

    public HomeFrg(List<Article> articles) {
        this.articles = articles;
    }

    @Override
    protected void onBackPressed() {
    }

    @Override
    protected void initLayout() {
        if (!NetworkUtils.isConnect(getContext())) {
            retrieveDataEnglishFFailed(getResources().getString(R.string.error_internet));
        }
        if (!AppUtils.isNullOrEmpty(articles)) {
            retrieveDataEnglishSuccess(articles);
        }
        ArrayList<Advertisement> advertisements = new ArrayList<>();
        advertisements.add(new Advertisement("https://emsc.vn/wp-content/uploads/2020/07/nhu-cau-tuyen-dung-quy3-2020.jpg"));
        advertisements.add(new Advertisement("https://hearlifevietnam.com/wp-content/uploads/2021/05/56599827-633138617112437-1982170988137152512-o.png"));
        advertisements.add(new Advertisement("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSK6gBqg6bP0F8f-YMDsZTN3Qs7WXCN0o_eZg&usqp=CAU"));
        retrieveDataSuccess(advertisements);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_home;
    }

    @Override
    public int getTitle() {
        return R.string.home;
    }

    public void retrieveDataSuccess(List<Advertisement> data) {
        BannerAdapter adapter1 = new BannerAdapter(Objects.requireNonNull(getContext()), (ArrayList<Advertisement>) data, R.layout.item_banner);
        binding.viewpager.setAdapter(adapter1);
        binding.circleIndicator.setViewPager(binding.viewpager);
        handler = new Handler();
        runnable = () -> {
            currentItem = binding.viewpager.getCurrentItem();
            currentItem++;
            if (currentItem >= Objects.requireNonNull(binding.viewpager.getAdapter()).getCount()) {
                currentItem = 0;
            }
            binding.viewpager.setCurrentItem(currentItem, true);
            handler.postDelayed(runnable, 4500);

        };
        handler.postDelayed(runnable, 4500);
    }

    public void retrieveDataEnglishSuccess(List<Article> articles) {
        if (!AppUtils.isNullOrEmpty(articles)) {
            BaseAdapter<Article> adapter = new BaseAdapter<>(getActivity(), R.layout.item_recruitment);
            binding.rcEnglish.setAdapter(adapter);
            ArrayList<Article> articles1 = new ArrayList<>();
            if (articles.size() > 0) {
                for (int i = 0; i <= 5; i++) {
                    articles1.add(articles.get(i));
                }
            }
            adapter.setData(articles1);
            adapter.setListener(this);
            binding.progress.setVisibility((articles1.size() == 0) ? View.VISIBLE : View.GONE);
        }
    }

    public void retrieveDataEnglishFFailed(String message) {
        binding.progress.setVisibility(View.GONE);
        binding.label1.setVisibility(View.GONE);
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemMediaClick(Article article) {
        Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(article.getLink()));
        startActivity(myIntent);
    }
}
