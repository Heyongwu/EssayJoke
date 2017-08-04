package example.wxx.com.baselibrary.ncalendar.listener;

import org.joda.time.DateTime;

/**
 * Created by necer on 2017/7/4.
 */

public interface OnCalendarChangeListener {
    void onClickCalendar(DateTime dateTime);
    void onCalendarPageChanged(DateTime dateTime);
}
