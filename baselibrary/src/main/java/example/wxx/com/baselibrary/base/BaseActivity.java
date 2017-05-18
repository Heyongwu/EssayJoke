package example.wxx.com.baselibrary.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import example.wxx.com.baselibrary.ioc.ViewUtils;

/**
 * 整个应用的BaseActivity
 * 作者：wengxingxia
 * 时间：2017/5/17 0017 21:30
 */

public abstract class BaseActivity extends AppCompatActivity {

    /**
     * intent之间传递bundle的Key
     */
    public String BUNDLE = "BUNDLE";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //        设置布局layout
        setContentView();

        ViewUtils.inject(this);

//        初始化头部
        initTitle();


//        初始化界面
        initView();

//        初始化数据
        initData();
    }

    /***
     * 设置布局layout
     */
    protected abstract void setContentView();

    /***
     * 初始化头部
     */
    protected abstract void initTitle();

    /***
     * 初始化界面
     */
    protected abstract void initView();

    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * 手动初始化控件
     *
     * @param viewId 控件Id
     * @param <T>
     * @return
     */
    public <T extends View> T viewById(int viewId) {
        return (T) findViewById(viewId);
    }

    /**
     * 启动Activity
     *
     * @param clazz
     */
    public void startActivity(Class<?> clazz) {
        startActivity(clazz, null);
    }

    /**
     * 启动Activity
     *
     * @param clazz
     * @param bundle
     */
    public void startActivity(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (bundle != null) {
            intent.putExtra(BUNDLE, bundle);
        }
        startActivity(intent);
    }


}
