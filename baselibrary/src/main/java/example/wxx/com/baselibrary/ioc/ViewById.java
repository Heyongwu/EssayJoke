package example.wxx.com.baselibrary.ioc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * View注解的Annotation
 * 作者：wengxingxia
 * 时间：2017/5/12 0012 10:54
 */
//@Target(ElementType.FIELD)代表Annotation的位置，Field代表的是属性,TYPE类上，CONSTRUCTOR构造器上
@Target(ElementType.FIELD)
//@Retention(RetentionPolicy.CLASS)代表什么时候生效 CLASS编译时，RUNTIME运行时，SOURCE源码资源
@Retention(RetentionPolicy.RUNTIME)
public @interface ViewById {
    //@ViewById(R.id.xxxx)
    int value();

}
