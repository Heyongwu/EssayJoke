package example.wxx.com.baselibrary.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import example.wxx.com.baselibrary.R;

/**
 * 自定义 万能Dialog
 * 作者：wengxingxia
 * 时间：2017/5/19 0019 14:18
 */

public class AlertDialog extends Dialog {


    private AlertController mAlert;

    public AlertDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);

        mAlert = new AlertController(this, getWindow());
    }

    /**
     * 设置文本
     *
     * @param viewId
     * @param text
     */
    public void setText(int viewId, CharSequence text) {
        mAlert.setText(viewId,text);
    }

    /**
     * 设置点击事件
     *
     * @param viewId
     * @param onClickListener
     */
    public void setOnClickListener(int viewId, View.OnClickListener onClickListener) {
        mAlert.setOnClickListener(viewId,onClickListener);
    }

    /**
     * 根据View id 获得View
     *
     * @param viewId
     * @param <T>
     * @return
     */
    public <T extends View> T getView(int viewId) {
        return mAlert.getView(viewId);
    }

    public static class Builder {

        private final AlertController.AlertParams P;

        /**
         * Creates a builder for an alert dialog that uses the default alert
         * dialog theme.
         * <p>
         * The default alert dialog theme is defined by
         * {@link android.R.attr#alertDialogTheme} within the parent
         * {@code context}'s theme.
         *
         * @param context the parent context
         */
        public Builder(Context context) {
            this(context, R.style.dialog);
        }

        /**
         * Creates a builder for an alert dialog that uses an explicit theme
         * resource.
         * <p>
         * The specified theme resource ({@code themeResId}) is applied on top
         * of the parent {@code context}'s theme. It may be specified as a
         * style resource containing a fully-populated theme, such as
         * {@link android.R.style#Theme_Material_Dialog}, to replace all
         * attributes in the parent {@code context}'s theme including primary
         * and accent colors.
         * <p>
         * To preserve attributes such as primary and accent colors, the
         * {@code themeResId} may instead be specified as an overlay theme such
         * as {@link android.R.style#ThemeOverlay_Material_Dialog}. This will
         * override only the window attributes necessary to style the alert
         * window as a dialog.
         * <p>
         * Alternatively, the {@code themeResId} may be specified as {@code 0}
         * to use the parent {@code context}'s resolved value for
         * {@link android.R.attr#alertDialogTheme}.
         *
         * @param context    the parent context
         * @param themeResId the resource ID of the theme against which to inflate
         *                   this dialog, or {@code 0} to use the parent
         *                   {@code context}'s default alert dialog theme
         */
        public Builder(Context context, int themeResId) {
            P = new AlertController.AlertParams(context, themeResId);
        }

        /**
         * Creates an {@link AlertDialog} with the arguments supplied to this
         * builder.
         * <p>
         * Calling this method does not display the dialog. If no additional
         * processing is needed, {@link #show()} may be called instead to both
         * create and display the dialog.
         */
        public AlertDialog create() {
            // Context has already been wrapped with the appropriate theme.
            final AlertDialog dialog = new AlertDialog(P.mContext, P.mThemeResId);
            P.apply(dialog.mAlert);
            dialog.setCancelable(P.mCancelable);
            if (P.mCancelable) {
                dialog.setCanceledOnTouchOutside(true);
            }
            dialog.setOnCancelListener(P.mOnCancelListener);
            dialog.setOnDismissListener(P.mOnDismissListener);
            if (P.mOnKeyListener != null) {
                dialog.setOnKeyListener(P.mOnKeyListener);
            }
            return dialog;
        }

        /**
         * Creates an {@link AlertDialog} with the arguments supplied to this
         * builder and immediately displays the dialog.
         * <p>
         * Calling this method is functionally identical to:
         * <pre>
         *     AlertDialog dialog = builder.create();
         *     dialog.show();
         * </pre>
         */
        public AlertDialog show() {
            final AlertDialog dialog = create();
            dialog.show();
            return dialog;
        }

        /**
         * 设置布局的View
         * Sets a custom view to be the contents of the alert dialog.
         * <p>
         * When using a pre-Holo theme, if the supplied view is an instance of
         * a {@link ListView} then the light background will be used.
         * <p>
         * <strong>Note:</strong> To ensure consistent styling, the custom view
         * should be inflated or constructed using the alert dialog's themed
         * context obtained via {@link #getContext()}.
         *
         * @param view the view to use as the contents of the alert dialog
         * @return this Builder object to allow for chaining of calls to set
         * methods
         */
        public Builder setContentView(View view) {
            P.mView = view;
            P.mViewLayoutResId = 0;
            return this;
        }

        /**
         * 设置布局 layout 的 Id
         *
         * @param layoutResId
         * @return
         */
        public Builder setContentView(int layoutResId) {
            P.mView = null;
            P.mViewLayoutResId = layoutResId;
            return this;
        }

//        配置一些万能的参数

        /**
         * 宽度全屏
         *
         * @return
         */
        public Builder fullWidth() {
            P.mWidth = ViewGroup.LayoutParams.MATCH_PARENT;
            return this;
        }

        /**
         * 从底部弹出动画
         *
         * @param isAnimation 是否有动画
         * @return
         */
        public Builder fromBottom(boolean isAnimation) {
            if (isAnimation) {
                P.mAnimatins = R.style.dialog_from_bottom_anim;
            }
            P.mGravity = Gravity.BOTTOM;
            return this;
        }

        /**
         * 设置dialog的宽高
         *
         * @param width
         * @param height
         * @return
         */
        public Builder setWidthAndHeight(int width, int height) {
            P.mWidth = width;
            P.mHeigth = height;
            return this;
        }

        /**
         * 添加默认动画
         *
         * @return
         */
        public Builder addDefaultAnimation() {
            P.mAnimatins = R.style.dialog_scale_anim;
            return this;
        }

        /**
         * 设置动画
         * @param animationStyle
         * @return
         */
        public Builder setAnimation(int animationStyle) {
            P.mAnimatins = animationStyle;
            return this;
        }

        /**
         * Sets whether the dialog is cancelable or not.  Default is true.
         *
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setCancelable(boolean cancelable) {
            P.mCancelable = cancelable;
            return this;
        }

        /**
         * Sets the callback that will be called if the dialog is canceled.
         * <p>
         * <p>Even in a cancelable dialog, the dialog may be dismissed for reasons other than
         * being canceled or one of the supplied choices being selected.
         * If you are interested in listening for all cases where the dialog is dismissed
         * and not just when it is canceled, see
         * {@link #setOnDismissListener(android.content.DialogInterface.OnDismissListener) setOnDismissListener}.</p>
         *
         * @return This Builder object to allow for chaining of calls to set methods
         * @see #setCancelable(boolean)
         * @see #setOnDismissListener(android.content.DialogInterface.OnDismissListener)
         */
        public Builder setOnCancelListener(OnCancelListener onCancelListener) {
            P.mOnCancelListener = onCancelListener;
            return this;
        }

        /**
         * Sets the callback that will be called when the dialog is dismissed for any reason.
         *
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setOnDismissListener(OnDismissListener onDismissListener) {
            P.mOnDismissListener = onDismissListener;
            return this;
        }

        /**
         * Sets the callback that will be called if a key is dispatched to the dialog.
         *
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setOnKeyListener(OnKeyListener onKeyListener) {
            P.mOnKeyListener = onKeyListener;
            return this;
        }

        /***
         * 设置文本
         * @param viewId
         * @param text
         * @return
         */
        public Builder setText(int viewId, CharSequence text) {
            P.mTextArray.put(viewId, text);
            return this;
        }

        /**
         * 设置点击事件
         *
         * @param viewId
         * @param listener
         * @return
         */
        public Builder setOnClickListener(int viewId, View.OnClickListener listener) {
            P.mClickListenerArray.put(viewId, listener);
            return this;
        }

    }
}
