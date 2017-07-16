package example.wxx.com.baselibrary.commonAdapter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * GridView样式的分割线
 * 作者：wengxingxia
 * 时间：2017/6/2 0002 08:41
 */

public class GridLayoutItemDecoration extends RecyclerView.ItemDecoration {

    private Context mContext;
    private int mDrawableResourceId;

    private Drawable mDivider;

//    private boolean isHideFirst;//是否隐藏第一个
//    private boolean isHideLast;//是否隐藏最后一个

    //    用的是系统的一个属性 android.attrs.listDriver
    public GridLayoutItemDecoration(Context context, int drawableResourceId) {
        mContext = context;
        mDrawableResourceId = drawableResourceId;

//        获取Drawable
        mDivider = ContextCompat.getDrawable(mContext, mDrawableResourceId);
    }

    /***
     * 基本操作，留出分割线位置
     * @param outRect
     * @param view
     * @param parent
     * @param state
     */
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

//        留出分割线的位置
        int bottom = mDivider.getIntrinsicHeight();
        int right = mDivider.getIntrinsicWidth();


        if (isLastColumn(view, parent)) {//最后一列
            right = 0;
        }
        if (isLastRow(view,parent)) {//最后一行
            bottom = 0;
        }
        outRect.bottom = bottom;
        outRect.right = right;
    }

    /**
     * 绘制分割线
     *
     * @param canvas
     * @param parent
     * @param state
     */
    @Override
    public void onDraw(Canvas canvas, RecyclerView parent, RecyclerView.State state) {

//        绘制分割线
        drawHorizontal(canvas, parent);
        drawVertical(canvas, parent);

    }

    /***
     * 绘制垂直方向上的分割线
     * @param canvas
     * @param parent
     */
    private void drawVertical(Canvas canvas, RecyclerView parent) {
        int childCount = parent.getChildCount();
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        for (int i = 0; i < childCount; i++) {
            View childView = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) childView.getLayoutParams();
            int left = childView.getRight() + params.rightMargin;
            int right = left + mDivider.getIntrinsicWidth();
            if (isLastColumn(childView, parent))
                right = left;
            int top = childView.getTop() - params.topMargin;
            int bottom = childView.getBottom() + params.bottomMargin;

//            计算分割线的位置
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(canvas);

        }
    }

    /**
     * 绘制水平方向
     *
     * @param canvas
     * @param parent
     */
    private void drawHorizontal(Canvas canvas, RecyclerView parent) {
        int childCount = parent.getChildCount();
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        for (int i = 0; i < childCount; i++) {
            View childView = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) childView.getLayoutParams();
            int left = childView.getLeft() - params.leftMargin;
            int right = childView.getRight() + mDivider.getIntrinsicWidth() + params.rightMargin;
            if (isLastColumn(childView, parent))
                right = right - mDivider.getIntrinsicWidth();
            int top = childView.getBottom() + params.bottomMargin;
            int bottom = top + mDivider.getIntrinsicHeight();
            if(isLastRow(childView,parent)){
                bottom=top;
            }
//            计算分割线的位置
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(canvas);

        }
    }


    /**
     * 是否是最后一列
     *
     * @param view
     * @param parent
     * @return
     */
    private boolean isLastColumn(View view, RecyclerView parent) {
//        获取当前位置
        int currentPosition = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
//        获取列数
        int spanCount = getSpanCount(parent);//列数
        return (currentPosition + 1) % spanCount == 0;
    }

    /**
     * 是否是最后一行
     * @param view
     * @param parent
     * @return
     */
    private boolean isLastRow(View view, RecyclerView parent) {
//      当前位置>(行数-1)*列数

//        列数
        int spanCount = getSpanCount(parent);

//        行数 = 总条目/列数+总条目%列数==0？0：1
        int counts = parent.getAdapter().getItemCount();//总条目
        int rowNumber = (counts/spanCount)+(counts%spanCount==0?0:1);//行数
//        获取当前位置
        int currentPosition = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
//      是否是最后一行
        boolean isLastRow = (currentPosition+1) > ((rowNumber - 1) * spanCount);
//        return isLastRow;
        return false;
    }

    /**
     * 获取RecyclerView的列数
     *
     * @param parent
     * @return
     */
    private int getSpanCount(RecyclerView parent) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;

            int spanCount = gridLayoutManager.getSpanCount();//列数
            return spanCount;
        }
        return 1;
    }
}

