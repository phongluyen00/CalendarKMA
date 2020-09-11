package com.example.retrofitrxjava.loginV3;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.retrofitrxjava.main.MainActivity;
import com.example.retrofitrxjava.R;
import com.example.retrofitrxjava.b.BActivity;
import com.example.retrofitrxjava.databinding.LayoutLoginBinding;
import com.example.retrofitrxjava.loginV3.model.LoginResponse;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.example.retrofitrxjava.utils.Constant.SUCCESS;

public class LoginActivity extends BActivity<LayoutLoginBinding> implements LoginListener {

    @Override
    protected void initLayout() {
        binding.setListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_login;
    }

    @Override
    public void onClick() {
        String userNam = binding.edtUser.getText().toString();
        String password = binding.edtPassword.getText().toString();
        binding.progressbar.setVisibility(View.VISIBLE);
        myAPI.loginStatus(userNam, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LoginResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(LoginResponse loginResponse) {
                        binding.progressbar.setVisibility(View.GONE);
                        if (loginResponse.getErrorCode() == SUCCESS) {
                            Toast.makeText(LoginActivity.this, "Đăng nhập thành công ! Xin bạn chờ trong giây lát để đồng bộ dữ liệu", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        } else {
                            Toast.makeText(LoginActivity.this, "Đăng nhập thất bại ! Vui lòng nhập đúng thông tin", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("AAA", e + "");
                    }


                    @Override
                    public void onComplete() {

                    }
                });
    }
}
