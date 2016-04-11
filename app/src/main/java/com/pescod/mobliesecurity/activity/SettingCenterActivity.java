package com.pescod.mobliesecurity.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

import com.pescod.mobliesecurity.R;

/**
 * Created by pescod on 4/6/2016.
 */
public class SettingCenterActivity extends Activity {
    //用于存储自动更新是否开启的bool值
    private SharedPreferences sharedPreferences;
    //自动更新是否开启对应的TextView控件的显示文字
    private TextView tv_setting_autoupadte_status;
    //显示自动更新是否开启的勾选框
    private CheckBox cb_setting_autoupdate;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_center);
        
        //获取Sdcard下的config.xml文件，如果该文件不存在，那么将会自动创建该文件
        //文件的操作类型为私有类型
        sharedPreferences = getSharedPreferences("config",MODE_PRIVATE);
        cb_setting_autoupdate = (CheckBox)findViewById(R.id.cb_setting_autoupdate);
        tv_setting_autoupadte_status = (TextView)findViewById(R.id.tv_setting_autoupdate_status);
        
        //初始化自动更新的UI，默认状态下是开启的
        boolean autoUpdate = sharedPreferences.getBoolean("autoupdate",true);
        if (autoUpdate){
            tv_setting_autoupadte_status.setText("自动更新已经开启");
            cb_setting_autoupdate.setChecked(true);
        }else{
            tv_setting_autoupadte_status.setText("自动更新已经关闭");
            cb_setting_autoupdate.setChecked(false);
            tv_setting_autoupadte_status.setTextColor(Color.RED);
        }
        /**
         * 当CheckBox的状态改变时执行以下代码
         */
        cb_setting_autoupdate.setOnCheckedChangeListener(new OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //获取编辑器
                Editor editor = sharedPreferences.edit();
                editor.putBoolean("autoupdate",isChecked);
                editor.commit();
                if (isChecked){
                    tv_setting_autoupadte_status.setTextColor(Color.BLACK);
                    tv_setting_autoupadte_status.setText("自动更新已经开启");
                }else{
                    tv_setting_autoupadte_status.setText("自动更新已经关闭");
                    tv_setting_autoupadte_status.setTextColor(Color.RED);
                }
            }
        });
    }
}
