package com.example.retrofitrxjava.loginV3;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.CountDownTimer;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.text.Editable;
import android.text.Html;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import com.example.retrofitrxjava.NetworkUtils;
import com.example.retrofitrxjava.R;
import com.example.retrofitrxjava.base.BActivity;
import com.example.retrofitrxjava.databinding.LayoutLoginBinding;
import com.example.retrofitrxjava.loginV3.model.LoginResponse;
import com.example.retrofitrxjava.main.MainActivity;
import com.example.retrofitrxjava.pre.PrefUtils;
import com.example.retrofitrxjava.utils.AppUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class LoginActivity extends BActivity<LayoutLoginBinding> implements LoginListener, LoginContract.View {

    private final int TIME_RESEND = 60000;
    private final int COUNTDOWN_INTERVAL = 1000;

    private static final String KEY_NAME = "key_name";
    private LoginPresenter presenter;
    private boolean isShowPass;
    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;

    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]

    private boolean mVerificationInProgress = false;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private CountDownTimer countDownTimer;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void initLayout() {
        binding.setListener(this);
        presenter = new LoginPresenter(this);
        binding.edtUser.setHint("User name");
        binding.edtPassword.setHint("Password");
        mAuth = FirebaseAuth.getInstance();
        setupUI(findViewById(R.id.layout_login));
        checkShowView(binding.edtUser.getText().toString(), binding.edtPassword.getText().toString());
        binding.ivClearUsername.setOnClickListener(view -> binding.edtUser.setText(""));
        binding.ivClearPassword.setOnClickListener(view -> binding.edtPassword.setText(""));
        binding.edtUser.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkShowView(binding.edtUser.getText().toString().trim(), null);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        binding.edtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkShowView(null, binding.edtPassword.getText().toString().trim());
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        binding.showPass.setOnClickListener(v -> {
            if (!isShowPass) {
                isShowPass = true;
                binding.edtPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            } else {
                isShowPass = false;
                binding.edtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            }

        });
        initFingerprint();
        binding.tvOk.setOnClickListener(v -> {
            String code = binding.fieldVerificationCode.getText().toString();
            if (TextUtils.isEmpty(code)) {
                binding.fieldVerificationCode.setError("Cannot be empty.");
                return;
            }

            verifyPhoneNumberWithCode(mVerificationId, code);
        });

        binding.fieldVerificationCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.tvReSend.setOnClickListener(v -> {
            if (!TextUtils.isEmpty(data.getSdt())) {
                resendVerificationCode(data.getSdt(), mResendToken);
            }
        });
        binding.tvFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.layoutLogin.setVisibility(View.VISIBLE);
                binding.layoutOtp.setVisibility(View.GONE);
            }
        });
        initCallBackOTP();
    }

    private void startCountDownTimer(){
        countDownTimer = new CountDownTimer(TIME_RESEND, COUNTDOWN_INTERVAL) {

            public void onTick(long millisUntilFinished) {
                binding.tvReSend.setEnabled(false);
            }

            public void onFinish() {
                binding.tvReSend.setEnabled(true);
            }

        }.start();
    }

    public void setupUI(View view) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    AppUtils.hideKeyboard(LoginActivity.this);
                    return false;
                }
            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView);
            }
        }
    }

    private void initCallBackOTP() {
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                mVerificationInProgress = false;
                signInWithPhoneAuthCredential(credential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Log.i("hadtt", "onVerificationFailed", e);
                binding.progressbar.setVisibility(View.GONE);
                mVerificationInProgress = false;
                if (e instanceof FirebaseAuthInvalidCredentialsException) {

                } else if (e instanceof FirebaseTooManyRequestsException) {
                    Snackbar.make(findViewById(android.R.id.content), "Quota exceeded.",
                            Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {

                Log.i("hadtt", "onCodeSent:" + verificationId);
                mVerificationId = verificationId;
                mResendToken = token;
            }
        };
    }

    private void verifyPhoneNumberWithCode(String verificationId, String code) {
        // [START verify_with_code]
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        // [END verify_with_code]
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = task.getResult().getUser();
                            if (data != null) {
                                loginSuccess(data);
                            }

                        } else {
                            // Sign in failed, display a message and update the UI
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                binding.fieldVerificationCode.setError("Invalid code.");
                            }
                        }
                    }
                });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initFingerprint() {
        executor = ContextCompat.getMainExecutor(this);
        biometricPrompt = new BiometricPrompt(this,
                executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode,
                                              @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Toast.makeText(getApplicationContext(),
                        "Authentication error: " + errString, Toast.LENGTH_SHORT)
                        .show();
            }

            @Override
            public void onAuthenticationSucceeded(
                    @NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                onClick();
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(getApplicationContext(), "Authentication failed",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        });

        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Biometric login for my app")
                .setSubtitle("Log in using your biometric credential")
                .setNegativeButtonText("Use account password")
                .setConfirmationRequired(false)
                .build();
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                generateSecretKey(new KeyGenParameterSpec.Builder(
                        KEY_NAME,
                        KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                        .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                        .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
                        .setUserAuthenticationRequired(true)
                        .setInvalidatedByBiometricEnrollment(true)
                        .build());
            }
        } catch (NoSuchProviderException | NoSuchAlgorithmException | InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }

        binding.imgFingerprint.setOnClickListener(v -> {
            Cipher cipher = null;
            try {
                cipher = getCipher();
            } catch (NoSuchPaddingException | NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            SecretKey secretKey = null;
            try {
                secretKey = getSecretKey();
            } catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException | UnrecoverableKeyException e) {
                e.printStackTrace();
            }
            try {
                assert cipher != null;
                cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            }
            if (PrefUtils.getSetting(getApplicationContext())) {
                if (PrefUtils.loadData(getApplicationContext()) != null) {
                    biometricPrompt.authenticate(promptInfo,
                            new BiometricPrompt.CryptoObject(cipher));
                }
            } else {
                Toast.makeText(this,
                        getString(R.string.ban_can_bat_face_id), Toast.LENGTH_SHORT).show();
            }

        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void generateSecretKey(KeyGenParameterSpec keyGenParameterSpec) throws NoSuchProviderException, NoSuchAlgorithmException, InvalidAlgorithmParameterException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(
                KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");
        keyGenerator.init(keyGenParameterSpec);
        keyGenerator.generateKey();
    }

    private SecretKey getSecretKey() throws KeyStoreException, CertificateException, NoSuchAlgorithmException, IOException, UnrecoverableKeyException {
        KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");

        // Before the keystore can be accessed, it must be loaded.
        keyStore.load(null);
        return ((SecretKey) keyStore.getKey(KEY_NAME, null));
    }

    private Cipher getCipher() throws NoSuchPaddingException, NoSuchAlgorithmException {
        return Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/"
                + KeyProperties.BLOCK_MODE_CBC + "/"
                + KeyProperties.ENCRYPTION_PADDING_PKCS7);
    }

    private void checkShowView(String user, String password) {
        if (user != null) {
            binding.edtUser.setHint("User name");
            binding.ivClearUsername.setVisibility((user.length() > 0) ? View.VISIBLE : View.GONE);
        }

        if (password != null) {
            int length = binding.edtPassword.getText().length();
            binding.edtPassword.setHint("Password");
            binding.ivClearPassword.setVisibility((length > 0) ? View.VISIBLE : View.GONE);
            binding.showPass.setVisibility((length > 0) ? View.VISIBLE : View.GONE);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_login;
    }

    @Override
    public void onClick() {
        if (!NetworkUtils.isConnect(this)) {
            verifyAccountFailed(getResources().getString(R.string.error_internet));
            return;
        }
//        if (!TextUtils.isEmpty(binding.edtUser.getText().toString().trim()) && !TextUtils.isEmpty(binding.edtPassword.getText().toString().trim())) {
            String userName = binding.edtUser.getText().toString().trim().toUpperCase();
            String password = binding.edtPassword.getText().toString().trim();
            String enCode = getResources().getString(R.string.encode_data,userName,password);
            Log.d("AAAAAAAAAAAA", enCode);
            Log.d("Encode", Objects.requireNonNull(AppUtils.entryData(enCode)));
            binding.progressbar.setVisibility(View.VISIBLE);
            presenter.verifyAccount(AppUtils.entryData(enCode));
//        }else {
//            Toast.makeText(this, getString(R.string.validate_eddittext), Toast.LENGTH_SHORT).show();
//        }
        AppUtils.hideKeyboard(this);
    }

    @Override
    public void onBackPressed() {
    }

    private LoginResponse.Data data;

    @Override
    public void pushView(LoginResponse.Data data) {
        binding.progressbar.setVisibility(View.GONE);
        presenter.syncDTB(data.getUserAndPassWordEntry(getApplicationContext()),myAPI);
        // login success check setting otp
        if (PrefUtils.isSettingOTP(getApplicationContext())) {
            if (PrefUtils.loadData(getApplicationContext()) != null) {
                if (binding.edtUser.getText().toString().equals(PrefUtils.loadData(getApplicationContext()).getUserName())){
                    if (!TextUtils.isEmpty(data.getSdt())) {
                        //  verify otp
                        this.data = data;
                        binding.layoutLogin.setVisibility(View.GONE);
                        binding.layoutOtp.setVisibility(View.VISIBLE);
                        String tel = data.getSdt();
                        int phone1 = Integer.parseInt(tel);
                        binding.tvContent2.setText(Html.fromHtml(getString(R.string.content_verification_otp, "+84" + phone1)));
                        startPhoneNumberVerification(phone1);
                    }
                }else {
                    loginSuccess(data);
                }
            }
        }else {
            loginSuccess(data);
        }

//        if (!TextUtils.isEmpty(data.getSdt())) {
//        //  verify otp
//            this.data = data;
//            binding.layoutLogin.setVisibility(View.GONE);
//            binding.layoutOtp.setVisibility(View.VISIBLE);
//            String tel = data.getSdt();
//            int phone1 = Integer.parseInt(tel);
//            binding.tvContent2.setText(Html.fromHtml(getString(R.string.content_verification_otp, "+84" + phone1)));
//            startPhoneNumberVerification(phone1);
//        } else {
//            loginSuccess(data);
//        }
    }

    private void startPhoneNumberVerification(int phone) {
        Animation shake;
        shake = AnimationUtils.loadAnimation(

                getApplicationContext(),R.anim.shake);
        binding.iconPhone.startAnimation(shake); // st
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber("+84" + phone)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
        mVerificationInProgress = true;
    }

    private void loginSuccess(LoginResponse.Data data) {
        Log.i("hadtt", "loginSuccess");
        PrefUtils.saveData(this, data);
        Toast.makeText(this, R.string.login_success, Toast.LENGTH_SHORT).show();
        startActivity();
    }

    public void startActivity() {
        Log.i("hadtt", "startActivity");
        binding.progressbar.setVisibility(View.GONE);
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();
    }

    @Override
    public void verifyAccountFailed(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        binding.progressbar.setVisibility(View.GONE);
    }

    private void resendVerificationCode(String phoneNumber, PhoneAuthProvider.ForceResendingToken token) {
        binding.progressbar.setVisibility(View.VISIBLE);
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .setForceResendingToken(token)     // ForceResendingToken from callbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }
}
