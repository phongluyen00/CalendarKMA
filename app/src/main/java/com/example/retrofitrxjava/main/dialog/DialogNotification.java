package com.example.retrofitrxjava.main.dialog;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.View;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.retrofitrxjava.NetworkUtils;
import com.example.retrofitrxjava.R;
import com.example.retrofitrxjava.b.BAdapter;
import com.example.retrofitrxjava.b.BDialogFragment;
import com.example.retrofitrxjava.databinding.DialogNotificationBinding;
import com.example.retrofitrxjava.main.model.Notification;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

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

        AndroidNetworking.post("https://mockapi.superoffice.vn/api/3pi69i/notification")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("aaa", response.toString());
                        Gson gson = new Gson();
                        Notification obj = gson.fromJson(response.toString(), Notification.class);
                        Log.d("aaaaa", obj.getData().size() + "");
                        arrayList.addAll(obj.getData());
                        if (obj.getData() != null && obj.getData().size() > 0){
                            adapter = new BAdapter<>(getActivity(), R.layout.item_notification);
                            binding.rclNotification.setAdapter(adapter);
                            adapter.setData(arrayList);
                            binding.groupNoData.setVisibility(View.GONE);

                        }else {
                            binding.groupNoData.setVisibility(View.VISIBLE);
                        }
                        // do anything with response
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                        binding.groupNoData.setVisibility(View.VISIBLE);
                    }
                });

        binding.ivClose.setOnClickListener(v -> dismiss());
    }
}
