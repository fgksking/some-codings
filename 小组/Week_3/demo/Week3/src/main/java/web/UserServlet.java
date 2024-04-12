package web;

import Service.UserService;
import com.alibaba.fastjson.JSON;
import singleClass.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
@WebServlet("/userServlet")
public class UserServlet extends BaseServlet{

    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        String username=request.getParameter("username");
        System.out.println(username);
        String password=request.getParameter("password");
        User user = UserService.login(username,password);
        Map<String,Object> map= new HashMap<>();
        map.put("status",200);
        map.put("data",user);
        map.put("msg",(user==null)?"用户登录失败":"用户登录成功");
        response.setContentType("application/json");
        System.out.println(map.toString());
        String data=JSON.toJSONString(map);
        System.out.println(data);
        response.getWriter().write(data);


    }

    public void test(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.getWriter().print("后台成功响应");

    }

}
