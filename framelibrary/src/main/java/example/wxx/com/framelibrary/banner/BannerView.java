package example.wxx.com.framelibrary.banner;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import example.wxx.com.framelibrary.R;

/**
 * 作者：wengxingxia
 * 时间：2017/7/12 0012 12:56
 */

public class BannerView extends RelativeLayout{


//   轮播的ViewPager
    private BannerViewPager mBannerViewPager;

    private Context mContext;
//    轮播描述
    private TextView mTvBannerDesc;

//    点的容器
    private LinearLayout llDotContainer;

//    自定义的BannerAdapter
    private BannerAdapter mAdapter;

//    初始化点的指示器——点选中的Drawable
    private Drawable mIndicatorFocusDrawable;
    //    初始化点的指示器——点默认的Drawable
    private Drawable mIndicatorNormalDrawable;

//    记录当前位置
    private int mCurrentPosition = 0;
//    自定义属性，点的显示位置，默认在右边
    private int mDotGravity = 1;//0是center，1是right，-1是Left
//    自定义属性，点的大小，默认 8 dp
    private int mDotSize = 8;
    //    自定义属性，点的间距，默认 8 dp
    private int mDotDistance = 8;
//    底部布局容器
    private RelativeLayout mRlBannerBottomView;
//     底部布局容器颜色默认透明
    private int mBottomColor = Color.TRANSPARENT;

//    宽高比
    private float mWidthProportion ;
    private float mHeightProportion;
    public BannerView(Context context) {
        this(context,null);
    }

    public BannerView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
//        把布局加载到这个view里面
        inflate(context, R.layout.ui_banner_layout,this);
        initAttribute(attrs);//初始化自定义属性
        initView();
//        mIndicatorFocusDrawable = new ColorDrawable(Color.RED);
//        mIndicatorNormalDrawable = new ColorDrawable(Color.WHITE);
    }

  /*  @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        *//*if(mHeightProportion == 0||mWidthProportion == 0){
            return;
        }
//        动态指定宽高，计算高度
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = (int) (width*mHeightProportion/mWidthProportion);
//        指定宽高
        setMeasuredDimension(width,height);*//*
    }*/

    /**
     * 初始化自定义属性
     * @param attrs
     */
    private void initAttribute(AttributeSet attrs) {
        final TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.BannerView);
//        获取自定义属性
        mDotGravity = typedArray.getInt(R.styleable.BannerView_dotGravity,mDotGravity);
//        获取点的选中颜色
        mIndicatorFocusDrawable = typedArray.getDrawable(R.styleable.BannerView_dotIndicatorFocus);
        if(mIndicatorFocusDrawable==null){
//            如果在布局文件中，用户没有配置点的颜色，有一个默认值
            mIndicatorFocusDrawable =  new ColorDrawable(Color.RED);
        }
//        获取点的未选中颜色
        mIndicatorNormalDrawable = typedArray.getDrawable(R.styleable.BannerView_dotIndicatorNormal);
        if(mIndicatorNormalDrawable==null){
//            如果在布局文件中，用户没有配置点的颜色，有一个默认值
            mIndicatorNormalDrawable =  new ColorDrawable(Color.WHITE);
        }
//        获取点的大小
        mDotSize = (int) typedArray.getDimension(R.styleable.BannerView_dotSize,dip2px(mDotSize));
//        获取点的距离
        mDotDistance = (int) typedArray.getDimension(R.styleable.BannerView_dotDistance,dip2px(mDotDistance));
//        获取底部颜色
        mBottomColor = typedArray.getColor(R.styleable.BannerView_bottomColor,mBottomColor);
//      获取宽高比
        mWidthProportion = typedArray.getFloat(R.styleable.BannerView_widthProportion,mWidthProportion);
        mHeightProportion = typedArray.getFloat(R.styleable.BannerView_heightProportion,mHeightProportion);

        typedArray.recycle();

    }

    /**
     * 初始化View
     */
    private void initView(){
        mBannerViewPager = (BannerViewPager) findViewById(R.id.vpBanner);
        mTvBannerDesc = (TextView) findViewById(R.id.tvBannerDesc);
        llDotContainer = (LinearLayout) findViewById(R.id.llDotContainer);
        mRlBannerBottomView = (RelativeLayout) findViewById(R.id.rlBannerBottomView);
        mRlBannerBottomView.setBackgroundColor(mBottomColor);
    }

    /**
     * 设置适配器
     * @param adapter
     */
    public void setAdapter(BannerAdapter adapter){
        try {
            mAdapter = adapter;
            mBannerViewPager.setAdapter(adapter);
            initDotIndicator();//初始化点的指示器

//
            mBannerViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
                @Override
                public void onPageSelected(int position) {
    //                监听当前选中位置
                    pageSelect(position);

                }
            });

//       默认初始化的时候获取第一条描述
            String firstDesc = mAdapter.getBannerDesc(0);
            mTvBannerDesc.setText(firstDesc);

//           自定义属性，动态指定高度
            if(mHeightProportion == 0||mWidthProportion == 0){
                return;
            }
//        动态指定宽高，计算高度
            /*int width = super.getMeasuredWidth();
            int height = (int) (width*mHeightProportion/mWidthProportion);
//        指定宽高
            getLayoutParams().height = height;*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 页面切换的回调
     * @param position
     */
    private void pageSelect(int position) {
//        把之前亮着的点 设置为默认
        DotIndicatorView oldIndicatorView = (DotIndicatorView) llDotContainer.getChildAt(mCurrentPosition);
        oldIndicatorView.setDrawable(mIndicatorNormalDrawable);
//        把当前位置的点  点亮
        mCurrentPosition = position%mAdapter.getCount();
        DotIndicatorView currentIndicatorView = (DotIndicatorView) llDotContainer.getChildAt(mCurrentPosition);
        currentIndicatorView.setDrawable(mIndicatorFocusDrawable);

//       设置广告描述
        String bannerDesc = mAdapter.getBannerDesc(mCurrentPosition);
        mTvBannerDesc.setText(bannerDesc);
    }

    /**
     * 5、初始化点的指示器
     */
    private void initDotIndicator() {
//        获取轮播图的数量
        int count = mAdapter.getCount();

//        设置点的位置在右边
        llDotContainer.setGravity(getDotGravity());
        for (int i = 0; i < count; i++) {
//            不断的往点的指示器中添加圆点
            final DotIndicatorView dotIndicatorView = new DotIndicatorView(mContext);

//            设置大小
            final LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(mDotSize, mDotSize);
            dotIndicatorView.setLayoutParams(layoutParams);
//            设置左右间距
//            layoutParams.leftMargin = layoutParams.rightMargin = dip2px(2);
            layoutParams.leftMargin = mDotDistance;
            if(i==0){
//                选中位置
                dotIndicatorView.setDrawable(mIndicatorFocusDrawable);
            }else {
//                未选中位置
                dotIndicatorView.setDrawable(mIndicatorNormalDrawable);
            }
            llDotContainer.addView(dotIndicatorView);
        }
    }

    /**
     * 获取点的位置
     * @return
     */
    private int getDotGravity() {
        switch (mDotGravity){
            case 0:
                return Gravity.CENTER;
            case 1:
                return Gravity.RIGHT;
            case -1:
                return Gravity.LEFT;
        }
        return Gravity.LEFT;
    }

    /**
     * 把dip转为px
     * @param dip
      @return
     */
    private int dip2px(int dip) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dip,getResources().getDisplayMetrics());
    }

    /**
     * 开始滚动
     */
    public void startRoll() {
        mBannerViewPager.startRoll();
    }

}
