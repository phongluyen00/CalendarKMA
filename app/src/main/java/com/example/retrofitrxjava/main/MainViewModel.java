package com.example.retrofitrxjava.main;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.example.retrofitrxjava.R;
import com.example.retrofitrxjava.base.BaseObserver;
import com.example.retrofitrxjava.base.BaseViewModel;
import com.example.retrofitrxjava.main.model.ResponseBDCT;
import com.example.retrofitrxjava.main.model.ResponseBDTB;
import com.example.retrofitrxjava.main.model.ResponseSchedule;
import com.example.retrofitrxjava.model.Article;
import com.example.retrofitrxjava.utils.AppUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by luyenphong on 13/11/2020.
 */
public class MainViewModel extends BaseViewModel {

    private MutableLiveData<ResponseSchedule> scheduleMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<ResponseBDCT> detailScoreLiveData = new MutableLiveData<>();
    private MutableLiveData<ResponseBDTB> liveData = new MutableLiveData<>();
    private MutableLiveData<List<Article>> articleLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Article>> articleStudy = new MutableLiveData<>();
    private MutableLiveData<List<Article>> liveDataParser = new MutableLiveData<>();
    public static final String MY_URL = "https://careerbuilder.vn/viec-lam/cntt-phan-mem-c1e1-vi.html";

    public void retrieveSchedule(String status) {
        AppUtils.HandlerRXJava(requestAPI.retrieveSchedule(username, password, status),
                new BaseObserver<ResponseSchedule>() {
            @Override
            public void onSuccess(ResponseSchedule responseSchedule) {
                scheduleMutableLiveData.postValue(responseSchedule);
            }

            @Override
            public void onFailed(String message) {
                scheduleMutableLiveData.postValue(null);
            }
        });
    }

    public void retrieveBDCT(String status) {
        AppUtils.HandlerRXJava(requestAPI.retrieveBDCT(username, password, status), new BaseObserver<ResponseBDCT>() {
            @Override
            public void onSuccess(ResponseBDCT responseBDCT) {
                if (!AppUtils.isNullOrEmpty(responseBDCT) && responseBDCT.isSuccess()) {
                    detailScoreLiveData.postValue(responseBDCT);
                } else {
                    Toast.makeText(activity, "Cập nhật dữ liệu thất bại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailed(String message) {
                detailScoreLiveData.postValue(null);
                Toast.makeText(activity, "Cập nhật dữ liệu thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void retrieveBDTB(String status) {
        AppUtils.HandlerRXJava(requestAPI.retrieveBDTB(username, password, status), new BaseObserver<ResponseBDTB>() {
            @Override
            public void onSuccess(ResponseBDTB responseBDTB) {
                if (!AppUtils.isNullOrEmpty(responseBDTB)) {
                    liveData.postValue(responseBDTB);
                }
            }

            @Override
            public void onFailed(String message) {

            }
        });
    }

    public void retrieveArtist(){
        if (AppUtils.isNullOrEmpty(getArticleLiveData().getValue())){
            new DownloadTask().execute(MY_URL);
        }else {
            articleLiveData.postValue(getArticleLiveData().getValue());
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
            articleLiveData.postValue(articles);
        }
    }

    public void downloadParser(){
        if (AppUtils.isNullOrEmpty(getLiveDataParser().getValue())){
            new DownloadParser().execute(activity.getString(R.string.link_recruitment));
        }else {
            liveDataParser.postValue(getLiveDataParser().getValue());
        }
    }

    public void downloadStudy(){
        if (AppUtils.isNullOrEmpty(getArticleStudy().getValue())){
            new DownloadStudy().execute(activity.getString(R.string.link_study));
        }else {
            articleStudy.postValue(getArticleStudy().getValue());
        }
    }

    //Download HTML bằng AsynTask
    @SuppressLint("StaticFieldLeak")
    private class DownloadParser extends AsyncTask<String, Void, ArrayList<Article>> {
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
            //Setup data recyclerView
            liveDataParser.setValue(articles);
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class DownloadStudy extends AsyncTask<String, Void, ArrayList<Article>> {

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
            articleStudy.postValue(articles);
        }

    }

    public MutableLiveData<List<Article>> getLiveDataParser() {
        return liveDataParser;
    }


    public MutableLiveData<ResponseSchedule> getScheduleMutableLiveData() {
        return scheduleMutableLiveData;
    }

    public MutableLiveData<ResponseBDCT> getDetailScoreLiveData() {
        return detailScoreLiveData;
    }

    public MutableLiveData<ResponseBDTB> getLiveData() {
        return liveData;
    }

    public MutableLiveData<List<Article>> getArticleLiveData() {
        return articleLiveData;
    }

    public MutableLiveData<List<Article>> getArticleStudy() {
        return articleStudy;
    }
}
