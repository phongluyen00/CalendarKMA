package com.example.retrofitrxjava.common.dialog;

import android.annotation.SuppressLint;
import android.widget.Toast;

import com.example.retrofitrxjava.R;
import com.example.retrofitrxjava.base.BAdapter;
import com.example.retrofitrxjava.base.BDialogFragment;
import com.example.retrofitrxjava.databinding.DialogCcBinding;
import com.example.retrofitrxjava.loginV3.model.LoginResponse;
import com.example.retrofitrxjava.model.CertificateResponse;
import com.example.retrofitrxjava.model.HandleLearning;
import com.example.retrofitrxjava.pre.PrefUtils;
import com.example.retrofitrxjava.utils.AppUtils;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.example.retrofitrxjava.utils.Constant.SUCCESS;

/**
 * Created by luyenphong on 10/11/2020.
 * 0358844343
 * luyenphong00@gmail.com
 */
public class DialogFullScreenXLHV extends BDialogFragment<DialogCcBinding> {

    private LoginResponse.Data userModel;
    private BAdapter<CertificateResponse.Data> adapter;
    private BAdapter<HandleLearning.Data> adapterHandler;

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_cc;
    }

    @SuppressLint("CheckResult")
    @Override
    protected void initLayout() {
        userModel = PrefUtils.loadData(getActivity());
        myAPI.getCertificate(AppUtils.entryData("mssv="+userModel.getUserName())).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(response -> response.getErrorCode().equals(SUCCESS))
                .subscribe(response -> retrieveSuccess(response),
                        throwable -> Toast.makeText(getActivity(), "Hệ thống đang bận ! Vui lòng thử lại sau !",
                                Toast.LENGTH_SHORT).show());

        myAPI.getHandleLearning(AppUtils.entryData("mssv="+userModel.getUserName())).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(response -> response.getErrorCode().equals(SUCCESS))
                .subscribe(response -> retrieveHandleLearningSuccess(response),
                        throwable -> Toast.makeText(getActivity(), "Hệ thống đang bận ! Vui lòng thử lại sau !",
                                Toast.LENGTH_SHORT).show());
        binding.ivBack.setOnClickListener(v -> dismiss());
    }

    private void retrieveHandleLearningSuccess(HandleLearning response) {
        adapterHandler = new BAdapter<>(getActivity(),R.layout.item_handler);
        binding.rclXlhv.setAdapter(adapterHandler);
        adapterHandler.setData((ArrayList<HandleLearning.Data>) response.getData());
    }

    private void retrieveSuccess(CertificateResponse certificateResponse) {
        adapter = new BAdapter<>(getActivity(), R.layout.item_header_score);
        binding.rclCc.setAdapter(adapter);
        adapter.setData((ArrayList<CertificateResponse.Data>) certificateResponse.getData());
    }
}
