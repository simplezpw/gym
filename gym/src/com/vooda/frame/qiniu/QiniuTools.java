package com.vooda.frame.qiniu;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import com.qiniu.http.Response;
import com.qiniu.processing.OperationManager;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.persistent.FileRecorder;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import com.vooda.frame.common.PageData;
import com.vooda.frame.util.HttpInvoker;
import com.vooda.frame.util.PropertiesUtil;
import com.vooda.frame.util.StringUtils;

public class QiniuTools {
	
    private static final Logger logger = Logger.getLogger(QiniuTools.class);
    
    public String operimgsrc(String content, boolean istrim){
        if(StringUtils.isEmpty(content)) return content;
        Matcher m = Pattern.compile("<img[^>]+src=\"?(.*?)(\"|>|\\s+)").matcher(content);
        while(m.find()){
            if(istrim)
                content = content.replace(m.group(1), trimparam(m.group(1)));
            else
                content = content.replace(m.group(1), packetDownloadUrl(m.group(1))); 
        } 
        return content;
    }
    
    public static String trimparam(String url){
        if(!StringUtils.isEmpty(url) && url.startsWith("http://" + PropertiesUtil.getValue("QINIU_UP_URL"))){
            url = url.substring(0, url.indexOf("?"));
        }
        return url;
    }
    
    public static Auth getAuth(){
        return Auth.create(PropertiesUtil.getValue("QINIU_ACCESS_KEY"), PropertiesUtil.getValue("QINIU_SECRET_KEY"));
    }
    
    public boolean isHttpUrl(String url){
        return !StringUtils.isEmpty(url) && url.startsWith("http://");
    }
    
    public String packetkey(String key, boolean ispacketdown){
        if(StringUtils.isEmpty(key)) return key;
        String keyurl = "http://" + PropertiesUtil.getValue("QINIU_UP_URL") + "/" + key;
        if(ispacketdown)
            return packetDownloadUrl(keyurl);
        else
            return keyurl;
    }
    
    public static String packetDownloadUrl(String url){
        if(!StringUtils.isEmpty(url) && url.startsWith("http://" + PropertiesUtil.getValue("QINIU_UP_URL"))){
            url = getAuth().privateDownloadUrl(url);
        }
        return url;
    }
    
    public static String getToken(){
        Auth auth = getAuth();
        if(auth != null)
            return auth.uploadToken(PropertiesUtil.getValue("QINIU_PUBLIC_NAME"), null, Long.parseLong(PropertiesUtil.getValue("QINIU_AUTH_TIME")), null);
        return "";
    }
    
    public static String getTokenByName(String bucket){
        Auth auth = getAuth();
        if(auth != null)
            return auth.uploadToken(bucket, null, Long.parseLong(PropertiesUtil.getValue("QINIU_AUTH_TIME")), null);
        return "";
    }
    
    public static PageData upload(byte[] fileBytes,
            String filename) throws Exception {
        PageData json = new PageData();
        if(fileBytes == null){
            json.put("statu", "error");
            json.put("msg", "no file");
            return json;
        }
        /*String filename = this.get32UUID();
        String temp = this.getRealPath() + "temp";*/
        UploadManager upm = new UploadManager(new FileRecorder(PropertiesUtil.getValue("QINIU_TEMP_PATH")));
//        UploadManager upm = new UploadManager();
        Response res = upm.put(fileBytes, filename, getToken());
        if(res.isOK()){
            json.put("statu", "success");
            String surl = PropertiesUtil.getValue("QINIU_PUBLIC_URL") + "/" + filename;
            json.put("preurl", packetDownloadUrl(surl));
            json.put("url", surl);
        }else{
            json.put("statu", "error");
            json.put("msg", res.error);
        }
        return json;
    }
    
    /**
     * 获取token
     * @param url
     * @return
     */
    public String getToken(String url){
        Auth auth = getAuth();
        if(auth != null)
            return auth.uploadToken(PropertiesUtil.getValue("QINIU_BUCKET_NAME"), null, Long.parseLong(PropertiesUtil.getValue("QINIU_AUTH_TIME")), new StringMap().put("returnUrl", url));
        return "";
    }
    
    /**
     * 删除资源 
     * @param resource 文件名
     * @throws Exception
     */
    public void deleteResource(String resource) throws Exception{
        if(StringUtils.isEmpty(resource)) return;
        new BucketManager(getAuth()).delete(PropertiesUtil.getValue("QINIU_BUCKET_NAME"), resouecePacket(resource));
    }
    
    /**
     * 文件链接截取出文件名
     * @param fileurl
     * @return
     */
    public String resouecePacket(String fileurl){
        if(StringUtils.isEmpty(fileurl)) return "";
        if(fileurl.startsWith("http://")){
            String upurl = PropertiesUtil.getValue("QINIU_UP_URL");
            fileurl = fileurl.substring(fileurl.indexOf(upurl) + upurl.length() + 1);
        }
        return fileurl;
    }
    
    /**
     * 视频截取
     * @param filename
     * @param fops
     * @return
     * @throws Exception
     */
    public PageData fopsFile(String filename, PageData fops) throws Exception{
        logger.error("视频截取开始-------------------------");
        if(StringUtils.isEmpty(filename)) return null;
        if(fops.isEmpty() || !fops.containsKey("id")) return null;
        String goodid = fops.getString("id");
        logger.error("商品id------------------" +  goodid);
        OperationManager opm = new OperationManager(getAuth());
        StringBuilder options = new StringBuilder();
        String option = "avthumb/mp4";
        options.append(option);
        boolean isContent = fops.containsKey("isContent");
        if(fops.containsKey("pretime")) options.append("/t/").append(fops.getStringOf("pretime"));
        if(fops.containsKey("cutstime")) options.append("/ss/").append(fops.getStringOf("cutstime"));
        StringMap params = new StringMap().putNotEmpty("notifyURL", PropertiesUtil.getValue("QINIU_CALLBACK_URL").
                replace("{1}", goodid).replace("{2}", options.toString()).
                replace("{3}", isContent ? resouecePacket(filename) : "")).putWhen("force", 1, true);
        return updatePreContent(opm.pfop(PropertiesUtil.getValue("QINIU_BUCKET_NAME"), resouecePacket(filename),
                options.toString(), params), options.toString(), isContent);
    }
    
    /**
     * 查询视频文件名称
     * @param persistent
     * @param cmd
     * @return
     * @throws Exception
     */
    private PageData updatePreContent(String persistent, String cmd, boolean isContent) throws Exception{
        PageData pd = new PageData();
        pd.put("code", "1");
        if(StringUtils.isEmpty(persistent) || StringUtils.isEmpty(cmd)) return null;
        logger.error("七牛持久化处理id------------------" +  persistent);
        String url = PropertiesUtil.getValue("persistent") + persistent;
        String ret = HttpInvoker.sendGetRequest(url);
        logger.error("七牛处理返回------------------" +  ret);
        if(StringUtils.isEmpty(ret)) return null;
        JSONObject json = JSONObject.fromObject(ret);
        if(json.containsKey("code"))
            pd.put("code", json.get("code"));
        else
            return pd;
        JSONArray items = json.getJSONArray("items");
        JSONObject jsonitem = null;
        for(Object item : items){
            jsonitem = JSONObject.fromObject(item);
            if(cmd.equals(jsonitem.getString("cmd")) && jsonitem.containsKey("code") && jsonitem.getInt("code") == 0){
                pd.put(isContent ? "content" : "precontent", jsonitem.getString("key")); 
                break;
            }
        }
        logger.error("返回数据------------------" + pd);
        return pd;
    }
    
    
    
   public static void main(String[] args) throws Exception{
       
   }
}