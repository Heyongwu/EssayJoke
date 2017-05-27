package example.wxx.com.baselibrary.navigationbar;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * 头部的Builder基类
 * 作者：wengxingxia
 * 时间：2017/5/20 0020 16:38
 */

public abstract class AbsNavigationBar<P extends AbsNavigationBar.Builder.AbsNavigationBarParams> implements INavigationBar {
    private P mParams;
    private View mNavigationView;

    public AbsNavigationBar(P params) {
        mParams = params;
        createAndBindView();
        applyView();
    }

    public P getParams() {
        return mParams;
    }

    /**
     * 绑定和创建View
     */
    private void createAndBindView() {
//      1、创建View
        if (mParams.mParent == null) {
//            获取Activity的根布局,来自源码
            ViewGroup activityViewRoot = (ViewGroup) ((Activity) (mParams.mContext)).getWindow().getDecorView();
            mParams.mParent = (ViewGroup) activityViewRoot.getChildAt(0);
        }

        if (mParams.mParent == null) {
            return;
        }
        mNavigationView = LayoutInflater.from(mParams.mContext).inflate(bindLayoutId(), mParams.mParent, false);

//        2、添加到第一个位置
        mParams.mParent.addView(mNavigationView, 0);
    }


    protected void setText(int viewId, String text) {
        TextView tv = findViewById(viewId);
        if (!TextUtils.isEmpty(text)) {
            tv.setVisibility(View.VISIBLE);
            tv.setText(text);
        }
    }

    protected void setIcon(int viewId, int iconResId) {
        TextView tv = findViewById(viewId);
        if (iconResId > 0) {
            tv.setVisibility(View.VISIBLE);
            tv.setBackgroundResource(iconResId);
        }
    }

    protected void setOnClickListener(int viewId, View.OnClickListener listener) {
        if (listener != null)
            findViewById(viewId).setOnClickListener(listener);
    }

    public <T extends View> T findViewById(int viewId) {
        return (T) mNavigationView.findViewById(viewId);
    }

    public abstract static class Builder {

        public Builder(Context context, ViewGroup parent) {
        }

        public abstract AbsNavigationBar builder();


        public static class AbsNavigationBarParams {
            public Context mContext;
            public ViewGroup mParent;

            public AbsNavigationBarParams(Context context, ViewGroup parent) {
                this.mContext = context;
                this.mParent = parent;
            }

        }
    }
}
