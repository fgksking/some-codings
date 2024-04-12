package com.test;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONWriter;
import com.mysql.cj.xdevapi.JsonArray;
import netscape.javascript.JSException;
import netscape.javascript.JSObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@WebServlet("/test")
public class TestServlet extends HttpServlet {


    @Override
    protected void doHead(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setHeader("Custom-Header","HEAD请求成功");
        resp.setStatus(HttpServletResponse.SC_OK);

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String path = req.getServletPath();
        System.out.println(path);
        if("/badrequest".equals(path)){
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().println("bad request");
        }
        String data=req.getParameter("data");
        if("change".equals(data)){
            System.out.println("跳转资源");
            String contextPath = req.getContextPath();
            resp.sendRedirect(contextPath+"resource.html");
        }
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("message", "GET请求成功！");
        resp.setContentType("application/json");
        resp.getWriter().print(jsonResponse.toString());
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        JSONObject jsonResponse = new JSONObject();
        String reduce = req.getReader().lines().reduce("", (s1, s2) -> s1 + s2);
        String params=req.getParameter("key");

        jsonResponse.put("message", "POST请求成功！收到消息："+params+reduce);
        resp.setContentType("application/json");
        resp.getWriter().print(jsonResponse.toString());
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        JSONObject jsonResponse = new JSONObject();
        String reduce = req.getReader().lines().reduce("", (s1, s2) -> s1 + s2);
        jsonResponse.put("message", "PUT请求成功！收到消息："+reduce);
        resp.setContentType("application/json");
        resp.getWriter().print(jsonResponse.toString());

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setHeader("Allow", "GET, POST, PUT, DELETE, HEAD, OPTIONS");
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doTrace(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/plain");
        resp.getWriter().write("请求成功");
    }
}
