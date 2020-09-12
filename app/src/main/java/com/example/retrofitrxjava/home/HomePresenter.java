package com.example.retrofitrxjava.home;

import com.example.retrofitrxjava.loginV3.model.LoginResponse;
import com.example.retrofitrxjava.home.model.Advertisement;

import java.util.ArrayList;

public class HomePresenter implements HomeContract.Presenter {

    private HomeContract.View view;

    public HomePresenter(HomeContract.View view) {
        this.view = view;
    }

    @Override
    public void retrieveDataHome(String userName) {
        ArrayList<Advertisement> advertisements = new ArrayList<>();
        advertisements.add(new Advertisement("http://home.actvn.edu.vn/Upload/svda/nco.jpg"));
        advertisements.add(new Advertisement("http://home.actvn.edu.vn/Upload/svda/299.png"));
        advertisements.add(new Advertisement("http://home.actvn.edu.vn/Upload/svda/ts19-8-web.jpg"));
        view.retrieveDataSuccess(advertisements);
    }

    @Override
    public void setData(LoginResponse.Data data) {

    }
}
