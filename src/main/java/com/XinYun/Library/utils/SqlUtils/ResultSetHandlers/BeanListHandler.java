package com.XinYun.Library.utils.SqlUtils.ResultSetHandlers;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

public class BeanListHandler<T> implements ResultSetHandler{
        private Class<T> clazz;

        public BeanListHandler() {

        }

        public BeanListHandler(Class<T> clazz) {
            this.clazz = clazz;
        }
        @Override
        public List<T> handler(ResultSet resultSet) {
            List<T> list = new ArrayList();
            try {
                while (resultSet.next()){
                    ResultSetMetaData metaData = resultSet.getMetaData();
                    int columnCount = metaData.getColumnCount();
                    T obj= clazz.newInstance();
                    for (int i = 0; i < columnCount; i++) {
                        String columnName = metaData.getColumnName(i + 1);
                        Field declaredField = clazz.getDeclaredField(columnName);
                        declaredField.setAccessible(true);
                        Object value = resultSet.getObject(i + 1);
                        declaredField.set(obj,value);
                    }
                    list.add(obj);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return list;
        }
}

