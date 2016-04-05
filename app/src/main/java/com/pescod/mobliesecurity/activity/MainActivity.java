package com.pescod.mobliesecurity.activity;

import android.app.Activity;
import android.os.Bundle;
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
    }
}
