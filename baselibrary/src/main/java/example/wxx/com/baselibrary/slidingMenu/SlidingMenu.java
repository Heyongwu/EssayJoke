package example.wxx.com.baselibrary.slidingMenu;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;

import example.wxx.com.baselibrary.R;

/**
 * 侧滑栏
 * @author wengxingxia
 * @time 2017/7/24 0024 9:27
 */
public class SlidingMenu extends HorizontalScrollView{

    private View mMenuView;
    private View mContentView;

//     4.3、默认进来的时候是关闭的,——菜单宽度
    private int mMenuWidth;

    public SlidingMenu(Context context) {
//        在代码中new的时候调用
        this(context,null);
    }

    public SlidingMenu(Context context, AttributeSet attrs) {
//        写在布局文件中的时候调用
        this(context, attrs,0);
    }

    public SlidingMenu(Context context, AttributeSet attrs, int defStyleAttr) {
//        也是写在布局文件中的时候调用，但是会有style
        super(context, attrs, defStyleAttr);

        TypedArray array = context.obtainStyledAttributes(attrs,R.styleable.SlidingMenu);
//        获取自定义属性
        float rightPadding = array.getDimension(R.styleable.SlidingMenu_rightPadding, dp2px(50));
        //        4.2、指定菜单和内容的宽度
        mMenuWidth = (int)(getScreenWidth() - rightPadding);
        array.recycle();
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
        mContentView = container.getChildAt(1);



//        4.2.1、指定菜单的宽度 = 屏幕的宽度-50dp(50dp可以用户自定义)
        mMenuView.getLayoutParams().width = mMenuWidth;

//        4.2.2、指定内容的宽度 = 屏幕的宽度
        mContentView.getLayoutParams().width = getScreenWidth();

//        4.3、默认进来的时候是关闭的,要让自己滚动一段距离，菜单的宽度
    }

//     6、处理onTouch事件 手指抬起，肯定要判断一下菜单是打开的还是关闭的


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_UP:
//                手指抬起
                float currentScrollX = getScaleX();
                if(currentScrollX>mMenuWidth/2){
                    closeMenu();
                }else{
                    openMenu();
                }
                break;

        }
        return super.onTouchEvent(ev);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
//       4.3、默认进来的时候是关闭的, 摆放子布局执行的方法
        if(changed){
//        4.3、默认进来的时候是关闭的,要让自己滚动一段距离，菜单的宽度
            scrollTo(mMenuWidth,0);
        }

    }

    /**
     * dipz转px
     * @param dip
     * @return
     */
    private int dp2px(int dip) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dip,getResources().getDisplayMetrics());
    }

    /**
     * 获取屏幕的宽度
     * @return
     */
    private int getScreenWidth() {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        return displayMetrics.widthPixels;
    }
}
