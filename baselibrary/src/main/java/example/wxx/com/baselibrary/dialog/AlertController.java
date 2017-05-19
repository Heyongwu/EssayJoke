package example.wxx.com.baselibrary.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

/**
 * 作者：wengxingxia
 * 时间：2017/5/19 0019 14:19
 */

class AlertController {

    private AlertDialog mDialog;
    private Window mWindow;

    private DialogViewHelper mViewHelper;

    public AlertController(AlertDialog alertDialog, Window window) {
        this.mDialog = alertDialog;
        this.mWindow = window;
    }

    public void setViewHelper(DialogViewHelper viewHelper) {
        mViewHelper = viewHelper;
    }

    /**
     * 获取Dialog
     *
     * @return
     */
    public AlertDialog getDialog() {
        return mDialog;
    }

    /**
     * 获取Dialog的Window
     *
     * @return
     */
    public Window getWindow() {
        return mWindow;
    }

    /**
     * 设置文本
     *
     * @param viewId
     * @param text
     */
    public void setText(int viewId, CharSequence text) {
        mViewHelper.setText(viewId, text);
    }

    /**
     * 设置点击事件
     *
     * @param viewId
     * @param onClickListener
     */
    public void setOnClickListener(int viewId, View.OnClickListener onClickListener) {
        mViewHelper.setOnClickListener(viewId, onClickListener);
    }

    /**
     * 根据View id 获得View
     *
     * @param viewId
     * @param <T>
     * @return
     */
    public <T extends View> T getView(int viewId) {
        return mViewHelper.getView(viewId);
    }

    public static class AlertParams {

        public Context mContext;
        public int mThemeResId;
        //点击空白是否能够取消,默认可取消
        public boolean mCancelable = true;
        //        dialog Cancel监听
        public DialogInterface.OnCancelListener mOnCancelListener;
        //        dialog Dismiss监听
        public DialogInterface.OnDismissListener mOnDismissListener;
        //        dialog Key监听
        public DialogInterface.OnKeyListener mOnKeyListener;
        //        布局View
        public View mView;
        //        布局的layout id
        public int mViewLayoutResId;
        //      存放字体的修改
        public SparseArray<CharSequence> mTextArray = new SparseArray<>();
        //       存放点击事件
        public SparseArray<View.OnClickListener> mClickListenerArray = new SparseArray<>();
        //        对话框的宽度
        public int mWidth = ViewGroup.LayoutParams.WRAP_CONTENT;
        //        对话框的高度
        public int mHeigth = ViewGroup.LayoutParams.WRAP_CONTENT;
        //        动画
        public int mAnimatins = 0;
        //        位置
        public int mGravity = Gravity.CENTER;


        public AlertParams(Context context, int themeResId) {
            mContext = context;
            mThemeResId = themeResId;
        }

        /**
         * 绑定和设置参数
         *
         * @param alert
         */
        public void apply(AlertController alert) {

//            1、设置参数

//            2、设置Dialog布局 DialogViewHelper
            DialogViewHelper viewHelper = null;
            if (mViewLayoutResId != 0) {
                viewHelper = new DialogViewHelper(mContext, mViewLayoutResId);
            }

            if (mView != null) {
                viewHelper = new DialogViewHelper();
                viewHelper.setContentView(mView);
            }

            if (viewHelper == null) {
                throw new IllegalArgumentException("请设置布局setContentView()");
            }

//            给Dialog设置布局
            alert.getDialog().setContentView(viewHelper.getContentView());

//            设置Controller的辅助类DialogViewHelper
            alert.setViewHelper(viewHelper);

//            3、设置文本
            int textArraySize = mTextArray.size();
            for (int i = 0; i < textArraySize; i++) {
                alert.setText(mTextArray.keyAt(i), mTextArray.valueAt(i));
            }

//            4、设置点击事件
            int clickArraySize = mClickListenerArray.size();
            for (int i = 0; i < clickArraySize; i++) {
                alert.setOnClickListener(mClickListenerArray.keyAt(i), mClickListenerArray.valueAt(i));
            }

//            5、设置自定义效果， 全屏  ， 底部弹出， 默认效果

            Window window = alert.getWindow();
//            设置位置
            window.setGravity(mGravity);
//            设置动画
            if (mAnimatins != 0)
                window.setWindowAnimations(mAnimatins);
//            设置宽度高度
            WindowManager.LayoutParams params = window.getAttributes();
            params.width = mWidth;
            params.height = mHeigth;
            window.setAttributes(params);
        }
    }
}
