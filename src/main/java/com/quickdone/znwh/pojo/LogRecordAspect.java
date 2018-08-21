package com.quickdone.znwh.pojo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.quickdone.znwh.controller.CallTaskController;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: zhum
 * @Date: 2018/8/10 9:50
 * @Description:
 */
@Aspect
@Configuration//定义一个切面
public class LogRecordAspect {

    private static final Logger logger = LoggerFactory.getLogger(CallTaskController.class);

    // 定义切点Pointcut
    @Pointcut("execution(* com.quickdone.znwh.controller..*.*(..))")
    public void excudeService() {
    }


    @Around("excudeService()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();

        String url = request.getRequestURL().toString();
        String method = request.getMethod();
        String queryString = request.getQueryString();
        Object[] args = pjp.getArgs();
        StringBuffer params = new StringBuffer();
        //获取请求参数集合并进行遍历拼接
        if (args.length > 0) {
            if ("POST".equals(method)) {
                Map map = new HashMap();
                for (Object o : args) {
                    map = getKeyAndValue(o);
                    if (params.length() > 0) {
                        params.append(",");
                    }
                    params.append(JSON.toJSONString(map, false));
                }
//                Object object = args[0];
//                Map map = getKeyAndValue(object);
            } else if ("GET".equals(method)) {
                params.append(queryString);
            }
        }

        logger.info("请求开始===地址:" + url);
        logger.info("请求开始===类型:" + method);
        logger.info("请求开始===参数:" + params.toString());

        // result的值就是被拦截方法的返回值
        Object result = pjp.proceed();
        Gson gson = new Gson();
        logger.info("请求结束===返回值:" + gson.toJson(result));
        return result;
    }

    public static Map<String, Object> getKeyAndValue(Object obj) {
        Map<String, Object> map = new HashMap<>();
        // 得到类对象
        Class userCla = (Class) obj.getClass();
        //如果是request请求则不做属性集合遍历操作
        if (userCla.getName().indexOf("org.apache.catalina.connector.RequestFacade") == -1) {
            /* 得到类中的所有属性集合 */
            Field[] fs = userCla.getDeclaredFields();
            for (int i = 0; i < fs.length; i++) {
                Field f = fs[i];
                f.setAccessible(true); // 设置些属性是可以访问的
                Object val = new Object();
                try {
                    val = f.get(obj);
                    // 得到此属性的值
                    map.put(f.getName(), val);// 设置键值
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

            }
        }
        return map;
    }
}

