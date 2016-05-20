package com.the_liuchao.interview.date;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.text.format.DateUtils;

import java.util.Calendar;

/**
 * @Description:自定义日期对话框
 * @author SmartCF
 *
 */
public class CustomDatetimeDialog extends AlertDialog implements OnClickListener {

    private CustomDatetimePicker mDatePicker;
    private OnDateChangeSetListener onDateChangeSetListener;
    private Calendar mDate = Calendar.getInstance();

    @SuppressWarnings("deprecation")
    public CustomDatetimeDialog(Context context, long date) {
        super(context);
        mDatePicker = new CustomDatetimePicker(context);
        setView(mDatePicker);
        mDatePicker.setOnDateChageListener(new CustomDatetimePicker.OnDateChangeListener() {

            @Override
            public void onChangeDate(CustomDatetimePicker picker, int year, int month,
                                     int day, int hours, int minutes, int seconds) {
                mDate.set(Calendar.YEAR, year);
                mDate.set(Calendar.MONTH, month);
                mDate.set(Calendar.DAY_OF_MONTH, day);
                mDate.set(Calendar.HOUR_OF_DAY, hours);
                mDate.set(Calendar.MINUTE, minutes);
                mDate.set(Calendar.SECOND, seconds);
                updateTitleDate(mDate.getTimeInMillis());
            }
        });
        setButton("设置", this);
        setButton2("取消", (OnClickListener) null);
        mDate.setTimeInMillis(date);
        updateTitleDate(mDate.getTimeInMillis());

    }

    public interface OnDateChangeSetListener {
        void OnDateSet(AlertDialog alertDailog, long date);
    }

    protected void updateTitleDate(long date) {
        int flags = DateUtils.FORMAT_SHOW_YEAR | DateUtils.FORMAT_SHOW_DATE
                | DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_WEEKDAY;
        setTitle(DateUtils.formatDateTime(getContext(), date, flags));

    }

    public void setOnDateSetChnange(OnDateChangeSetListener callBack) {
        this.onDateChangeSetListener = callBack;
    }

    @Override
    public void onClick(DialogInterface arg0, int arg1) {
        if (onDateChangeSetListener != null) {
            onDateChangeSetListener.OnDateSet(this, mDate.getTimeInMillis());
        }
    }
}
