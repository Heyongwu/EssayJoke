package example.wxx.com.framelibrary;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.view.LayoutInflaterFactory;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.VectorEnabledTintResources;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewParent;

import java.util.List;

import example.wxx.com.baselibrary.base.BaseActivity;
import example.wxx.com.framelibrary.skin.attr.SkinAttr;
import example.wxx.com.framelibrary.skin.attr.SkinView;
import example.wxx.com.framelibrary.skin.support.SkinAppCompatViewInflater;
import example.wxx.com.framelibrary.skin.support.SkinAttrSupport;

/**
 * 作者：wengxingxia
 * 时间：2017/5/18 0018 08:52
 */

public abstract class BaseSkinActivity extends BaseActivity implements LayoutInflaterFactory {
    private static final String TAG = "TAG";
//    后面会写插件换肤

    public SkinAppCompatViewInflater mAppCompatViewInflater;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        LayoutInflaterCompat.setFactory(layoutInflater, this);
       /* LayoutInflater layoutInflater = LayoutInflater.from(this);
        LayoutInflaterCompat.setFactory(layoutInflater, new LayoutInflaterFactory() {
            @Override
            public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
//                拦截View的创建，获取View之后要解析
//              1、创建View

//                2、解析属性 src textColor background 自定义属性

//                3、统一交给SkinManager管理

                return null;
            }
        });*/
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setContentView() {

    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
//                拦截View的创建，获取View之后要解析

//              1、创建View
        View view = createView(parent, name, context, attrs);
        Log.e(TAG, view + "");
//                2、解析属性 src textColor backgrund 自定义属性

//        2.1  一个Activity的布局肯定对应多个这样的 SkinView
        if (view != null) {
            List<SkinAttr> skinAttrs = SkinAttrSupport.getSkinAttrs(context, attrs);
            SkinView skinView = new SkinView(view, skinAttrs);
//                3、统一交给SkinManager管理
            managerSkinView(skinView);
        }

        return view;
    }

    /**
     * 统一管理SkinView
     * @param skinView
     */
    private void managerSkinView(SkinView skinView) {

    }

    public View createView(View parent, final String name, @NonNull Context context,
                           @NonNull AttributeSet attrs) {
        final boolean isPre21 = Build.VERSION.SDK_INT < 21;

        if (mAppCompatViewInflater == null) {
            mAppCompatViewInflater = new SkinAppCompatViewInflater();
        }

        // We only want the View to inherit its context if we're running pre-v21
        final boolean inheritContext = isPre21 && shouldInheritContext((ViewParent) parent);

        return mAppCompatViewInflater.createView(parent, name, context, attrs, inheritContext,
                isPre21, /* Only read android:theme pre-L (L+ handles this anyway) */
                true, /* Read read app:theme as a fallback at all times for legacy reasons */
                VectorEnabledTintResources.shouldBeUsed() /* Only tint wrap the context if enabled */
        );
    }

    private boolean shouldInheritContext(ViewParent parent) {
        if (parent == null) {
            // The initial parent is null so just return false
            return false;
        }
        final View windowDecor = getWindow().getDecorView();
        while (true) {
            if (parent == null) {
                // Bingo. We've hit a view which has a null parent before being terminated from
                // the loop. This is (most probably) because it's the root view in an inflation
                // call, therefore we should inherit. This works as the inflated layout is only
                // added to the hierarchy at the end of the inflate() call.
                return true;
            } else if (parent == windowDecor || !(parent instanceof View)
                    || ViewCompat.isAttachedToWindow((View) parent)) {
                // We have either hit the window's decor view, a parent which isn't a View
                // (i.e. ViewRootImpl), or an attached view, so we know that the original parent
                // is currently added to the view hierarchy. This means that it has not be
                // inflated in the current inflate() call and we should not inherit the context.
                return false;
            }
            parent = parent.getParent();
        }
    }

}
