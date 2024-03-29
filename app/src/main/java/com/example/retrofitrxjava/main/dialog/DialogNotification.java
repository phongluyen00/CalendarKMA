package com.example.retrofitrxjava.main.dialog;

import android.annotation.SuppressLint;
import android.view.View;

import com.example.retrofitrxjava.NetworkUtils;
import com.example.retrofitrxjava.R;
import com.example.retrofitrxjava.base.BAdapter;
import com.example.retrofitrxjava.base.BDialogFragment;
import com.example.retrofitrxjava.databinding.DialogNotificationBinding;
import com.example.retrofitrxjava.main.model.Notification;
import com.example.retrofitrxjava.pre.PrefUtils;
import com.example.retrofitrxjava.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.example.retrofitrxjava.utils.Constant.SUCCESS;

/**
 * Created by luyenphong on 10/17/2020.
 * 0358844343
 * luyenphong00@gmail.com
 */
public class DialogNotification extends BDialogFragment<DialogNotificationBinding> {
    private BAdapter<Notification.Data> adapter;
    private ArrayList<Notification.Data> arrayList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_notification;
    }

    @SuppressLint("CheckResult")
    @Override
    protected void initLayout() {
        binding.ivClose.setOnClickListener(v -> dismiss());
        if (!NetworkUtils.isConnect(getActivity())) {
            retrieveHandleLearningSuccess(userModel.getNotification().getData());
            return;
        }
        myAPI.getThongBao(AppUtils.entryData("mssv="+userModel.getUserName())).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(response -> response.getErrorCode().equals(SUCCESS))
                .subscribe(response -> {
                            if (userModel.getNotification() == null) {
                                userModel.setNotification(response);
                                PrefUtils.saveData(getActivity(),userModel);
                            }
                            retrieveHandleLearningSuccess(response.getData());
                        },
                        throwable -> {
//                            retrieveHandleLearningSuccess(userModel.getNotification().getData());
                        });
    }

    private void retrieveHandleLearningSuccess(List<Notification.Data> data) {
        if (data != null && data.size() > 0) {
            arrayList.addAll(data);
            adapter = new BAdapter<>(getActivity(), R.layout.item_notification);
            binding.rclNotification.setAdapter(adapter);
            adapter.setData(arrayList);
            binding.groupNoData.setVisibility(View.GONE);
        } else {
            binding.groupNoData.setVisibility(View.VISIBLE);
        }
    }
}
