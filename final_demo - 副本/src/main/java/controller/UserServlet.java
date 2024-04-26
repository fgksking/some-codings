package controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import po.Com_relation;
import po.Message;
import po.User;
import po.User_pwd;
import service.Impl.ComServiceImpl;
import service.Impl.UserServiceImpl;
import utils.ConnectionPoolManager;
import utils.SendEmail;
import utils.ServerResponse;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
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
/*            byte [] bytes = username.getBytes("ISO-8859-1");
            username= new String(bytes, "utf-8");*/
            User user = userService.login(username,password);
            //加锁，map是实例对象，避免出问题
            Map<String, Object> map = new ConcurrentHashMap<>();


                map.put("msg", (user == null) ? "用户登录失败" : "用户登录成功");
                if (user != null) {
                    HttpSession session = request.getSession();
                    //管理员身份
                    if(user.getRole().equals("admin")){
                        map.put("role","admin");
                        session.setAttribute("role","admin");
                    }
                    logger.info(user.toString());
                    map.put("status", 200);
                    //返回user的数据
                    map.put("data", user);
                    // Cookie cookie = new Cookie("username",username);
                    //加入cookie和session

                    session.setAttribute("username", username);
                    //   response.addCookie(cookie);
                    response.setContentType("application/json");
                    String data = JSON.toJSONString(map);
                    response.getWriter().write(data);
                    //不能这样写
                    //   response.sendRedirect("/com.html");
                } else {
                    map.put("status", 101);
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
        response.setContentType("application/json");
        HttpSession session =request.getSession();
        if(session!=null) {
            //request.getSession().removeAttribute("username");
            session.invalidate();
        }
        Cookie cookie = new Cookie("JSESSIONID", "");    // 同时销毁 浏览器的 Cookie 数据
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        try {
            //request.getRequestDispatcher("/login.html").forward(request, response);
            response.sendRedirect("http://localhost:8080/login.html");
        } catch (IOException e) {
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
        System.out.println(System.currentTimeMillis());
        Long timeStamp = System.currentTimeMillis();  //获取当前时间戳
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sd = sdf.format(timeStamp);
        String sd2 = sdf.format(new Date(timeStamp));
        String sd3 = sdf.format(timeStamp);
        System.out.println("格式化结果：" + sd);
        System.out.println("格式化结果：" + sd2);
        System.out.println("格式化结果：" + sd3);

    }



    public void register(HttpServletRequest request,HttpServletResponse response) throws IOException{
        //注册时邮箱只能唯一
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json");
        BufferedReader br = request.getReader();
        String params = br.readLine();
        User_pwd user = JSONObject.parseObject(params,User_pwd.class);
        String username=user.getUsername();
        String password = user.getPassword();
        String email =user.getEmail();

        Map<String, Object> map = new ConcurrentHashMap<>();
        //注册逻辑

            //把用户资金表也注册了
            Boolean b = userService.register(username, password, email);
            map.put("msg", b ? "用户注册成功" : "用户注册失败");
            if (b) {
                map.put("code", 200);
                logger.info("注册成功");
                //跳转登录页

                /*response.sendRedirect("login.html");*/
            }else{
                map.put("code",100);
            }
        String data = JSON.toJSONString(map);
        response.getWriter().write(data);

    }


    //@Test
    public void forgetpwd(HttpServletRequest request,HttpServletResponse response)  throws IOException{
        response.setContentType("application/json");
        //先验证此人是否存在
        BufferedReader br = request.getReader();
        String params = br.readLine();
        logger.info(params);
        String usernamedata = params.split(":")[1].replace("\"", "").replace("}", "");
        String username =usernamedata.substring(0,usernamedata.indexOf(","));
        String  email = params.split(":")[2].replace("\"", "").replace("}", "");
       /* byte [] bytes = username.getBytes("ISO-8859-1");
        username= new String(bytes, "utf-8");*/
        User user = userService.findUser(username);
        Map<String, Object> map = new ConcurrentHashMap<>();
        if(user!=null) {
            logger.info(user.toString());
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
        response.setContentType("application/json");
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
                Boolean change = userService.change(username, newpwd);
                if(change) {
                    map.put("msg", "修改成功");
                    map.put("code", "200");
                    String jsonString = JSON.toJSONString(map);
                    response.getWriter().write(jsonString);
                    /*  response.sendRedirect("http://localhost:8080/user/login.html");*/
                    //销毁session
                    request.getSession().invalidate();
                    return;
                }
                map.put("msg","后台error");
            }else{
              //  response
                map.put("msg","验证码不一致");
                map.put("code",100);

            }

        }
        String jsonString = JSON.toJSONString(map);
        response.getWriter().write(jsonString);

    }


    //头像上传
    public void getPhoto(HttpServletRequest request,HttpServletResponse response) throws IOException{
        String username= (String) request.getSession().getAttribute("username");
        String path = userService.path(username);
        logger.info("图片路径"+path);
        response.setContentType("image/jpeg");
        // 从文件系统读取头像图片并写入响应
        try (InputStream in = getServletContext().getResourceAsStream("//image/"+path);
             OutputStream out = response.getOutputStream()) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }


        }

    }

    public void changePhoto(HttpServletRequest request,HttpServletResponse response) throws IOException{
        String username = (String) request.getSession().getAttribute("username");
        Part filePart  = null;

        try {
            if(ServletFileUpload.isMultipartContent(request)) {
                //1. 创建DiskFileItemFactory对象，设置缓冲区大小和临时文件目录
                DiskFileItemFactory factory = new DiskFileItemFactory();

                //2. 创建ServletFileUpload对象，并设置上传文件的大小限制。
                ServletFileUpload fileUpload = new ServletFileUpload(factory);
                fileUpload.setSizeMax(1024 * 1024 * 10);//限制上传文件的大小 , 以byte为单位   10M,  1024byte = 1kb   1024kb = 1M   1024M=1G....
                fileUpload.setHeaderEncoding("utf-8");//设置编码格式

                //3. 调用ServletFileUpload.parseRequest方法解析request对象，得到一个保存了所有上传内容的List对象。
                List<FileItem> fileItemList = fileUpload.parseRequest(request);
                if(fileItemList==null||fileItemList.isEmpty()){
                    response.getWriter().write(JSON.toJSONString(ServerResponse.createError("不能为空")));
                }
                for (FileItem fileItem : fileItemList) {
                    //true为普通表单元素
                    if (fileItem.isFormField()) {
                        String name = fileItem.getFieldName();//表单元素的name属性值
                    } else {

                        String fileName = fileItem.getName();
                        System.out.println("源文件名称：" + fileName);//1.jpg
                        if(!fileName.contains("jpg")){
                            response.getWriter().write(JSON.toJSONString(ServerResponse.createError("不能为空")));
                            return;
                        }
                        //扩展名
                        String suffix = fileName.substring(fileName.lastIndexOf("."));
                        System.out.println("扩展名：" + suffix);//.jpg
                        if(".jpg".equals(suffix)) {

                            //新文件名称 : 时间毫秒值  + 扩展名
                            String newFileName = new Date().getTime() + suffix;
                            System.out.println("新文件名称：" + newFileName);//21570606348709.jpg

                            // 调用FileItem的write()方法，写入文件   D:/apache-tomcat-7.0.73/webapps/day08_my12306/uploadFile
                            File file = new File(request.getServletContext().getRealPath("") + "/image/" + newFileName);
                            fileItem.write(file);

                            //. 调用FileItem的delete()方法，删除临时文件
                            fileItem.delete();
                            userService.changPath(username,newFileName);
                            response.getWriter().write(JSON.toJSONString(ServerResponse.createSuccess("成功修改")));
                            return;
                        }
                    }
                }
            }
            response.getWriter().write(JSON.toJSONString(ServerResponse.createError("上传图像是失败")));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }


      /*  try {
            filePart = request.getPart("img");
        } catch (ServletException e) {
            logger.debug("更改头像出错",e);
            throw new RuntimeException(e);
        }
        if(filePart==null){
            logger.debug("文件为空");
            return;
        }
        String fileName= Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
        System.out.println(fileName);
        String uploadPath  =getServletContext().getRealPath("")+ File.separator+"image";
        System.out.println(uploadPath);*/
        // 保存文件到服务器
     /*   File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }
        String filePath = uploadPath + File.separator + fileName;
        filePart.write(filePath);
        //更新数据库的image路径
        userService.changPath(username,filePath);

        //重定向此页面刷新（后端实现）
        response.sendRedirect(request.getContextPath()+"/");*/
    }

    //查找用户角色
    public void getUerInfo(HttpServletRequest request,HttpServletResponse response) throws IOException{
        String username = (String) request.getSession().getAttribute("username");
        User user = userService.findUser(username);
        Map<String, Object> map = new ConcurrentHashMap<>();
        ComServiceImpl comService = new ComServiceImpl();
        List<Com_relation> relation = comService.get_relation(username);
        if(relation!=null) {
            map.put("com", relation.get(0));
        }
        if(user!=null) {
            map.put("role", user.getRole());
            map.put("username", username);
            map.put("email",user.getEmail());
            String jsonString = JSON.toJSONString(map);
            response.getWriter().write(jsonString);
        }

    }
    public void getAll_use(HttpServletRequest request,HttpServletResponse response) throws IOException{
        List<User> alluser = userService.getAlluser();
        synchronized (this) {
            List<User> list  = new ArrayList<>();
            if(alluser!=null){
                for (User user : alluser) {
                    if(user.getRole().equals("user")){
                        list.add(user);
                    }
                }
                response.getWriter().write(JSON.toJSONString(list));
            }else{
                logger.error("用户为空？");
            }
        }

    }

    public void get_user_msg(HttpServletRequest request,HttpServletResponse response) throws IOException{
        String username = (String)request.getSession().getAttribute("username");
        List<Message> userMeg = userService.get_user_meg(username);
        if(userMeg!=null){
            response.getWriter().write(JSON.toJSONString(userMeg));
        }else{
            response.getWriter().write("无消息");
        }

    }









}
