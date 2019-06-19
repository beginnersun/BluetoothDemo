package com.example.bluetoothdemo.utils;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.ParcelableSpan;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;

import com.example.bluetoothdemo.activity.bean.ImageSpanBean;
import com.example.bluetoothdemo.activity.bean.SpanBean;

import java.net.URL;
import java.util.Stack;

public class SpannableUtil {

    public static Stack<ParcelableSpan> spanneds_start;
    public static Stack<Integer> start_index;
    public static Stack<SpanBean> spanneds_end;
    public static Stack<ImageSpanBean> imageSpans;
    public static StringBuilder builder;

    public static void getSpans(String html){
        boolean addContent = true;
        for (int i = 0; i < html.length() ;i ++){
            if (html.charAt(i) == '<' && html.charAt(i+1) != '/'){  //标签开头
                addContent = false;
                int end = html.indexOf(">",i);    //找到第一个 > 结尾号
                i = end;  //中间的字符串截出去处理
                if (html.startsWith("<span",i)){
                    dealSpan(html.substring(i,end),builder.length());
                }else if (html.startsWith("<p",i)){
                }else if (html.startsWith("<h",i)){
                }else if (html.startsWith("<img",i)){
                    dealImg(html.substring(i,end),builder.length());
                }else if (html.startsWith("<font",i)){

                }else if (html.startsWith("<br",i)){  //br 必须在b前面

                }else if (html.startsWith("<u",i)){
                    dealU(builder.length());
                }else if (html.startsWith("<i",i)){
                    dealI(builder.length());
                }else if (html.startsWith("<b",i)){
                    dealB(builder.length());
                }
            }else if(html.charAt(i) == '<' && html.charAt(i+1) == '/'){ //标签结尾
                addContent = false;
                int end = html.indexOf(">",i);    //找到第一个 > 结尾号
                if (html.startsWith("</span>",i)){
                    spanneds_end.push(new SpanBean(spanneds_start.pop(),start_index.pop(),builder.length()));
                }else if (html.startsWith("</u>",i)){
                    spanneds_end.push(new SpanBean(spanneds_start.pop(),start_index.pop(),builder.length()));
                }else if (html.startsWith("</i>",i)){
                    spanneds_end.push(new SpanBean(spanneds_start.pop(),start_index.pop(),builder.length()));
                }else if (html.startsWith("</b>",i)){
                    spanneds_end.push(new SpanBean(spanneds_start.pop(),start_index.pop(),builder.length()));
                }
            }
            else {
                addContent = true;
            }
            if (addContent){
                builder.append(html.charAt(i));
            }
        }

    }


    private static String getValue(String html,String key){
        String value = "";
        int key_index = html.indexOf(key);
        if (key_index == -1){
            return "";
        }
        int equal_index_1 = html.indexOf("\"",key_index);  //找到第一个 "
        int equal_index_2 = html.indexOf("\"",key_index+1); //找到第二个
        value = html.substring(equal_index_1+1,equal_index_2+1);
        return value.trim();
    }

    private static String getValueForAlong(String html,String key){
        String value = "";
        int key_index = html.indexOf(key);
        if (key_index == -1){
            return "";
        }
        int equal_index_1 = html.indexOf("'",key_index);  //找到第一个 "
        int equal_index_2 = html.indexOf("'",key_index+1); //找到第二个
        value = html.substring(equal_index_1+1,equal_index_2+1);
        return value.trim();
    }

    public static String getParamsValue(String html,String key){
        String value = "";
        int key_index = html.indexOf(key);
        if (key_index == -1){
            return "";
        }
        int m_index = html.indexOf(":",key_index);
        int f_index = html.indexOf(";",m_index);
        value = html.substring(m_index+1,f_index);
        return value.trim();
    }


    private static void dealSpan(String html,int position){
        String value_style = getValue(html,"style");
        if (!TextUtils.isEmpty(value_style)){
            String color = getParamsValue(value_style,"color").replace(";","");
            String font_size = getParamsValue(value_style,"font-size").replace(";","").replace("px","");
            if (!TextUtils.isEmpty(color)){
                ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor(color));
                spanneds_start.push(colorSpan);
                start_index.push(position);
            }
            if (!TextUtils.isEmpty(font_size)){
                AbsoluteSizeSpan sizeSpan = new AbsoluteSizeSpan((int) Double.parseDouble(font_size));
                spanneds_start.push(sizeSpan);
                start_index.push(position);
            }
        }
    }

    public static void dealImg(String html,int position){
        String imgSrc = getValueForAlong(html,"src");
        if (!TextUtils.isEmpty(imgSrc)){
            Drawable drawable = null;
            URL url;
            try {
                url = new URL(imgSrc);
                Log.i("RG", "url---?>>>" + url);
                drawable = Drawable.createFromStream(url.openStream(), ""); // 获取网路图片
            } catch (Exception e) {
                e.printStackTrace();
                return ;
            }
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                    drawable.getIntrinsicHeight());
            ImageSpan span = new ImageSpan(drawable);
            imageSpans.push(new ImageSpanBean(span,position,position));
        }
    }

    public static void dealFont(){

    }

    public static void dealBr(){

    }

    public static void dealU(int position){
        UnderlineSpan span = new UnderlineSpan();
        spanneds_start.push(new UnderlineSpan());
        start_index.push(position);

    }

    public static void dealI(int position){
        StyleSpan span = new StyleSpan(Typeface.ITALIC);
        spanneds_start.push(span);
        start_index.push(position);
    }

    public static void dealB(int position){
        StyleSpan span = new StyleSpan(Typeface.BOLD);
        spanneds_start.push(span);
        start_index.push(position);
    }

}
