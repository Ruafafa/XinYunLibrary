package com.XinYun.Library.utils.SqlUtils.ResultSetHandlers;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
/**
 * <h1>该Handler参考自链接</h1>
 * <a href="https://blog.csdn.net/weixin_44663675/article/details/109457017#">参考连接<a/>
 */
public class BeanHandler<T> implements ResultSetHandler {
    private Class<T> clazz;

    public BeanHandler() {

    }


    public BeanHandler(Class<T> clazz) {
        this.clazz = clazz;
    }

    /**
     * 处理函数，核心是反射的使用，构造方法中传入了结果集对应的类的Class对象
     * 根据Class对象动态的创建实例，并获取属性，设置属性值，返回对象
     * @param rs 传入结果集
     * @return 将结果集装入对应的对象中
     */
    @Override
    public T handler(ResultSet rs) {
        T obj = null;
        try {
            if (rs.next()){
                //获取结果集的元数据，根据元数据获取结果集列数，反射创建obj
                ResultSetMetaData metaData = rs.getMetaData();
                //返回列数
                int columnCount = metaData.getColumnCount();
                obj= clazz.newInstance();
                //循环将结果集的值对应赋值个obj的属性值
                for (int i = 0; i < columnCount; i++) {
                    //获取列名 (第一列是1-第二列是-2)
                    String columnName = metaData.getColumnName(i + 1);
                    //获取列名对应属性对象
                    Field declaredField = clazz.getDeclaredField(columnName);
                    declaredField.setAccessible(true);
                    //获取结果集的值 (第一列是1-第二列是-2)
                    Object value = rs.getObject(i + 1);
                    //设置属性值
                    declaredField.set(obj,value);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }
}
