package com.example.retrofitrxjava.loginV3;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Notification;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.biometric.BiometricPrompt;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import com.example.retrofitrxjava.NetworkUtils;
import com.example.retrofitrxjava.R;
import com.example.retrofitrxjava.base.BaseActivity;
import com.example.retrofitrxjava.base.BaseObserver;
import com.example.retrofitrxjava.databinding.LayoutLoginBinding;
import com.example.retrofitrxjava.loginV3.model.DataResponse;
import com.example.retrofitrxjava.main.MainActivity;
import com.example.retrofitrxjava.utils.AESHelper;
import com.example.retrofitrxjava.utils.PrefUtils;
import com.example.retrofitrxjava.utils.AppUtils;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
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

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import static com.example.retrofitrxjava.utils.Constant.IS_FACE_ID;

public class LoginActivity extends BaseActivity<LayoutLoginBinding> implements LoginListener {

    private static final String KEY_NAME = "key_name";
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

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void initLayout() {
        binding.setListener(this);
        setupUI(findViewById(R.id.parent));
        mAuth = FirebaseAuth.getInstance();
        checkShowView(binding.edtUser.getText().toString(), binding.edtPassword.getText().toString());
        binding.ivClearUsername.setOnClickListener(view -> binding.edtUser.setText(""));
        binding.ivClearPassword.setOnClickListener(view -> binding.edtPassword.setText(""));
        binding.edtPassword.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                binding.btnLogin.performClick();
                return true;
            }
            return false;
        });
        binding.edtUser.setBackgroundResource(R.drawable.border_edt_focus);
        binding.edtUser.setFocusable(true);
        binding.edtUser.requestFocus();
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

        initCallBackOTP();

        binding.edtUser.setOnFocusChangeListener((view, focus) -> {
            if (focus) {
                binding.edtUser.requestFocus();
                binding.edtUser.setBackgroundResource(R.drawable.border_edt_focus);
            } else {
                binding.edtUser.setBackgroundResource(R.drawable.border_edt);
                binding.edtUser.clearFocus();
            }
        });

        binding.edtPassword.setOnFocusChangeListener((view, focus) -> {
            if (focus) {
                binding.edtPassword.requestFocus();
                binding.edtPassword.setBackgroundResource(R.drawable.border_edt_focus);
            } else {
                binding.edtPassword.clearFocus();
                binding.edtPassword.setBackgroundResource(R.drawable.border_edt);
            }
        });

//        if (PrefUtils.loadCacheData(this) != null
//                && PrefUtils.loadCacheData(this).getId() != null) {
//            startActivity();
//        }

        binding.switchCompat.setOnCheckedChangeListener(
                (compoundButton, b) -> {
                    if (binding.switchCompat.isChecked()) {
                        binding.switchCompat.setTrackTintList
                                (ColorStateList.valueOf(Color.parseColor("#0CEBF3")));
                    } else {
                        binding.switchCompat.setTrackTintList
                                (ColorStateList.valueOf(Color.parseColor("#929697")));
                    }
                    binding.tvName.setText(binding.switchCompat.isChecked() ? "Sinh Viên" : "Tài khoản khác");
                });
    }

    @SuppressLint("ClickableViewAccessibility")
    public void setupUI(View view) {
        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    hideSoftKeyboard();
                    binding.edtPassword.clearFocus();
                    binding.edtUser.clearFocus();
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

    public void hideSoftKeyboard() {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(Objects.requireNonNull(getCurrentFocus()).getWindowToken(), 0);
        }catch (Exception ignored){

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
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = task.getResult().getUser();
//                            if (data != null) {
//                                loginSuccess(data);
//                            }

                    } else {
                        // Sign in failed, display a message and update the UI
                        if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                            binding.fieldVerificationCode.setError("Invalid code.");
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
                startActivity();
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
                        // Invalidate the keys if the user has registered a new biometric
                        // credential, such as a new fingerprint. Can call this method only
                        // on Android 7.0 (API level 24) or higher. The variable
                        // "invalidatedByBiometricEnrollment" is true by default.
                        .setInvalidatedByBiometricEnrollment(true)
                        .build());
            }
        } catch (NoSuchProviderException | InvalidAlgorithmParameterException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        binding.imgFingerprint.setOnClickListener(v -> {
//            biometricPrompt.authenticate(promptInfo);
            Cipher cipher = null;
            try {
                cipher = getCipher();
            } catch (NoSuchPaddingException | NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            SecretKey secretKey = null;
            try {
                secretKey = getSecretKey();
            } catch (KeyStoreException | UnrecoverableKeyException | IOException | NoSuchAlgorithmException | CertificateException e) {
                e.printStackTrace();
            }
            try {
                cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            }
            if (PrefUtils.getSetting(this)) {
                if (PrefUtils.loadCacheData(this) != null) {
                    biometricPrompt.authenticate(promptInfo);
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
            binding.ivClearUsername.setVisibility((user.length() > 0) ? View.VISIBLE : View.GONE);
        }

        if (password != null) {
            int length = binding.edtPassword.getText().length();
            binding.ivClearPassword.setVisibility((length > 0) ? View.VISIBLE : View.GONE);
            binding.showPass.setVisibility((length > 0) ? View.VISIBLE : View.GONE);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_login;
    }

    private NotificationManagerCompat notificationManagerCompat;

    @Override
    public void onClick() {
        String userName = binding.edtUser.getText().toString();
        String password = binding.edtPassword.getText().toString();
        binding.progressbar.setVisibility(View.VISIBLE);
        if (!NetworkUtils.isConnect(this)) {
            verifyAccountFailed(getResources().getString(R.string.error_internet));
            return;
        }
        veryFyAccount(userName, password);
        AppUtils.hideKeyboard(this);
    }

    private void veryFyAccount(String username, String password) {
        if (!AppUtils.isNullOrEmpty(binding.edtUser.getText().toString())
                && !AppUtils.isNullOrEmpty(binding.edtPassword.getText().toString())) {
            int permission = !binding.switchCompat.isChecked() ? 1 : 0;
            password = AESHelper.encrypt(password,AESHelper.KEY);
            AppUtils.HandlerRXJava(requestAPI.loginWebSite(username, password, permission), new BaseObserver<DataResponse>() {
                @Override
                public void onSuccess(DataResponse dataResponse) {
                    if (dataResponse.getSuccessFull()) {
                        pushView(dataResponse);
                    } else {
                        verifyAccountFailed(dataResponse.getMessage());
                        binding.progressbar.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onFailed(String message) {
                    verifyAccountFailed(getResources().getString(R.string.error_default));
                }
            });
        } else {
            Toast.makeText(activity, "Nhập đủ thông tin", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
    }

    public void pushView(DataResponse dataResponse) {
        binding.progressbar.setVisibility(View.GONE);
        loginSuccess(dataResponse);
    }

    @SuppressLint("CheckResult")
    private void loginSuccess(DataResponse dataResponse) {
        PrefUtils.cacheData(this, dataResponse);
        Toast.makeText(this, dataResponse.getMessage(), Toast.LENGTH_SHORT).show();
        startActivity();
    }

    public void startActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }

    public void verifyAccountFailed(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        binding.progressbar.setVisibility(View.GONE);
    }

}
