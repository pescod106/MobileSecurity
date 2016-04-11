package com.pescod.mobliesecurity.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.pescod.mobliesecurity.R;
import com.pescod.mobliesecurity.utils.Md5Encoder;

/**
 * Created by pescod on 4/7/2016.
 */
public class LostProtectedActivity extends Activity implements View.OnClickListener{
    //偏好设置存储对象
    private SharedPreferences sp;
    //第一次进入手机防盗界面时的界面控件对象
    private EditText et_first_dialog_pwd;
    private EditText et_first_dialog_pwd_confirm;
    private Button bt_first_dialog_ok;
    private Button bt_first_dialog_cancle;
    //第2次进入手机防盗界面时的界面控件对象
    private EditText et_normal_dialog_pwd;
    private Button bt_normal_dialog_ok;
    private Button bt_normal_dialog_cancle;
    //修改手机防盗标题界面时的控件对象
    private EditText et_change_lost_pritected_title;
    private Button bt_change_title_ok;
    private Button bt_change_title_cancle;
    //对话框对象
    private AlertDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //获取Sdcard下的config.xml文件，如果该文件不存在，那么会自动创建
        sp = getSharedPreferences("config",MODE_PRIVATE);
        //判断用户是否设置过密码
        if (isSetupPwd()){
            //进入非第一次进入手机防盗是要显示的对话框
            showNormalEntryDialog();
        }else{
            //进入第一次进入手机防盗要显示的对话框
            showFirstEntryDialog();
        }
    }

    /**
     * 判断用户是否设置过密码
     * @return
     */
    private boolean isSetupPwd(){
        String savedpwd = sp.getString("password","");
        if (TextUtils.isEmpty(savedpwd)){
            return false;
        }else{
            return true;
        }
    }

    /**
     * 第一次进入手机防盗时要显示的对话框
     */
    private void showFirstEntryDialog(){
        //得到对话框的构造器
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //将第一次进入手机防盗要弹出的窗体的布局文件转化为一个View对象
        View view = View.inflate(this,R.layout.first_entry_dialog,null);
        et_first_dialog_pwd = (EditText)view.findViewById(R.id.et_first_dialog_pwd);
        et_first_dialog_pwd_confirm = (EditText)view.findViewById(R.id.et_first_dialog_pwd_confirm);
        bt_first_dialog_ok = (Button)view.findViewById(R.id.bt_first_dialog_ok);
        bt_first_dialog_cancle = (Button)view.findViewById(R.id.bt_first_dialog_cancle);
        
        //分别为“取消”“确定”设置一个监听器
        bt_first_dialog_ok.setOnClickListener(this);
        bt_first_dialog_cancle.setOnClickListener(this);
        
        //将上面的View对象设置到对话框上
        builder.setView(view);
        dialog = builder.create();
        dialog.show();
    }

    /**
     * 当设置过密码后，正常进入手机防盗是要显示的对话框
     */
    private void showNormalEntryDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            //当单击取消后，直接结束当前的LostProtectedActivity，进入主界面
            @Override
            public void onCancel(DialogInterface dialog) {
                finish();
            }
        });
        //将非第一次进入手机防盗要弹出的窗体的布局文件转化为一个View对象
        View view = View.inflate(this,R.layout.normal_entry_dialog,null);
        //查找view对象中的各个控件
        et_normal_dialog_pwd = (EditText) view.findViewById(R.id.et_normal_dialog_pwd);
        bt_normal_dialog_ok = (Button)view.findViewById(R.id.bt_normal_dialog_ok);
        bt_normal_dialog_cancle = (Button)view.findViewById(R.id.bt_normal_dialog_cancle);
        //分别为取消、确定按钮设置一个监听器
        bt_normal_dialog_ok.setOnClickListener(this);
        bt_normal_dialog_cancle.setOnClickListener(this);
        //将上面的View对象添加到对话框shang
        builder.setView(view);
        dialog = builder.create();
        dialog.show();
    }
    
    private void showChangeTitleDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        
        View view = View.inflate(this,R.layout.change_lost_protected_title_dialog,null); 
        //查找iew对象中的各个控件
        et_change_lost_pritected_title = (EditText)view.findViewById(
                R.id.et_change_lost_pritected_title);
        bt_change_title_ok = (Button)view.findViewById(R.id.bt_change_title_ok);
        bt_change_title_cancle = (Button)view.findViewById(R.id.bt_change_title_cancle);
        bt_change_title_ok.setOnClickListener(this);
        bt_change_title_cancle.setOnClickListener(this);
        builder.setView(view);
        dialog = builder.create();
        dialog.show();
    }
    
    
    /**
     * 为两个对话框中的“确定”、“取消”按钮设置监听器
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_first_dialog_cancle:
                dialog.cancel();//取消对话框
                finish();//结束当前Activity后进入程序的主界面
                break;
            case R.id.bt_first_dialog_ok:
                //获取到两个TextView中的输入密码，并将前后空格去掉
                String pwd = et_first_dialog_pwd.getText().toString().trim();
                String pwd_confirm = et_first_dialog_pwd_confirm.getText().toString().trim();
                if (TextUtils.isEmpty(pwd)||TextUtils.isEmpty(pwd_confirm)){
                    Toast.makeText(this,"密码不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (pwd.equals(pwd_confirm)){
                    //获取到一个编辑器对象，此处用于向sp中编辑数据
                    SharedPreferences.Editor editor = sp.edit();
                    //将加密后的密码放入sp对应的文件中
                    editor.putString("password", Md5Encoder.encode(pwd));
                    //将编辑的内容提交后才真正的存入sp中
                    editor.commit();
                    //结束当前的Activity后，跳转至主界面
                    dialog.dismiss();
                    Intent intent = new Intent(LostProtectedActivity.this,Setup1Activity.class);
                    startActivity(intent);
                    finish();
                    return;
                }else{
                    Toast.makeText(this,"两次输入的密码不一致",Toast.LENGTH_SHORT).show();
                    et_first_dialog_pwd.setText("");
                    et_first_dialog_pwd_confirm.setText("");
                    return;
                }
            case R.id.bt_normal_dialog_cancle:
                dialog.cancel();
                finish();
                break;
            case R.id.bt_normal_dialog_ok:
                String userenttrypws = et_normal_dialog_pwd.getText().toString();
                if (TextUtils.isEmpty(userenttrypws)){
                    Toast.makeText(this,"密码不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                String savePwd = sp.getString("password","");
                //因为设置密码后，存入的是加密后的密码，所以当我们将输入的密码与设置的密码
                //比较时需要将输入的密码先加密
                if (savePwd.equals(Md5Encoder.encode(userenttrypws))){
                    Toast.makeText(this,"密码正确",Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    //加载主机面
                    Intent intent = new Intent(LostProtectedActivity.this,Setup1Activity.class);
                    startActivity(intent);
                    finish();
                    return;
                }else{
                    Toast.makeText(this,"密码不正确", Toast.LENGTH_SHORT).show();
                    et_normal_dialog_pwd.setText("");
                    return;
                }
            case R.id.bt_change_title_ok:
                String  title = et_change_lost_pritected_title.getText().toString();
                if (!TextUtils.isEmpty(title)){
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("title",title);
                    editor.commit();
                    dialog.dismiss();
                }else{
                    Toast.makeText(this, "标题不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.bt_change_title_cancle:
                dialog.dismiss();
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0,0,0,"修改手机防盗标题");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getGroupId()){
            case 0:
                showChangeTitleDialog();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
