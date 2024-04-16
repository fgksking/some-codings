package controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.JSONObjectCodec;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import po.User;
import service.Impl.UserServiceImpl;
import utils.SendEmail;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@WebServlet("/userServlet")
public class UserServlet extends BaseServlet{

    //Servlet是单例多线程，避免出现实例对象！！！！！

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final UserServiceImpl userService =new UserServiceImpl();
    public void login(HttpServletRequest request, HttpServletResponse response) throws IOException{
        logger.info("正在登录");
        try {
            request.setCharacterEncoding("utf-8");
            response.setCharacterEncoding("utf-8");

            BufferedReader br = request.getReader();
            String params = br.readLine();
            logger.info(params);
            String usernamedata = params.split(":")[1].replace("\"", "").replace("}", "");
            String username =usernamedata.substring(0,usernamedata.indexOf(","));
            String  password = params.split(":")[2].replace("\"", "").replace("}", "");
            User user = userService.login(username,password);
            logger.info(user.toString());
            //加锁，map是实例对象，避免出问题
            Map<String, Object> map = new ConcurrentHashMap<>();

                map.put("msg", (user == null) ? "用户登录失败" : "用户登录成功");
                if(user!=null) {
                    map.put("status", 200);
                    map.put("data", user);
                   // Cookie cookie = new Cookie("username",username);
                    //加入cookie和session
                    HttpSession session = request.getSession();
                    session.setAttribute("username",username);
                 //   response.addCookie(cookie);
                    response.setContentType("application/json");
                    String data = JSON.toJSONString(map);
                    System.out.println(data);
                    response.getWriter().write(data);
                    response.sendRedirect("/com.html");
                }else{
                    map.put("status",101);
                    String data = JSON.toJSONString(map);
                    response.getWriter().write(data);
                }

        } catch (IOException e) {

            logger.debug("servlet层出现异常",e);

        }

    }

    public void unlog(HttpServletRequest request,HttpServletResponse response)throws IOException{
        //退出登录的逻辑，删除seesion以及与前端交互删除cookie
        logger.info("正在退出");
        HttpSession session =request.getSession();
        if(session!=null) {
            //request.getSession().removeAttribute("username");
            session.invalidate();
        }
        Cookie cookie = new Cookie("JSESSIONID", "");    // 同时销毁 浏览器的 Cookie 数据
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        try {
            request.getRequestDispatcher("/login.html").forward(request, response);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
public  void test(){
        //分割字符串，也可以用正则表达式
        String params="{\"username\":\"zhansan\",\"password\":\"123\"}";
        String username = params.split(":")[1].replace("\"", "").replace("}", "");
        String  password = params.split(":")[2].replace("\"", "").replace("}", "");
        String data=username.substring(0,username.indexOf(","));
        System.out.println(data);
        System.out.println(username);
        System.out.println(password);

}




    public void register(HttpServletRequest request,HttpServletResponse response) throws IOException{
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json");
        BufferedReader br = request.getReader();
        String params = br.readLine();
        User user = JSONObject.parseObject(params,User.class);
        String username=user.getUsername();
        String password = user.getPassword();
        String email =user.getEmail();
        Map<String, Object> map = new ConcurrentHashMap<>();
        //注册逻辑

            //把用户资金表也注册了
            Boolean b = userService.register(username, password, email);
            map.put("msg", (b == null) ? "用户注册失败" : "用户注册成功");
            if (b == true) {
                map.put("status", 200);
                logger.info("注册成功");
                String data = JSON.toJSONString(map);
                response.getWriter().write(data);
                //跳转登录页

                response.sendRedirect("login.html");
            }

    }


    //@Test
    public void forgetpwd(HttpServletRequest request,HttpServletResponse response)  throws IOException{
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        //先验证此人是否存在
        BufferedReader br = request.getReader();
        String params = br.readLine();
        logger.info(params);
        String usernamedata = params.split(":")[1].replace("\"", "").replace("}", "");
        String username =usernamedata.substring(0,usernamedata.indexOf(","));
        String  email = params.split(":")[2].replace("\"", "").replace("}", "");

        User user = userService.findUser(username);
        logger.info(user.toString());
        Map<String, Object> map = new ConcurrentHashMap<>();
        if(user!=null) {
            //发送邮箱
            String base = "0123456789ABCDEFGHIJKLMNOPQRSDUVWXYZabcdefghijklmnopqrsduvwxyz";
            int size = base.length();
            Random r = new Random();
            StringBuilder code = new StringBuilder();
            for (int i = 1; i <= 4; i++) {
                //产生0到size-1的随机值
                int index = r.nextInt(size);
                //在base字符串中获取下标为index的字符
                char c = base.charAt(index);
                //将c放入到StringBuffer中去
                code.append(c);
            }

            map.put("msg","验证码发送成功，请及时查看");
            String data = JSON.toJSONString(map);
            //把验证码保存在session中，方便后续判断
            //验证码时间最长为60秒
            HttpSession session = request.getSession();
            session.setAttribute("EmailCode", code.toString());
            session.setAttribute("username",username);
            session.setMaxInactiveInterval(60);
            SendEmail mySendMail = new SendEmail();
            mySendMail.sendMail(email, "您正在找回密码，验证码为：" + code.toString() +"\n只有60秒时间，只有一次输入机会，请正确输入！如非本人操作，请忽略。");
            response.getWriter().write(data);
            //

        }else{
           // 用户名不存在
            map.put("msg","用户名不存在");
            String data = JSON.toJSONString(map);
            response.getWriter().write(data);
           //
        }

    }

    public void changePassword(HttpServletRequest request,HttpServletResponse response) throws IOException{
       /* String newpwd =  request.getParameter("newpwd");
        String emailcode = request.getParameter("emailcode");*/
        BufferedReader br = request.getReader();
        String params = br.readLine();
        String newpwddata = params.split(":")[1].replace("\"", "").replace("}", "");
        String  emailcode = params.split(":")[2].replace("\"", "").replace("}", "");
        String newpwd = newpwddata.substring(0,newpwddata.indexOf(","));
        String emailCode =(String) request.getSession().getAttribute("EmailCode");
        Map<String, Object> map = new ConcurrentHashMap<>();
        if(emailCode==null){
            //超过60秒
            //多线程下怎么办,此情况不用担心
            map.put("msg","验证码超时,请重新获取");

        }else{
            if(emailCode.equals(emailcode)){
                //验证码一致
                //更改新密码
                String username= (String) request.getSession().getAttribute("username");
                userService.change(username,newpwd);
                map.put("msg","修改成功");
                String jsonString = JSON.toJSONString(map);
                response.getWriter().write(jsonString);
                response.sendRedirect("http://localhost:8080/login.html");
                return;
            }else{
              //  response
                map.put("msg","验证码不一致");

            }

        }
        String jsonString = JSON.toJSONString(map);
        response.getWriter().write(jsonString);

    }


    //头像上传






}
