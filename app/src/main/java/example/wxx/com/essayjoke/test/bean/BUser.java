package example.wxx.com.essayjoke.test.bean;

import example.wxx.com.framelibrary.indexlib.IndexBar.bean.BaseIndexPinyinBean;

/**
 * 用户对象
 * @author wengxingxia
 * @time 2017/7/25 0025 16:19
 */
public class BUser extends BaseIndexPinyinBean {
    private String mName;
    private String mPhone;

    public BUser(String name, String phone) {
        mName = name;
        mPhone = phone;
    }

    @Override
    public String getTarget() {
        return mName;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String phone) {
        mPhone = phone;
    }
}
