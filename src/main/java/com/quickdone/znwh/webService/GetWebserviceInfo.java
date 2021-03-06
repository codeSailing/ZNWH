package com.quickdone.znwh.webService;

import com.quickdone.znwh.utils.HttpClient4Util;
import com.quickdone.znwh.utils.WebServiceConfigUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


//外部接口调用
public class GetWebserviceInfo {

    /**
     * 调用语义接口
     * @param content 客户回答后识别的文本   iss_uid: anything
     * @return
     */
    public static String getIntelligentCall(String content,Long iss_uid){
        String url = WebServiceConfigUtils.getProperty("GET_INTELLIGENTCALL") + "?content=" +  content+"&iss_uid="+iss_uid;
        String result = HttpClient4Util.doGet(url, "UTF-8");
//        JSONObject jsonObject = JSONObject.fromObject(result);
        return result;
    }

//    /**
//     * 根据账号获取用户基本信息
//     * @param accountName
//     * @return
//     */
//    public static JSONObject getUserInfoByAccount(String accountName) {
//        String url = WebServiceConfigUtils.getProperty("GET_USERINFO") + "?accountName=" + accountName;
//        String result = HttpClient4Util.doGet(url, "UTF-8");
//        JSONObject jsonObject = JSONObject.fromObject(result);
//        return jsonObject;
//    }

    /**
     * 登录接口
     * @param accountName
     * @param password
     * @return
     */
    public static JSONObject getUserRoleInfo(String accountName,String password){
        String url = WebServiceConfigUtils.getProperty("GET_USERROLEINFO") + "/" +  accountName+"/"+password;
        String result = HttpClient4Util.doGet(url, "UTF-8");
        JSONObject jsonObject = JSONObject.fromObject(result);
        return jsonObject;
    }

    /**
     * 根据账号和系统路径获取用户的菜单权限
     * @param accountName
     * @param contextPath
     * @return
     * @throws Exception
     */
    public static JSONArray getAllResources(String accountName,String contextPath) throws Exception{
        CloseableHttpClient client = HttpClients.createDefault();
        String url=WebServiceConfigUtils.getProperty("GET_ALLRESOURCES");
        HttpGet post = new HttpGet(url);
        post.setHeader("Content-Type", "application/json;charset=UTF-8");
        post.setHeader("accountName", accountName);
        post.setHeader("contextPath", contextPath);
        post.setHeader("resourcePId", "");
      //  StringEntity entity = new StringEntity("json", "utf-8");
        CloseableHttpResponse response = client.execute(post);

        String result = EntityUtils.toString(response.getEntity());
        JSONObject jsonObject=JSONObject.fromObject(result);
        JSONArray jsonArray=jsonObject.getJSONArray("data");

        return jsonArray;
    }

    /**
     * 获取二级菜单
     * @param accountName
     * @param contextPath
     * @param resourcePId
     * @return
     * @throws Exception
     */
    public static JSONArray getResources(String accountName,String contextPath,String resourcePId)throws Exception{
        CloseableHttpClient client = HttpClients.createDefault();
        String url=WebServiceConfigUtils.getProperty("GET_ALLRESOURCES");
        HttpGet post = new HttpGet(url);
        post.setHeader("Content-Type", "application/json;charset=UTF-8");
        post.setHeader("accountName", accountName);
        post.setHeader("contextPath", contextPath);
        post.setHeader("resourcePId", resourcePId);
        //  StringEntity entity = new StringEntity("json", "utf-8");
        CloseableHttpResponse response = client.execute(post);

        String result = EntityUtils.toString(response.getEntity());
        JSONObject jsonObject=JSONObject.fromObject(result);
        JSONArray jsonArray=null;
        if(jsonObject.size()>1){
             jsonArray=jsonObject.getJSONArray("data");
        }

        return jsonArray;
    }
}

