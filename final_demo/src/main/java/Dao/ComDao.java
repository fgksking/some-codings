package Dao;

import po.Com_relation;
import po.Company;
import po.permission;
import po.trade;

import java.math.BigDecimal;
import java.security.Permission;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public interface ComDao {
     void create_Com(String username,String ComName, String ComSize, String ComDirection, boolean ComIspublic);

     boolean Is_Com_exist(String ComName);
     //创建Com_relation表，公司负责人
     boolean create_Com_relation(String ComName,String username,String role);
     //

    boolean destroyCom(String ComName);

    Company findCom(String ComName);
     //展示所有公开企业
     List<Company> getisfreeCom(boolean b);
    boolean disapproval(String ComName);
    List<Company> getCom();
    //搜索公司
    List<Company> getCom(String params);

    boolean joinCom(String username,String ComName,long time);

    //从负责人名字得到公司
    List<Company> get_Com_relation(String username);

    boolean approvalCom(String ComName,String username,String role);

    boolean createRelation(String ComName);

    //用户离开公司
    boolean exitCom(String username);

    List<Com_relation> getRelation(String ComName);

    ArrayList<String> getComName(String username);

    List<permission> getfund(String username);

    List<Com_relation> get_relation(String username);

    List<trade> getComTrade(String ComName);

    boolean pay(String username,BigDecimal amount,String ComName);
    List<Company> getallCom();
    boolean frozen(String ComName);
    boolean de_frozen(String ComName);
}
