package service.Impl;

import Dao.UserDaoImpl.ComDaoImpl;
import po.Com_relation;
import po.Company;
import po.permission;
import po.trade;
import service.ComService;
import utils.CRUD_Utils;
import utils.MyHander;

import java.math.BigDecimal;
import java.security.Permission;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class ComServiceImpl implements ComService {
    private final ComDaoImpl comDao =new ComDaoImpl();
    @Override
    public void create_Com(String username, String ComName, String ComSize, String ComDirection, boolean ComIspublic) {
        comDao.create_Com(username, ComName,  ComSize,  ComDirection, ComIspublic);
    }


    @Override
    public boolean exitCom(String username) {
        return comDao.exitCom(username);
    }

    @Override
    public boolean Is_Com_exist(String ComName) {
        return comDao.Is_Com_exist(ComName);
    }

    /**
     *
     * @param ComName 公司名
     * @param username  用户
     * @param role    用户在公司角色
     * @return
     */
    @Override
    public boolean create_Com_relation(String ComName, String username, String role) {
        return comDao.create_Com_relation(ComName,username,role);
    }

    @Override
    public List<Company> getCom() {
        List<Company> com = comDao.getCom();
        //检查企业是否公开
        if(com!=null) {
            List<Company> companyList=new ArrayList<>();
            for (Company company : com) {
                if(company.getComIspublic()&&company.isApply()){
                    //公开且通过申请
                    companyList.add(company);
                }
            }
            return companyList;
        }
        return null;
    }

    @Override
    public Company findCom(String ComName) {
        return comDao.findCom(ComName);
    }

    @Override
    public boolean frozen(String ComName) {
        return comDao.frozen(ComName);
    }

    @Override
    public boolean de_frozen(String ComName) {
        return comDao.de_frozen(ComName);
    }

    @Override
    public boolean disapproval(String ComName) {
        return comDao.disapproval(ComName);
    }

    @Override
    public boolean destroyCom(String ComName) {
        return comDao.destroyCom(ComName);
    }

    @Override
    public List<Company> getCom(String params) {
        List<Company> com = comDao.getCom(params);
        //检查企业是否公开
        if(com!=null) {
            List<Company> companyList=new ArrayList<>();
            for (Company company : com) {
                if(company.getComIspublic()&&company.isApply()){
                    //公开
                    companyList.add(company);
                }
            }
            return companyList;
        }
        return null;
    }



    @Override
    public boolean pay(String username, BigDecimal bigDecimal, String ComName) {
        return comDao.pay(username,bigDecimal,ComName);
    }

    @Override
    public boolean joinCom(String username, String ComName,long time) {
        return comDao.joinCom(username,ComName,time);
    }

    @Override
    public boolean approvalCom(String ComName,String username,String role) {
        //设置apply为true,并且创建关系表
        return comDao.approvalCom(ComName, username, role);

    }

    /**
     * 得到交易信息，也就是流水账
     * @param username
     * @param role
     * @return
     */

    public List<trade> getComTrade(String ComName){
        List<trade> comTrade = comDao.getComTrade(ComName);
        if(comTrade.isEmpty()){
            return null;
        }else{
            return comTrade;
        }
    }

    /**
     * 返回该用户的所有与公司的关系
     * @param username
     * @return
     */
    @Override
    public List<Com_relation> get_relation(String username) {
        List<Com_relation> relation = comDao.get_relation(username);
        if(relation.isEmpty()){
            return null;
        }else {
            return relation;
        }
    }

    @Override
    public List<permission> getfund(String username) {
        return comDao.getfund(username);
    }

    @Override
    public List<Com_relation> getRelation(String ComName) {
        return comDao.getRelation(ComName);
    }
    @Override
    public ArrayList<String> getComName(String username){
        return comDao.getComName(username);
    }

    @Override
    public List<Company> getallCom() {
        return comDao.getallCom();
    }
    public List<Company> getisfreeCom(boolean b){
        return comDao.getisfreeCom(b);
    }
}
