package example.wxx.com.baselibrary.ioc;

import android.app.Activity;
import android.view.View;

/**
 * View的findViewById的辅助类
 * 作者：wengxingxia
 * 时间：2017/5/12 0012 11:08
 */

public class ViewFinder {

    private Activity mActivity;
    private View mView;

    public ViewFinder(View view) {
        this.mView = view;
    }

    public ViewFinder(Activity activity) {
        this.mActivity = activity;
    }

    public View findViewById(int viewId){
        return mActivity!=null?mActivity.findViewById(viewId):mView.findViewById(viewId);
    }
}
