package com.quickdone.znwh.controller;

import com.quickdone.znwh.pojo.ResponseData;
import com.quickdone.znwh.utils.MD5Util;
import com.quickdone.znwh.vo.UserVo;
import com.quickdone.znwh.webService.GetWebserviceInfo;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class LoginController {
    private static Logger logger = LoggerFactory.getLogger(LoginController.class);
    @RequestMapping(value = "/login.do",method =  RequestMethod.GET)
    public String login(){
      logger.info("LoginController----"+"/login.do");
        return "login1";
    }

    /**
     * 登录
     * @param accountName
     * @param password
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/realLogin.do")
    @ResponseBody
    public ResponseData realLogin(String accountName,String password,HttpServletRequest request)throws Exception{
        HttpSession httpSession = request.getSession();
        String pwd= MD5Util.md5(password);
        JSONObject jsonObject= GetWebserviceInfo.getUserRoleInfo(accountName, pwd);
        if(jsonObject.get("status").toString().equals("true")){
            JSONObject jsonObject1=jsonObject.getJSONObject("data");
            UserVo userVo=(UserVo)JSONObject.toBean(jsonObject1,UserVo.class);
            httpSession.setAttribute("userVo",userVo);
            return ResponseData.getSuccessResponse("登录成功");
        }else{
            return ResponseData.getErrorResponse("登录失败");
        }

    }

    /**
     * 获取用户的菜单权限
     * @param accountName
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/loginSuccess.do")
    public ModelAndView loginSuccess(String accountName)throws Exception{
        ModelAndView mv =new ModelAndView("index");
        List<Map<String,String>> list=new ArrayList<Map<String,String>>();
        List<Map<String,String>> list1=new ArrayList<Map<String,String>>();
        //StringBuilder stringBuilder=new StringBuilder();
        String contextPath="/znwh";//系统路径，在权限系统配置的
        JSONArray jsonArray=GetWebserviceInfo.getAllResources(accountName,contextPath);
        if(jsonArray.size()>0){
            for(int i=0;i<jsonArray.size();i++){//获取一级菜单的内容
                Map<String,String> map=new HashMap<String,String>();
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                map.put("resourceName",jsonObject.get("resourceName").toString());
                map.put("url",jsonObject.get("link").toString());
                JSONArray jsonArray1=GetWebserviceInfo.getResources(accountName,contextPath,jsonObject.get("resourceId").toString());

                if(jsonArray1!=null){
                    map.put("status","1");//代表此菜单下还有菜单
                   for(int j=0;j<jsonArray1.size();j++){//获取二级菜单的内容
                       Map<String,String> map1=new HashMap<String,String>();
                       JSONObject jsonObject1=jsonArray1.getJSONObject(j);
                       map1.put("resourceName",jsonObject1.get("resourceName").toString());
                       map1.put("url",jsonObject1.get("link").toString());
                       map1.put("parentResourceName",jsonObject.get("resourceName").toString());
                       list1.add(map1);
                   }
                }else{
                    map.put("status","0");//代表此菜单是一级菜单
                }
                //list.add(jsonObject.get("resourceName").toString());
               // stringBuilder.append(jsonObject.get("resourceName").toString()+",");
                list.add(map);
            }
        }
        //String jsonList=JSONArray.fromObject(list).toString();
        //String str=stringBuilder.toString();
        mv.addObject("list",list);
        mv.addObject("childList",list1);
        //mv.addObject("str",str.substring(0,str.length()-1));
        return mv;
    }
}
