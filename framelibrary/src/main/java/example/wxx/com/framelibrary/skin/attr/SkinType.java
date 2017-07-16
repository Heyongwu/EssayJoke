package example.wxx.com.framelibrary.skin.attr;

import android.view.View;

/**
 * 作者：wengxingxia
 * 时间：2017/6/27 0027 21:31
 */

public enum SkinType {

    TEXT_COLOR("textColor") {
        @Override
        public void skin(View view, String resName) {

        }
    },BACKGROUND("background") {
        @Override
        public void skin(View view, String resName) {

        }
    },SRC("src") {
        @Override
        public void skin(View view, String resName) {
//          获取资源

        }
    };
//  根据名字调用对应的方法
    private String mResName;

    SkinType(String resName) {
        mResName = resName;
    }

//    抽象方法
    public abstract void skin(View view, String resName);

    public String getResName() {
        return mResName;
    }
}
