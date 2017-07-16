package example.wxx.com.framelibrary.skin;

/**
 * 皮肤管理类
 * 作者：wengxingxia
 * 时间：2017/6/27 0027 21:05
 */

public class SkinManager {

    private static SkinManager mInstance;

    static {
        mInstance = new SkinManager();
    }

    public static SkinManager getInstance() {
        return mInstance;
    }

    /**
     * 加载皮肤
     * @param skinPath
     * @return
     */
    public int loadSkin(String skinPath) {
//        校验签名 增量更新

//        初始化资源管理
//        SkinResource skinResource = new SkinResource(skinPath);


        return 0;
    }

    /**
     * 恢复默认
     * @return
     */
    public int restoreDefault() {
//

        return 0;
    }
}
