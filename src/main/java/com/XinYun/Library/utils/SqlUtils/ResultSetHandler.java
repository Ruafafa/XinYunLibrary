package com.XinYun.Library.utils.SqlUtils;

import java.sql.ResultSet;

public interface ResultSetHandler <T>{
    T handler(ResultSet resultSet);
}
