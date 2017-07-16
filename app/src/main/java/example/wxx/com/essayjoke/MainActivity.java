package example.wxx.com.essayjoke;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import example.wxx.com.baselibrary.dialog.AlertDialog;
import example.wxx.com.baselibrary.exception.ExceptionCrashHandler;
import example.wxx.com.baselibrary.fixbug.FixDexManager;
import example.wxx.com.baselibrary.http.HttpUtils;
import example.wxx.com.baselibrary.ioc.OnClick;
import example.wxx.com.baselibrary.ioc.ViewById;
import example.wxx.com.essayjoke.model.DiscoverListResult;
import example.wxx.com.essayjoke.model.Person;
import example.wxx.com.framelibrary.BaseSkinActivity;
import example.wxx.com.framelibrary.DefaultNavigationBar;
import example.wxx.com.framelibrary.banner.BannerAdapter;
import example.wxx.com.framelibrary.banner.BannerView;
import example.wxx.com.framelibrary.db.DaoSupportFactory;
import example.wxx.com.framelibrary.db.IDaoSupport;
import example.wxx.com.framelibrary.http.HttpCallback;
import example.wxx.com.framelibrary.skin.SkinManager;

public class MainActivity extends BaseSkinActivity implements View.OnClickListener {
    public static final int PERMISSION = 1;

    private static final String TAG = "MainActivity";
    /****测试****/
    @ViewById(R.id.btnTest)
    private Button mBtnTest;
    /****换肤****/
//    @ViewById(R.id.tvTestSkin)
//    private Button mTvTestSkin;
    /****Button****/
    @ViewById(R.id.button)
    private Button mButton;
    //    @ViewById(R.id.rl)
//    private LinearLayout mRl;
    @ViewById(R.id.ivSkin)
    private ImageView mIvSkin;
    /****换肤****/
    @ViewById(R.id.btnSkin)
    private Button mBtnSkin;
    /****默认****/
    @ViewById(R.id.btnDefault)
    private Button mBtnDefault;
    /****跳转****/
    @ViewById(R.id.btnGoTo)
    private Button mBtnGoTo;
    @ViewById(R.id.activity_main)
    private LinearLayout mActivityMain;
    @ViewById(R.id.BV)
    private BannerView mBannerView;

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_main);
        Button btnTest = viewById(R.id.btnTest);
        btnTest.setOnClickListener(this);
        //Android 6.0需要获取危险权限
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE
            }, PERMISSION);
        }

    }

    @Override
    protected void initTitle() {
        new DefaultNavigationBar.Builder(this)
                .setTitle("自定义Navigation")
                .setRightText("发布")
                .setLeftIcon(R.drawable.btn_back)
                .setRightClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this, "5555", Toast.LENGTH_SHORT).show();
                    }
                }).builder();
    }

    @Override
    protected void initView() {
        initBanner();
    }

    @Override
    protected void initData() {
// 路径和参数是不能让别人反编译的，NDK -> .so  1.列表保存第一次，2.有些是保存最后所有
        HttpUtils.with(this).url("http://is.snssdk.com/2/essay/discovery/v3/")
                .cache(true)
                .addParam("iid", "6152551759")
                .addParam("aid", "7")
                .execute(new HttpCallback<DiscoverListResult>() {
                    @Override
                    public void onSuccess(DiscoverListResult result) {

                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });

//        1、为什么要用Factory  目前的数据是在 内存卡中，有时候我们需要放到 data/data/xxxxx/databases中
//           获取的Factory不一样 那么写入的位置 是可以不一样的
//        2、为什么要用接口，
//           面向接口编程 获取IDaoSupport 那么不需要关心实现 目前的实现是自己写的，方便以后使用第三方的

//        3、就是为了高扩展

        IDaoSupport<Person> daoSupport = DaoSupportFactory.getFactory().getDao(Person.class);
//        daoSupport.insert(new Person("Xander",23));

        daoSupport.delete(null, new String[]{});
        List<Person> persons = new ArrayList<>();
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            persons.add(new Person("Xander", 23 + i));
        }

        daoSupport.insertList(persons);
        long end = System.currentTimeMillis();

        Log.e(TAG, "time-->" + (end - start));
        List<Person> personList = daoSupport.querySupport().columns("age").selection("age>?").selectionArgs("26").query();
        for (Person person : personList) {
            Log.e(TAG, person.toString());
        }
//        upLoadCrashLog();//


//        aliFixBug(); //阿里热修复

//        fixDexBug();//自己定义的热修复

//        HttpUtils.with(this).url("http://is.snssdk.com/2/essay/discovery/v3/")// 路径 apk  参数都需要放到jni
//                .addParams("iid", "6152551759")
//                .exchangeEngine(new OkHttpEngine())  // 切换引擎
//                .addParams("aid", "7").execute(new HttpCallback<DiscoverListResult>() {
//            @Override
//            public void onPreExecute() {
//                super.onPreExecute();
////                加载进度条
//            }
//
//            @Override
//            public void onError(Exception e) {
////              取消进度条
//            }
//
//            @Override
//            public void onSuccess(final DiscoverListResult result) {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Toast.makeText(MainActivity.this, result.getData().getCategories().getName(), Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//        });


    }


    /***
     *  自己定义的修复方法
     */
    private void fixDexBug() {
        File fixFile = new File(Environment.getExternalStorageDirectory(), "fix.dex");

        if (fixFile.exists()) {
            FixDexManager fixDexManager = new FixDexManager(this);
            try {
                fixDexManager.fixDex(fixFile.getAbsolutePath());
                Toast.makeText(this, "修复成功", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "修复失败", Toast.LENGTH_SHORT).show();
            }
        }
    }


    /**
     * 将异常上传至服务器
     */
    private void upLoadCrashLog() {
        //        获取上次的崩溃信息

        File crashFile = ExceptionCrashHandler.getmInstance().getCrashFile();
        if (crashFile.exists()) {
//        上传至服务器，后面再实现，现在只是打印Log

            try {
                InputStreamReader fileReader = new InputStreamReader(new FileInputStream(crashFile));

                char[] buffer = new char[1024];
                int len = 0;
                while ((len = fileReader.read(buffer)) != -1) {
                    String str = new String(buffer, 0, len);
                    Log.e(TAG, str);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION:
                if (!(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    Toast.makeText(this, "没有权限许可", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    /**
     * 阿里热修复bug
     */
    private void aliFixBug() {


//        每次启动的时候，去后台获取差分包 fix.aptach 然后修复本地的bug

//        测试，直接获取本地内存卡里面的fix.aptach

        File fixFile = new File(Environment.getExternalStorageDirectory(), "fix.apatch");

        if (fixFile.exists()) {
//            修复bug
            try {
//              修复，立马生效，不需要重启
                BaseApplication.mPatchManager.addPatch(fixFile.getAbsolutePath());
                Toast.makeText(this, "修复成功", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "修复失败", Toast.LENGTH_SHORT).show();
            }

        }
    }


    @Override
    public void onClick(View v) {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setContentView(R.layout.detail_comment_dialog)
                .setText(R.id.submit_btn, "接收")
                .fullWidth()
                .fromBottom(true)
                .show();
        final EditText et = dialog.getView(R.id.comment_editor);


        dialog.setOnClickListener(R.id.account_icon_weibo, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, et.getText().toString().trim(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    /**
     * 换肤
     *
     * @param tvTestSkin
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
//    @OnClick(R.id.tvTestSkin)
    private void tvTestSkinClick(Button tvTestSkin) throws IllegalAccessException, InstantiationException {
        //        读取本地的一个  skin里面的资源
        try {
            Resources superRes = getResources();
//        创建AssetManager
            AssetManager asset = AssetManager.class.newInstance();

            final Method method = AssetManager.class.getDeclaredMethod("addAssetPath", String.class);
//            method.setAccessible(true);//如果是私有的
//            反射执行方法
            method.invoke(asset, Environment.getExternalStorageDirectory().getAbsolutePath()
                    + File.separator + "skin.skin");

            Resources resources = new Resources(asset, superRes.getDisplayMetrics(), superRes.getConfiguration());
//            获取资源
            int drawableId = resources.getIdentifier("image_src", "drawable", "example.wxx.com.skin");
            final Drawable drawable = resources.getDrawable(drawableId);
            mIvSkin.setImageDrawable(drawable);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @OnClick(R.id.btnSkin)
    private void btnSkinClick(Button btnSkin) {
        String skinPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "red.skin";
//        换肤
        int result = SkinManager.getInstance().loadSkin(skinPath);

    }

    @OnClick(R.id.btnDefault)
    private void btnDefaultClick(Button btnDefault) {
//        回复默认
        int result = SkinManager.getInstance().restoreDefault();

    }

    @OnClick(R.id.btnGoTo)
    private void btnGoToClick(Button btnGoTo) {
//        跳转
        startActivity(MainActivity.class);

    }


    private void initBanner() {
        final List<String> imagePaths = new ArrayList<>();
        imagePaths.add("http://img3.redocn.com/tupian/20150430/mantenghuawenmodianshiliangbeijing_3924704.jpg");
        imagePaths.add("http://pic6.huitu.com/res/20130116/84481_20130116142820494200_1.jpg");
        imagePaths.add("http://pic.58pic.com/58pic/13/85/85/73T58PIC9aj_1024.jpg");

        final List<String> descs = new ArrayList<>();
        descs.add("描述1");
        descs.add("描述2");
        descs.add("描述3");

        mBannerView.setAdapter(new BannerAdapter() {
            @Override
            public View getView(int position, View convertView) {
                ImageView bannerIv = null;
                if (convertView==null) {
                    bannerIv = new ImageView(MainActivity.this);
                    bannerIv.setScaleType(ImageView.ScaleType.FIT_XY);
                }else{
                   bannerIv = (ImageView) convertView;
                }
                String imagePath = "";
                if (position < imagePaths.size())
                    imagePath = imagePaths.get(position);

//                利用第三方工具加载图片 Glide
                Glide.with(MainActivity.this).load(imagePath)
                        .placeholder(R.mipmap.ic_launcher_round).into(bannerIv);
                return bannerIv;
            }

            @Override
            public int getCount() {
                return imagePaths.size();
            }

            @Override
            public String getBannerDesc(int position) {
                return descs.get(position);
            }
        });
//      开始滚动
        mBannerView.startRoll();
    }
}
