package example.wxx.com.framelibrary.banner;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Field;

/**
 * 轮播图的ViewPager
 * 作者：wengxingxia
 * 时间：2017/7/11 0011 20:51
 */

public class BannerViewPager extends ViewPager {

    //1、 自定义BannerViewPager 的自定义Adapter
    private BannerAdapter mAdapter;

    private final int SCROLL_MSG = 0x0011;//自动轮播时，发送消息的messageWhat

    //    实现自动轮播-页面切换时间间隔 默认值3500
    private int mCutDownTime = 3500;

    private View mConvertView;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            //        每个xx秒后切换到下一页
            setCurrentItem(getCurrentItem() + 1);
            startRoll();//再次启动
        }

    };
    //    改变ViewPager切换速率——自定义的页面切换的的Scroller
    private BannerScroller mScroller;

    public BannerViewPager(Context context) {
        this(context, null);
    }

    public BannerViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);

        try {
            //        4、改变Viewpager 切换的速率
//        4.1duration 持续的事件 局部变量
//        4.2 改变mScroller private的 通过反射
            final Field scroller = ViewPager.class.getDeclaredField("mScroller");
//            设置参数 第一个参数Object 代表当前属性在哪个类，第二个参数 代表要设置的值
            mScroller = new BannerScroller(context);
            scroller.setAccessible(true);
            scroller.set(this, mScroller);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置切换页面动画持续的时间
     *
     * @param scrollerDuration
     */
    public void setScrollerDuration(int scrollerDuration) {
        mScroller.setScrollerDuration(scrollerDuration);
    }

    /**
     * 1、设置自定义的BannerAdapter
     *
     * @param adapter
     */
    public void setAdapter(BannerAdapter adapter) {
        mAdapter = adapter;
//        设置父类 ViewPager的Adapter
        setAdapter(new BannerPagerAdapter());
    }

    /**
     * 2、实现自动轮播
     */
    public void startRoll() {
//        清除消息
        mHandler.removeMessages(SCROLL_MSG);
//        消息 延时时间 让用户自定义 有一个默认值 3500
        mHandler.sendEmptyMessageDelayed(SCROLL_MSG, mCutDownTime);
    }

    /**
     * 3、销毁Handler停止发送  解决内存泄漏
     */
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
//        销毁Handler的生命周期
        mHandler.removeMessages(SCROLL_MSG);
        mHandler = null;
    }

    /**
     * 给ViewPager设置适配器
     */
    private class BannerPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
//            为了无限循环轮播
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
//            官方推荐这么写，源码
            return view == object;
        }

        /**
         * 创建Viewpager 条目回调方法
         *
         * @param container
         * @param position
         * @return
         */
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
//            采用Adapter设计模式  为了完全让用户自定义
            View bannerItemView = mAdapter.getView(position % mAdapter.getCount(),mConvertView);

//            将View添加ViewPager中
            container.addView(bannerItemView);
            return bannerItemView;
        }

        /**
         * 销毁条目 回调方法
         *
         * @param container
         * @param position
         * @param object
         */
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
            mConvertView = (View) object;
        }
    }
}
