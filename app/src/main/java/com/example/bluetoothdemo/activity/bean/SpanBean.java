package com.example.bluetoothdemo.activity.bean;

import android.text.ParcelableSpan;

public class SpanBean {


    ParcelableSpan span;
    int start;
    int end;

    public SpanBean(ParcelableSpan span, int start, int end) {
        this.span = span;
        this.start = start;
        this.end = end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public void setSpan(ParcelableSpan span) {
        this.span = span;
    }

    public void setStart(int start) {
        this.start = start;
    }
}
