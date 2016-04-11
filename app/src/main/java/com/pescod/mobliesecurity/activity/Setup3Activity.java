package com.pescod.mobliesecurity.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.pescod.mobliesecurity.R;

public class Setup3Activity extends Activity {
    private EditText et_setup3_number;
    private SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setup3);
        
        et_setup3_number = (EditText)findViewById(R.id.et_setup3_number);
        sp = getSharedPreferences("config",MODE_PRIVATE);
        String number = sp.getString("number","");
        et_setup3_number.setText(number);
    }
    
    public void selectContact(View view){
        Intent intent = new Intent(this,SelectContactActivity.class);
        //激活一个带返回值的Activity
        startActivityForResult(intent,0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data!=null){
            //获得返回的数据
            String number = data.getStringExtra("number");
            //将返回的数据显示到EditText中
            et_setup3_number.setText(number);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void next(View view){
        String number = et_setup3_number.getText().toString().trim();
        if (TextUtils.isEmpty(number)){
            Toast.makeText(this,"安全密码不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        //将EditText中的安全码持久化，也方便数据的回显
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("number",number);
        editor.commit();
        Intent intent = new Intent(this,Setup4Activity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.tran_in,R.anim.tran_out);
    }

    public void pre(View view){
        Intent intent = new Intent(this,Setup2Activity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.tran_in,R.anim.tran_out);
    }
}
