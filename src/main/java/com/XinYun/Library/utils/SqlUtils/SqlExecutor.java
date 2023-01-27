package com.XinYun.Library.utils.SqlUtils;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.db.sql.SqlUtil;
import org.apache.poi.ss.formula.functions.T;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.*;
import java.util.Arrays;
import java.util.Map;
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
    public static<T> T select(Connection connection, String sql,ResultSetHandler<T> hd,Object... params) throws SQLException {
        security(sql);
        //获取statement对象,绑定sql语句
        PreparedStatement ps = connection.prepareStatement(sql);
        fillParam(connection,ps,sql);
        ResultSet rs = ps.executeQuery();
        //对结果集进行处理
        T obj = hd.handler(rs);
        //关闭资源
        DruidUtil.close(connection,ps,rs);
        return obj;
    }

    /**
     * 修改、删除、添加
     */
    public static int update(Connection connection,String sql,Object... params) throws SQLException {
        security(sql);
        //获取statement对象,绑定sql语句
        PreparedStatement ps = connection.prepareStatement(sql);
        fillParam(connection,ps,sql);
        int line = ps.executeUpdate();
        //关闭资源
        DruidUtil.close(connection,ps);
        return line;
    }

    private static void fillParam(Connection connection,PreparedStatement ps,Object... params) throws SQLException {
        //有参数便填充
        if (!(params == null || params.length == 0)){
            int paramIndex = 1;
            for (Object param : params) {
                setParam(ps, paramIndex++, param);
            }
        }
    }

    private static void setParam(PreparedStatement ps, int paramIndex, Object param) throws SQLException {
        if (null == param) {
            ps.setNull(paramIndex,JDBCType.CHAR.getVendorTypeNumber());
        }
        /**
         * >以下逻辑复制自cn.hutool.db.StatementUtil.setParams<
         */
        // 日期特殊处理，默认按照时间戳传入，避免毫秒丢失
        if (param instanceof java.util.Date) {
            if (param instanceof java.sql.Date) {
                ps.setDate(paramIndex, (java.sql.Date) param);
            } else if (param instanceof java.sql.Time) {
                ps.setTime(paramIndex, (java.sql.Time) param);
            } else {
                ps.setTimestamp(paramIndex, SqlUtil.toSqlTimestamp((java.util.Date) param));
            }
            return;
        }
        // 针对大数字类型的特殊处理
        if (param instanceof Number) {
            if (param instanceof BigDecimal) {
                // BigDecimal的转换交给JDBC驱动处理
                ps.setBigDecimal(paramIndex, (BigDecimal) param);
                return;
            }
            if (param instanceof BigInteger) {
                // BigInteger转为Long
                ps.setBigDecimal(paramIndex, new BigDecimal((BigInteger) param));
                return;
            }
            // 忽略其它数字类型，按照默认类型传入
        }
        // 其它参数类型
        ps.setObject(paramIndex, param);
    }
}
