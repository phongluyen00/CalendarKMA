package com.example.retrofitrxjava.main.dialog;

import android.view.View;

import com.example.retrofitrxjava.R;
import com.example.retrofitrxjava.b.BDialogFragment;
import com.example.retrofitrxjava.databinding.DialogConfirmBinding;

/**
 * Created by luyenphong on 9/30/2020.
 * 0358844343
 * luyenphong00@gmail.com
 */
public class DialogConfirmShowCalendar extends BDialogFragment<DialogConfirmBinding> {

    private itemOnClick listener;

    public DialogConfirmShowCalendar(itemOnClick listener) {
        this.listener = listener;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_confirm;
    }

    @Override
    protected void initLayout() {
        binding.tvMonth.setOnClickListener(v -> listener.onClickMonth());
        binding.tvWeek.setOnClickListener(v -> listener.onClickWeek());
    }

    public interface itemOnClick{
        void onClickMonth();
        void onClickWeek();
    }
}
