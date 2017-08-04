package example.wxx.com.essayjoke.ui;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import example.wxx.com.essayjoke.R;
import example.wxx.com.essayjoke.test.adapter.BUserAdapter;
import example.wxx.com.essayjoke.test.bean.BUser;
import example.wxx.com.essayjoke.test.decoration.DividerItemDecoration;
import example.wxx.com.framelibrary.indexlib.IndexBar.widget.IndexBar;
import example.wxx.com.framelibrary.indexlib.suspension.SuspensionDecoration;

/**
 * 仿微信通信录列表
 *
 * @author wengxingxia
 * @time 2017/7/25 0025 16:06
 */
public class LeaguerRecylerView extends FrameLayout {
    private Context mContext;

    private RecyclerView mRv;
    private SuspensionDecoration mDecoration;
    /**
     * 右侧边栏导航区域
     */
    private IndexBar mIndexBar;
    /**
     * 显示指示器DialogText
     */
    private TextView mTvSideBarHint;

    private BUserAdapter mAdapter;
    private LinearLayoutManager mManager;
    private List<BUser> mDatas;
    private DividerItemDecoration mDividerItemDecoration;

    public LeaguerRecylerView(Context context) {
        this(context, null);
    }

    public LeaguerRecylerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LeaguerRecylerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        //        把布局加载到这个view里面
        inflate(context, R.layout.ui_index_rv, this);
        initView();//初始化控件
        initData();//初始化数据
    }

    /**
     * 初始化控件
     */
    private void initView() {
        mRv = (RecyclerView) findViewById(R.id.rv);
        //使用indexBar
        mTvSideBarHint = (TextView) findViewById(R.id.tvSideBarHint);//HintTextView
        mIndexBar = (IndexBar) findViewById(R.id.indexBar);//IndexBar
    }

    /**
     * 初始化数据
     */
    private void initData() {
        mDatas = new ArrayList<>();
        mManager = new LinearLayoutManager(mContext);
        mAdapter = new BUserAdapter(mContext, mDatas);
        mDecoration = new SuspensionDecoration(mContext, mDatas);
        mRv.setAdapter(mAdapter);
        mRv.setLayoutManager(mManager);
        mRv.addItemDecoration(mDecoration);
        //如果add两个，那么按照先后顺序，依次渲染。
        mDividerItemDecoration = new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL_LIST);
        mRv.addItemDecoration(mDividerItemDecoration);
        //indexbar初始化
        mIndexBar.setmPressedShowTextView(mTvSideBarHint)//设置HintTextView
                .setNeedRealIndex(true)//设置需要真实的索引
                .setmLayoutManager(mManager);//设置RecyclerView的LayoutManager
    }

    /**
     * 设置数据源
     * @param datas
     */
    public void setDatas(List<BUser> datas) {
        if (mAdapter == null) {
            mAdapter = new BUserAdapter(mContext, mDatas);
            mRv.setAdapter(mAdapter);
        }
        mDatas.clear();
        mDatas.addAll(datas);
        mAdapter.notifyDataSetChanged();
        mIndexBar.setmSourceDatas(mDatas)//设置数据
                .invalidate();
        mDecoration.setmDatas(mDatas);
    }
}
