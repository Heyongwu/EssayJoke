package example.wxx.com.essayjoke.test.adapter;

import android.content.Context;

import java.util.List;

import example.wxx.com.baselibrary.commonAdapter.RecyclerCommonAdapter;
import example.wxx.com.baselibrary.commonAdapter.ViewHolder;
import example.wxx.com.essayjoke.R;
import example.wxx.com.essayjoke.test.bean.BUser;

/**
 * 通信录Adapter
 *
 * @author wengxingxia
 * @time 2017/7/25 0025 16:19
 */

public class BUserAdapter extends RecyclerCommonAdapter<BUser> {

    public BUserAdapter(Context context, List<BUser> bUsers) {
        super(context, R.layout.item_buser, bUsers);
    }

    @Override
    protected void convert(ViewHolder holder, BUser bUser, int position) {
        holder.setText(R.id.tvName, bUser.getName());
        holder.setText(R.id.tvPhone, bUser.getPhone());
    }
}
