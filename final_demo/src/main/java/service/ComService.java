package service;

import po.Com_relation;
import po.Company;
import po.permission;
import po.trade;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public interface ComService {

    void create_Com(String username,String ComName,String ComSize,String ComDirection,boolean  ComIspublic);
    boolean Is_Com_exist(String ComName);
    boolean  create_Com_relation(String ComName,String username,String role);
    List<Company> getCom();

    List<Company> getCom(String params);
    boolean joinCom(String username,String ComName,long time);

    boolean exitCom(String username);

    boolean  approvalCom(String ComName,String username,String role);
    List<Com_relation> getRelation(String ComName);
    ArrayList<String> getComName(String username);

    List<permission> getfund(String username);
    boolean pay(String username,BigDecimal bigDecimal,String ComName);
    List<Company> getallCom();
    Company findCom(String ComName);

    boolean disapproval(String ComName);
    boolean frozen(String ComName);
    boolean de_frozen(String ComName);
    boolean destroyCom(String ComName);
    List<Company> getisfreeCom(boolean b);

    public List<Com_relation> get_relation(String username);
    List<trade> getComTrade(String ComName);

}
