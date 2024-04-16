package controller;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.Impl.ComServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
@WebServlet("/comServlet")
public class ComServlet extends BaseServlet{


    private Map<String, Object> map = new ConcurrentHashMap<>();
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final ComServiceImpl  comService = new ComServiceImpl();

    private static final String Com_admin="Com_admin";
    public void create_Com(HttpServletRequest request, HttpServletResponse response)throws IOException {
        String username = request.getParameter("username");
        String ComName =request.getParameter("ComName");
        String ComSize = request.getParameter("ComSize");
        String ComDirection = request.getParameter("ComDirection");
        String ComIspublic = request.getParameter("ComIspublic");
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
            //返回为空，后面看看需不要改返回Company
            comService.create_Com(ComName,ComSize,ComDirection,comispublic);


            //同时创建Com_relation表,需要传入username公司负责人
            comService.create_Com_relation(ComName,username,Com_admin);



            map.put("msg","公司创建成功");
        }else{
            map.put("msg","公司名已存在，创建失败");
        }
        String jsonString = JSON.toJSONString(map);
        response.getWriter().write(jsonString);




    }


    public void create_Com_group(HttpServletRequest request, HttpServletResponse response)throws IOException{
        //需要username管理员、Com_id公司、Com_group_name、Com_amount余额（默认）


    }

    public void getAllCom(HttpServletRequest request, HttpServletResponse response)throws IOException{
        //可以显示所有公司


    }

    public void search_Com(HttpServletRequest request, HttpServletResponse response)throws IOException{
        //搜索公司

    }



}
