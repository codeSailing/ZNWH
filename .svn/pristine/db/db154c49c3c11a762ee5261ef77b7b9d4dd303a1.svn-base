package com.quickdone.znwh.utils;

import com.sun.org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtilsBean;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Xing ye on 2017/12/2.
 */
public class ToClass {

    /**
     * 将object[] 转为 map
     *
     * @param T
     * @param list
     * @return
     */
    public static List<Map<String, Object>> objectToMap(Class T, List<Object[]> list) {
        List<Map<String, Object>> list1 = new ArrayList<Map<String, Object>>();
        try {
            Field[] fields = T.getDeclaredFields();
            for (Object[] objects : list) {
                int i = 0;
                Map<String, Object> map = new HashMap<String, Object>();
                for (Field f : fields) {
                    map.put(f.getName(), objects[i]);
                    i++;
                }
                list1.add(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list1;
    }

    /**
     * 将object[]转为 对象
     *
     * @param T
     * @param list
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> List<T> convertMapBean(Class T, List<Object[]> list) throws Exception {
     /*   if(map==null || map.size()==0){
            return null;
        }*/
        List<Map<String, Object>> list1 = objectToMap(T, list);
        List<T> list2 = new ArrayList<T>();
        BeanInfo beanInfo = Introspector.getBeanInfo(T);
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (Map<String, Object> map : list1) {
            T bean = (T) T.newInstance();
            for (int i = 0, n = propertyDescriptors.length; i < n; i++) {
                PropertyDescriptor descriptor = propertyDescriptors[i];
                String propertyName = descriptor.getName();
           /* String upperPropertyName = propertyName.toUpperCase();*/
                if (map.containsKey(propertyName)) {
                    Object value = map.get(propertyName);
                    //这个方法不会报参数类型不匹配的错误。
                    BeanUtils.copyProperty(bean, propertyName, value);
                    //用这个方法对int等类型会报参数类型不匹配错误，需要我们手动判断类型进行转换，比较麻烦。
                    //descriptor.getWriteMethod().invoke(bean, value);
                    //用这个方法对时间等类型会报参数类型不匹配错误，也需要我们手动判断类型进行转换，比较麻烦。
                    //BeanUtils.setProperty(bean, propertyName, value);
                }
            }
            list2.add(bean);
        }

        return list2;
    }

    /**
     * 将object[]转为 对象
     *
     * @param T
     * @param list
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> List<T> MaptoBean(Class T, List<Map<String, Object>> list) throws Exception {
     /*   if(map==null || map.size()==0){
            return null;
        }*/
        List<T> list2 = new ArrayList<T>();
        BeanInfo beanInfo = Introspector.getBeanInfo(T);
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (Map<String, Object> map : list) {
            T bean = (T) T.newInstance();
            for (int i = 0, n = propertyDescriptors.length; i < n; i++) {
                PropertyDescriptor descriptor = propertyDescriptors[i];
                String propertyName = descriptor.getName();
           /* String upperPropertyName = propertyName.toUpperCase();*/
                if (map.containsKey(propertyName)) {
                    Object value = map.get(propertyName);
                    //这个方法不会报参数类型不匹配的错误。
                    BeanUtils.copyProperty(bean, propertyName, value);
                    //用这个方法对int等类型会报参数类型不匹配错误，需要我们手动判断类型进行转换，比较麻烦。
                    //descriptor.getWriteMethod().invoke(bean, value);
                    //用这个方法对时间等类型会报参数类型不匹配错误，也需要我们手动判断类型进行转换，比较麻烦。
                    //BeanUtils.setProperty(bean, propertyName, value);
                }
            }
            list2.add(bean);
        }

        return list2;
    }

    /**
     * 对象转为map
     *
     * @param obj
     * @return
     */
    public static Map<String, Object> beanToMap(Object obj) {
        Map<String, Object> params = new HashMap<String, Object>(0);
        try {
            PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean();
            PropertyDescriptor[] descriptors = propertyUtilsBean.getPropertyDescriptors(obj);
            for (int i = 0; i < descriptors.length; i++) {
                String name = descriptors[i].getName();
                if (!"class".equals(name)) {
                    params.put(name, propertyUtilsBean.getNestedProperty(obj, name));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return params;
    }

}
