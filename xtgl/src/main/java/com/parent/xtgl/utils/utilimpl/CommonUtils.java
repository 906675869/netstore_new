package com.parent.xtgl.utils.utilimpl;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommonUtils {
    /**
     * @param object 传入的对象
     将对象转换为字符串 为null时转化为空字符
     */
    public static String convertNullToString(Object object){
        return object == null ? "" :object.toString();
    }

    /**
     *
     * @param obj 需要匹配的对象
     * @param list 需要匹配的列表
     * @param key  对象中的字段
     * @return 匹配结果
     */
    public static boolean toMatchList(Object obj, List list,String key){
        //[1]获取对象中需要匹配的值
        String string = convertNullToString(getFieldValueByName(key,obj));
        //[2]遍历需要匹配的数组
        for(Object _object : list){
            String _string = convertNullToString(getFieldValueByName(key,_object));
            if( _string.contains(string)  || string.contains(_string) ){
                return true;
            }
        }
        return false;
    }
    /**
     *
     * @param string 需要匹配的字符串
     * @param list 需要匹配的列表
     * @param key  对象中的字段
     * @return 匹配结果
     */
    public static boolean toMatchList(String string, List list,String key){
        //[2]遍历需要匹配的数组
        for(Object _object : list){
            String _string = convertNullToString(getFieldValueByName(key,_object));
            if( _string.contains(string)  || string.contains(_string) ){
                return true;
            }
        }
        return false;
    }


    /**
     * 根据属性名获取属性值
     * */
    public static Object getFieldValueByName(String fieldName, Object o) {
        try {
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + fieldName.substring(1);
            Method method = o.getClass().getMethod(getter, new Class[] {});
            Object value = method.invoke(o, new Object[] {});
            return value;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取属性名数组
     * */
    public static  String[] getFiledName(Object o){
        Field[] fields=o.getClass().getDeclaredFields();
        String[] fieldNames=new String[fields.length];
        for(int i=0;i<fields.length;i++){
            System.out.println(fields[i].getType());
            fieldNames[i]=fields[i].getName();
        }
        return fieldNames;
    }

    /**
     * 获取属性类型(type)，属性名(name)，属性值(value)的map组成的list
     * */
    public static  List getFiledsInfo(Object o){
        Field[] fields=o.getClass().getDeclaredFields();
        String[] fieldNames=new String[fields.length];
        List list = new ArrayList();
        Map infoMap=null;
        for(int i=0;i<fields.length;i++){
            infoMap = new HashMap();
            infoMap.put("type", fields[i].getType().toString());
            infoMap.put("name", fields[i].getName());
            infoMap.put("value", getFieldValueByName(fields[i].getName(), o));
            list.add(infoMap);
        }
        return list;
    }

    /**
     * 获取对象的所有属性值，返回一个对象数组
     * */
    public static  Object[] getFiledValues(Object o){
        String[] fieldNames=getFiledName(o);
        Object[] value=new Object[fieldNames.length];
        for(int i=0;i<fieldNames.length;i++){
            value[i]= getFieldValueByName(fieldNames[i], o);
        }
        return value;
    }
}
