package com.XinYun.Library.utils.SqlUtils.ResultSetHandlers;

import java.sql.ResultSet;

public interface ResultSetHandler <T>{
    T handler(ResultSet resultSet);
}
