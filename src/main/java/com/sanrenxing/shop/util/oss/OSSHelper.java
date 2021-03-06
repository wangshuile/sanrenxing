package com.sanrenxing.shop.util.oss;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 七牛云存储
 * Created on 2017/2/8
 * @author tony
 */
public class OSSHelper {

    String domainOfBucket = "http://p6n4s9kik.bkt.clouddn.com";

    private Auth auth;

    private UploadManager uploadManager;

    private String upToken;

    private final Logger logger = LoggerFactory.getLogger(OSSHelper.class);

    public OSSHelper(String accessKeyId, String accessKeySecret , String bucket) {
        auth = Auth.create(accessKeyId, accessKeySecret);
        String upToken = auth.uploadToken(bucket);
        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.zone0());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        this.uploadManager = uploadManager;
        this.upToken = upToken;
    }

    //获取私有空间链接
    public String privateUrl(String key) {
        return auth.privateDownloadUrl(key);
    }

    //获取私有空间链接
    public String publicUrl(String key) {

        String encodedFileName = null;
        try {
            encodedFileName = URLEncoder.encode(key, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            logger.error("获取私有空间失败！！！");
        }
        return String.format("%s/%s", domainOfBucket, encodedFileName);
    }

    //上传文件
    public boolean putObject(String key, InputStream inputStream) {
        try {
            Response response = uploadManager.put(inputStream ,key, upToken, null, null);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
        } catch (QiniuException ex) {
            Response r = ex.response;
            logger.error("上传文件失败！！！ 返回内容为：", r.toString());
        }
        return true;
    }

}
