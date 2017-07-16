package example.wxx.com.framelibrary.skin.attr;

import android.view.View;

/**
 * 作者：wengxingxia
 * 时间：2017/6/27 0027 21:30
 */

public class SkinAttr {

    private String mResName;

    private SkinType mSkinType;

    public SkinAttr(String resName, SkinType skinType) {
        this.mResName = resName;
        this.mSkinType = skinType;
    }

    public void skin(View view) {
        mSkinType.skin(view,mResName);
    }

}
