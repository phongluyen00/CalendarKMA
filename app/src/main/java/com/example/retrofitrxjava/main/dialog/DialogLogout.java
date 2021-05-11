package com.example.retrofitrxjava.main.dialog;

import android.view.View;

import com.example.retrofitrxjava.R;
import com.example.retrofitrxjava.base.BDialogFragment;
import com.example.retrofitrxjava.databinding.DialogLogoutBinding;

public class DialogLogout extends BDialogFragment<DialogLogoutBinding> {

    private onItemClick listener;

    public DialogLogout(onItemClick listener) {
        this.listener = listener;
    }

    @Override
    protected int getLayoutId() {
        return  R.layout.dialog_logout;
    }

    @Override
    protected void initLayout() {
        binding.tvOk.setOnClickListener(view -> listener.onClickAccept(view));
        binding.tvCancel.setOnClickListener(view -> listener.onClickCancel(view));
    }

    public interface onItemClick {
        void onClickAccept(View view);
        void onClickCancel(View view);
    }

}
