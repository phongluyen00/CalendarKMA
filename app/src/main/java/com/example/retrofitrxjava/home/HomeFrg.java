package com.example.retrofitrxjava.home;

import android.os.Handler;
import android.util.Log;

import com.example.retrofitrxjava.R;
import com.example.retrofitrxjava.b.BFragment;
import com.example.retrofitrxjava.b.BannerAdapter;
import com.example.retrofitrxjava.databinding.LayoutHomeBindingImpl;
import com.example.retrofitrxjava.model.Advertisement;

import java.util.ArrayList;
import java.util.List;

public class HomeFrg extends BFragment<LayoutHomeBindingImpl> implements HomeListener, HomeContract.View {

    private BannerAdapter adapter1;
    private android.os.Handler handler;
    private HomePresenter presenter;
    private ArrayList<Advertisement> advertisements = new ArrayList<>();

    @Override
    protected void initLayout() {
        presenter = new HomePresenter(this);
        presenter.retrieveDataHome("");
        Log.d("AAAAA", "HOME");
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

    public void setDataHome(ArrayList<Advertisement> advertisements) {
        this.advertisements = advertisements;
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
        adapter1 = new BannerAdapter(getActivity(), (ArrayList<Advertisement>) data);
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
