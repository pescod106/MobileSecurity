package com.pescod.mobliesecurity.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.pescod.mobliesecurity.R;
import com.pescod.mobliesecurity.adapter.MainAdapter;

/**
 * Created by pescod on 4/5/2016.
 */
public class MainActivity extends Activity {
    private GridView gv_main;//main.xml中的GridView控件
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        gv_main = (GridView)findViewById(R.id.gv_main);
        gv_main.setAdapter(new MainAdapter(this));
        
        //为GridView对象的item设置单击时的监听事件
        gv_main.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /**
             * @param parent item的父控件
             * @param view 当前单击的item
             * @param position 当前单击的item在GridView中的位置
             * @param id 单击GridView的那一项对应的数值
             */
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 8:
                        Intent intent = new Intent(MainActivity.this,
                                SettingCenterActivity.class);
                        startActivity(intent);
                        break;
                }
            }
        });
        
    }
}
