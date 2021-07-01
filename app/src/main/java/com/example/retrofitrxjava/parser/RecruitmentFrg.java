package com.example.retrofitrxjava.parser;

import android.text.TextUtils;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.retrofitrxjava.R;
import com.example.retrofitrxjava.base.BaseAdapter;
import com.example.retrofitrxjava.base.BaseFragment;
import com.example.retrofitrxjava.base.ItemOnclickListener;
import com.example.retrofitrxjava.databinding.LayoutRecruitmentBinding;
import com.example.retrofitrxjava.model.Article;
import com.example.retrofitrxjava.parser.event.EventUpdateTitle;
import com.example.retrofitrxjava.utils.AppUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class RecruitmentFrg extends BaseFragment<LayoutRecruitmentBinding> implements ItemOnclickListener<Article> {
    private BaseAdapter<Article> adapter;

    private List<Article> listTask;
    private List<Article> listStudy;

    public RecruitmentFrg(List<Article> listTask, List<Article> listStudy) {
        this.listTask = listTask;
        this.listStudy = listStudy;
    }

    @Override
    protected void initLayout() {
        if (!AppUtils.isNullOrEmpty(listStudy)) {
            adapter = new BaseAdapter<>(getActivity(), R.layout.item_study);
            binding.rvRecruitment.setAdapter(adapter);
            adapter.setData((ArrayList<Article>) listStudy);
            binding.progressLoadData.setVisibility(View.GONE);
            adapter.setListener(this);
        } else {
            setData((ArrayList<Article>) listTask);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_recruitment;
    }

    @Override
    public int getTitle() {
        return R.string.add_widget;
    }

    @Override
    public void onItemMediaClick(Article article) {
        try {
            defaultWedView(article.getLink());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void defaultWedView(String link) {
        binding.helpWebview.setWebViewClient(new WebViewClient());
        binding.helpWebview.getSettings().setSupportZoom(true);
        binding.helpWebview.getSettings().setAllowContentAccess(true);
        binding.helpWebview.getSettings().setBuiltInZoomControls(true);
        binding.helpWebview.getSettings().setLoadWithOverviewMode(true);
        binding.helpWebview.getSettings().setUseWideViewPort(true);
        binding.helpWebview.loadUrl(link);
        binding.rvRecruitment.setVisibility(View.GONE);
        binding.helpWebview.setVisibility(View.VISIBLE);

        binding.helpWebview.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                if (!TextUtils.isEmpty(title)) {
                    EventBus.getDefault().post(new EventUpdateTitle(title));
                }
            }
        });
    }

    private void setData(ArrayList<Article> articles) {
        adapter = new BaseAdapter<>(getActivity(), R.layout.item_recruitment);
        binding.rvRecruitment.setAdapter(adapter);
        adapter.setData(articles);
        binding.progressLoadData.setVisibility(View.GONE);
        adapter.setListener(this);
    }

    @Override
    protected void onBackPressed() {
        binding.helpWebview.setVisibility(View.GONE);
        binding.rvRecruitment.setVisibility(View.VISIBLE);
        EventBus.getDefault().post(new EventUpdateTitle(getResources().getString(R.string.recruitment)));
    }
}