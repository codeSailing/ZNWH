package com.quickdone.znwh.pojo;

import org.springframework.util.StringUtils;

/**
 * @Author: zhum
 * @Date: 2018/8/22 14:15
 * @Description:
 */
public class IvrResponseData {
    private static final long serialVersionUID = 1143287635399905813L;
    private String code;//0000 成功   0001 失败
    private String message;
    private Object data;


    public static IvrResponseData getSuccessResponse(Object data){
        IvrResponseData ivrResponseData=new IvrResponseData();
        ivrResponseData.setMessage("操作成功");
        ivrResponseData.setCode("0000");
        ivrResponseData.setData(data);
        return ivrResponseData;
    }



    public static IvrResponseData getSuccessResponse(String message){
        IvrResponseData ivrResponseData=new IvrResponseData();
        if(!StringUtils.isEmpty(message)){
            ivrResponseData.setMessage(message);
        }else{
            ivrResponseData.setMessage("操作成功");
        }
        ivrResponseData.setCode("0000");
        return ivrResponseData;
    }
    public static IvrResponseData getErrorResponse(String message,String exceptCode){
        IvrResponseData ivrResponseData=new IvrResponseData();
        if(!StringUtils.isEmpty(message)){
            ivrResponseData.setMessage(message);
        }else{
            ivrResponseData.setMessage("操作失败");
        }
        ivrResponseData.setCode(exceptCode);
        return ivrResponseData;

    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
