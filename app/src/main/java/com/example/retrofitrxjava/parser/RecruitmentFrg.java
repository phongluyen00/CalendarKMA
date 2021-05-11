package com.example.retrofitrxjava.parser;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
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

import org.greenrobot.eventbus.EventBus;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class RecruitmentFrg extends BaseFragment<LayoutRecruitmentBinding> implements ItemOnclickListener<Article> {
    private BaseAdapter<Article> adapter;

    private boolean isCheck = false;

    public void setCheck(boolean check) {
        this.isCheck = check;
    }

    public static RecruitmentFrg getInstance() {
        return new RecruitmentFrg();
    }


    @Override
    protected void initLayout() {
        if (isCheck) {
            new DownloadStudy().execute(getString(R.string.link_study));
        } else {
            new DownloadTask().execute(getString(R.string.link_recruitment));
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

    @SuppressLint("StaticFieldLeak")
    private class DownloadTask extends AsyncTask<String, Void, ArrayList<Article>> {
        @Override
        protected ArrayList<Article> doInBackground(String... strings) {
            Document document;
            ArrayList<Article> listArticle = new ArrayList<>();
            try {
                document = Jsoup.connect(strings[0]).get();
                if (document != null) {
                    //Lấy  html có thẻ như sau: div#latest-news > div.row > div.col-md-6 hoặc chỉ cần dùng  div.col-md-6
                    Elements sub = document.select("div.job-item");
                    for (Element element : sub) {
                        Article article = new Article();
                        Element titleSubject = element.getElementsByTag("a").first();
                        Element imgSubject = element.getElementsByTag("img").first();
                        Elements linkSubject = element.getElementsByTag("a").after("job_link").get(1).getElementsByAttribute("title");
                        Element descrip = element.getElementsByTag("p").first();
                        Element linkHtml = element.getElementsByTag("a").first();
                        Element locationHtml = element.getElementsByTag("ul").first();
                        //Parse to model
                        article.setDes(titleSubject == null ? "" : titleSubject.attr("title"));
                        article.setThumb(imgSubject == null ? "" : imgSubject.attr("data-src"));
                        article.setTitle(linkSubject.attr("title"));
                        article.setIncome(descrip == null ? "" : descrip.text());
                        article.setLink(linkHtml == null ? "" : linkHtml.attr("href"));
                        article.setLocation(locationHtml != null ? locationHtml.text() : "");
                        //Add to list
                        listArticle.add(article);
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return listArticle;
        }

        @Override
        protected void onPostExecute(ArrayList<Article> articles) {
            super.onPostExecute(articles);
            setData(articles);
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

    @SuppressLint("StaticFieldLeak")
    private class DownloadStudy extends AsyncTask<String, Void, ArrayList<Article>> implements ItemOnclickListener<Article> {

        @Override
        protected ArrayList<Article> doInBackground(String... strings) {
            Document document;
            ArrayList<Article> listArticle = new ArrayList<>();
            try {
                document = Jsoup.connect(strings[0]).get();
                if (document != null) {
                    //Lấy  html có thẻ như sau: div#latest-news > div.row > div.col-md-6 hoặc chỉ cần dùng  div.col-md-6
                    Elements sub = document.select("div.wrap-course-item");
                    for (Element element : sub) {
                        Article article = new Article();
                        Element titleSubject = element.getElementsByTag("h3").first();
                        Element imgSubject = element.getElementsByTag("img").first();
                        Element linkSubject = element.getElementsByTag("a").first();
                        Element descrip = element.getElementsByTag("p").first();
                        //Parse to model
                        article.setTitle(titleSubject == null ? "" : titleSubject.text());
                        article.setThumb(imgSubject == null ? "" : "https://codelearn.io/" + imgSubject.attr("src"));
                        if (linkSubject != null) {
                            String link = linkSubject.attr("href");
                            String title = linkSubject.attr("title");
                            article.setLink("https://codelearn.io/" + link);
                            article.setTitle(title);
                        }
                        article.setDes(descrip == null ? "" : descrip.text());
                        //Add to list
                        listArticle.add(article);
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return listArticle;
        }

        @Override
        protected void onPostExecute(ArrayList<Article> articles) {
            super.onPostExecute(articles);
            //Setup data recyclerView
            adapter = new BaseAdapter<>(getActivity(), R.layout.item_study);
            binding.rvRecruitment.setAdapter(adapter);
            adapter.setData(articles);
            binding.progressLoadData.setVisibility(View.GONE);
            adapter.setListener(this);
        }

        @Override
        public void onItemMediaClick(Article article) {
            try {
                defaultWedView(article.getLink());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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