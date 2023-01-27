package com.XinYun.Library.modules.TestServlet;

import com.XinYun.Library.utils.WebExcelUtil;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/ExcelTestServlet")
public class ExcelServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        testExcel(response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    private void testExcel(HttpServletResponse response){
        try {
            WebExcelUtil.listToExcel(response,"test",List.of("1","2","3"),List.of("3","3","3"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
