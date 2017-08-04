package example.wxx.com.essayjoke.ui;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.joda.time.DateTime;

import example.wxx.com.baselibrary.ncalendar.calendar.MonthCalendar;
import example.wxx.com.baselibrary.ncalendar.listener.OnClickMonthCalendarListener;
import example.wxx.com.baselibrary.ncalendar.listener.OnMonthCalendarPageChangeListener;
import example.wxx.com.essayjoke.R;

import static example.wxx.com.essayjoke.R.id.monthCalendar;

/**
 * 月份日历
 *
 * @author wengxingxia
 * @time 2017/7/25 0025 13:39
 */
public class MonthCalendarView extends LinearLayout {
    private Context mContext;
    private LinearLayout llMonthCalendarView;
    private TextView tvToday;
    private TextView tvCancel;
    private TextView tvTitle;
    private MonthCalendar mMonthCalendar;
    private OnClickMonthCalendarCallback mOnClickMonthCalendarCallback;

    public MonthCalendarView(Context context) {
        this(context, null);
    }

    public MonthCalendarView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MonthCalendarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        //        把布局加载到这个view里面
        inflate(context, R.layout.ui_month_calendar, this);
        initView();//初始化控件
        setListener();//设置监听器
    }

    /**
     * 设置监听器
     */
    private void setListener() {
        tvCancel.setOnClickListener(mOnClickListener);
        tvToday.setOnClickListener(mOnClickListener);

        mMonthCalendar.setOnClickMonthCalendarListener(new OnClickMonthCalendarListener() {
            @Override
            public void onClickMonthCalendar(DateTime dateTime) {
                if (mOnClickMonthCalendarCallback != null) {
                    mOnClickMonthCalendarCallback.onClickMonthCalendar(dateTime);
                }
            }
        });

        mMonthCalendar.setOnMonthCalendarPageChangeListener(new OnMonthCalendarPageChangeListener() {
            @Override
            public void onMonthCalendarPageSelected(DateTime dateTime) {
                tvTitle.setText(dateTime.getYear() + "年" + dateTime.getMonthOfYear() + "月");
            }
        });
    }

    /**
     * 初始化控件
     */
    private void initView() {
        llMonthCalendarView = (LinearLayout) findViewById(R.id.llMonthCalendarView);
        tvToday = (TextView) findViewById(R.id.tvToday);
        tvCancel = (TextView) findViewById(R.id.tvCancel);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        mMonthCalendar = (MonthCalendar) findViewById(monthCalendar);
    }

    /**
     * 是否将今后的日期设置为灰色
     *
     * @param isHind true表示设置为灰色
     */
    public void isHindAfter(boolean isHind) {
        mMonthCalendar.setEndDateTime(new DateTime());
    }

    private OnClickListener mOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tvCancel:
                    dismiss();
                    break;
                case R.id.tvToday:
                    DateTime dateTime = new DateTime();
                    mMonthCalendar.setDate(dateTime.getYear(), dateTime.getMonthOfYear(), dateTime.getDayOfMonth(), true);
                    break;
            }
        }
    };

    /**
     * 点击月份日历回调
     */
    public interface OnClickMonthCalendarCallback {
        void onClickMonthCalendar(DateTime dateTime);
    }

    /**
     * 显示MonthCalendarView
     */
    public void show() {
        if (!isShowing())
            llMonthCalendarView.setVisibility(View.VISIBLE);
    }

    /**
     * 隐藏MonthCalendarView
     */
    public void dismiss() {
        if (isShowing())
            llMonthCalendarView.setVisibility(View.GONE);
    }

    /**
     * true表示MonthCalendarView正在显示
     *
     * @return
     */
    private boolean isShowing() {
        int visibility = llMonthCalendarView.getVisibility();
        if (visibility == View.VISIBLE)
            return true;
        return false;
    }

    public void setOnClickMonthCalendarCallback(OnClickMonthCalendarCallback onClickMonthCalendarCallback) {
        mOnClickMonthCalendarCallback = onClickMonthCalendarCallback;
    }
}
