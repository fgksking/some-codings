package controller;

import Dao.Impl.UserDaoImpl;
import com.alibaba.fastjson.JSON;
import po.User;
import service.Impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
@WebServlet("/userServlet")
public class UserServlet extends BaseServlet{


    private UserServiceImpl userService =new UserServiceImpl();

    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        String username=request.getParameter("username");
        System.out.println(username);
        String password=request.getParameter("password");
        User user = userService.login(username,password);
        Map<String,Object> map= new HashMap<>();
        map.put("status",200);
        map.put("data",user);
        map.put("msg",(user==null)?"用户登录失败":"用户登录成功");
        response.setContentType("application/json");
        System.out.println(map.toString());
        String data= JSON.toJSONString(map);
        System.out.println(data);
        response.getWriter().write(data);

    }


    public void register(){

    }


}
