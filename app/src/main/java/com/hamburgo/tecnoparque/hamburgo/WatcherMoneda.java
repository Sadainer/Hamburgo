package com.hamburgo.tecnoparque.hamburgo;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

/**
 * Created by YOLIMA on 26/08/2016.
 */
public class WatcherMoneda implements TextWatcher {
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        Log.e("Sadainer",s.toString());
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        DecimalFormat decimalFormat = new DecimalFormat("$ #,###", symbols);
        decimalFormat.format(Integer.valueOf(s.toString()));
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
