package com.example.retrofitrxjava.persional.dialog;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;

import com.example.retrofitrxjava.R;
import com.example.retrofitrxjava.databinding.DialogLogoutBinding;
import com.example.retrofitrxjava.databinding.DialogSyncBinding;
import com.example.retrofitrxjava.main.dialog.DialogListener;

import io.reactivex.annotations.NonNull;

public class DialogSync extends DialogFragment {

    private DialogSyncBinding binding;
    private itemOnClick listener;

    public DialogSync(itemOnClick listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()),
                R.layout.dialog_sync, null, false);
        setCancelable(false);
        return binding.getRoot();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // the content
        final RelativeLayout root = new RelativeLayout(getActivity());
        root.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        // creating the fullscreen dialog
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(root);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        return dialog;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        binding.syncMoney.setOnClickListener(view -> {  listener.onClickSynScore(); });
        binding.tvSyncSchedule.setOnClickListener(view -> { listener.onClickSynSchedule(); });
        binding.syncMoney.setOnClickListener(view -> {  listener.onClickSyncMoney(); });
    }

    public interface itemOnClick{
        void onClickSynScore();
        void onClickSynSchedule();
        void onClickSyncMoney();
    }

}
