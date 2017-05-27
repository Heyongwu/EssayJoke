package example.wxx.com.framelibrary;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import example.wxx.com.baselibrary.navigationbar.AbsNavigationBar;

/**
 * 作者：wengxingxia
 * 时间：2017/5/20 0020 16:56
 */

public class DefaultNavigationBar extends AbsNavigationBar<DefaultNavigationBar.Builder.DefaultNavigationBarParams>{

    public DefaultNavigationBar(Builder.DefaultNavigationBarParams params) {
        super(params);
    }

    @Override
    public int bindLayoutId() {
        return R.layout.title_bar;
    }

    @Override
    public void applyView() {
//      绑定效果
//        设置文本
        setText(R.id.title,getParams().mTitle);
        setText(R.id.right_text,getParams().mRightText);
        setText(R.id.back,getParams().mLeftText);

        setIcon(R.id.right_text,getParams().mRightIconId);
        setIcon(R.id.back,getParams().mLeftIconId);
        
//        设置监听器
        setOnClickListener(R.id.right_text,getParams().mRightClickListener);
//        左边写一个默认的onClickListener 用来finishActivity
        setOnClickListener(R.id.back,getParams().mLeftClickListener);

    }

    public static class Builder extends AbsNavigationBar.Builder{

        private DefaultNavigationBarParams P;

        public Builder(Context context, ViewGroup parent) {
            super(context, parent);
            P = new DefaultNavigationBarParams(context,parent);
        }

        public Builder(Context context) {
            super(context, null);
            P = new DefaultNavigationBarParams(context,null);
        }

        @Override
        public DefaultNavigationBar builder() {
            DefaultNavigationBar navigationBar = new DefaultNavigationBar(P);
            return navigationBar;
        }

//        1、设置所有效果

        /**
         * 设置标题
         * @param title
         * @return
         */
        public DefaultNavigationBar.Builder setTitle(String title){
            P.mTitle = title;
            return this;
        }

        /**
         * 设置右边文字
         * @param rightText
         * @return
         */
        public DefaultNavigationBar.Builder setRightText(String rightText){
            P.mRightText = rightText;
            return this;
        }

        /**
         * 设置右边图片
         * @param rightResId
         * @return
         */
        public DefaultNavigationBar.Builder setRightIcon(int rightResId){
            P.mRightIconId = rightResId;
            return this;
        }

        /**
         * 设置右边控件的点击事件
         * @param rightListener
         * @return
         */
        public DefaultNavigationBar.Builder setRightClickListener(View.OnClickListener rightListener){
            P.mRightClickListener = rightListener;
            return this;
        }

        /**
         * 设置左边文字
         * @param leftText
         * @return
         */
        public DefaultNavigationBar.Builder setLeftText(String leftText){
            P.mLeftText = leftText;
            return this;
        }

        /**
         * 设置左边图片
         * @param leftResId
         * @return
         */
        public DefaultNavigationBar.Builder setLeftIcon(int leftResId){
            P.mLeftIconId = leftResId;
            return this;
        }

        /**
         * 设置左边控件的点击事件
         * @param leftListener
         * @return
         */
        public DefaultNavigationBar.Builder setLeftClickListener(View.OnClickListener leftListener){
            P.mLeftClickListener = leftListener;
            return this;
        }


        public static class DefaultNavigationBarParams extends AbsNavigationBar.Builder.AbsNavigationBarParams{

//         2、放置所有效果
            public String mTitle;//标题
            public int mRightIconId;//右边图标id
            public String mRightText;//

            public View.OnClickListener mRightClickListener;//右边点击事件
            public String mLeftText;
            public View.OnClickListener mLeftClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    关闭当前Activity
                    ((Activity)mContext).finish();
                }
            };
            public int mLeftIconId;


            public DefaultNavigationBarParams(Context context, ViewGroup parent) {
                super(context, parent);
            }

        }
    }
}
