package example.wxx.com.essayjoke;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.alipay.euler.andfix.patch.PatchManager;

import example.wxx.com.baselibrary.ExceptionCrashHandler;

/**
 * 作者：wengxingxia
 * 时间：2017/5/18 0018 09:53
 */

public class BaseApplication extends Application {

    public static PatchManager mPatchManager;
    private static Context mContext;
    private PackageInfo mPackageInfo = null;
    private PackageManager mPackageManager;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this.getApplicationContext();
//      设置全局异常捕捉类
        ExceptionCrashHandler.getmInstance().init(this);
//      初始化阿里的热修复
        mPatchManager = new PatchManager(mContext);


//        获取当前应用版本号
        mPackageManager = mContext.getPackageManager();
        try {
            mPackageInfo = mPackageManager.getPackageInfo(
                    mContext.getPackageName(), PackageManager.GET_ACTIVITIES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        //初始化版本
        mPatchManager.init(mPackageInfo.versionName);//当前应用版本号

//        加载之前的apatch 包
        mPatchManager.loadPatch();
    }

    public static Context getmContext() {
        return mContext;
    }
}
