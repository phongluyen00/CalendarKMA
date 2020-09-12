package com.example.retrofitrxjava.home;

import android.os.Handler;

import com.example.retrofitrxjava.R;
import com.example.retrofitrxjava.b.BFragment;
import com.example.retrofitrxjava.home.adapter.BannerAdapter;
import com.example.retrofitrxjava.databinding.LayoutHomeBindingImpl;
import com.example.retrofitrxjava.loginV3.model.LoginResponse;
import com.example.retrofitrxjava.home.model.Advertisement;

import java.util.ArrayList;
import java.util.List;

public class HomeFrg extends BFragment<LayoutHomeBindingImpl> implements HomeListener, HomeContract.View {

    private BannerAdapter adapter1;
    private android.os.Handler handler;
    private HomePresenter presenter;
    private ArrayList<Advertisement> advertisements = new ArrayList<>();
    private LoginResponse.Data data;

    @Override
    protected void initLayout() {
        presenter = new HomePresenter(this);
        presenter.retrieveDataHome("");
    }

    public void setDataHome(ArrayList<Advertisement> advertisements, LoginResponse.Data data) {
        this.advertisements = advertisements;
        this.data = data;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_home;
    }

    @Override
    public void onClick() {

    }

    @Override
    public void retrieveDataSuccess(List<Advertisement> data) {
        adapter1 = new BannerAdapter(getContext(), (ArrayList<Advertisement>) data, R.layout.item_banner);
        binding.viewpager.setAdapter(adapter1);
        binding.circleIndicator.setViewPager(binding.viewpager);
        handler = new Handler();
//        runnable = new Runnable() {
//            @Override
//            public void run() {
//                curentItem = binding.viewpager.getCurrentItem();
//                curentItem++;
//                if (curentItem >= binding.viewpager.getAdapter().getCount()) {
//                    curentItem = 0;
//                }
//                binding.viewpager.setCurrentItem(curentItem, true);
//                handler.postDelayed(runnable, 4500);
//
//            }
//        };
//        handler.postDelayed(runnable, 4500);
    }
}
