package com.XinYun.Library.utils.SqlUtils;

import cn.hutool.core.lang.Assert;
import cn.hutool.db.StatementUtil;
import com.XinYun.Library.utils.SqlUtils.ResultSetHandlers.ResultSetHandler;
import java.sql.*;
import java.util.Scanner;

/**
 * 数据库
 * 基于JDBC的简单封装，参考了Mybatis、BasicDAO、DBUtils
 */
public class SqlExecutor {

    /**
     * 执行CRUD操作前的检查
     */
    private static void security(String sql) throws SQLException {
        //sql语句·检测，调用了HuTools的Assert
        Assert.notBlank(sql, "sql语句不能为空！");
        sql = sql.trim();
        String tag = new Scanner(sql).next();
        if(tag.equalsIgnoreCase("SELECT")||
                    tag.equalsIgnoreCase("UPDATE")||
                    tag.equalsIgnoreCase("INSERT")||
                    tag.equalsIgnoreCase("DELETE")) {
        } else {
            throw new SQLException("请不要输入CRUD以外语句");
        }
    }

    /**
     * <h1>查询</h1>
     * @param connection 数据库连接
     * @param sql sql语句
     * @param hd 结果集接口实现类 -用于控制返回类型
     * @param params 占位参数
     * @return 结果集解构
     * @param <T>
     * @throws SQLException
     */
    public static<T> T select(Connection connection, String sql, ResultSetHandler<T> hd, Object... params) throws SQLException {
        security(sql);
        //获取statement对象,绑定sql语句
        PreparedStatement ps = connection.prepareStatement(sql);
        StatementUtil.fillParams(ps,params);
        ResultSet rs = ps.executeQuery();
        //对结果集进行处理
        T obj = (T)hd.handler(rs);
        //关闭资源
        DruidUtil.close(connection,ps,rs);
        return obj;
    }

    /**
     * 修改、删除、添加
     * @return 返回影响的行数
     */
    public static int update(Connection connection,String sql,Object... params) throws SQLException {
        security(sql);
        //获取statement对象,绑定sql语句
        PreparedStatement ps = connection.prepareStatement(sql);
        StatementUtil.fillParams(ps,params);
        int line = ps.executeUpdate();
        //关闭资源
        DruidUtil.close(connection,ps);
        return line;
    }
    /**
     *  由于该填充方式可能会出现数据缺损
     *  出于安全性考虑，故舍弃，改用Hutool工具类填充
     */
    /*private static void fillParam(PreparedStatement ps,Object... params) throws SQLException {
        //有参数便填充
        if (!(params == null || params.length == 0)){
            int paramIndex = 1;
            for (Object param : params) {
                ps.setObject(paramIndex++,param);
            }
        }
    }*/

}
