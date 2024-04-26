package Dao.UserDaoImpl;

import Dao.ComDao;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import po.*;
import utils.CRUD_Utils;
import utils.JDBCutil;
import utils.MyHander;

import java.math.BigDecimal;
import java.security.Permission;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ComDaoImpl implements ComDao {

    public Logger logger = LoggerFactory.getLogger(getClass());
    @Override
    public void create_Com(String username, String ComName, String ComSize, String ComDirection,boolean ComIspublic) {
        try {
            String mysql="insert into Company (username,ComName,ComSize,ComDirection,ComIspublic) values (?,?,?,?,?)";
            int result = CRUD_Utils.executeUpdate(mysql,username,ComName,ComSize,ComDirection,ComIspublic);
        } catch (Exception e) {
            logger.debug("插入公司出错",e);
            throw new RuntimeException(e);
        }

    }

    /**
     *
     * @param ComName  公司名
     * @param username  员工名
     * @param role      员工的角色
     * @return
     */
    @Override
    public boolean create_Com_relation(String ComName, String username, String role) {
        String mysql = "insert into com_relation (ComName,username,role) values(?,?,?)";
        int i = CRUD_Utils.executeUpdate(mysql, ComName, username, role);
        if(i==1)
        { return true;}
        else {
            logger.debug("公司负责人表插入出错");
            return false;
        }

    }



    /**
     *    批准成为公司，并创建公司关系表，采取了事务操作
     * @param ComName
     * @param username
     * @param role
     * @return
     */
    @Override
    public boolean approvalCom(String ComName,String username,String role) {
       //公司的isApply为true;
        //用回滚
        boolean b= true;
        Connection connection = CRUD_Utils.getConnection();
        if(connection!=null) {
            String mysql = "update company set IsApply=true where ComName=?";
            String mysql2 = "insert into com_relation(ComName,username,role) values(?,?,?)";
            String s = "insert into com_fund (ComName,com_amount) values(?,?)";
            String s2 = "update user set role=? where username=?";

            try {
                connection.setAutoCommit(false);

                int i = CRUD_Utils.executeUpdate(mysql, connection,ComName);
                int j =CRUD_Utils.executeUpdate(mysql2,connection,ComName,username,role);
                int q = CRUD_Utils.executeUpdate(s,connection,ComName,1000);
                int y = CRUD_Utils.executeUpdate(s2,connection,role,username);
                if(i==1&&j==1&&q==1){
                    //commit
                    connection.commit();
                    logger.info("企业创建成功");
                }else {
                    connection.rollback();
                    logger.info("关系事务回滚中");
                    return false;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
                CRUD_Utils.closeConnection(connection);
            }

            return true;
        }
        return false;
    }

    @Override
    public List<Com_relation> getRelation(String ComName) {
        String mysql ="select * from com_relation where ComName=?";
        List<Com_relation> query = CRUD_Utils.query(mysql, new MyHander<>(Com_relation.class), ComName);
        if(query.isEmpty()){
            logger.info("公司为空？");
            return null;
        }
        return query;
    }

    @Override
    public List<Company> getComName(String username) {
        String mysql = "select * from company where username=?";
        List<Company> query  =CRUD_Utils.query(mysql,new MyHander<>(Company.class),username);
        if(query.isEmpty()){
            return null;
        }else{
            return query;
        }

    }

    @Override
    public boolean request_permission(Connection con,String ComName, String username, String approval_name, BigDecimal amount, String bank) {
        String s = "update permission set per_amount=per_amount-? where username=?";
        int i = CRUD_Utils.executeUpdate(s, con, amount, username);
        return i==1;
    }

    /**
     * 用公司账户分配资源表资金
     * @param con
     * @param ComName
     * @param username
     * @param approval_name
     * @param amount
     * @param bank
     * @return
     */
    public boolean pay_permission(Connection con,String ComName, String username, String approval_name, BigDecimal amount, String bank) {
        String s = "update permission set per_amount=per_amount+? where username=?";
        int i = CRUD_Utils.executeUpdate(s, con, amount, username);
        return i==1;
    }

    public boolean request_com(Connection con,String ComName,BigDecimal amount){
        String s = "update com_fund set com_amount=com_amount+? where ComName=?";
        int i = CRUD_Utils.executeUpdate(s, con, amount,ComName);
        return i==1;
    }
    public boolean pay_com(Connection con,String ComName,BigDecimal amount){
        String s = "update com_fund set com_amount=com_amount-? where ComName=?";
        int i = CRUD_Utils.executeUpdate(s, con, amount,ComName);
        return i==1;
    }
    public boolean com_pay(String ComName,BigDecimal bigDecimal){
        String s ="update com_fund set com_amount=com_amount-? where ComName=?";
        int i = CRUD_Utils.executeUpdate(s, bigDecimal, ComName);
        return i==1;
    }

    @Override
    public BigDecimal get_com_fund(String ComName) {
        String s = "select * from com_fund where ComName=?";
        List<Com_fund> query = CRUD_Utils.query(s,new MyHander<>(Com_fund.class),ComName);
        if(query.isEmpty()){
            logger.info("get_com_fund公司为空");
            return new BigDecimal(0);
        }else{
            return  query.get(0).getCom_amount();
        }
    }


    public boolean request_user(Connection con, String username, BigDecimal amount){
        String s = "update user_fund set user_amount=user_amount+? where username=?";
        int i = CRUD_Utils.executeUpdate(s, con, amount,username);
        return i==1;
    }

   public boolean pay_user(Connection con,String username,BigDecimal amount){
        String s = "update user_fund set user_amount=user_amount-? where username=?";
        int i = CRUD_Utils.executeUpdate(s, con, amount,username);
        return i==1;
    }

    @Override
    public boolean delete_join(String username, String ComName) {
        String s  ="delete * from joincom where username=? and ComName =?";
        int i = CRUD_Utils.executeUpdate(s, username, ComName);
        return i==1;
    }

    /**
     * 用公司账户支付
     * @param username
     * @param amount
     * @param ComName
     * @return
     */
    @Override
    public boolean pay(String username, BigDecimal amount,String ComName) {
        boolean b= true;
        Connection connection = CRUD_Utils.getConnection();
        if(connection!=null) {
            String mysql = "update permission set per_amount=per_amount-? where username=?";
            String s = "update com_fund set com_amount=com_amount-? where ComName=?";
            String mysql2 = "insert into temp_fund (ComName,amount)values(?,?)";

            try {
                connection.setAutoCommit(false);
                int i = CRUD_Utils.executeUpdate(mysql, connection,amount,username);
                int q = CRUD_Utils.executeUpdate(s,connection,amount,ComName);
                int j =CRUD_Utils.executeUpdate(mysql2,connection,ComName,amount);
                if(i==1&&j==1&&q==1){
                    //commit
                    connection.commit();
                    logger.info("资金冻结");
                }else {
                    connection.rollback();
                    logger.info("关系事务回滚中");
                    return false;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
                CRUD_Utils.closeConnection(connection);
            }

            return true;
        }
        return false;
    }

    @Override
    public boolean log_trade(Connection con,String username,String type, String target, BigDecimal amount, String bank,String ComName) {
        String mysql ="insert into trade(username,traget,trade_amount,bank,trade_time,ComName) values(?,?,?,?,?)";
        int i = CRUD_Utils.executeUpdate(mysql, con,username, target, amount, bank, System.currentTimeMillis(),ComName);
        return i==1;
    }
    public boolean log_trade(String username, String target,String type, BigDecimal amount, String bank) {
        String mysql = "insert into trade(username,traget,trade_type,trade_amount,bank,trade_time) values(?,?,?,?,?,?)";
        int i = CRUD_Utils.executeUpdate(mysql, username, target,type, amount, bank, System.currentTimeMillis());
        return i == 1;
    }
    public boolean log_trade(String username, String target,String type, BigDecimal amount, String bank,String ComName){
        String mysql ="insert into trade(username,traget,trade_amount,bank,trade_time,ComName) values(?,?,?,?,?,?)";
        int i = CRUD_Utils.executeUpdate(mysql,username, target, amount, bank, System.currentTimeMillis(),ComName);
        return i==1;
    }
    @Override
    public boolean admin_pay(String username, BigDecimal amount,String bank) {
        if(bank.equals("")){

        }
        String mysql = "update permission set per_amount=per_amount+? where username=?";
        return true;
    }

    @Override
    public List<JoinComMsg> get_user_join(String ComName) {
        String mysql = "select * from joincom where ComName=?";
        return CRUD_Utils.query(mysql,new MyHander<>(JoinComMsg.class),ComName);
    }

    @Override
    public boolean createRelation(String ComName) {
        return  false;

    }


    /**
     *
     * @param ComName
     * @return     公司是否存在
     */
    @Override
    public boolean Is_Com_exist(String ComName) {
        String mysql="select * from company where ComName=?";
        List<Company> query = CRUD_Utils.query(mysql, new MyHander<>(Company.class), ComName);
        if(query.isEmpty()){
            logger.info("公司创建成功");
            return true;
        }else {
            logger.info("公司名信息已存在");
            return false;

        }
    }

    /**
     * 用户在公司的资金
     * @param username
     * @return
     */
    @Override
    public List<permission> getfund(String username) {
        String mysql = "select * from permission where username=?";
        List<permission> query = CRUD_Utils.query(mysql, new MyHander<>(permission.class), username);
        if(query.isEmpty()){
            return null;
        }else{
            return query;
        }
    }

    /**
     * 通过用户名找到ComName
     * @param username
     * @return
     */
    @Override
    public List<Com_relation> get_relation(String username) {
        String mysql = "select * from com_relation where username=?";
        List<Com_relation> query = CRUD_Utils.query(mysql, new MyHander<>(Com_relation.class), username);
        return query;
    }

    @Override
    public List<trade> getComTrade(String ComName) {
        String mysql = "select * from trade where ComName=?";
        List<trade> query = CRUD_Utils.query(mysql, new MyHander<>(trade.class), ComName);
        return query;
    }

    @Override
    public List<Company> getallCom() {
        String mysql = "select * from company";
        List<Company> query = CRUD_Utils.query(mysql, new MyHander<>(Company.class));
        if(query.isEmpty()){
            return null;
        }else{
            return query;
        }
    }

    /**
     * 从公司表中找ComName
     * @param ComName
     * @return
     */
    @Override
    public Company findCom(String ComName) {
        String mysql = "select * from company where ComName=?";
        List<Company> query = CRUD_Utils.query(mysql, new MyHander<>(Company.class), ComName);
        if(query.isEmpty()){
            return null;
        }else {
            return query.get(0);
        }
    }



    @Override
    public boolean disapproval(String ComName) {
        String mysql = "delete from company where ComName=?";
        int i=1;
        return i == CRUD_Utils.executeUpdate(mysql,ComName);
    }

    @Override
    public boolean exitCom(String username) {
        String mysql = "delete from com_relation where username=?";
        int i = CRUD_Utils.executeUpdate(mysql, username);
        return i==1;
    }

    @Override
    public boolean frozen(String ComName) {
        String mysql = "update company set is_frozen=? where ComName=?";
        int result = CRUD_Utils.executeUpdate(mysql,true,ComName);
        return result==1;
    }

    @Override
    public List<Company> getisfreeCom(boolean b) {
        String mysql = "select * from company where is_frozen=?";
        List<Company> query = CRUD_Utils.query(mysql, new MyHander<>(Company.class), b);
        if(query.isEmpty()){
            return null;
        }
        return  query;
    }

    @Override
    public boolean de_frozen(String ComName) {
        String mysql = "update company set is_frozen=? where ComName=?";
        int result = CRUD_Utils.executeUpdate(mysql,false,ComName);
        return result==1;
    }

    @Override
    public boolean sendMsg(String sender, String receiver, String content) {
        String mysql ="insert into msg (send_name,accept_name,content) values(?,?,?)";
        int i = CRUD_Utils.executeUpdate(mysql, sender, receiver, content);
        return i==1;
    }

    @Override
    public boolean create_permission(String ComName, String username, String approvalName) {
        String mysql = "insert into permission (ComName,username,approval_name,time,per_amount) values(?,?,?,?,?)";
        int i = CRUD_Utils.executeUpdate(mysql, ComName, username, approvalName, System.currentTimeMillis(), 0);
        return i==1;

    }

    /**
     * 先删除公司的relations 再删除公司
     * @param ComName
     * @return
     */
    @Override
    public boolean destroyCom(String ComName,String username) {
        String mysql ="delete from com_relation where ComName=?";
        String y ="delete from com_fund where ComName =?";
        String mysql2 ="delete from company where ComName =?";
        String s = "update user set role = ? where username=?";
        Connection connection = CRUD_Utils.getConnection();
        if(connection!=null) {
            try {
                connection.setAutoCommit(false);
                int i = CRUD_Utils.executeUpdate(mysql, connection,ComName);
                int j =CRUD_Utils.executeUpdate(y,connection,ComName);
                int p = CRUD_Utils.executeUpdate(mysql2,ComName);
                int q = CRUD_Utils.executeUpdate(s,connection,"user",username);
                if(i==1&&j==1&&q==1&&p==1){
                    //commit
                    connection.commit();
                    logger.info("删除公司");
                }else {
                    connection.rollback();
                    logger.info("关系事务回滚中");
                    return false;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
                CRUD_Utils.closeConnection(connection);
            }

            return true;



        }
        return false;

    }

    /**
     *
     * @return
      */
    @Override
    public List<Company> getCom() {
        String mysql = "select * from company";
        List<Company> query = CRUD_Utils.query(mysql,new MyHander<>(Company.class));
        if(query.isEmpty()){
            return null;
        }else {
           // logger.info("公司名信息已存在");
            return query;

        }
    }

    /**
     *
     * @param params  搜索的参数
     * @return    返回符合参数的企业
     */
    @Override
    public List<Company> getCom(String params) {
        String mysql = "select * from company where comName LIKE ?";
        String search = "%"+params+"%";
        List<Company> query = CRUD_Utils.query(mysql,new MyHander<>(Company.class),search);
        System.out.println(query);
        if(query.isEmpty()){
            return null;
        }else {
            return query;

        }
    }

    @Override
    public boolean joinCom(String username, String ComName,long time) {
        String mysql ="insert into joinCom (username,ComName,join_time) values(?,?,?)";
        int result = CRUD_Utils.executeUpdate(mysql,username,ComName,time);
        return result==1;
    }

    /**
     *
     * @param username  用户名
     * @return      返回关系表
     */
    @Override
    public List<Company> get_Com_relation(String username) {
        return null;
    }
}
