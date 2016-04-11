package com.pescod.mobliesecurity.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.pescod.mobliesecurity.R;
import com.pescod.mobliesecurity.domain.ContactInfo;
import com.pescod.mobliesecurity.engine.ContactInfoProvider;

import java.util.List;

/**
 * Created by pescod on 4/11/2016.
 */
public class SelectContactActivity extends Activity {
    private ListView lv_seletc_contact;//用于展现联系人列表
    private ContactInfoProvider provider;//获取手机联系人对象
    private List<ContactInfo> infos;//接受获取到的所有联系人
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_contact);
        
        lv_seletc_contact = (ListView)findViewById(R.id.lv_select_contact);
        provider = new ContactInfoProvider(this);
        infos = provider.getContactInfos();
        //为lv_seletc_contact设置一个数据适配器，用于将所有联系人展现到界面
        lv_seletc_contact.setAdapter(new ContactAdapter());
        //为lv_seletc_contact中的item设置监听
        lv_seletc_contact.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //获取到单击item对应的联系人的信息对象
                ContactInfo info = (ContactInfo)lv_seletc_contact.getItemAtPosition(position);
                //获取到该联系人的号码
                String number = info.getPhone();
                //将该联系人的号码返回给激活当前Activity的Activity
                Intent intent = new Intent();
                //将数据存入，用于返回给Activity
                intent.putExtra("number",number);
                setResult(0,intent);
                finish();
            }
        });
    }
    
    private class ContactAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return infos.size();
        }

        @Override
        public Object getItem(int position) {
            return infos.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            ContactInfo info = infos.get(position);
            TextView tv;
            if (convertView==null){
                tv = new TextView(getApplicationContext());
                holder = new ViewHolder();
                holder.textView = tv;
                tv.setTag(holder);
            }else{
                tv = (TextView)convertView;
                holder = (ViewHolder)convertView.getTag();
            }

            holder.textView.setTextSize(24);
            holder.textView.setText(info.getName()+"\n"+info.getPhone());
            return tv ;
        }
        
        class ViewHolder{
            TextView textView;
        }
    }
}
