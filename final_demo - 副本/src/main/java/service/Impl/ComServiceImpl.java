package service.Impl;

import Dao.UserDaoImpl.ComDaoImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import po.*;
import service.ComService;
import utils.CRUD_Utils;
import utils.JDBCutil;
import utils.MyHander;

import java.math.BigDecimal;
import java.security.Permission;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ComServiceImpl implements ComService {
    private final ComDaoImpl comDao = new ComDaoImpl();
    public Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void create_Com(String username, String ComName, String ComSize, String ComDirection, boolean ComIspublic) {
        comDao.create_Com(username, ComName, ComSize, ComDirection, ComIspublic);
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
     * @param ComName  公司名
     * @param username 用户
     * @param role     用户在公司角色
     * @return
     */
    @Override
    public boolean create_Com_relation(String ComName, String username, String role) {
        return comDao.create_Com_relation(ComName, username, role);
    }

    public boolean logTrade(String username, String target,String trade_type, BigDecimal amount, String bank) {
        return comDao.log_trade(username, target,trade_type, amount, bank);
    }

    public boolean logTrade(String username, String target,String trade_type, BigDecimal amount, String bank,String ComName){
       return comDao.log_trade(username,target,trade_type,amount,bank,ComName);
    }

    @Override
    public boolean create_permission(String username, String approvalName, String ComName) {
        return comDao.create_permission(ComName, username, approvalName);
    }

    @Override
    public List<Company> getCom() {
        List<Company> com = comDao.getCom();
        //检查企业是否公开
        if (com != null) {
            List<Company> companyList = new ArrayList<>();
            for (Company company : com) {
                if (company.getComIspublic() && company.isApply()) {
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
    public boolean destroyCom(String ComName,String username) {
        return comDao.destroyCom(ComName,username);
    }


    @Override
    public List<Company> getCom(String params) {
        List<Company> com = comDao.getCom(params);
        //检查企业是否公开
        if (com != null) {
            List<Company> companyList = new ArrayList<>();
            for (Company company : com) {
                if (company.getComIspublic() && company.isApply()) {
                    //公开
                    companyList.add(company);
                }
            }
            return companyList;
        }
        return null;
    }


    /**
     * 用户用公司账户支付
     *
     * @param username
     * @param bigDecimal
     * @param ComName
     * @return
     */
    @Override
    public boolean pay(String username, BigDecimal bigDecimal, String ComName) {
        return comDao.pay(username, bigDecimal, ComName);
    }

    public boolean com_pay(String ComName,BigDecimal amount){
       return comDao.com_pay(ComName,amount);
    }
    @Override
    public BigDecimal get_com_fund(String ComName) {
        return  comDao.get_com_fund(ComName);
    }

    /**
     * 回收资源表的逻辑  回收只能回收给群组
     *
     * @param ComName
     * @param username
     * @param approval_name
     * @param amount
     * @param bank
     * @return
     */
    public boolean Request_permission(String ComName, String username, String approval_name, BigDecimal amount, String bank) {
        Connection connection = CRUD_Utils.getConnection();
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //

        boolean b = comDao.request_permission(connection, ComName, username, approval_name, amount, bank);
        try {
           if(bank.equals("Com")) {
               //企业
               boolean b1 = comDao.log_trade(connection, approval_name,"回收资源" ,username, amount, bank,ComName);
               //   boolean c  = comDao.pay_com(connection,ComName,amount);//即把资金回收给企业//a
               if (b && b1) {
                   connection.commit();
                   logger.info("资金回收给企业");
                   return true;
               } else {
                   connection.rollback();
                   logger.info("Request_permission回滚中");
                   return false;
               }
           }
            else{
                //个人
               boolean b1 = comDao.log_trade(connection, approval_name, "回收资源",username, amount, bank,"null");
                   //回收给个人
               boolean b2 = comDao.request_user(connection, username, amount);
               boolean b3 = comDao.pay_com(connection, ComName, amount);
               //公司账户
                   if (b1 && b2 && b&&b3) {
                       connection.commit();
                       return true;
                   }
                   connection.rollback();
                   logger.info("Request_permission回滚中");
                   return false;
               }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JDBCutil.close(connection, null, null);
        }
    }

    public boolean payPermission(String ComName, String username, String approval_name, BigDecimal amount, String bank){
        Connection connection = CRUD_Utils.getConnection();
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            if(bank.equals("Com")){
                //用公司账户分配资源
                boolean b = comDao.pay_permission(connection, ComName, username, approval_name, amount, bank);
                boolean c = comDao.log_trade(connection,approval_name,"分配资源",username,amount,bank,ComName);
                logger.info("公司账户分配资源");
                if(b&&c) {
                    logger.info("分配成功");
                    connection.commit();
                    JDBCutil.close(connection,null,null);
                    return true;
                }else{
                    connection.rollback();
                    JDBCutil.close(connection,null,null);
                    return false;
                }

            }else{
                //用自己账户分配资源
                boolean b = comDao.pay_user(connection, username, amount);
                boolean d= comDao.log_trade(connection,username,"分配资源",approval_name,amount,bank,ComName);
                if(b&&d){
                   connection.commit();
                    JDBCutil.close(connection,null,null);
                    return true;
                }else{
                    connection.rollback();
                    JDBCutil.close(connection,null,null);
                    return  false;
                }

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 得到该公司的用户加入申请表
     * @param ComName
     * @return
     */
    @Override
    public List<JoinComMsg> get_join_user(String ComName) {
        List<JoinComMsg> userJoin = comDao.get_user_join(ComName);
        if(userJoin.isEmpty()){
            logger.error("申请为空");
            return null;
        }
        return userJoin;
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
     * @param
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
    public boolean sendMsg(String sender, String receiver, String content) {
        return comDao.sendMsg(sender,receiver,content);
    }

    /**
     * 得到该公司分配给员工的所有资金信息
     * @param username
     * @return
     */
    @Override
    public List<permission> getfund(String username) {
        return comDao.getfund(username);
    }

    /**
     * 得到该用户在公司目前的所有资金
     * @param username
     * @return
     */
    public BigDecimal get_user_com_fund(String username){
        List<permission> getfund = comDao.getfund(username);
        BigDecimal bigDecimal = new BigDecimal(0);
        if(getfund!=null){
            for (permission permission : getfund) {
                bigDecimal.add(permission.getPer_amount());
            }
        }
        return bigDecimal;
    }


    @Override
    public List<Com_relation> getRelation(String ComName) {
        return comDao.getRelation(ComName);
    }

    @Override
    public List<Company> getComName(String username){
        return comDao.getComName(username);
    }

    @Override
    public boolean delete_join(String username, String ComName) {
        return comDao.delete_join(username,ComName);
    }

    @Override
    public List<Company> getallCom() {
        return comDao.getallCom();
    }
    public List<Company> getisfreeCom(boolean b){
        return comDao.getisfreeCom(b);
    }
}
