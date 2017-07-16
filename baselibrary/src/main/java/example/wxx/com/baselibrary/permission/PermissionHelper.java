package example.wxx.com.baselibrary.permission;

import android.app.Activity;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;

import java.util.List;

/**
 * 作者：wengxingxia
 * 时间：2017/6/17 0017 23:37
 */

public class PermissionHelper {


    private Object mObject;
    private int mRequestCode;
    private String[] mRequestPermissions;

    public PermissionHelper(Object object) {
        mObject = object;
    }


    //  1、传什么参数，
//  1.1、Object Activity  or  Fragment  1.2、int 请求码 1.3、需要请求的权限 String[]

    //  2、  以什么样的方式传参
//    2.1直接传参
    public static void requestPermision(Activity activity, int requestCode, String[] permissions) {
        PermissionHelper.with(activity)
                .requestCode(requestCode)
                .requestPermissions(permissions)
                .request();
    }

    //    2.2 链式的方式传参
//    传Activity
    public static PermissionHelper with(Activity activity) {

        return new PermissionHelper(activity);
    }

    //    传Fragment
    public static PermissionHelper with(Fragment fragment) {

        return new PermissionHelper(fragment);
    }

    /**
     * 添加请求码
     *
     * @param requestCode
     * @return
     */
    public PermissionHelper requestCode(int requestCode) {
        mRequestCode = requestCode;
        return this;
    }

    /**
     * 设置请求权限
     *
     * @param permissions
     * @return
     */
    public PermissionHelper requestPermissions(String... permissions) {
        mRequestPermissions = permissions;
        return this;
    }

    /**
     * 3、1真正的判断和发起请求权限
     */
    public void request() {
//       3.2、 首先判断当前的版本是不是6.0以上
        if (!PermissionUtils.isOverMarshmallow()) {
            //3.3、如果不是6.0以上那么直接执行方法 反射执行方法

//            执行什么方法并不确定，那么我们只能采用注解的方式给方法打一个标记，然后通过反射执行
            PermissionUtils.executeSuccesssMethod(mObject.getClass(),mRequestCode);
            return;
        }

//        3.4、如果是6.0以上，那么首先需要判断权限是否已经授予
//        需要申请的权限中 获取 还没有授予过的权限
        List<String> deniedPermissions = PermissionUtils.getDeniedPermissions(mObject,mRequestPermissions);
//        3.4.1、如果是，那么直接执行方法
        if(deniedPermissions.isEmpty()){
//            全部都是授予过的权限
            PermissionUtils.executeSuccesssMethod(mObject,mRequestCode);
        }else{
//        3.4.2、如果没有授予，那么就申请权限
            ActivityCompat.requestPermissions(PermissionUtils.getActivity(mObject),
                    deniedPermissions.toArray(new String[deniedPermissions.size()]),
                    mRequestCode);
        }
    }


    /**
     * 处理权限申请回调
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    public static void requestPermissionsResult(Object object,int requestCode,
                                                String[] permissions,int[] grantResults){
//       再次获取没有获取的权限
        List<String> deniedPermissions = PermissionUtils.getDeniedPermissions(object,permissions);

        if(deniedPermissions.isEmpty()){
//            申请的权限，用户都已经授予
            PermissionUtils.executeSuccesssMethod(object,requestCode);
        }else{
//            你申请的权限中，有用户不同意的
            PermissionUtils.executeFailedMethod(object,requestCode);
        }
    }
}
