package com.pescod.mobliesecurity.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.pescod.mobliesecurity.R;

/**
 * Created by pescod on 4/11/2016.
 */
public class Setup1Activity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setup1);
    }
    
    public void next(View view){
        Intent intent = new Intent(this,Setup2Activity.class);
        startActivity(intent);
        finish();
        /**
         * Activity切换时播放的动画，overridePendingTransition
         * 必须紧挨startActivity() or finish()
         * 参数1：界面进入时的动画效果
         * 参数2：界面出去时的动画效果
         */
        overridePendingTransition(R.anim.alpha_out,R.anim.alpha_out);
    }
}
