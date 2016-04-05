package com.pescod.mobliesecurity.domain;

/**
 * Created by pescod on 4/4/2016.
 */
public class UpdateInfo {
    private String version;//服务器端的版本号
    private String description;//服务器端的升级提示
    private String apkurl;//服务器端的apk下载路径

    public void setVersion(String version) {
        this.version = version;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setApkurl(String apkurl) {
        this.apkurl = apkurl;
    }

    public String getVersion() {
        return version;
    }

    public String getDescription() {
        return description;
    }

    public String getApkurl() {
        return apkurl;
    }
}
