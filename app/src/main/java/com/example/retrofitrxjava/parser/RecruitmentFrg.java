package com.example.retrofitrxjava.parser;

import android.os.AsyncTask;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.example.retrofitrxjava.R;
import com.example.retrofitrxjava.b.BAdapter;
import com.example.retrofitrxjava.b.BFragment;
import com.example.retrofitrxjava.b.ItemOnclickListener;
import com.example.retrofitrxjava.databinding.LayoutRecruitmentBinding;
import com.example.retrofitrxjava.loginV3.model.LoginResponse;
import com.example.retrofitrxjava.model.Article;
import com.example.retrofitrxjava.pre.PrefUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RecruitmentFrg extends BFragment<LayoutRecruitmentBinding> implements ItemOnclickListener<Article> {
    private BAdapter<Article> adapter;

    private boolean isCheck = false;

    public void setCheck(boolean check) {
        this.isCheck = check;
    }

    public static RecruitmentFrg getInstance(){
        return new RecruitmentFrg();
    }

    private LoginResponse.Data userModel;

    @Override
    protected void initLayout() {
        Toast.makeText(getActivity(),  isCheck + "", Toast.LENGTH_SHORT).show();
        userModel = PrefUtils.loadData(getActivity());
        if (isCheck){
            new DownloadStudy().execute(getString(R.string.link_study));
        }else {
            if (userModel.getArticleListTD() != null && userModel.getArticleListTD().size() > 0) {
                setData(userModel.getArticleListTD());
            }else {
                new DownloadTask().execute(getString(R.string.link_recruitment));
            }
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
            binding.helpWebview.setWebViewClient(new WebViewClient());
            binding.helpWebview.getSettings().setSupportZoom(true);
            binding.helpWebview.getSettings().setAllowContentAccess(true);
            binding.helpWebview.getSettings().setBuiltInZoomControls(true);
            binding.helpWebview.getSettings().setLoadWithOverviewMode(true);
            binding.helpWebview.getSettings().setUseWideViewPort(true);
            binding.helpWebview.loadUrl(article.getLink());
            binding.rvRecruitment.setVisibility(View.GONE);
            binding.helpWebview.setVisibility(View.VISIBLE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class DownloadTask extends AsyncTask<String, Void, ArrayList<Article>> {
        @Override
        protected ArrayList<Article> doInBackground(String... strings) {
            Document document = null;
            ArrayList<Article> listArticle = new ArrayList<>();
            try {
                document = (Document) Jsoup.connect(strings[0]).get();
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
                        if (titleSubject != null) {
                            String title = titleSubject.attr("title");
                            article.setDes(title);
                        }
                        if (imgSubject != null) {
                            String src = imgSubject.attr("data-src");
                            article.setThumb(src);
                        }
                        if (linkSubject != null) {
                            String link = linkSubject.attr("title");
                            article.setTitle(link);
                        }
                        if (descrip != null) {
                            String des = descrip.text();
                            article.setIncome(des);
                        }

                        if (linkHtml != null) {
                            String link = linkHtml.attr("href");
                            article.setLink(link);
                        }

                        if (locationHtml != null) {
                            String location = locationHtml.text();
                            article.setLocation(location);
                        }
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
            userModel.setArticleListTD(articles);
            PrefUtils.saveData(getActivity(), userModel);
            setData(articles);
        }
    }

    private class DownloadStudy extends AsyncTask<String, Void, ArrayList<Article>> implements ItemOnclickListener<Article>{

        @Override
        protected ArrayList<Article> doInBackground(String... strings) {
            Document document = null;
            ArrayList<Article> listArticle = new ArrayList<>();
            try {
                document = (Document) Jsoup.connect(strings[0]).get();
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
                        if (titleSubject != null) {
                            String title = titleSubject.text();
                            article.setTitle(title);
                        }
                        if (imgSubject != null) {
                            String src = imgSubject.attr("src");
                            article.setThumb(src);
                        }
                        if (linkSubject != null) {
                            String link = linkSubject.attr("href");
                            String title = linkSubject.attr("title");
                            article.setLink("https://codelearn.io/" +link);
                            article.setTitle(title);
                        }
                        if (descrip != null) {
                            String des = descrip.text();
                            article.setDes(des);
                        }
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
            adapter = new BAdapter<>(getActivity(), R.layout.item_study);
            binding.rvRecruitment.setAdapter(adapter);
            adapter.setData(articles);
            binding.progressLoadData.setVisibility(View.GONE);
            adapter.setListener(this);
        }

        @Override
        public void onItemMediaClick(Article article) {
            try {
                binding.helpWebview.setWebViewClient(new WebViewClient());
                binding.helpWebview.getSettings().setSupportZoom(true);
                binding.helpWebview.getSettings().setAllowContentAccess(true);
                binding.helpWebview.getSettings().setBuiltInZoomControls(true);
                binding.helpWebview.getSettings().setLoadWithOverviewMode(true);
                binding.helpWebview.getSettings().setUseWideViewPort(true);
                binding.helpWebview.loadUrl(article.getLink());
                binding.rvRecruitment.setVisibility(View.GONE);
                binding.helpWebview.setVisibility(View.VISIBLE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void setData(ArrayList<Article> articles) {
        adapter = new BAdapter<>(getActivity(), R.layout.item_recruitment);
        binding.rvRecruitment.setAdapter(adapter);
        adapter.setData(articles);
        binding.progressLoadData.setVisibility(View.GONE);
        adapter.setListener(this);
    }

    @Override
    protected void onBackPressed() {
        binding.helpWebview.setVisibility(View.GONE);
        binding.rvRecruitment.setVisibility(View.VISIBLE);
    }
}