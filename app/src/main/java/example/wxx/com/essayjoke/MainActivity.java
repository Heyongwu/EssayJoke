package example.wxx.com.essayjoke;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import example.wxx.com.baselibrary.ExceptionCrashHandler;
import example.wxx.com.baselibrary.ioc.ViewById;
import example.wxx.com.framelibrary.BaseSkinActivity;

public class MainActivity extends BaseSkinActivity {
    public static final int PERMISSION = 1;

    private static final String TAG = "MainActivity";
    /****测试****/
    @ViewById(R.id.btnTest)
    private Button mBtnTest;

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_main);
        Button btnTest = viewById(R.id.btnTest);
        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, 2 / 1 + "测试", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

//        获取上次的崩溃信息

        File crashFile = ExceptionCrashHandler.getmInstance().getCrashFile();
        if (crashFile.exists()) {
//        上传至服务器，后面再实现，现在只是打印Log

//            try {
//                InputStreamReader fileReader = new InputStreamReader(new FileInputStream(crashFile));
//
//                char[] buffer = new char[1024];
//                int len = 0;
//                while((len = fileReader.read(buffer))!=-1){
//                    String str = new String(buffer, 0, len);
//                    Log.e(TAG, str );
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
        }

//        每次启动的时候，去后台获取差分包 fix.aptach 然后修复本地的bug

//        测试，直接获取本地内存卡里面的fix.aptach
        //Android 6.0需要获取危险权限
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE
            }, PERMISSION);
        } else {

            fixApk();
        }
    }

    /**
     * 热修复apk
     */
    private void fixApk() {
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    fixApk();
                } else {
                    Toast.makeText(this, "没有权限许可", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
