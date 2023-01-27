package com.XinYun.Library.utils;

import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import org.apache.poi.ss.formula.functions.T;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
     * @param rows 每一行数据
     */
    public static  <T> void listToExcel(HttpServletResponse respon, String excelName, List<T>... rows) throws IOException {
        int width = 0;
        List<List<T>> excelRudiment = new ArrayList<>();
        for (List<T> row : rows) {
            excelRudiment.add(row);
            width = row.size();
        }
        //xlsx格式输出设置
        ExcelWriter writer = ExcelUtil.getWriter(true);
        writer.write(excelRudiment);
        responXlsx(respon,writer,excelName);
    }

    /**
     * //注意！:如果为自动下载，检查excelName是否为非中文！
     * 通过Map输出表格到客户端下载
     * @param respon 响应
     * @param excelName 表格名称不能为中文！
     * @param rows String为表头
     */
    public static  <T> void mapToExcel(HttpServletResponse respon, String excelName, Map<String,T>... rows) throws IOException {
        int width = 0;
        List<Map<String,T>> excelRudiment = new ArrayList<>();
        for (Map<String, T> row : rows) {
            excelRudiment.add(row);
            width = row.size();
        }
        //xlsx格式输出设置
        ExcelWriter writer = ExcelUtil.getWriter(true);
        writer.write(excelRudiment);
        responXlsx(respon,writer,excelName);
    }

    /**
     * //注意！:如果为自动下载，检查excelName是否为非中文！
     * 通过bean输出表格到客户端下载
     * @param respon 响应
     * @param excelName 表格名称不能为中文！
     * @param rows String为表头
     */
    public static  <T> void beanToExcel(HttpServletResponse respon, String excelName, T... beans) throws IOException {
        int width = 0;
        List<T> excelRudiment = new ArrayList<>();
        for (T bean : beans) {
            excelRudiment.add(bean);
        }
        //xlsx格式输出设置
        ExcelWriter writer = ExcelUtil.getWriter(true);
        writer.write(excelRudiment);
        responXlsx(respon,writer,excelName);
    }


    /**
     * 输出到Servlet
     * @param respon
     * @param excelName
     * @throws IOException
     */
    private static void responXlsx(HttpServletResponse respon,ExcelWriter writer,String excelName) throws IOException {
        //设置相应
        respon.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        respon.setHeader("Content-Disposition","attachment;filename="+excelName+".xlsx");
        //输出表格
        ServletOutputStream os = respon.getOutputStream();
        writer.flush(os, true);
        writer.close();
    }
}
