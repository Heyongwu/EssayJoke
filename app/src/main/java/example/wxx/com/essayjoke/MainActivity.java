package example.wxx.com.essayjoke;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import example.wxx.com.baselibrary.dialog.AlertDialog;
import example.wxx.com.baselibrary.exception.ExceptionCrashHandler;
import example.wxx.com.baselibrary.fixbug.FixDexManager;
import example.wxx.com.baselibrary.http.HttpUtils;
import example.wxx.com.baselibrary.ioc.ViewById;
import example.wxx.com.essayjoke.model.DiscoverListResult;
import example.wxx.com.framelibrary.BaseSkinActivity;
import example.wxx.com.framelibrary.DefaultNavigationBar;
import example.wxx.com.framelibrary.http.HttpCallback;

public class MainActivity extends BaseSkinActivity implements View.OnClickListener {
    public static final int PERMISSION = 1;

    private static final String TAG = "MainActivity";
    /****测试****/
    @ViewById(R.id.btnTest)
    private Button mBtnTest;

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

        /*IDaoSupport<Person> daoSupport = DaoSupportFactory.getFactory().getDao(Person.class);
//        daoSupport.insert(new Person("Xander",23));

        daoSupport.delete(null,null);
        List<Person> persons = new ArrayList<>();
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            persons.add(new Person("Xander",23+i));
        }

        daoSupport.insertList(persons);
        long end = System.currentTimeMillis();

        Log.e(TAG, "time-->" + (end - start));
        List<Person> personList = daoSupport.querySupport().columns("age").selection("age>?").selectionArgs("26").query();
        for (Person person : personList) {
            Log.e(TAG, person.toString());
        }*/
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
}
