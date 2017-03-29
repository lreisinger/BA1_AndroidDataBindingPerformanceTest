package com.ba.reisinger.ba1app;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

/**
 * Created by Lukas on 25.03.2017.
 */

public class Model extends BaseObservable {

    public static final String text1 = "TEXT11111";
    public static final String text2 = "TEXT22222";

    private String text;

    public Model() {
        this.text = text1;
    }

    @Bindable
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
        notifyPropertyChanged(BR.text);
    }
}
