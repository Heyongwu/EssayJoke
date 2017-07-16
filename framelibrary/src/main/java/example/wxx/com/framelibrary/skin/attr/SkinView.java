package example.wxx.com.framelibrary.skin.attr;

import android.view.View;

import java.util.List;

/**
 * 作者：wengxingxia
 * 时间：2017/6/27 0027 21:13
 */

public class SkinView {

    private View mView;

    private List<SkinAttr> mSkinAttrs;

    public SkinView(View view, List<SkinAttr> skinAttrs) {
        this.mView = view;
        this.mSkinAttrs = skinAttrs;
    }

    public void skin(){
        for (SkinAttr attr : mSkinAttrs) {
            attr.skin(mView);
        }
    }
}
