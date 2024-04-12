package com.test;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
@WebServlet("/user")
public class BaseServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
/*        String reqURL = req.getRequestURI();
        //  获取最后`/`的索引
        int beginIndex = reqURL.lastIndexOf("/");
        //  使用substring，获取方法名称
        String methodName = reqURL.substring(beginIndex+1);
        */
        String methodName =req.getParameter("method");
        try {

          /*  记住谁调用了“service”方法，this就是谁，
             * 如果自己编写了UserServlet继承了BaseServlet，那this就是UserServlet*/
            Method method =this.getClass().getDeclaredMethod(methodName, HttpRequest.class, HttpResponse.class);
            method.invoke(this,req,resp);

        } catch (NoSuchMethodException e ) {
            throw new RuntimeException(e);
        }catch (IllegalAccessException e){
            throw new RuntimeException(e);
        }catch (InvocationTargetException e){
            throw new RuntimeException(e);
        }


    }
}
