package Dao;

import po.*;
import utils.CRUD_Utils;
import utils.MyHander;

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

    boolean destroyCom(String ComName,String username);

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

    /**
     * 记录交易信息
     * @param username
     * @param target
     * @param amount
     * @param bank
     * @return
     */
    boolean log_trade(Connection con,String username,String type,String target,BigDecimal amount,String bank,String ComName);


    boolean admin_pay(String username,BigDecimal amount,String bank);

    List<JoinComMsg> get_user_join(String ComName);

    boolean delete_join(String username,String ComName);

    List<Com_relation> getRelation(String ComName);

    List<Company> getComName(String username);

    List<permission> getfund(String username);

    List<Com_relation> get_relation(String username);

    List<trade> getComTrade(String ComName);

    boolean pay(String username,BigDecimal amount,String ComName);
    List<Company> getallCom();
    boolean frozen(String ComName);
    boolean de_frozen(String ComName);
    boolean request_permission(Connection con,String ComName,String username,String approval_name,BigDecimal amount,String bank);

    boolean request_com(Connection con,String ComName,BigDecimal amount);

    boolean sendMsg(String sender,String receiver,String content);
    boolean create_permission(String ComName,String username,String approvalName);

    BigDecimal get_com_fund(String ComName);

}
