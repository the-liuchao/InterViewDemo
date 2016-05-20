package com.the_liuchao.interview.date;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.NumberPicker;
import android.widget.NumberPicker.OnValueChangeListener;

import com.the_liuchao.interview.R;

import java.util.Calendar;


/**
 * @author SmartCF
 *
 */
public class CustomDatetimePicker extends FrameLayout {

	private Calendar mDate;
	private int cYear;
	private int mYear, mMonth, mDay;
	private NumberPicker mYearSpinner;
	private NumberPicker mMonthSpinner;
	private NumberPicker mDaySpinner;
	private boolean isLeapYear;
	private OnDateChangeListener onDateChangeListener;
//	private NumberPicker mSeconddSpinner;
//	private NumberPicker mMinutesSpinner;
//	private NumberPicker mHoursSpinner;
//	private int mHours, mMinutes, mSeconds;

	public CustomDatetimePicker(Context context) {
		super(context);
		mDate = Calendar.getInstance();
		mYear = mDate.get(Calendar.YEAR);
		mMonth = mDate.get(Calendar.MONTH);
		mDay = mDate.get(Calendar.DAY_OF_MONTH);
//		mHours = mDate.get(Calendar.HOUR_OF_DAY);
//		mMinutes = mDate.get(Calendar.MINUTE);
//		mSeconds = mDate.get(Calendar.SECOND);
		cYear = mYear;
		inflate(context, R.layout.custom_datetime_layout, this);

		mYearSpinner = (NumberPicker) findViewById(R.id.np_year);
		mYearSpinner.setMaxValue(cYear + 100);
		mYearSpinner.setMinValue(cYear - 1000);
		mYearSpinner.setValue(mYear);
		mYearSpinner.setOnValueChangedListener(mOnYearChangedListener);

		mMonthSpinner = (NumberPicker) findViewById(R.id.np_month);
		mMonthSpinner.setMaxValue(12);
		mMonthSpinner.setMinValue(1);
		mMonthSpinner.setValue(mMonth + 1);
		mMonthSpinner.setOnValueChangedListener(mOnMonthChangedListener);

		mDaySpinner = (NumberPicker) findViewById(R.id.np_day);
		mDaySpinner.setMaxValue(30);
		mDaySpinner.setMinValue(0);
		mDaySpinner.setValue(mDay);
		mDaySpinner.setOnValueChangedListener(mOnDayChangedListener);

//		mHoursSpinner = (NumberPicker) findViewById(R.id.np_hours);
//		mHoursSpinner.setMaxValue(24);
//		mHoursSpinner.setMinValue(0);
//		mHoursSpinner.setValue(mHours);
//		mHoursSpinner.setOnValueChangedListener(mOnHoursChangedListener);
//
//		mMinutesSpinner = (NumberPicker) findViewById(R.id.np_minutes);
//		mMinutesSpinner.setMaxValue(60);
//		mMinutesSpinner.setMinValue(1);
//		mMinutesSpinner.setValue(mMinutes);
//		mMinutesSpinner.setOnValueChangedListener(mOnMinutesChangedListener);
//
//		mSeconddSpinner = (NumberPicker) findViewById(R.id.np_seconds);
//		mSeconddSpinner.setMaxValue(60);
//		mSeconddSpinner.setMinValue(1);
//		mSeconddSpinner.setValue(mSeconds);
//		mSeconddSpinner.setOnValueChangedListener(mOnSecondsChangedListener);
	}

	private OnValueChangeListener mOnYearChangedListener = new OnValueChangeListener() {

		@Override
		public void onValueChange(NumberPicker numberPicker, int oldNum,
				int newNum) {
			mDate.add(Calendar.YEAR, newNum - oldNum);
			mYear = newNum;
			onChangeDate();
		}
	};
	private OnValueChangeListener mOnMonthChangedListener = new OnValueChangeListener() {

		@Override
		public void onValueChange(NumberPicker numberPicker, int oldNum,
				int newNum) {
			mDate.add(Calendar.MONTH, newNum - oldNum);
			setDaysByMonth(newNum);
			onChangeDate();
		}
	};
	private OnValueChangeListener mOnDayChangedListener = new OnValueChangeListener() {

		@Override
		public void onValueChange(NumberPicker numberPicker, int oldNum,
				int newNum) {
			mDate.add(Calendar.DAY_OF_MONTH, newNum - oldNum);
			onChangeDate();
		}

	};

	private OnValueChangeListener mOnHoursChangedListener = new OnValueChangeListener() {

		@Override
		public void onValueChange(NumberPicker numberPicker, int oldNum,
				int newNum) {
			mDate.add(Calendar.HOUR_OF_DAY, newNum - oldNum);
			onChangeDate();
		}

	};

	private OnValueChangeListener mOnMinutesChangedListener = new OnValueChangeListener() {

		@Override
		public void onValueChange(NumberPicker numberPicker, int oldNum,
				int newNum) {
			mDate.add(Calendar.MINUTE, newNum - oldNum);
			onChangeDate();
		}

	};

	private OnValueChangeListener mOnSecondsChangedListener = new OnValueChangeListener() {

		@Override
		public void onValueChange(NumberPicker numberPicker, int oldNum,
				int newNum) {
			mDate.add(Calendar.SECOND, newNum - oldNum);
			onChangeDate();
		}

	};

	public interface OnDateChangeListener {
		void onChangeDate(CustomDatetimePicker picker, int year, int month, int day,
						  int hours, int minutes, int seconds);
	}

	public void setOnDateChageListener(OnDateChangeListener callBack) {
		this.onDateChangeListener = callBack;
	}

	protected void setDaysByMonth(int month) {
		isLeapYear = mYear % 400 == 0 ? true : mYear % 100 == 0 ? false
				: mYear % 4 == 0 ? true : false;
		switch (month) {
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			mDaySpinner.setMinValue(1);
			mDaySpinner.setMaxValue(31);
			break;
		case 4:
		case 6:
		case 9:
		case 11:
			mDaySpinner.setMinValue(1);
			mDaySpinner.setMaxValue(30);
			break;
		case 2:
			if (isLeapYear) {
				mDaySpinner.setMinValue(1);
				mDaySpinner.setMaxValue(28);
			} else {
				mDaySpinner.setMinValue(1);
				mDaySpinner.setMaxValue(29);
			}
			break;

		default:
			break;
		}
	}

	private void onChangeDate() {
		if (onDateChangeListener != null) {
			onDateChangeListener.onChangeDate(this, mDate.get(Calendar.YEAR),
					mDate.get(Calendar.MONTH),
					mDate.get(Calendar.DAY_OF_MONTH),
					mDate.get(Calendar.HOUR_OF_DAY),
					mDate.get(Calendar.MINUTE), mDate.get(Calendar.SECOND));
		}
	}
}
