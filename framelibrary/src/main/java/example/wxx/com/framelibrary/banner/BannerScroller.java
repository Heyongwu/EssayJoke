package example.wxx.com.framelibrary.banner;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

/**
 * 用于——改变ViewPager切换的速率
 * 作者：wengxingxia
 * 时间：2017/7/12 0012 11:39
 */

public class BannerScroller extends Scroller {
//    动画持续的时间
    private int mScrollerDuration = 1000;

    /**
     * 设置切换界面持续的时间
     * @param scrollerDuration
     */
    public void setScrollerDuration(int scrollerDuration) {
        mScrollerDuration = scrollerDuration;
    }

    public BannerScroller(Context context) {
        super(context);
    }

    public BannerScroller(Context context, Interpolator interpolator) {
        super(context, interpolator);
    }

    public BannerScroller(Context context, Interpolator interpolator, boolean flywheel) {
        super(context, interpolator, flywheel);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        super.startScroll(startX, startY, dx, dy, mScrollerDuration);
    }
}
