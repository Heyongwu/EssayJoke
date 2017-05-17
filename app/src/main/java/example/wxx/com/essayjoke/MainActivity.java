package example.wxx.com.essayjoke;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import example.wxx.com.baselibrary.ioc.CheckNet;
import example.wxx.com.baselibrary.ioc.OnClick;
import example.wxx.com.baselibrary.ioc.ViewById;
import example.wxx.com.baselibrary.ioc.ViewUtils;

public class MainActivity extends AppCompatActivity {

    @ViewById(R.id.tvTest)
    private TextView mTvTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewUtils.inject(this);

        mTvTest.setText("hello ioc");
    }

    @OnClick({R.id.tvTest,R.id.ivTest})
    @CheckNet//没有网络就执行该方法，而是直接打印没有网络的Toast
    private void onClick(){
        Toast.makeText(this, "dianji", Toast.LENGTH_SHORT).show();
    }
}
