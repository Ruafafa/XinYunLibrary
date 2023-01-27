package com.XinYun.Library.utils;

import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 用于Excel表格的相关操作
 * 基于HuTool的简单封装
 */
public class WebExcelUtil {
    /**
     * //注意！:如果为自动下载，检查excelName是否为非中文！
     * 通过列表输出表格到客户端下载
     * @param respon 响应
     * @param excelName 表格名称不能为中文！
     * @param excelName 每一行数据
     */
    public static  <T> void listToExcel(HttpServletResponse respon, String excelName, List<T>... rows) throws IOException {
        int width = 0;
        List<List<T>> excelRudiment = new ArrayList<>();
        for (List<T> row : rows) {
            excelRudiment.add(row);
            width = row.size();
        }
        //设置相应
        respon.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        respon.setHeader("Content-Disposition","attachment;filename="+excelName+".xlsx");
        //xlsx格式
        ExcelWriter writer = ExcelUtil.getWriter(true);
        //合并单元格后的标题行，标题自行输入
        writer.merge(width - 1,excelName == null ? "默认标题":excelName);
        //输出标题
        writer.write(excelRudiment, true);
        //输出表格
        ServletOutputStream os = respon.getOutputStream();
        writer.flush(os, true);
        writer.close();
    }
}
