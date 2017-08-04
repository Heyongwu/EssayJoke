package example.wxx.com.essayjoke.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import org.joda.time.DateTime;

import butterknife.BindView;
import butterknife.ButterKnife;
import example.wxx.com.essayjoke.R;
import example.wxx.com.essayjoke.ui.MonthCalendarView;

public class TestCalendarActivity extends AppCompatActivity {

    @BindView(R.id.monthCalendarView)
    MonthCalendarView mMonthCalendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_calendar);
        ButterKnife.bind(this);
        mMonthCalendarView.isHindAfter(true);
        mMonthCalendarView.setOnClickMonthCalendarCallback(new MonthCalendarView.OnClickMonthCalendarCallback() {
            @Override
            public void onClickMonthCalendar(DateTime dateTime) {
                Toast.makeText(TestCalendarActivity.this, dateTime.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
