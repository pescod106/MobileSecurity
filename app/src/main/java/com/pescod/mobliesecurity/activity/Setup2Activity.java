package com.pescod.mobliesecurity.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.pescod.mobliesecurity.R;

public class Setup2Activity extends Activity implements View.OnClickListener {
    /**
     * 单击绑定sim卡的父控件，该控件中有两个子控件，获取该控件的目的
     * 在于设置单击事件，便于单击该控件中的任何一个控件都响应单击事件
     */
    private RelativeLayout rl_seup2_bind;
    //用于显示sim卡是否被绑定时的不同状态
    private ImageView iv_setup2_bind_status;
    //用于保存sim卡是否被绑定的信息，以便程序下次加载时使用
    private SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setup2);
        
        rl_seup2_bind = (RelativeLayout)findViewById(R.id.rl_setup2_bind);
        rl_seup2_bind.setOnClickListener(this);
        iv_setup2_bind_status = (ImageView)findViewById(R.id.iv_setup2_bind_status);
        
        sp = getSharedPreferences("config",MODE_PRIVATE);
        //初始化的逻辑，判断sim卡是否被绑定
        String simSeral = sp.getString("simserial","");
        if (TextUtils.isEmpty(simSeral)){
            iv_setup2_bind_status.setImageResource(R.drawable.switchoff);
        }else{
            iv_setup2_bind_status.setImageResource(R.drawable.switchon);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_setup2_bind:
                //判断当前sim卡的状态
                String simSerial = sp.getString("simserial","");
                if (TextUtils.isEmpty(simSerial)){
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("simserial",getSimSerial());
                    editor.commit();
                    iv_setup2_bind_status.setImageResource(R.drawable.switchon);
                }else{
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("simserial","");
                    editor.commit();
                    iv_setup2_bind_status.setImageResource(R.drawable.switchoff);
                }
                break;
        }
    }

    /**
     * 获得sim卡的串号
     * @return
     */
    private String getSimSerial(){
        TelephonyManager tm = (TelephonyManager)getSystemService(TELEPHONY_SERVICE);
        return tm.getSimSerialNumber();
    }
    
    public void next(View view){
        Intent intent = new Intent(this,Setup3Activity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.tran_in,R.anim.tran_out);
    }

    public void pre(View view){
        Intent intent = new Intent(this,Setup1Activity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.tran_in,R.anim.tran_out);
    }
}
