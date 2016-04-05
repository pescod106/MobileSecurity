package com.pescod.mobliesecurity.engine;

import android.util.Xml;

import com.pescod.mobliesecurity.domain.UpdateInfo;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by pescod on 4/4/2016.
 *解析XML数据
 */
public class UpdateInfoParser {
    /**
     * 获得升级信息
     * @param is XML文件的输入流
     * @return updateInfo对象
     * @throws XmlPullParserException
     * @throws IOException
     */
    public static UpdateInfo getUpdateInfo(InputStream is)
            throws XmlPullParserException,IOException {
        //获得一个Pull机械的实例
        XmlPullParser parser = Xml.newPullParser();
        //将要解析的文件传入
        parser.setInput(is,"UTF-8");
        //创建UpdateInfo实例，用于存放从XML里面得到的数据，最终将对象返回
        UpdateInfo info = new UpdateInfo();
        //获取当前触发的事件类型
        int type = parser.getEventType();
        //使用while循环，如果获得的事件码是文档结束，那么就停止解析
        while(type!=XmlPullParser.END_DOCUMENT){
            //判断当前元素是否是读者需要检索的元素
            if (type==XmlPullParser.START_TAG){
                if ("version".equals(parser.getName())){
                    //因为内容也相当于一个节点，所以获取文件内容时需要调用parser对象
                    //的nextText（）方法才可以得到内容
                    String version = parser.nextText();
                    info.setVersion(version);
                }else if ("description".equals(parser.getName())){
                    String description = parser.nextText();
                    info.setDescription(description);
                }else if ("apkurl".equals(parser.getName())){
                    String apkurl = parser.nextText();
                    info.setApkurl(apkurl);
                }
            }
            type = parser.next();
        }
        return info;
    }
}
