package com.example.bluetoothdemo.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.bluetoothdemo.R;

import androidx.annotation.NonNull;


/**
 * 展示通用的那种 信息 确认  取消 弹框
 * Created by Home on 2019/3/29.
 */

public class NoTitleMessageDialog extends Dialog {

    private Context mContext;
    private String message;
    private boolean showClose;
    private OnOkClickListener onOkClickListener;

    private String btn1Title;
    private String btn2Title;

    public NoTitleMessageDialog(@NonNull Context context, String message) {
        this(context,message,false);
//        getWindow().setGravity(Gravity.CENTER); //显示在底部
    }

    public NoTitleMessageDialog(@NonNull Context context,String message,boolean showClose){
//        super(context,R.style.Dialog_Show_Message);
//        this.mContext = context;
//        this.showClose = showClose;
//        this.message = message;
//        init();
//        final WindowManager.LayoutParams params = getWindow().getAttributes();
//        params.width = (int) (Resources.getSystem().getDisplayMetrics().density * 270);
//        params.height = (int) (Resources.getSystem().getDisplayMetrics().density  * 170);
//        getWindow().setAttributes(params);
        this(context,message,showClose,"确认","取消");
//        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
//        lp.alpha = 0.3f;
//        this.getWindow()
//                .addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
//        this.getWindow().setAttributes(lp);
    }

    public NoTitleMessageDialog(@NonNull Context context,String message,boolean showClose,String okMessage,String cancelMessage){
        super(context, R.style.Dialog_Show_Message);
        this.mContext = context;
        this.showClose = showClose;
        this.message = message;
        this.btn1Title = cancelMessage;
        this.btn2Title = okMessage;
        init();
        final WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = (int) (Resources.getSystem().getDisplayMetrics().density * 270);
        params.height = (int) (Resources.getSystem().getDisplayMetrics().density  * 170);
        getWindow().setAttributes(params);
    }

    private void init(){
//
//        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_show_message,null,false);
//        setCanceledOnTouchOutside(true);
//        setContentView(view);
//
//        TextView content = (TextView) view.findViewById(R.id.tv_content);
//
//        content.setText(message);
//
//
//        TextView save = (TextView) view.findViewById(R.id.tv_ok);
//        save.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dismiss();
//            }
//        });
//
//        TextView cancel = (TextView) view.findViewById(R.id.tv_cancel);
//        if (showClose){
//            save.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if (onOkClickListener!=null){
//                        onOkClickListener.onOkClick();
//                    }
//                    dismiss();
//                }
//            });
//        }else{
//            view.findViewById(R.id.tv_line).setVisibility(View.GONE);
//            cancel.setVisibility(View.GONE);
//        }
//
//        if (!TextUtils.isEmpty(this.btn1Title) && !TextUtils.isEmpty(this.btn2Title)){
//            save.setText(this.btn2Title);
//            cancel.setText(this.btn1Title);
//        }
//
//        cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dismiss();
//            }
//        });

    }

    public void setOnOkClickListener(OnOkClickListener onOkClickListener) {
        this.onOkClickListener = onOkClickListener;
    }

    public interface OnOkClickListener{
        void onOkClick();
    }
}
