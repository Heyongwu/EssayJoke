package example.wxx.com.framelibrary.banner;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * 圆点指示器
 * 作者：wengxingxia
 * 时间：2017/7/12 0012 21:10
 */

public class DotIndicatorView extends View {
    private Drawable mDrawable;

    public DotIndicatorView(Context context) {
        this(context, null);
    }

    public DotIndicatorView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DotIndicatorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mDrawable != null) {
//            mDrawable.setBounds(0, 0, getMeasuredWidth(), getMeasuredHeight());
//            mDrawable.draw(canvas);
//            画圆
            Bitmap bitmap = drawableToBitmap(mDrawable);//从drawable中得到Bitmap
//            把Bitmap变为圆的
            Bitmap circleBitmap = getCircleBitmap(bitmap);

//            把圆形的Bitmap绘制到画布上
            canvas.drawBitmap(circleBitmap, 0, 0, null);
        }
    }

    /**
     * 获取圆形Bitmap
     *
     * @param bitmap
     * @return
     */
    private Bitmap getCircleBitmap(Bitmap bitmap) {
//        创建一个Bitmap
        final Bitmap circleBitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(circleBitmap);

        final Paint paint = new Paint();
//        设置抗锯齿
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
//        设置防抖动
        paint.setDither(true);
//        在画布上画个圆、
        canvas.drawCircle(getMeasuredWidth() / 2, getMeasuredHeight() / 2, getMeasuredWidth() / 2, paint);
//        取圆和Bitmap的交集
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
//        把原来的Bitmap绘制到新的圆上面
        canvas.drawBitmap(bitmap, 0, 0, paint);
//        内存优化，回收bitmap
        bitmap.recycle();
        bitmap = null;
        return circleBitmap;
    }

    /**
     * 从drawable中得到Bitmap
     *
     * @param drawable
     * @return
     */
    private Bitmap drawableToBitmap(Drawable drawable) {
//        如果是BitmapDrawable类型
        if (drawable instanceof BitmapDrawable)
            return ((BitmapDrawable) drawable).getBitmap();

//        其他类型 比如ColorDrawable
//        创建一个什么也没有的Bitmap
        final Bitmap outBitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.ARGB_8888);
//        创建一个画布
        final Canvas canvas = new Canvas(outBitmap);

//        把drawable 画到 Bitmap上
        drawable.setBounds(0, 0, getMeasuredWidth(), getMeasuredHeight());
        drawable.draw(canvas);
        return outBitmap;
    }

    /**
     * 设置Drawable
     *
     * @param drawable
     */
    public void setDrawable(Drawable drawable) {
        mDrawable = drawable;
//        重新绘制当前的View
        invalidate();
    }
}
