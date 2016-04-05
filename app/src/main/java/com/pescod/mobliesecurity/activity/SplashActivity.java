package com.pescod.mobliesecurity.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pescod.mobliesecurity.R;
import com.pescod.mobliesecurity.domain.UpdateInfo;
import com.pescod.mobliesecurity.engine.UpdateInfoParser;
import com.pescod.mobliesecurity.utils.DownloadUtil;

import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SplashActivity extends Activity {
    private UpdateInfo info;
    private static final int GET_INFO_SUCCESS = 10;
    private static final int SERVER_ERROR = 11;
    private static final int SERVER_URL_ERROR = 12;
    private static final int PROTOCOL_ERROR = 13;
    private static final int IO_ERROR = 14;
    private static final int XML_PARSER_ERROR = 15;
    private static final int DOWNLOAD_SUCCESS = 16;
    private static final int DOWNLOAD_ERROR = 17;
    protected static final String TAG = "SplashActivity";
    private ProgressDialog pd;
    private long startTime;
    private RelativeLayout rl_splash;
    private long endTime;
    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case GET_INFO_SUCCESS:
                    String serverVersion = info.getVersion();
                    String currentVersion = getVersion();
                    Log.i(TAG,"服务器版本为"+serverVersion+",本地版本号为"+currentVersion);
                    if (currentVersion.equals(serverVersion)){
                        Log.i(TAG,"版本号相同，进入主界面");
                        loadMainUI();
                    }else{
                        Log.i(TAG,"版本号不同，升级对话框");
                        showUpdateDialog();
                    }
                    break;
                case SERVER_ERROR:
                    Toast.makeText(getApplicationContext(),"服务器内部异常",Toast.LENGTH_SHORT).show();
                    break;
                case SERVER_URL_ERROR:
                    Toast.makeText(getApplicationContext(),"服务器路径不对",Toast.LENGTH_SHORT).show();
                    break;
                case PROTOCOL_ERROR:
                    Toast.makeText(getApplicationContext(),"协议不支持",Toast.LENGTH_SHORT).show();
                    break;
                case IO_ERROR:
                    Toast.makeText(getApplicationContext(),"I/O错误",Toast.LENGTH_SHORT).show();
                    break;
                case XML_PARSER_ERROR:
                    Toast.makeText(getApplicationContext(),"xml解析错误",Toast.LENGTH_SHORT).show();
                    break;
                case DOWNLOAD_SUCCESS:
                    Log.i(TAG,"文件下载成功");
                    //得到发送过来的消息中的文件对象
                    File file = (File)msg.obj;
                    //将对象传入，执行安装方法来安装该文件
                    installApk(file);
                    break;
                case DOWNLOAD_ERROR:
                    Toast.makeText(getApplicationContext(),"文件下载失败",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
    private TextView tv_splash_version;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置为全屏模式
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //设置为无标题
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_splash);
        rl_splash = (RelativeLayout)findViewById(R.id.rl_splash);
        tv_splash_version = (TextView)findViewById(R.id.tv_splash_version);
        tv_splash_version.setText("版本号:"+getVersion());

        //设置动画,透明度有0.3-1.0
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.3f,1.0f);
        //设置动画执行时间
        alphaAnimation.setDuration(2000);
        rl_splash.startAnimation(alphaAnimation);

        new Thread(new CheckVersionTask()){
        }.start();
    }

    /**
     * 获取当前应用程序的版本号
     * @return 当前应用程序的版本号
     */
    private String getVersion(){
        //得到系统的包管理器，已经得到了apk的面向对象的包装
        PackageManager pm = this.getPackageManager();
        try{
            //参数1：当前应用程序的包名；参数2：可选的附加信息，这里用不到，定义为0
            PackageInfo info = pm.getPackageInfo(getPackageName(),0);
            //返回当前应用程序的版本号
            return info.versionName;
        }catch (Exception e){
            //包名未找到，理论上不会发生
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 连接网络检查应用的版本号与服务器上的版本号是否一致
     */
    private class CheckVersionTask implements Runnable{
        public void run(){
            startTime = System.currentTimeMillis();
            //得到一个向主线程发送消息的消息对象
            Message msg = Message.obtain();
            try{
                //获取服务器端的配置信息的链接地址
                String serverUrl = getResources().getString(R.string.serverurl);
                Log.d(TAG,serverUrl);
                URL url = new URL(serverUrl);
                HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                connection.setRequestMethod("GET");
//                connection.setRequestProperty("Host","192.168.79.13:8080");
                connection.setConnectTimeout(5000);
                int code = connection.getResponseCode();
                if (code==200){//响应码为200，表示与服务器连接成功
                    Log.d(TAG,"-----------连接成功--------");
                    InputStream is = connection.getInputStream();
                    info = UpdateInfoParser.getUpdateInfo(is);
                    endTime = System.currentTimeMillis();
                    long resultTime = endTime - startTime;
                    if (resultTime<2000){
                        try{
                            Thread.sleep(2000-resultTime);
                        }catch (InterruptedException e){
                            e.printStackTrace();
                        }
                    }
                    msg.what = GET_INFO_SUCCESS;
                    handler.sendMessage(msg);
                }else{
                    //服务器状态错误
                    msg.what = SERVER_ERROR;
                    handler.sendMessage(msg);

                    endTime = System.currentTimeMillis();
                    long resultTime = endTime - startTime;
                    if (resultTime<2000){
                        try{
                            Thread.sleep(2000-resultTime);
                        }catch (InterruptedException e){
                            e.printStackTrace();
                        }
                    }
                }
            }catch(MalformedURLException e){
                msg.what = SERVER_URL_ERROR;
                handler.sendMessage(msg);
                e.printStackTrace();
            }catch (IOException e){
                msg.what = PROTOCOL_ERROR;
                handler.sendMessage(msg);
                e.printStackTrace();
            }catch (XmlPullParserException e){
                msg.what = XML_PARSER_ERROR;
                handler.sendMessage(msg);
                e.printStackTrace();
            }
        }
    }

    /**
     * 显示升级提示对话框
     */
    protected void showUpdateDialog(){
        //创建对话框的构造器
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //设置对话框提示标题左边的提示图标
        builder.setIcon(getResources().getDrawable(R.drawable.upgrade));
        //设置对话框标题
        builder.setTitle("升级提示");
        //设置对话框的提示内容
        builder.setMessage(info.getDescription());
        //创建下载进度条
        pd = new ProgressDialog(this);
        //设置进度条在显示时的提示信息
        pd.setMessage("正在下载...");
        //之地呢显示下载进度条为水平形状
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        //设置升级按钮
        builder.setPositiveButton("升级", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.i(TAG,"升级，下载"+info.getApkurl());
                //判断SDCard是否存在
                if (Environment.MEDIA_MOUNTED.equals(
                        Environment.getExternalStorageState())){
                    //显示下载进度条
                    pd.show();
                    //开启子线程下载apk
                    new Thread(){
                        @Override
                        public void run() {
                            //获取服务器端新版本apk的下载路径
                            String path = info.getApkurl();
                            //获取最新apk的文件名
                            String fileName = DownloadUtil.getFileName(path);
                            //在SDCard上创建一个文件
                            File file = new File(Environment.getExternalStorageDirectory(),fileName);
                            //得到下载后的apk文件
                            file = DownloadUtil.getFile(path,file.getAbsolutePath(),pd);
                            if (file!=null){
                                //向主线程发送下载成功的消息
                                Message msg = Message.obtain();
                                msg.what = DOWNLOAD_SUCCESS;
                                msg.obj = file;
                                handler.sendMessage(msg);
                            }else{
                                //向主线程发送下载失败的消息
                                Message msg = Message.obtain();
                                msg.what = DOWNLOAD_ERROR;
                                handler.sendMessage(msg);
                            }
                            //下载结束后，将下载的进度条关闭
                            pd.dismiss();
                        }
                    }.start();
                }else{
                    Toast.makeText(getApplicationContext(),"sd卡不可用",Toast.LENGTH_SHORT).show();
                    loadMainUI();
                }

            }
        });
        //设置取消按钮
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                loadMainUI();
            }
        });
        builder.create().show();
    }

    /**
     * 安装一个apk文件
     * @param file
     */
    protected void installApk(File file){
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        //为意图添加额外的数据
//        intent.setType("application/vnd.android.package-archive");
//        intent.setData(Uri.fromFile(file));
        //设置意图的数据和类型
        intent.setDataAndType(Uri.fromFile(file),"application/vnd.android.package-archive");
        startActivity(intent);
    }

    protected void loadMainUI(){
        Intent intent = new Intent(SplashActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }


}
