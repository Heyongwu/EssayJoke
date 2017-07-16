package example.wxx.com.baselibrary.permission;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 作者：wengxingxia
 * 时间：2017/6/18 0018 01:04
 */

public class PermissionUtils {
//  这个类里面所有的方法都是静态方法，所以不能让别人去new对象
    private PermissionUtils(){
        throw new UnsupportedOperationException("can not be instantiated");
    }

    /**
     * 判断版本号是否是6.0（包括6.0）以上版本
     * Marshmallow 棉花糖 6.0版本名
     * @return
     */
    public static boolean isOverMarshmallow(){
        return Build.VERSION.SDK_INT>=Build.VERSION_CODES.M;
    }

    /**
     * 执行成功的方法
     * @param object
     * @param requestCode
     */
    public static void executeSuccesssMethod(Object object, int requestCode) {
//        获取Class中所有的方法
        Method[] methods = object.getClass().getDeclaredMethods();

//        遍历找到打了标记的方法
        for (Method method : methods) {
//            获取该方法上面有没有打这个成功的标记
            PermissionSuccess permissionSuccess = method.getAnnotation(PermissionSuccess.class);
            if (permissionSuccess!=null){
                //代表该方法打了标记
//                并且我们的请求码必须和requestCode一样
                int methodCode = permissionSuccess.requestCode();
                if(methodCode ==requestCode){
//                    这个就是我们要找的成功方法
//                    反射执行该方法
                    executeMethod(object,method);
                }

            }
        }
    }

    /**
     * 反射执行该方法
     * @param object
     * @param method
     */
    private static void executeMethod(Object object, Method method) {
//        反射执行方法
        try {
            method.setAccessible(true);//允许执行私有方法
            method.invoke(object,new Object[]{});
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取没有授予的权限
     * @param object Activity or Fragment
     * @param requestPermissions
     * @return 没有授予过的权限
     */
    public static List<String> getDeniedPermissions(Object object, String[] requestPermissions) {
        List<String> deniedPermissions = new ArrayList<>();
        for (String requestPermission : requestPermissions) {
//            把没有授予的权限加入到集合
            if(ContextCompat.checkSelfPermission(getActivity(object),requestPermission)
                    == PackageManager.PERMISSION_DENIED){
                deniedPermissions.add(requestPermission);
            }
        }
        return deniedPermissions;
    }

    /**
     * 获取Activity
     * @param object
     * @return
     */
    public static Activity getActivity(Object object) {
        if(object instanceof  Activity)
            return (Activity) object;
        if(object instanceof Fragment)
            return ((Fragment) object).getActivity();
        return null;
    }

    /**
     * 执行获取权限失败的方法
     * @param object
     * @param requestCode
     */
    public static void executeFailedMethod(Object object, int requestCode) {
//        获取Class中所有的方法
        Method[] methods = object.getClass().getDeclaredMethods();

//        遍历找到打了标记的方法
        for (Method method : methods) {
//            获取该方法上面有没有失败回调的标记
            PermissionFailed permissionFailed = method.getAnnotation(PermissionFailed.class);
            if (permissionFailed!=null){
                //代表该方法打了标记
//                并且我们的请求码必须和requestCode一样
                int methodCode = permissionFailed.requestCode();
                if(methodCode ==requestCode){
//                    这个就是我们要找的成功方法
//                    反射执行该方法
                    executeMethod(object,method);
                }

            }
        }

    }
}
