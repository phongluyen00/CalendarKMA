package com.example.retrofitrxjava.home;

import android.os.AsyncTask;

import com.example.retrofitrxjava.loginV3.model.LoginResponse;
import com.example.retrofitrxjava.home.model.Advertisement;
import com.example.retrofitrxjava.model.Article;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class HomePresenter implements HomeContract.Presenter {

    public static final String MY_URL = "http://flc.actvn.edu.vn/";
    private HomeContract.View view;

    public HomePresenter(HomeContract.View view) {
        this.view = view;
    }

    @Override
    public void retrieveDataHome(String userName) {
        ArrayList<Advertisement> advertisements = new ArrayList<>();
        advertisements.add(new Advertisement("http://home.actvn.edu.vn/Upload/svda/diem-chuan.jpg"));
        advertisements.add(new Advertisement("http://home.actvn.edu.vn/Upload/svda/qt-nhaphoc.jpg"));
        advertisements.add(new Advertisement("https://vietthuong.vn/upload/content/images/tu-van/t-10/top-3-bai-piano-chao-mung-ngay-phu-nu-viet-nam-20-10-1.gif"));
        view.retrieveDataSuccess(advertisements);
    }

    @Override
    public void setData(LoginResponse.Data data) {

    }

    @Override
    public void retrieveDataEnglish() {
        new DownloadTask().execute(MY_URL);
    }

    //Download HTML bằng AsynTask
    private class DownloadTask extends AsyncTask<String, Void, ArrayList<Article>> {

        private static final String TAG = "DownloadTask";

        @Override
        protected ArrayList<Article> doInBackground(String... strings) {
            Document document = null;
            ArrayList<Article> listArticle = new ArrayList<>();
            try {
                document = (Document) Jsoup.connect(strings[0]).get();
                if (document != null) {
                    //Lấy  html có thẻ như sau: div#latest-news > div.row > div.col-md-6 hoặc chỉ cần dùng  div.col-md-6
                    Elements sub = document.select("div.course");
                    for (Element element : sub) {
                        Article article = new Article();
                        Element titleSubject = element.getElementsByTag("img").first();
                        Element imgSubject = element.getElementsByTag("img").first();
                        Element linkSubject = element.getElementsByTag("a").first();
                        Element descrip = element.getElementsByTag("p").first();
                        //Parse to model
                        if (titleSubject != null) {
                            String title = titleSubject.attr("title");
                            article.setTitle(title);
                        }
                        if (imgSubject != null) {
                            String src = imgSubject.attr("src");
                            article.setThumb(MY_URL + src);
                        }
                        if (linkSubject != null) {
                            String link = linkSubject.attr("href");
                            article.setLink(MY_URL + link);
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
            view.retrieveDataEnglishSuccess(articles);
        }
    }
}
