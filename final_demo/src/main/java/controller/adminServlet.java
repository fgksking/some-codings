package controller;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import po.Company;
import po.User;
import service.Impl.ComServiceImpl;
import service.Impl.UserServiceImpl;
import service.Impl.adminServiceImpl;
import utils.ServerResponse;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
@WebServlet("/adminServlet")
public class adminServlet extends BaseServlet{

    private static final ComServiceImpl comService = new ComServiceImpl();
    private static final UserServiceImpl userService  =new UserServiceImpl();
    private static final adminServiceImpl adminService = new adminServiceImpl();
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final String Com_admin="Com_admin";
    public void getNewCom(HttpServletRequest request, HttpServletResponse response)throws IOException{
        //调用Dao层，把未批准的企业传到前端
        List<Company> newCom = adminService.getNewCom();
        logger.info("getnewCom");
        if(newCom.isEmpty()){
            //为空
        }else{
            String jsonString = JSON.toJSONString(newCom);
            response.getWriter().write(jsonString);
        }
    }
    //不同的资源
    public void approvalCom(HttpServletRequest request, HttpServletResponse response)throws IOException{
        //接收前端的同意或不同意企业的创建
        String comName = request.getParameter("comName");
        byte [] bytes = comName.getBytes("ISO-8859-1");
        comName= new String(bytes, "utf-8");
        logger.info("同意"+comName);
        Company com = comService.findCom(comName);
        if(com!=null) {
            String username = com.getUsername();
            ServerResponse<Object> json;
            //approval改写true
                boolean b = comService.approvalCom(comName, username, Com_admin);
                if (b) {
                    json = ServerResponse.createSuccess("成功批准");

                } else {
                    // map.put("msg","批准失败");
                    json = ServerResponse.createError("操作失败");
                }

            String jsonString = JSON.toJSONString(json);
            response.getWriter().write(jsonString);

        }


    }
    public void disagree(HttpServletRequest request, HttpServletResponse response)throws IOException{
        String comName = request.getParameter("comName");
        byte [] bytes = comName.getBytes("ISO-8859-1");
        comName= new String(bytes, "utf-8");
        logger.info("不同意"+comName);
        Company com = comService.findCom(comName);
        if(com!=null) {
            String username= com.getUsername();
            boolean disapproval = comService.disapproval(comName);
            if(disapproval){
                response.getWriter().write(JSON.toJSONString(ServerResponse.createSuccess("不同意成功")));
                //给用户发送消息

                return;
            }
        }
        logger.info("不同意的群组为空？");
    }
    public void getcom(HttpServletRequest request, HttpServletResponse response)throws IOException{
        List<Company> companyList = comService.getallCom();
        Map<String, Object> map = new ConcurrentHashMap<>();
        if(companyList!=null){
            map.put("msg","返回成功");
            map.put("data",companyList);
        }else {
            map.put("msg","错误");
        }
        response.getWriter().write(JSON.toJSONString(map));
    }


    //封禁禁言等操作
    public void User_frozen(HttpServletRequest request, HttpServletResponse response)throws IOException{
        String username = request.getParameter("username");
        User user = userService.findUser(username);
        if(user.isIs_frozen()) {

        }else {
            //设置禁言
            //
            boolean b = userService.frozen(username);
            if (b) {
                response.getWriter().write(JSON.toJSONString(ServerResponse.createSuccess("封禁成功")));
            } else {
                response.getWriter().write(JSON.toJSONString(ServerResponse.createError("封禁失败")));
            }
        }

    }

    public void User_de_frozen(HttpServletRequest request, HttpServletResponse response)throws IOException{
        String username = request.getParameter("username");
        User user = userService.findUser(username);
        //设置禁言
        //
        boolean b =userService.de_frozen(username);
        if(b){
            response.getWriter().write(JSON.toJSONString(ServerResponse.createSuccess("解禁成功")));
        }
        else{
            response.getWriter().write(JSON.toJSONString(ServerResponse.createError("解封失败")));
        }
    }
    public void Com_frozen(HttpServletRequest request, HttpServletResponse response)throws IOException{
        String ComName = request.getParameter("ComName");
        //设置禁言
        //
        boolean frozen = comService.frozen(ComName);

        //设置禁言
        //
        if(frozen){
            response.getWriter().write(JSON.toJSONString(ServerResponse.createSuccess("封禁成功")));
        }
        else{
            response.getWriter().write(JSON.toJSONString(ServerResponse.createError("封禁失败")));
        }
    }
    public  void dis_frozen_com(HttpServletRequest request, HttpServletResponse response)throws IOException{
        String ComName = request.getParameter("ComName");
        //设置禁言
        //
        boolean frozen = comService.de_frozen(ComName);
        if(frozen){
            response.getWriter().write(JSON.toJSONString(ServerResponse.createSuccess("解禁成功")));
        }
        else{
            response.getWriter().write(JSON.toJSONString(ServerResponse.createError("解封失败")));
        }
    }

    public void getuser(HttpServletRequest request, HttpServletResponse response)throws IOException{
        List<User> allUser = adminService.getAllUser();
        if(allUser!=null){
            String json = JSON.toJSONString(allUser);
            response.getWriter().write(json);
        }

    }

    public void frozen_user(HttpServletRequest request, HttpServletResponse response)throws IOException{
        String username = request.getParameter("username");
        if(userService.findUser(username).getRole().equals("admin")){
            response.getWriter().write(JSON.toJSONString(ServerResponse.createError("权限不足")));
            return;
        }
        boolean frozeUser = adminService.is_froze_user(username, true);
        if(frozeUser){
            response.getWriter().write(JSON.toJSONString(ServerResponse.createSuccess("冻结成功")));
        }else{
            response.getWriter().write(JSON.toJSONString(ServerResponse.createError("冻结失败")));
        }
    }
    public  void dis_frozen_user(HttpServletRequest request, HttpServletResponse response)throws IOException{
        String username = request.getParameter("username");
        if(userService.findUser(username).getRole().equals("admin")){
            response.getWriter().write(JSON.toJSONString(ServerResponse.createError("权限不足")));
            return;
        }
        boolean frozeUser = adminService.is_froze_user(username, false);
        if(frozeUser){
            response.getWriter().write(JSON.toJSONString(ServerResponse.createSuccess("冻结成功")));
        }else{
            response.getWriter().write(JSON.toJSONString(ServerResponse.createError("冻结失败")));
        }
    }


}
