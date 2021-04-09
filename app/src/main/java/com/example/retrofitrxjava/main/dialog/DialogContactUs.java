package com.example.retrofitrxjava.main.dialog;

import com.example.retrofitrxjava.R;
import com.example.retrofitrxjava.base.BDialogFragment;
import com.example.retrofitrxjava.databinding.DialogContactBinding;

/**
 * Created by luyenphong on 10/7/2020.
 * 0358844343
 * luyenphong00@gmail.com
 */
public class DialogContactUs extends BDialogFragment<DialogContactBinding> {

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_contact;
    }

    @Override
    protected void initLayout() {
        binding.ivClose.setOnClickListener(v -> dismiss());
    }

}
