package com.example.retrofitrxjava.loginV3;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import com.example.retrofitrxjava.NetworkUtils;
import com.example.retrofitrxjava.R;
import com.example.retrofitrxjava.b.BActivity;
import com.example.retrofitrxjava.databinding.LayoutLoginBinding;
import com.example.retrofitrxjava.main.MainActivity;
import com.example.retrofitrxjava.utils.AppUtils;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.concurrent.Executor;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class LoginActivity extends BActivity<LayoutLoginBinding> implements LoginListener, LoginContract.View {

    private static final String KEY_NAME = "key_name";
    private LoginPresenter presenter;
    private boolean isShowPass;
    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void initLayout() {
        binding.setListener(this);
        presenter = new LoginPresenter(this);
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

//        if (PrefUtils.loadData(getApplicationContext()) != null && PrefUtils.loadData(this).getToken() != null) {
//            startActivity();
//        }
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
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }

        binding.imgFingerprint.setOnClickListener(v -> {
//            biometricPrompt.authenticate(promptInfo);
            Cipher cipher = null;
            try {
                cipher = getCipher();
            } catch (NoSuchPaddingException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            SecretKey secretKey = null;
            try {
                secretKey = getSecretKey();
            } catch (KeyStoreException e) {
                e.printStackTrace();
            } catch (CertificateException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (UnrecoverableKeyException e) {
                e.printStackTrace();
            }
            try {
                cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            }
            biometricPrompt.authenticate(promptInfo,
                    new BiometricPrompt.CryptoObject(cipher));
//            if (PrefUtils.getSetting(getApplicationContext())) {
//                if (PrefUtils.loadData(getApplicationContext()) != null) {
//                    biometricPrompt.authenticate(promptInfo);
//                }
//            } else {
//                Toast.makeText(this,
//                        getString(R.string.ban_can_bat_face_id), Toast.LENGTH_SHORT).show();
//            }

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
        return ((SecretKey)keyStore.getKey(KEY_NAME, null));
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

    @Override
    public void onClick() {
        String userName = binding.edtUser.getText().toString();
        String password = binding.edtPassword.getText().toString();
        binding.progressbar.setVisibility(View.VISIBLE);
        if (!NetworkUtils.isConnect(this)) {
            verifyAccountFailed(getResources().getString(R.string.error_internet));
            return;
        }
        presenter.verifyAccount(myAPI, userName, password);
        AppUtils.hideKeyboard(this);
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    public void pushView() {
        Toast.makeText(this, R.string.login_success, Toast.LENGTH_SHORT).show();
        binding.progressbar.setVisibility(View.GONE);
        startActivity();
    }

    public void startActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }

    @Override
    public void verifyAccountFailed(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        binding.progressbar.setVisibility(View.GONE);
    }
}
