package com.example.retrofitrxjava.home;

import android.os.AsyncTask;

import com.example.retrofitrxjava.R;
import com.example.retrofitrxjava.home.model.Advertisement;
import com.example.retrofitrxjava.model.Article;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class HomePresenter implements HomeContract.Presenter {

    public static final String MY_URL = "https://careerbuilder.vn/viec-lam/cntt-phan-mem-c1e1-vi.html";
    private HomeContract.View view;

    public HomePresenter(HomeContract.View view) {
        this.view = view;
    }

    @Override
    public void retrieveDataHome() {
        ArrayList<Advertisement> advertisements = new ArrayList<>();
        advertisements.add(new Advertisement("https://emsc.vn/wp-content/uploads/2020/07/nhu-cau-tuyen-dung-quy3-2020.jpg"));
        advertisements.add(new Advertisement("https://emsc.vn/wp-content/uploads/2020/07/nhu-cau-tuyen-dung-quy3-2020.jpg"));
        advertisements.add(new Advertisement("https://emsc.vn/wp-content/uploads/2020/07/nhu-cau-tuyen-dung-quy3-2020.jpg"));
        view.retrieveDataSuccess(advertisements);
    }

    @Override
    public void retrieveDataEnglish() {
        new DownloadTask().execute(MY_URL);
    }

    //Download HTML bằng AsynTask
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
            //Setup data recyclerView
            view.retrieveDataEnglishSuccess(articles);
        }
    }

}
