package example.wxx.com.baselibrary.ncalendar.adapter;

import android.content.Context;
import android.view.ViewGroup;

import org.joda.time.DateTime;

import java.util.List;

import example.wxx.com.baselibrary.ncalendar.listener.OnClickWeekViewListener;
import example.wxx.com.baselibrary.ncalendar.view.WeekView;

/**
 * Created by necer on 2017/6/13.
 */

public class WeekCalendarAdapter extends CalendarAdapter{

    private OnClickWeekViewListener mOnClickWeekViewListener;

    public WeekCalendarAdapter(Context context, int count,int curr, DateTime dateTime, OnClickWeekViewListener onClickWeekViewListener,List<String> pointList) {
        super(context, count,curr, dateTime,pointList);
        this.mOnClickWeekViewListener = onClickWeekViewListener;
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        WeekView weekView = (WeekView) mCalendarViews.get(position);
        if (weekView == null) {
            weekView = new WeekView(mContext, mDateTime.plusDays((position - mCurr) * 7),mOnClickWeekViewListener,mPointList);
            mCalendarViews.put(position, weekView);
        }
        container.addView(mCalendarViews.get(position));
        return mCalendarViews.get(position);
    }


}
