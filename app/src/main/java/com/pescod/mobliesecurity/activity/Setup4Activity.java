package com.pescod.mobliesecurity.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.pescod.mobliesecurity.R;

public class Setup4Activity extends Activity {
    private SharedPreferences sp;
    private CheckBox cb_setup4_protect;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setup4);
        
        sp = getSharedPreferences("confing",MODE_PRIVATE);
        cb_setup4_protect = (CheckBox)findViewById(R.id.cb_setup4_protect);
        
        boolean protecting = sp.getBoolean("protecting",false);
        cb_setup4_protect.setChecked(protecting);
        cb_setup4_protect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = sp.edit();
                if (isChecked){
                    editor.putBoolean("protecting",true);
                    cb_setup4_protect.setText("防盗保护已经开启");
                }else{
                    editor.putBoolean("protecting",false);
                    cb_setup4_protect.setText("防盗保护已经关闭");
                }
                editor.commit();
            }
        });
    }
    
    public void next(View view){
        if (!cb_setup4_protect.isChecked()){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("温馨提示");
            builder.setMessage("手机防盗极大的保护你的手机安全，强烈建议开启！");
            builder.setPositiveButton("开启", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    cb_setup4_protect.setChecked(true);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putBoolean("protecting",true);
                    editor.commit();
                }
            });
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putBoolean("protecting",false);
                    editor.commit();
                }
            });
            builder.create().show();
            return;
        }
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("protecting",true);
        editor.commit();
        Intent intent = new Intent(this,LostProtectedActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.tran_in,R.anim.tran_out);
    }
    
    public void pre(View view){
        Intent intent = new Intent(this,Setup3Activity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.alpha_in,R.anim.alpha_out);
    }
}
