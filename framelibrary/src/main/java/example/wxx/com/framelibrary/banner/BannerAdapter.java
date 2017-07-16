package example.wxx.com.framelibrary.banner;

import android.view.View;

/**
 * 作者：wengxingxia
 * 时间：2017/7/11 0011 21:01
 */

public abstract class BannerAdapter {

    /**
     * 1、根据位置获取ViewPager里面的子View
     *
     * @param position
     * @param convertView
     * @return
     */
    public abstract View getView(int position, View convertView);

    /**
     * 获取轮播的数量
     *
     * @return
     */
    public abstract int getCount();

    /**
     * 根据位置获取广告位的描述
     *
     * @param position
     * @return
     */
    public String getBannerDesc(int position) {
        return "";
    }
}
