package com.XinYun.Library.modules.commons;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class BasicServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取请求路径
        String requestURI = req.getRequestURI();
        //获取方法
        String methodName = requestURI.substring(requestURI.lastIndexOf("/") + 1);
        //获取类
        Class<? extends BasicServlet> clazz = this.getClass();
        try {
            //获取对应方法
            Method method = clazz.getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            //反射生成调用
            method.invoke(this,req,resp);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }catch(NoSuchMethodException e){
            //这里网址记得替换成为资源不存在哦姐买你
            resp.sendRedirect("https://www.baidu.com/");
        }
    }
}
