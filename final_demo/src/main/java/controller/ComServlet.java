package controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import po.Com_relation;
import po.Company;
import po.User;
import service.Impl.ComServiceImpl;
import service.Impl.UserServiceImpl;
import utils.ServerResponse;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
@WebServlet("/comServlet")
public class ComServlet extends BaseServlet{



    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final ComServiceImpl  comService = new ComServiceImpl();
    private final UserServiceImpl userService = new UserServiceImpl();
    private static final String Com_admin="Com_admin";
    private static


    public void create_Com(HttpServletRequest request, HttpServletResponse response)throws IOException {
        BufferedReader br = request.getReader();
        String params = br.readLine();
        JSONObject jsonObject = JSONObject.parseObject(params);
        String username =(String) request.getSession().getAttribute("username");
        String  ComName  = jsonObject.getString("ComName");
        String ComDirection = jsonObject.getString("ComDirection");
        String ComSize = jsonObject.getString("ComSize");
        String ComIspublic = jsonObject.getString("ComIspublic");
        System.out.println(params);
        logger.info("正在尝试创建公司");
        boolean comispublic;
        if("true".equals(ComIspublic)){
             comispublic =true;
        }else {
            comispublic=false;
        }
        //查看公司名是否存在
        boolean b = comService.Is_Com_exist(ComName);
        Map<String, Object> map = new ConcurrentHashMap<>();
        if(b){
            //调用service再Dao层
            //返回为空，后面看看需不要改返回Company  //发起申请

            comService.create_Com(username,ComName,ComSize,ComDirection,comispublic);
            //同时创建Com_relation表,需要传入username公司负责人
            //comService.create_Com_relation(ComName,username,Com_admin);
            map.put("msg","申请成功");
        }else{
            map.put("msg","公司名已存在，创建失败");
        }
        String jsonString = JSON.toJSONString(map);
        response.getWriter().write(jsonString);

    }

    @Test
    public void test(){
        /*
        ServerResponse.createSuccess("msg");*/
       ServerResponse.createSuccess("msg");
       ServerResponse.createSuccess("msg");

    }



    public void approval_join_Com(HttpServletRequest request, HttpServletResponse response)throws IOException{
        //用户申请加入公司

        String username = (String) request.getSession().getAttribute("username");
        Map<String, Object> map = new ConcurrentHashMap<>();
        if(username==null){
            //提示首先要登录
            map.put("msg","请先登录");
        }else {
            ArrayList<String> comName = comService.getComName(username);

            if (comName==null) {
                String ComName = request.getParameter("ComName");
                //转码
                byte [] bytes = ComName.getBytes("ISO-8859-1");
                ComName= new String(bytes, "utf-8");
                //获取公司名
                //申请的原因
                String intro = request.getParameter("reason");
                Long timeStamp = System.currentTimeMillis();  //获取当前时间戳

                //写入申请表
                boolean b = comService.joinCom(username, ComName, timeStamp);
                if (b) {
                    map.put("msg", "申请成功");
                    logger.info(username + "申请表成功");
                } else {
                    map.put("msg", "申请失败");
                }
            }else{
                //已经加入了一家公司
                map.put("msg","你已经加入了一家公司，申请失败");
            }


        }
        String jsonString = JSON.toJSONString(map);
        response.getWriter().write(jsonString);
    }



    //用户退出公司
    public void exitCom(HttpServletRequest request, HttpServletResponse response)throws IOException{
        String username= (String) request.getSession().getAttribute("username");

        //并实时给管理员发送消息


        //并退出公司删除relation


    }

    //分配资源表
    public void permission(HttpServletRequest request, HttpServletResponse response)throws IOException{
        //管理员分配资源表给员工
        //逻辑后端实现？role的判断前端实现
        //分配给谁 谁分配
        String sender =(String) request.getSession().getAttribute("username");
        //验证身份已经在前端完成了
        ArrayList<String> comName = comService.getComName(sender);
        BufferedReader br = request.getReader();
        String params = br.readLine();
        String usernamedata = params.split(":")[1].replace("\"", "").replace("}", "");
        String username =usernamedata.substring(0,usernamedata.indexOf(","));
        String  email = params.split(":")[2].replace("\"", "").replace("}", "");
    }

    public void loginCom(HttpServletRequest request, HttpServletResponse response)throws IOException{
        //传com_relation
        String username = (String)request.getSession().getAttribute("username");
        User user = userService.findUser(username);
        Map<String, Object> map = new ConcurrentHashMap<>();
        int i =0;
        if(user!=null) {
            map.put("role",user.getRole());
            ArrayList<String> comName = comService.getComName(username);
            List<Com_relation> relation = new ArrayList<>();
            if(comName!=null) {
                logger.info("公司关系拿到");
                for (String s : comName) {
                    List<Com_relation> relation1 = comService.getRelation(s);
                    for (Com_relation comRelation : relation1) {
                        relation.add(comRelation);
                    }
                }

            }
            //公司的数量
            map.put("data", relation);
            map.put("username",username);
            String jsonString = JSON.toJSONString(map);
            response.getWriter().write(jsonString);

        }
        //用户为空
    }

    public void destoryCom(HttpServletRequest request, HttpServletResponse response)throws IOException{
        logger.info("管理员正在注销公司");

        //因为关系表用到了外键
        String comName = request.getParameter("ComName");
        String username = (String) request.getSession().getAttribute("username");
        User user =userService.findUser(username);
        //先删除relation 表有关的公司名，在删除公司表
        if("Com_admin".equals(user.getRole())){
            //注销公司
            boolean b = comService.destroyCom(comName);
            if(b){
                response.getWriter().write(JSON.toJSONString(ServerResponse.createSuccess("注销成功")));
            }else{
                response.getWriter().write(JSON.toJSONString(ServerResponse.createError("注销失败")));
            }


        }else{
            response.getWriter().write("你不是管理员，没有权限");
        }

    }


//企业是否公开的逻辑在ServiceImpl实现
    public void getAllCom(HttpServletRequest request, HttpServletResponse response)throws IOException{
        //可以显示所有公司
        logger.info("显示公司");
        List<Company> com = comService.getCom();
        if(com!=null) {
          /*  Map<String, Object> map = new ConcurrentHashMap<>();
            map.put("data", com);
            response.setContentType("text/json;charset=utf-8");

                String jsonString = JSON.toJSONString(map);*/
           //  ServerResponse.createSuccess("显示公司").writeToResponse(response);

            response.getWriter().write(JSON.toJSONString(com));
            logger.info(JSON.toJSONString(com));

        }

    }

    public void search_Com(HttpServletRequest request, HttpServletResponse response)throws IOException{
        //搜索公司
        logger.info("正在搜索公司");
        request.setCharacterEncoding("utf-8");
        String search = request.getParameter("search");
        byte [] bytes = search.getBytes("ISO-8859-1");
        search= new String(bytes, "utf-8");
        logger.info("搜索词为"+search);
        List<Company> com = comService.getCom(search);
        Map<String, Object> map = new ConcurrentHashMap<>();
        if(com!=null) {
            map.put("data", com);
        }
        response.setContentType("text/json;charset=utf-8");
        String jsonString = JSON.toJSONString(map);
        response.getWriter().write(jsonString);

    }
    //关于消息记录
    public void agreeJoin(HttpServletRequest request, HttpServletResponse response)throws IOException{
        //从消息中得知信息

        //同意加入公司


    }

    public void inviteJoin(HttpServletRequest request, HttpServletResponse response)throws IOException{
        //管理员拉取用户加入公司

    }



}
