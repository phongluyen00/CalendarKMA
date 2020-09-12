package com.example.retrofitrxjava.loginV3;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.example.retrofitrxjava.R;
import com.example.retrofitrxjava.b.BActivity;
import com.example.retrofitrxjava.databinding.LayoutLoginBinding;
import com.example.retrofitrxjava.loginV3.model.LoginResponse;
import com.example.retrofitrxjava.main.MainActivity;
import com.example.retrofitrxjava.model.AccountModel;

public class LoginActivity extends BActivity<LayoutLoginBinding> implements LoginListener, LoginContract.View {

    LoginPresenter presenter;

    @Override
    protected void initLayout() {
        binding.setListener(this);
        presenter = new LoginPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_login;
    }

    @Override
    public void onClick() {
        String userName = binding.edtUser.getText().toString();
        String password = binding.edtPassword.getText().toString();
        binding.progressbar.setVisibility(View.VISIBLE);
        presenter.verifyAccount(myAPI, userName, password);
    }

    @Override
    public void verifyAccountSuccess(String userAccount, String password) {
        Toast.makeText(this, R.string.sync, Toast.LENGTH_SHORT).show();
        presenter.synchronization(myAPI, userAccount, password);
    }

    @Override
    public void pushView(LoginResponse.Data data) {
        Toast.makeText(this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
        AccountModel.name = data.getName();
        AccountModel.classRoom = data.getClassRoom();
        AccountModel.status = data.getStatus();
        binding.progressbar.setVisibility(View.GONE);
        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra("obj",data);
        startActivity(intent);
    }


    @Override
    public void synchronizationSuccess(String message) {
        binding.progressbar.setVisibility(View.GONE);
        Toast.makeText(this, R.string.sync_success, Toast.LENGTH_LONG).show();
        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    public void synchronizationFailed(String message) {
        binding.progressbar.setVisibility(View.GONE);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
