package example.wxx.com.framelibrary.skin;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Environment;

import java.io.File;
import java.lang.reflect.Method;

/**
 * 皮肤的资源管理类
 * 作者：wengxingxia
 * 时间：2017/6/27 0027 21:05
 */

public class SkinResource {

//  资源通过这个对象获取
    private Resources mSkinResources;

    public SkinResource(Context context, String skinPath) {

        try {
            Resources superRes = context.getResources();
//        创建AssetManager
            AssetManager asset = null;
            asset = AssetManager.class.newInstance();

            final Method method = AssetManager.class.getDeclaredMethod("addAssetPath", String.class);
//            method.setAccessible(true);//如果是私有的
//            反射执行方法
            method.invoke(asset, Environment.getExternalStorageDirectory().getAbsolutePath()
                    + File.separator + "red.skin");
            mSkinResources = new Resources(asset, superRes.getDisplayMetrics(), superRes.getConfiguration());
//            获取资源
            int drawableId = mSkinResources.getIdentifier("image_src", "drawable", "example.wxx.com.skin");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
