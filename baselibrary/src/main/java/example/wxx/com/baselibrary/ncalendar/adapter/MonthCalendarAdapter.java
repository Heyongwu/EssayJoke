package example.wxx.com.baselibrary.ncalendar.adapter;

import android.content.Context;
import android.view.ViewGroup;

import org.joda.time.DateTime;

import java.util.List;

import example.wxx.com.baselibrary.ncalendar.listener.OnClickMonthViewListener;
import example.wxx.com.baselibrary.ncalendar.view.MonthView;

/**
 * Created by necer on 2017/6/12.
 */

public class MonthCalendarAdapter extends CalendarAdapter {

    private OnClickMonthViewListener mOnClickMonthViewListener;

    private DateTime mEndDateTime;

    public MonthCalendarAdapter(Context context, int count, int curr, DateTime dateTime, OnClickMonthViewListener onClickMonthViewListener, List<String> pointList) {
        super(context, count, curr, dateTime, pointList);
        this.mOnClickMonthViewListener = onClickMonthViewListener;
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        MonthView monthView = (MonthView) mCalendarViews.get(position);
        if (monthView == null) {
            int i = position - mCurr;
            DateTime dateTime = this.mDateTime.plusMonths(i);
            monthView = new MonthView(mContext, dateTime, mOnClickMonthViewListener, mPointList);
            if (mEndDateTime != null)
                monthView.setEndDateTime(mEndDateTime);
            mCalendarViews.put(position, monthView);
        }
        container.addView(monthView);
        return monthView;
    }

    public void setEndDateTime(DateTime endDateTime) {
        mEndDateTime = endDateTime;
    }
}
