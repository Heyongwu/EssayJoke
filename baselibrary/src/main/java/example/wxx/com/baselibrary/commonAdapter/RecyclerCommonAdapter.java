package example.wxx.com.baselibrary.commonAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * RecyclerView的通用Adapter
 * 作者：wengxingxia
 * 时间：2017/6/8 0008 19:36
 */

public abstract class RecyclerCommonAdapter<DATA> extends RecyclerView.Adapter<ViewHolder> {
    //  条目布局不一样，只能通过参数传递
    private int mLayoutId;
    //    参数通用，那么只能用泛型
    private List<DATA> mDatas;
    //    实例化View的LayoutInflater
    private LayoutInflater mInflater;

    public Context mContext;

    private MultiTypeSupport<DATA> mMultiTypeSupport;

    public RecyclerCommonAdapter(Context context, int layoutId, List<DATA> datas) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        this.mLayoutId = layoutId;
        mDatas = datas;
    }
    public RecyclerCommonAdapter(Context context, List<DATA> datas,MultiTypeSupport typeSupport) {
        this(context,-1,datas);
        this.mMultiTypeSupport = typeSupport;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//      多布局问题
        if(mMultiTypeSupport!=null){
//            需要多布局
            mLayoutId = viewType;
        }
//        创建View  context
        View view = mInflater.inflate(mLayoutId, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        if(mMultiTypeSupport!=null)
           return mMultiTypeSupport.getLayoutId(mDatas.get(position));
        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
//        Viewholder优化
        convert(holder, mDatas.get(position), position);
//        条目点击事件
        if (mItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    利用回调点击事件
                    mItemClickListener.onItemClick(position);
                }
            });
        }

        if (mItemLongClickListener != null) {
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return mItemLongClickListener.onItemLongClick(position);
                }
            });
        }

    }

    /**
     * 把必要参数传出去
     *
     * @param holder   ViewHolder
     * @param data     当前位置的条目数据
     * @param position 当前位置
     */
    protected abstract void convert(ViewHolder holder, DATA data, int position);

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    private ItemClickListener mItemClickListener;
    private ItemLongClickListener mItemLongClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    public void setItemLongClickListener(ItemLongClickListener itemLongClickListener) {
        mItemLongClickListener = itemLongClickListener;
    }
}
