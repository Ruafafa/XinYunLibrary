package com.XinYun.Library.modules;

import com.XinYun.Library.modules.TestEntity.TestEntity;
import com.XinYun.Library.utils.SqlUtils.DruidUtil;
import com.XinYun.Library.utils.SqlUtils.ResultSetHandlers.BeanHandler;
import com.XinYun.Library.utils.SqlUtils.SqlExecutor;

import java.sql.Connection;
import java.sql.SQLException;

public class TestAPI {
    public static void main(String[] args) throws SQLException {
        Connection connection1 = DruidUtil.getConnection();
        String sql1 = "SELECT * FROM test1 WHERE name = ?";
        TestEntity select = (TestEntity)SqlExecutor.select(connection1,sql1,new BeanHandler<TestEntity>(TestEntity.class),"Ruafafa");
        System.out.println(select);
        Connection connection2 = DruidUtil.getConnection();
        String sql2 = "UPDATE test1 SET name='wang' WHERE id = 3;";
        int update = SqlExecutor.update(connection2, sql2);
        System.out.println(update);
    }
}
