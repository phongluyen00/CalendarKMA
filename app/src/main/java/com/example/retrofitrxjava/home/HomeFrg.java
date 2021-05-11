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
import com.example.retrofitrxjava.home.adapter.BannerAdapter;
import com.example.retrofitrxjava.databinding.LayoutHomeBindingImpl;
import com.example.retrofitrxjava.home.model.Advertisement;
import com.example.retrofitrxjava.model.Article;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HomeFrg extends BaseFragment<LayoutHomeBindingImpl> implements HomeListener,
        HomeContract.View, ItemOnclickListener<Article> {

    private android.os.Handler handler;
    private int currentItem;
    private Runnable runnable;

    public static HomeFrg getInstance() {
        return new HomeFrg();
    }

    @Override
    protected void onBackPressed() {
    }

    @Override
    protected void initLayout() {
        HomePresenter presenter = new HomePresenter(this);
        presenter.retrieveDataHome();
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
    }

    @Override
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

    @Override
    public void retrieveDataEnglishSuccess(List<Article> articles) {
        if (articles != null) {
            BaseAdapter<Article> adapter = new BaseAdapter<>(getActivity(), R.layout.item_recruitment);
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
