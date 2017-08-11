package example.wxx.com.baselibrary.slidingMenu;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;

import example.wxx.com.baselibrary.R;

/**
 * 侧滑栏
 *
 * @author wengxingxia
 * @time 2017/7/24 0024 9:27
 */
public class SlidingMenu extends HorizontalScrollView {

    private View mMenuView;

    //     4.3、默认进来的时候是关闭的,——菜单宽度
    private int mMenuWidth;

    //    7、 手指快速移动——手势处理类
    private GestureDetector mGestureDetector;

    //    7、 手指快速移动——菜单是否打开，true打开
    private boolean mMenuIsOpen;
    //        9.1.1.2、把阴影加入新的内容容器——阴影View
    private ImageView mShadowView;


    public SlidingMenu(Context context) {
//        在代码中new的时候调用
        this(context, null);
    }

    public SlidingMenu(Context context, AttributeSet attrs) {
//        写在布局文件中的时候调用
        this(context, attrs, 0);
    }

    public SlidingMenu(Context context, AttributeSet attrs, int defStyleAttr) {
//        也是写在布局文件中的时候调用，但是会有style
        super(context, attrs, defStyleAttr);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.SlidingMenu);
//        获取自定义属性
        float rightPadding = array.getDimension(R.styleable.SlidingMenu_rightPadding, dp2px(50));
        //        4.2、指定菜单和内容的宽度
        mMenuWidth = (int) (getScreenWidth() - rightPadding);
        array.recycle();

//        7.1、初始化手势处理类
        mGestureDetector = new GestureDetector(context, new Gus());

    }

    //   整个布局加载完毕会执行的方法
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        //     4、用代码动态指定布局的宽度

//        4.1、获取菜单和内容的View
//        4.1.1、先获取根布局LinearLayout
        ViewGroup container = (ViewGroup) getChildAt(0);
        //4.1.2、获取菜单View
        mMenuView = container.getChildAt(0);

        //        4.1.3、获取内容的View
//        9、处理内容的阴影效果
//                9.1、思路在内容布局的外面加一层阴影  ImageView
//                9.1.1、把原来的内容从根布局里面移除
        View oldContentView = container.getChildAt(1);
        container.removeView(oldContentView);
//                9.1.2、新建一个布局容器 = 原来的内容+阴影
        FrameLayout newContentView = new FrameLayout(getContext());
//        9.1.2.1、把原来的内容加入新的容器
        newContentView.addView(oldContentView);
//        9.1.1.2、把阴影加入新的内容容器
        mShadowView = new ImageView(getContext());
        mShadowView.setBackgroundColor(Color.parseColor("#99000000"));
        newContentView.addView(mShadowView);
//                9.1.3、把新的容器再放回原来的位置
        container.addView(newContentView);

//        4.2.1、指定菜单的宽度 = 屏幕的宽度-50dp(50dp可以用户自定义)
        mMenuView.getLayoutParams().width = mMenuWidth;

//        4.2.2、指定内容的宽度 = 屏幕的宽度
        newContentView.getLayoutParams().width = getScreenWidth();

//        4.3、默认进来的时候是关闭的,要让自己滚动一段距离，菜单的宽度
    }

//     6、处理onTouch事件 手指抬起，肯定要判断一下菜单是打开的还是关闭的

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
//        7.2、处理手指快速滑动，手势处理类使用，拦截
        if (mGestureDetector.onTouchEvent(ev)) {
            return false;
        }
        switch (ev.getAction()) {
            case MotionEvent.ACTION_UP:
//                手指抬起
                float currentScrollX = getScrollX();
                if (currentScrollX > mMenuWidth / 2) {
//                    当前滚动x>菜单宽度一般
                    closeMenu();
                } else {
                    openMenu();
                }
                return false;
        }
        return super.onTouchEvent(ev);
    }

    /**
     * 打开菜单
     */
    private void openMenu() {
//        滚动到当前位置 并且带一个动画
        smoothScrollTo(0, 0);
        mMenuIsOpen = true;
    }

    /**
     * 关闭菜单
     */
    private void closeMenu() {
        smoothScrollTo(mMenuWidth, 0);
        mMenuIsOpen = false;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
//       4.3、默认进来的时候是关闭的, 摆放子布局执行的方法
        if (changed) {
//        4.3、默认进来的时候是关闭的,要让自己滚动一段距离，菜单的宽度
            scrollTo(mMenuWidth, 0);
        }

    }

    /**
     * dipz转px
     *
     * @param dip
     * @return
     */
    private int dp2px(int dip) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, getResources().getDisplayMetrics());
    }

    /**
     * 获取屏幕的宽度
     *
     * @return
     */
    private int getScreenWidth() {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        return displayMetrics.widthPixels;
    }

    /**
     * 7.1、初始化手势处理类——手势处理类的监听回调
     */
    private class Gus extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

//            Bug 判断手指是 上、下、左、右滑动，这里只有左右滑动才切换

            if (Math.abs(velocityY) > Math.abs(velocityX)) {
//                表示上下快速滑，这个时候不作处理
                return super.onFling(e1, e2, velocityX, velocityY);
            }


//            向右边快速滑动，是一个大于0 的数
//            向左边快速滑动，是一个小于 0 的数

            if (mMenuIsOpen && velocityX < 0) {
//            逻辑  如果是菜单打开，向左边滑动，切换菜单状态
                toggleMenu();
                return true;
            } else if (!mMenuIsOpen && velocityX > 0) {
//            逻辑  如果是菜单关闭，向右边滑动，切换菜单状态
                toggleMenu();
                return true;
            }

            return super.onFling(e1, e2, velocityX, velocityY);
        }
    }

    /**
     * 切换菜单状态
     */
    public void toggleMenu() {
        if (mMenuIsOpen) {
            closeMenu();
        } else
            openMenu();
    }

    /**
     * 8、处理菜单的抽屉效果
     *
     * @param l
     * @param t
     * @param oldl
     * @param oldt
     */
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

//        onscrollChanged方法有四个参数
//        第一个参数为变化后的X轴位置
//        第二个参数为变化后的Y轴的位置
//        第三个参数为原先的X轴的位置
//        第四个参数为原先的Y轴的位置

//        if (Math.abs(oldt - t) > Math.abs(oldl - l)) {
////                表示上下快速滑，这个时候不作处理
//            return;
//        }
//        滚动回调的方法，这个方法是不断的执行的
//        l代表当前滚动的距离 scrollX
        mMenuView.setTranslationX(l * 0.7f);
//        滑动到不同的位置，改变阴影的透明度
//        透明度肯定是一个 梯度值
        float scale = l * 1f / mMenuWidth;//1->0
        float alphaScale = 1 - scale;//0->1
        mShadowView.setAlpha(alphaScale);//
    }

    /**
     * 事件分发
     *
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
//        如果菜单打开 并且 手指按下的位置 > mMenuWidth  关闭菜单  停止分发事件
        if (mMenuIsOpen) {
            float x = ev.getX();
//            手指按下位置> mMenuWidth
            if (x > mMenuWidth) {
//                关闭切换菜单
                toggleMenu();
//                停止分发事件
                return false;
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 事件拦截
     *
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }
}
