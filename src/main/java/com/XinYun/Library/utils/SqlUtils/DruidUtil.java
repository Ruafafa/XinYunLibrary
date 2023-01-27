package com.XinYun.Library.utils.SqlUtils;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import javax.sql.DataSource;
import java.io.Closeable;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * <h1>用于从连接池获得连接，以及关闭</h1>
 * @author WangYisen
 */
public class DruidUtil {
    //数据源(池)
    private static DataSource dataSource = null;

    //初始化数据源
    static {
        try {
            //加载配置文件
            Properties properties = new Properties();
            properties.load(new FileInputStream("src/druid.properties"));
            dataSource = DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取连接
     * @return 连接
     * @throws RuntimeException
     */
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    /**
     * 更快速的关闭方式
     * 修改自HuTool的close源码
     * --不是很安全
     * @param ccs
     */
    public static void close(AutoCloseable... ccs) {
        for (AutoCloseable cc : ccs) {
            if (null != cc) {
                try {
                    cc.close();
                } catch (Exception var2) {
                }
            }
        }
    }

    /**
     * 仅支持关闭数据库连接
     * @param set
     * @param statement
     * @param connection
     */
    public static void close(ResultSet set, Statement statement, Connection connection){
        //判断是否为null
        try{
            if (set != null) {
                //结果集关闭
                set.close();
            }
            if (statement != null) {
                //执行对象关闭
                statement.close();
            }
            if (connection != null) {
                //连接关闭
                connection.close();
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
