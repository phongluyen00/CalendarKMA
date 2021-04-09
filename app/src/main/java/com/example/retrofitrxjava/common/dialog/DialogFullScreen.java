package com.example.retrofitrxjava.common.dialog;

import android.annotation.SuppressLint;
import android.util.Log;

import com.example.retrofitrxjava.R;
import com.example.retrofitrxjava.base.BAdapter;
import com.example.retrofitrxjava.base.BDialogFragment;
import com.example.retrofitrxjava.databinding.LayoutDialogFullscreenBinding;
import com.example.retrofitrxjava.model.PaymentModel;
import com.example.retrofitrxjava.pre.PrefUtils;
import com.example.retrofitrxjava.utils.AppUtils;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by luyenphong on 9/29/2020.
 * https://www.facebook.com/phong.luyen.96/
 * luyenphong00@gmail.com
 */
public class DialogFullScreen extends BDialogFragment<LayoutDialogFullscreenBinding> {

    private BAdapter<PaymentModel.Data> adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.layout_dialog_fullscreen;
    }

    @SuppressLint("CheckResult")
    @Override
    protected void initLayout() {
        binding.tvId.setText(PrefUtils.loadData(getActivity()).getTen());
        binding.ivBack.setOnClickListener(view -> dismiss());
        myAPI.getPayment(AppUtils.entryData(userModel.getUserEntry(getActivity())))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(paymentModel -> {
                    adapter = new BAdapter<>(getActivity(), R.layout.item_money);
                    binding.rclPayment.setAdapter(adapter);
                    adapter.setData((ArrayList<PaymentModel.Data>) paymentModel.getData());
                }, error -> {
                    Log.d("AAAAA", error.getMessage());
                });

        myAPI.getlephiHocphi(AppUtils.entryData(userModel.getUserEntry(getActivity())))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(tuitionResponse -> {
                    binding.setItem(tuitionResponse.getData().get(0));
                }, throwable -> {
                    Log.d("AAAAA", throwable.getMessage());
                });
    }
}
