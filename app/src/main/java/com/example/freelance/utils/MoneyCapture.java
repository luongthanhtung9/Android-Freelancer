package com.example.freelance.utils;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import com.vnpay.merchant.utils.Utils;

public class MoneyCapture implements TextWatcher {
    private String currency = "";
    private String beforeText;
    private EditText mEditext;
    private int caretPoint;
    private int length = 10;
    private final int LENGTH_AFTER_POINT = 2;
    private String VND = "VND";

    public MoneyCapture(EditText mEditext) {
        this.mEditext = mEditext;
    }

    public MoneyCapture(EditText mEditext, int length) {
        this.mEditext = mEditext;
        this.length = length;
    }

    public MoneyCapture setCurrency(String currency) {
        if (currency != null)
            this.currency = currency;
        if (mEditext != null) {
            mEditext.setText(mEditext.getText());
        }
        return this;
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        this.beforeText = s.toString();
        caretPoint = mEditext.getSelectionEnd();

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        long time1 = System.currentTimeMillis();
        if (s.length() > 0) {
            String text = s.toString().trim();
            String p = text.replaceAll("[^\\d]", "");
            String bf = this.beforeText.replaceAll("[^\\d]", "");
            String newText = this.beforeText;
            if (TextUtils.isEmpty(p)) {
                newText = "";
            } else if (!p.startsWith("0")) {
                if (p.length() > length) {
                    newText = this.beforeText;
                } else if (TextUtils.isEmpty(currency)) {
                    newText = Utils.g().getDotMoney(p);
                    caretPoint += (newText.length() - beforeText.length());
                } else {
                    newText = Utils.g().getDotMoneyHasCcy(p, currency);
                    caretPoint += (newText.length() - beforeText.length());
                }
            } else {
                newText = "";
            }
            setValueText(newText);
        }
        Log.wtf("TIME_MONEY", "T= " + (System.currentTimeMillis() - time1));
    }

    private boolean isVND() {
        return TextUtils.isEmpty(currency) || VND.equalsIgnoreCase(currency);
    }

    private void setValueText(String data) {
        this.mEditext.removeTextChangedListener(this);
        this.mEditext.setText(data);
        int append = !TextUtils.isEmpty(currency) ? currency.length() + 1 : 0;
        if (caretPoint > data.length() - append)
            caretPoint = data.length() - append;
        if (caretPoint < 0)
            caretPoint = 0;
        this.mEditext.setSelection(caretPoint);
        this.mEditext.addTextChangedListener(this);
    }

    public void setNoCCY() {
        currency = null;
    }
}
