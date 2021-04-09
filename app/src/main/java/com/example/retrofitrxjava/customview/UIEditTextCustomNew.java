package com.example.retrofitrxjava.customview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputConnectionWrapper;
import android.widget.EditText;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.content.ContextCompat;

import com.example.retrofitrxjava.R;

/**
 * creat by duongnk
 * UIEditTextCustom creat 13/9/2019
 */
public class UIEditTextCustomNew extends AppCompatEditText {
    private int tranperent = 0;
    private boolean isAlreadyHasClear = false;
    private onListenerEditText onTextChanged;
    private boolean isHideClose = true;
    public void setAlreadyHasClear(boolean alreadyHasClear) {
        isAlreadyHasClear = alreadyHasClear;
        this.setCompoundDrawablesWithIntrinsicBounds(this.getCompoundDrawables()[0], this.getCompoundDrawables()[1], null, this.getCompoundDrawables()[3]);
    }

    public interface Listener {
        void onListener();
    }

    public interface TextChangedListener {
        void onTextChanged();
    }

    public UIEditTextCustomNew(Context context) {
        super(context);
        initView(context, null);
    }

    public UIEditTextCustomNew(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public UIEditTextCustomNew(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    @SuppressLint({"NewApi", "ClickableViewAccessibility"})
    public void initView(Context context, AttributeSet attrs) {
        this.setSelected(false);
        this.clearFocus();

        if (tranperent != 1) {
            this.setBackgroundResource(R.drawable.border_default_app);
        } else {
            this.setBackgroundColor(Color.TRANSPARENT);
        }

        this.setOnFocusChangeListener((v, b) -> {
            if (b) {
                UIEditTextCustomNew.this.getText().toString();
                if (tranperent != 1) {
                    v.setBackgroundResource(R.drawable.bg_boder_defaut_new);
                }
            } else {
                UIEditTextCustomNew.this.setCompoundDrawablesWithIntrinsicBounds(UIEditTextCustomNew.this.getCompoundDrawables()[0], UIEditTextCustomNew.this.getCompoundDrawables()[1], null, UIEditTextCustomNew.this.getCompoundDrawables()[3]);
                if (tranperent != 1) {
                    v.setBackgroundResource(R.drawable.border_default_app);
                }
            }

        });

        this.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (onTextChanged != null) {
                    onTextChanged.textChangedListener();
                }
            }

            @Override
            public void afterTextChanged(Editable arg0) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
        });

        this.setOnTouchListener((v, event) -> {
            UIEditTextCustomNew et = UIEditTextCustomNew.this;
            if (et.getCompoundDrawables()[2] == null)
                return false;
            if (event.getAction() != MotionEvent.ACTION_UP)
                return false;
            return false;
        });
    }

    public EditText getEditText() {
        return (EditText) this;
    }

    public void removeFocus() {
        this.clearFocus();
    }

    @Override
    public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
        return new CustomEditTextKeyEvent(super.onCreateInputConnection(outAttrs), true);
    }

    private class CustomEditTextKeyEvent extends InputConnectionWrapper {

        public CustomEditTextKeyEvent(InputConnection target, boolean mutable) {
            super(target, mutable);
        }

        @Override
        public boolean sendKeyEvent(KeyEvent event) {
            if (event.getAction() == KeyEvent.ACTION_DOWN
                    && event.getKeyCode() == KeyEvent.KEYCODE_DEL) {

            }
            return super.sendKeyEvent(event);
        }

        @Override
        public boolean deleteSurroundingText(int beforeLength, int afterLength) {

            if (beforeLength == 1 && afterLength == 0) {

                return sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL));
            }
            return super.deleteSurroundingText(beforeLength, afterLength);
        }
    }

    public interface onListenerEditText{
        void textChangedListener();
    }

}

