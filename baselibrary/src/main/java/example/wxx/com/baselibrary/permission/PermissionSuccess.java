package example.wxx.com.baselibrary.permission;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 作者：wengxingxia
 * 时间：2017/6/18 0018 01:13
 */
@Target(ElementType.METHOD)//放在什么位置，方法上面
@Retention(RetentionPolicy.RUNTIME)//是编译时检测，还是运行时检测
public @interface PermissionSuccess {


    int requestCode();//请求码
}
