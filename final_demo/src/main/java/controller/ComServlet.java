package controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import po.*;
import service.Impl.ComServiceImpl;
import service.Impl.UserServiceImpl;
import utils.ConnectionPoolManager;
import utils.ServerResponse;

import javax.naming.CompositeName;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
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


    public void create_Com(HttpServletRequest request, HttpServletResponse response)throws IOException {
        BufferedReader br = request.getReader();
        String params = br.readLine();
        JSONObject jsonObject = JSONObject.parseObject(params);
        String username =(String) request.getSession().getAttribute("username");
        String  ComName  = jsonObject.getString("ComName");
        String ComDirection = jsonObject.getString("ComDirection");
        String ComSize = jsonObject.getString("ComSize");
        String ComIspublic = jsonObject.getString("ComIspublic");
        Map<String, Object> map = new ConcurrentHashMap<>();
        List<Com_relation> relation = comService.get_relation(username);
        if(relation!=null){
            map.put("msg","已经加入公司");
            response.getWriter().write(JSON.toJSONString(map));
            return;
        }
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
            List<Company> comName = comService.getComName(username);

            if (comName==null) {
                //查看申请表是否有申请了
                String ComName = request.getParameter("ComName");
                //转码
                byte [] bytes = ComName.getBytes("ISO-8859-1");
                ComName= new String(bytes, "utf-8");
                List<JoinComMsg> joinUser = comService.get_join_user(ComName);
                if(joinUser!=null) {
                    for (JoinComMsg joinComMsg : joinUser) {
                        if (joinComMsg.getUsername().equals(username)) {
                            response.getWriter().write("已经申请过了");
                            return;
                        }
                    }
                }
                List<Com_relation> relation = comService.get_relation(username);
                if(relation!=null){
                    response.getWriter().write("你已经加入了一个群组");
                    return;
                }
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
        String comName = request.getParameter("ComName");
        User user = userService.findUser(username);
        if(user!=null){
           if(user.getRole().equals("Com_admin")){
                response.getWriter().write("你是管理员，不能退出，去注销");
                return;
            }
        }
        boolean b = comService.exitCom(username);
        Company com = comService.findCom(comName);
        String admin = com.getUsername();
        //并实时给管理员发送消息
 //       boolean b1 = comService.sendMsg(username, admin, "退出群组");
        //并退出公司删除relation
        if(b){
            response.getWriter().write("退出成功");
        }else{
            response.getWriter().write("退出失败");
        }
    }


    //分配资源表 or回收
    public void permission(HttpServletRequest request, HttpServletResponse response)throws IOException{
        //管理员分配资源表给员工
        //逻辑后端实现？role的判断前端实现
        //分配给谁 谁分配
        String Com_admin =(String) request.getSession().getAttribute("username");
        //验证身份已经在前端完成了
        List<Company> companyList = comService.getComName(Com_admin);
        Company company = companyList.get(0);
        String ComName = company.getComName();

        BufferedReader br = request.getReader();
        String params = br.readLine();
        JSONObject jsonObject = JSONObject.parseObject(params);
        String username = jsonObject.getString("username");
        String bank = jsonObject.getString("bank");

        /*
        * bank的值只有User或者Com
        * */
        String type = jsonObject.getString("type");
        String fund = jsonObject.getString("fund");
        String password = jsonObject.getString("password");
        BigDecimal funds = new BigDecimal(fund);
        User user = userService.login(Com_admin, password);
        boolean isFrozen = user.isIs_frozen();
        if(isFrozen){
            response.getWriter().write("用户被封禁中");
        }
        if(user==null){
            response.getWriter().write("密码错误");
            return;
        }
        if(company.isIs_frozen()){
            response.getWriter().write("企业封禁中");
            return;
        }
        //校验密码
        //记录交易信息
        //事务
        //回滚
        //余额

        if("request".equals(type)){
            //回收资金
            //校验回收余额
            BigDecimal Fund;
            if(user.getRole().equals("Com_admin")){
                Fund=comService.get_com_fund(ComName);
            }else {
                List<permission> getfund = comService.getfund(username);
                Fund = getfund.get(0).getPer_amount();
            }
            int result = funds.compareTo(Fund);
            if(result>0){
                //余额不足
                response.getWriter().write("余额不足");
                return;
            }

            boolean b = comService.Request_permission(ComName, Com_admin, username, funds, bank);
            if(b){
                response.getWriter().write("收款成功");
            }

        }else {
        /*    //分配资金
            if(bank.equals("Com")){*/
                //用企业账号
                //校验公司余额
            if(bank.equals("Com")) {
                BigDecimal comFund = comService.get_com_fund(ComName);
                int result = comFund.compareTo(funds);
                if (result >= 0) {
                    //余额充足
                    //在service层实现
                    boolean b = comService.payPermission(ComName, username, Com_admin, funds, bank);
                    if(b){
                    response.getWriter().write("分配成功");
                    return;
                    }else{
                        response.getWriter().write("分配失败");
                    }
                }else{
                    response.getWriter().write("余额不足");
                }

          /*  }else {
                //校验余额
            }*/
            }else {
                //用自己账户分配资源
                //先校验余额
                BigDecimal fund1 = userService.fund(username);
                int r = fund1.compareTo(funds);
                if(r>=0){
                    //余额充足
                    comService.payPermission(ComName,username,Com_admin,fund1,bank);
                    logger.info("用自己账户分配资源成功");
                    response.getWriter().write("分配成功");
                }else{
                    response.getWriter().write("余额不足");
                }

            }


        }


    }


    public void loginCom(HttpServletRequest request, HttpServletResponse response)throws IOException{
        //传com_relation
        String username = (String)request.getSession().getAttribute("username");
        User user = userService.findUser(username);
        Map<String, Object> map = new ConcurrentHashMap<>();
        int i =0;
        if(user!=null) {
            map.put("role",user.getRole());
            List<Com_relation> relation2 = comService.get_relation(username);
            String comName1 = relation2.get(0).getComName();
            List<Company> comName = comService.getComName(username);
            List<Com_relation> relation = new ArrayList<>();
            if(comName1!=null) {
                logger.info("公司关系拿到");
                for (Company s : comName) {
                    List<Com_relation> relation1 = comService.getRelation(comName1);
                    if(relation1==null){
                        return;
                    }
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
        //
        String username = (String) request.getSession().getAttribute("username");
        User user = userService.findUser(username);
        List<Company> comName1 = comService.getComName(username);
        String comName = comName1.get(0).getComName();

        //先删除relation 表有关的公司名，在删除公司表
        if("Com_admin".equals(user.getRole())){
            //注销公司
            boolean b = comService.destroyCom(comName,username);
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
    public void get_user_apply(HttpServletRequest request, HttpServletResponse response)throws IOException{
        //从消息中得知信息
        //同意加入公司
        String username =(String)request.getSession().getAttribute("username");
        User user = userService.findUser(username);
        if(user.getRole().equals("user")){
            response.getWriter().write(JSON.toJSONString(ServerResponse.createError("权限不足")));
            return;
        }
        List<Company> comName = comService.getComName(username);
        if(comName==null){
            return;
        }

        String ComName = comName.get(0).getComName();
        logger.info("COmName"+comName);
        List<JoinComMsg> joinUser = comService.get_join_user(ComName);
        String s  =JSON.toJSONString(joinUser);
      //  response.getWriter().write(/*JSON.toJSONString(ServerResponse.createSuccess(s))*/));
        response.getWriter().write(s);
    }

    public void invitejoin(HttpServletRequest request, HttpServletResponse response)throws IOException{
        String username1 = (String)request.getSession().getAttribute("username");
        List<Com_relation> relation = comService.get_relation(username1);
        BufferedReader br = request.getReader();
        String params = br.readLine();
        JSONObject jsonObject = JSONObject.parseObject(params);
        String username = jsonObject.getString("username");
        if(relation!=null) {
            String role = relation.get(0).getRole();
            logger.info("职位"+role);
            if (role.equals("Com_admin")) {
                String ComName=relation.get(0).getComName();
                //管理员拉取用户加入公司
                logger.info("拉取的用户名"+username);
                //查看其是否有其他公司的关系
                List<Com_relation> relation1 = comService.get_relation(username);
                if(relation1!=null){
                    response.getWriter().write("此用户已经加入别的群组");
                    return;
                }
                //写入员工关系表，创建permission
                boolean user = comService.create_Com_relation(ComName, username, "user");
                boolean permission = comService.create_permission(username, username1, ComName);
                if(user&&permission){
                    logger.info("拉取成功");
                    response.getWriter().write("拉取成功");
                }else{
                    response.getWriter().write(JSON.toJSONString(ServerResponse.createError("后台错误")));
                    logger.info("后台错误");
                }
            } else {
                response.getWriter().write(JSON.toJSONString(ServerResponse.createError("权限不足")));
                logger.info("权限不足");
            }
        }else{
            response.getWriter().write(JSON.toJSONString(ServerResponse.createError("你不是企业管理员")));
        }
    }
    public void agree_join(HttpServletRequest request, HttpServletResponse response)throws IOException{
        BufferedReader br = request.getReader();
        String params = br.readLine();
        String Com_admin = (String)request.getSession().getAttribute("username");
        JSONObject jsonObject = JSONObject.parseObject(params);
        String username = jsonObject.getString("username");
        List<Company> ComName = comService.getComName(Com_admin);
        String comName;
        if(ComName!=null) {
            comName = ComName.get(0).getComName();


            //写入关系表
            boolean user = comService.create_Com_relation(comName, username, "user");
            //删除申请表
            boolean b = comService.delete_join(username, comName);
            boolean permission = comService.create_permission(username, Com_admin, comName);
            if (b && user & permission) {
                logger.info("成功删除申请表");
                response.getWriter().write(JSON.toJSONString(ServerResponse.createSuccess("成功")));
            } else {
                response.getWriter().write(JSON.toJSONString(ServerResponse.createSuccess("出错了")));
            }
        }

    }
    public void dis_join(HttpServletRequest request, HttpServletResponse response)throws IOException{
        BufferedReader br = request.getReader();
        String params = br.readLine();
        String Com_admin = (String)request.getSession().getAttribute("username");
        JSONObject jsonObject = JSONObject.parseObject(params);
        String username = jsonObject.getString("username");
        List<Company> ComName = comService.getComName(Com_admin);
        String comName ;
        if(ComName!=null) {
            comName = ComName.get(0).getComName();
            //删除申请表
            boolean b = comService.delete_join(username, comName);
            if (b) {
                response.getWriter().write(JSON.toJSONString(ServerResponse.createSuccess("成功")));
            } else {
                response.getWriter().write(JSON.toJSONString(ServerResponse.createSuccess("出错了")));
            }
        }
    }






}
