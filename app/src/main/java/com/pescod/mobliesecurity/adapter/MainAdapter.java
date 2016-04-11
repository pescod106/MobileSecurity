package com.pescod.mobliesecurity.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

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
    private static final int[] icons = {R.drawable.antitheft,R.drawable.communicationguard,
        R.drawable.softmanage,R.drawable.progress,R.drawable.data,R.drawable.kill,
        R.drawable.system,R.drawable.tool,R.drawable.setting};
    //将9个item的每一个标题都存放到数组
    private static final String[] names = {"手机防盗","通信卫士","软件管理","进程管理",
            "流量统计","手机杀毒","系统优化","高级工具","设置中心"};
    //用于替换“手机防盗”的新标题
    private String name;
    public MainAdapter(Context context){
        this.context = context;
        //获得系统中的布局填充器
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        SharedPreferences sp = context.getSharedPreferences("config",Context.MODE_PRIVATE);
        name = sp.getString("title","");
    }

    @Override
    public int getCount() {
        return names.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.main_item,null);
        TextView tv_name =(TextView)view.findViewById(R.id.tv_main_item_name);
        ImageView iv_icon = (ImageView)view.findViewById(R.id.iv_main_item_icon);
        tv_name.setText(names[position]);
        iv_icon.setImageResource(icons[position]);
        if (position==0){
            //判断sp中取出的name是否为空，如果不为空，将“手机防盗”对应的标题修改为sp中修改的标题
            if (!TextUtils.isEmpty(name)){
                tv_name.setText(name);
            }
        }
        return view;
    }
}
