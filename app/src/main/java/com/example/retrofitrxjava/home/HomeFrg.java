package com.example.retrofitrxjava.home;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.example.retrofitrxjava.NetworkUtils;
import com.example.retrofitrxjava.R;
import com.example.retrofitrxjava.b.BAdapter;
import com.example.retrofitrxjava.b.BFragment;
import com.example.retrofitrxjava.b.ItemOnclickListener;
import com.example.retrofitrxjava.home.adapter.BannerAdapter;
import com.example.retrofitrxjava.databinding.LayoutHomeBindingImpl;
import com.example.retrofitrxjava.loginV3.model.LoginResponse;
import com.example.retrofitrxjava.home.model.Advertisement;
import com.example.retrofitrxjava.model.Article;
import com.example.retrofitrxjava.pre.PrefUtils;
import com.example.retrofitrxjava.Test123;

import java.util.ArrayList;
import java.util.List;

public class HomeFrg extends BFragment<LayoutHomeBindingImpl> implements HomeListener,
        HomeContract.View, ItemOnclickListener<Article> {

    private BannerAdapter adapter1;
    private android.os.Handler handler;
    private HomePresenter presenter;
    private LoginResponse.Data data;
    private int currentItem;
    private Runnable runnable;
    private BAdapter<Article> adapter;

    public static HomeFrg getInstance() {
        return new HomeFrg();
    }

    @Override
    protected void onBackPressed() {
    }

    @Override
    protected void initLayout() {
        data = PrefUtils.loadData(getActivity());
        presenter = new HomePresenter(this);
        presenter.retrieveDataHome(data.getToken());
        if (!NetworkUtils.isConnect(getContext())) {
            retrieveDataEnglishFFailed(getResources().getString(R.string.error_internet));
            return;
        }
        presenter.retrieveDataEnglish();
        binding.setListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_home;
    }

    @Override
    public int getTitle() {
        return R.string.home;
    }

    @Override
    public void onClick() {
        startActivity(new Intent(getActivity(), Test123.class));
    }

    @Override
    public void retrieveDataSuccess(List<Advertisement> data) {
        adapter1 = new BannerAdapter(getContext(), (ArrayList<Advertisement>) data, R.layout.item_banner);
        binding.viewpager.setAdapter(adapter1);
        binding.circleIndicator.setViewPager(binding.viewpager);
        handler = new Handler();
        runnable = () -> {
            currentItem = binding.viewpager.getCurrentItem();
            currentItem++;
            if (currentItem >= binding.viewpager.getAdapter().getCount()) {
                currentItem = 0;
            }
            binding.viewpager.setCurrentItem(currentItem, true);
            handler.postDelayed(runnable, 4500);

        };
        handler.postDelayed(runnable, 4500);
    }

    @Override
    public void retrieveDataEnglishSuccess(List<Article> articles) {
        if (articles != null) {
            adapter = new BAdapter<>(getActivity(), R.layout.item_recruitment);
            binding.rcEnglish.setAdapter(adapter);
            ArrayList<Article> articles1 = new ArrayList<>();
            if (articles.size() > 0){
                for (int i = 0; i <= 5 ; i++) {
                    articles1.add(articles.get(i));
                }
            }
            adapter.setData(articles1);
            adapter.setListener(this);
            binding.progress.setVisibility((articles1.size()== 0) ? View.VISIBLE : View.GONE);
        }
    }

    @Override
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
