package com.example.bluetoothdemo.activity.bean;

import android.text.ParcelableSpan;
import android.text.style.ImageSpan;

public class ImageSpanBean {


    ImageSpan span;
    int start;
    int end;

    public ImageSpanBean(ImageSpan span, int start, int end) {
        this.span = span;
        this.start = start;
        this.end = end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public void setSpan(ImageSpan span) {
        this.span = span;
    }

    public void setStart(int start) {
        this.start = start;
    }
}
