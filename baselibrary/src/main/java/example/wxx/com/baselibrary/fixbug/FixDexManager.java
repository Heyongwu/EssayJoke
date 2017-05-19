package example.wxx.com.baselibrary.fixbug;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import dalvik.system.BaseDexClassLoader;
import example.wxx.com.baselibrary.utils.FileUtil;

/**
 * 作者：wengxingxia
 * 时间：2017/5/18 0018 17:08
 */

public class FixDexManager {
    private static final String TAG = "FixDexManager";

    private Context mContext;

    private File mDexDir;

    public FixDexManager(Context context) {
        this.mContext = context;
//      获取应用可以访问的dex目录
        this.mDexDir = context.getDir("odex", Context.MODE_PRIVATE);
    }

    /**
     * 修复dex包
     *
     * @param fixDexPath dex包的文件路径
     */
    public void fixDex(String fixDexPath) throws Exception {

//        1、获取下载好的补丁的 dexElement

//        1.1、移动到系统能够访问的dex目录下   ClassLoader
        File srcFile = new File(fixDexPath);
        if (!srcFile.exists()) {

            throw new FileNotFoundException(fixDexPath);
        }

        File destFile = new File(mDexDir, srcFile.getName());

        if (destFile.exists()) {
            Log.d(TAG, "patch [" + fixDexPath + "] has be loaded.");

            return;//存在的话，直接返回，不用覆盖，因为不同补丁文件，版本号不同
        }

        FileUtil.copyFile(srcFile, destFile);

//        1.2、ClassLoader读取fixDex路径 为什么加入集合，因为一启动就可能修复

        List<File> fixDexFiles = new ArrayList<>();
        fixDexFiles.add(destFile);

        fixDexFiles(fixDexFiles);//修复dex

    }

    /**
     * 把dexElements注入到classLoader中
     *
     * @param classLoader
     * @param dexElements
     */
    private void injectDexElements(ClassLoader classLoader, Object dexElements) throws Exception {
        // 1.先获取 pathList
        Field pathListField = BaseDexClassLoader.class.getDeclaredField("pathList");
        // IOC 熟悉反射
        pathListField.setAccessible(true);
        Object pathList = pathListField.get(classLoader);

        // 2. pathList里面的dexElements
        Field dexElementsField = pathList.getClass().getDeclaredField("dexElements");
        dexElementsField.setAccessible(true);

        dexElementsField.set(pathList, dexElements);
    }

    /**
     * 合并两个数组
     *
     * @param arrayLhs
     * @param arrayRhs
     * @return
     */
    private static Object combineArray(Object arrayLhs, Object arrayRhs) {
        Class<?> localClass = arrayLhs.getClass().getComponentType();
        int i = Array.getLength(arrayLhs);
        int j = i + Array.getLength(arrayRhs);
        Object result = Array.newInstance(localClass, j);
        for (int k = 0; k < j; ++k) {
            if (k < i) {
                Array.set(result, k, Array.get(arrayLhs, k));
            } else {
                Array.set(result, k, Array.get(arrayRhs, k - i));
            }
        }
        return result;
    }

    /**
     * 从classLoader中获取dexElements
     *
     * @param classLoader
     * @return
     */
    private Object getDexElementsByClassLoader(ClassLoader classLoader) throws Exception {
//      1、先获取pathList
        Field pathListField = BaseDexClassLoader.class.getDeclaredField("pathList");
        pathListField.setAccessible(true);
        Object pathList = pathListField.get(classLoader);

//      2、获取pathList里面的dexElements
        Field dexElementsField = pathList.getClass().getDeclaredField("dexElements");
        dexElementsField.setAccessible(true);

        return dexElementsField.get(pathList);
    }

    /**
     * 加载全部的修复包
     */
    public void loadFixDex() throws Exception {
        File[] dexFiles = mDexDir.listFiles();

        List<File> fixDexFiles = new ArrayList<>();

        for (File dexFile : dexFiles) {
            if (dexFile.getName().endsWith(".dex")) {
                fixDexFiles.add(dexFile);
            }
        }

        fixDexFiles(fixDexFiles);
    }

    /**
     * 修复dex
     *
     * @param fixDexFiles
     */
    private void fixDexFiles(List<File> fixDexFiles) throws Exception {
//        1、先获取已经运行的dexElement
        ClassLoader applicationClassLoader = mContext.getClassLoader();

        Object applicationDexElements = getDexElementsByClassLoader(applicationClassLoader);

//      修复
        File optimizedDirectory = new File(mDexDir, "odex");
        if (!optimizedDirectory.exists()) {
            optimizedDirectory.mkdirs();
        }
//        dexPath   dex路径
//        optimizedDirectory  解压路径
//        librarySearchPath   .so文件路径
//        parent  父classLoader
        for (File fixDexFile : fixDexFiles) {
            BaseDexClassLoader fixDexClassLoader = new BaseDexClassLoader(
                    fixDexFile.getAbsolutePath(),//dex路径，必须要在应用目录下的dex文件中
                    optimizedDirectory,
                    null,
                    applicationClassLoader
            );
            Object fixDexElements = getDexElementsByClassLoader(fixDexClassLoader);

//        3、把补丁的dexElement 插到 已经运行的 dexElement的最前面

//         applicationDexElements 数组 合并 fixDexElements 数组

            applicationDexElements = combineArray(fixDexElements, applicationDexElements);//合并完成

//            把合并的数组 注入 到原来的applicationClassLoader中

            injectDexElements(applicationClassLoader, applicationDexElements);

        }

    }
}
