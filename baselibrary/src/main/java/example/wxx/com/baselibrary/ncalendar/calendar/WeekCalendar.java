package example.wxx.com.baselibrary.ncalendar.calendar;

import android.content.Context;
import android.util.AttributeSet;
import android.util.SparseArray;

import org.joda.time.DateTime;
import org.joda.time.Weeks;

import java.util.List;

import example.wxx.com.baselibrary.ncalendar.adapter.CalendarAdapter;
import example.wxx.com.baselibrary.ncalendar.adapter.WeekCalendarAdapter;
import example.wxx.com.baselibrary.ncalendar.listener.OnClickWeekCalendarListener;
import example.wxx.com.baselibrary.ncalendar.listener.OnClickWeekViewListener;
import example.wxx.com.baselibrary.ncalendar.listener.OnWeekCalendarPageChangeListener;
import example.wxx.com.baselibrary.ncalendar.utils.Utils;
import example.wxx.com.baselibrary.ncalendar.view.CalendarView;
import example.wxx.com.baselibrary.ncalendar.view.WeekView;

/**
 * Created by necer on 2017/6/13.
 */
public class WeekCalendar extends CalendarViewPager implements OnClickWeekViewListener {

    private OnClickWeekCalendarListener onClickWeekCalendarListener;
    private OnWeekCalendarPageChangeListener onWeekCalendarPageChangeListener;


    public WeekCalendar(Context context) {
        this(context, null);
    }

    public WeekCalendar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected CalendarAdapter getCalendarAdapter(List<String> pointList) {

        DateTime startSunFirstDayOfWeek = Utils.getSunFirstDayOfWeek(startDateTime);
        DateTime endSunFirstDayOfWeek = Utils.getSunFirstDayOfWeek(endDateTime);
        DateTime todaySunFirstDayOfWeek = Utils.getSunFirstDayOfWeek(DateTime.now());

        mPageSize = Weeks.weeksBetween(startSunFirstDayOfWeek, endSunFirstDayOfWeek).getWeeks() + 1;
        mCurrPage = Weeks.weeksBetween(startSunFirstDayOfWeek, todaySunFirstDayOfWeek).getWeeks();


        return new WeekCalendarAdapter(getContext(), mPageSize, mCurrPage, new DateTime(), this, pointList);
    }


    @Override
    protected void initCurrentCalendarView() {
        currentView = calendarAdapter.getCalendarViews().get(getCurrentItem());
        if (onWeekCalendarPageChangeListener != null && currentView != null) {
            DateTime selectDateTime = currentView.getSelectDateTime();
            DateTime initialDateTime = currentView.getInitialDateTime();
            onWeekCalendarPageChangeListener.onWeekCalendarPageSelected(selectDateTime == null ? initialDateTime : selectDateTime);
        }
    }

    @Override
    public void setDate(int year, int month, int day, boolean smoothScroll) {
        DateTime dateTime = new DateTime(year, month, day, 0, 0, 0);
        int i = jumpDate(dateTime, smoothScroll);

        WeekView weekView = (WeekView) calendarAdapter.getCalendarViews().get(i);

        if (weekView == null) {
            return;
        }
        weekView.setSelectDateTime(dateTime);
    }

    @Override
    public int jumpDate(DateTime dateTime, boolean smoothScroll) {
        SparseArray<CalendarView> calendarViews = calendarAdapter.getCalendarViews();
        if (calendarViews.size() == 0) {
            return getCurrentItem();
        }

        DateTime initialDateTime = calendarViews.get(getCurrentItem()).getInitialDateTime();
        int weeks = Utils.getIntervalWeek(initialDateTime, dateTime);
        int i = getCurrentItem() + weeks;

        setCurrentItem(i, smoothScroll);
        return i;
    }

    public void setOnClickWeekCalendarListener(OnClickWeekCalendarListener onClickWeekCalendarListener) {
        this.onClickWeekCalendarListener = onClickWeekCalendarListener;
    }

    public void setOnWeekCalendarPageChangeListener(OnWeekCalendarPageChangeListener onWeekCalendarPageChangeListener) {
        this.onWeekCalendarPageChangeListener = onWeekCalendarPageChangeListener;
    }


    @Override
    public void onClickCurrentWeek(DateTime dateTime) {
        WeekView weekView = (WeekView) calendarAdapter.getCalendarViews().get(getCurrentItem());
        weekView.setSelectDateTime(dateTime);
        //清除其他选中
        if (!isMultiple) {
            clearSelect(weekView);
        }
        if (onClickWeekCalendarListener != null) {
            onClickWeekCalendarListener.onClickWeekCalendar(dateTime);
        }
    }
}
