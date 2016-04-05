package com.pescod.mobliesecurity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.pescod.mobliesecurity.R;

/**
 * Created by pescod on 4/5/2016.
 */
public class MainAdapter extends BaseAdapter {
    //不急填充器
    private LayoutInflater inflater;
    //接受MainActivity传递过来的上下文对象
    private Context context;
    //将9个item的每一个图片对应的id都存到数组
    private static final int[] icons = {R.drawable.AntiTheft,R.drawable.CommunicationGuard,
        R.drawable.softManage,R.drawable.progress,R.drawable.data,R.drawable.kill,
        R.drawable.system,R.drawable.tool,R.drawable.setting};
    //将9个item的每一个标题都存放到数组
    private static final String[] names = {"手机防盗","通信卫士","软件管理","进程管理",
            "流量统计","手机杀毒","系统优化","高级工具","设置中心"};
    public MainAdapter(Context context){

    }
    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
