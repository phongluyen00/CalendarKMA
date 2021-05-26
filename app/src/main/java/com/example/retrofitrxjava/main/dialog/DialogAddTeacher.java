package com.example.retrofitrxjava.main.dialog;

import android.widget.Toast;

import com.example.retrofitrxjava.R;
import com.example.retrofitrxjava.admin.Account;
import com.example.retrofitrxjava.base.BDialogFragment;
import com.example.retrofitrxjava.databinding.DialogAddTeacherBinding;
import com.example.retrofitrxjava.utils.AppUtils;

import java.util.Objects;

public class DialogAddTeacher extends BDialogFragment<DialogAddTeacherBinding> {

    private onItemClick listener;
    private Account account;

    public void setAccount(Account account) {
        this.account = account;
    }

    public DialogAddTeacher(onItemClick listener) {
        this.listener = listener;
    }


    private int isChangeAccount;

    public void setIsChangeAccount(int isChangeAccount) {
        this.isChangeAccount = isChangeAccount;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_add_teacher;
    }

    @Override
    protected void initLayout() {
        binding.setIsChange(isChangeAccount);
        if (isChangeAccount != 0){
            binding.setAccount(account);
        }
        binding.title.setText(isChangeAccount == 1 || isChangeAccount == 2 ? "Cập nhật dữ liệu" : "Thêm tài khoảng giảng viên");
        binding.submit.setText(isChangeAccount == 1 || isChangeAccount == 2 ? "Cập nhật" : "Tạo tài khoản");
        binding.submit.setOnClickListener(v -> {
            String username = Objects.requireNonNull(binding.edtUser.getText()).toString();
            String password = Objects.requireNonNull(binding.edtPassword.getText()).toString();
            String name = Objects.requireNonNull(binding.edtName.getText()).toString();
            String faculty = Objects.requireNonNull(binding.faculty.getText()).toString();
                    if (isChangeAccount  == 1 || isChangeAccount == 2) {
                        listener.onClickAccept(username, password, name, faculty);
                        dismiss();
                        return;
                    }
                    if (!AppUtils.isNullOrEmpty(username) && !AppUtils.isNullOrEmpty(username)
                            && !AppUtils.isNullOrEmpty(username) && !AppUtils.isNullOrEmpty(username)) {
                        listener.onClickAccept(username, password, name, faculty);
                    } else {
                        Toast.makeText(getContext(), "Tạo tài khoản thất bại", Toast.LENGTH_SHORT).show();
                    }
                    dismiss();
                }
        );

        binding.cancel.setOnClickListener(view -> dismiss());
    }

    public interface onItemClick {
        void onClickAccept(String username, String password, String name, String faculty);
    }

}
