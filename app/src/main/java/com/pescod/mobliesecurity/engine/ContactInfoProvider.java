package com.pescod.mobliesecurity.engine;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.pescod.mobliesecurity.domain.ContactInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pescod on 4/11/2016.
 */
public class ContactInfoProvider {
    private Context context;
    public ContactInfoProvider(Context context){
        this.context = context;
    }

    /**
     * 返回所有联系人的信息
     * @return
     */
    public List<ContactInfo> getContactInfos(){
        //将所有联系人存入该集合
        List<ContactInfo> infos = new ArrayList<ContactInfo>();
        //获取raw_contacts表所对应的Uri
        Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");
        //获取data表对应的Uri
        Uri dataUri = Uri.parse("content://com.android.contacts/data");
        //参数2：所要查询的列
        Cursor cursor = context.getContentResolver().query(uri,
                new String[]{"contact_id"},null,null,null);
        while(cursor.moveToNext()){
            //因为只需要查询一列数据---联系人的id
            String id = cursor.getString(0);
            //用于封装每个联系人的具体信息
            ContactInfo contactInfo = new ContactInfo();
            //得到id后，通过该id来查询data表中的联系人的具体数据
            Cursor dataCursor = context.getContentResolver().query(dataUri,
                    null,"raw_contact_id=?",new String[]{id},null);
            while(dataCursor.moveToNext()){
                if ("vnd.android.cursor.item/name".equals(
                        dataCursor.getString(dataCursor.getColumnIndex("mimetype")))){
                    contactInfo.setName(dataCursor.getString(dataCursor.getColumnIndex("data1")));
                }else if ("vnd.android.cursor.item/phone_v2".equals(
                        dataCursor.getString(dataCursor.getColumnIndex("mimetype")))){
                    contactInfo.setPhone(dataCursor.getString(dataCursor.getColumnIndex("data1")));
                }
            }
            infos.add(contactInfo);
            dataCursor.close();
        }
        cursor.close();
        return infos;
    }
}
