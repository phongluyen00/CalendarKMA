package com.example.retrofitrxjava.home;

import android.os.Handler;
import android.util.Log;

import com.example.retrofitrxjava.R;
import com.example.retrofitrxjava.b.BFragment;
import com.example.retrofitrxjava.home.adapter.BannerAdapter;
import com.example.retrofitrxjava.databinding.LayoutHomeBindingImpl;
import com.example.retrofitrxjava.loginV3.model.LoginResponse;
import com.example.retrofitrxjava.home.model.Advertisement;
import com.example.retrofitrxjava.main.model.ScoreMediumResponse;

import java.util.ArrayList;
import java.util.List;

public class HomeFrg extends BFragment<LayoutHomeBindingImpl> implements HomeListener, HomeContract.View {

    private BannerAdapter adapter1;
    private android.os.Handler handler;
    private HomePresenter presenter;
    private LoginResponse.Data data;
    private int currentItem;
    private Runnable runnable;
    private ArrayList<ScoreMediumResponse.Datum> datum = new ArrayList<>();

    @Override
    protected void onBackPressed() {
    }

    @Override
    protected void initLayout() {
        presenter = new HomePresenter(this);
        presenter.retrieveDataHome("");
    }

    public void setDataHome(LoginResponse.Data data, ArrayList<ScoreMediumResponse.Datum> datumArrayList) {
        this.data = data;
        this.datum = datumArrayList;
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
        runnable = new Runnable() {
            @Override
            public void run() {
                currentItem = binding.viewpager.getCurrentItem();
                currentItem++;
                if (currentItem >= binding.viewpager.getAdapter().getCount()) {
                    currentItem = 0;
                }
                binding.viewpager.setCurrentItem(currentItem, true);
                handler.postDelayed(runnable, 4500);

            }
        };
        handler.postDelayed(runnable, 4500);
    }
}
