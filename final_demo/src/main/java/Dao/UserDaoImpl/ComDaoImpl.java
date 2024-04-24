package Dao.UserDaoImpl;

import Dao.ComDao;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import po.Com_relation;
import po.Company;
import po.permission;
import po.trade;
import utils.CRUD_Utils;
import utils.JDBCutil;
import utils.MyHander;

import java.math.BigDecimal;
import java.security.Permission;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
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

            try {
                connection.setAutoCommit(false);

                int i = CRUD_Utils.executeUpdate(mysql, connection,ComName);
                int j =CRUD_Utils.executeUpdate(mysql2,connection,ComName,username,role);
                if(i==1&&j==1){
                    //commit
                    connection.commit();
                    logger.info("关系表创建成功");
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
    public ArrayList<String> getComName(String username) {
        String mysql = "select * from com_relation where username=?";
        List<Com_relation> query  =CRUD_Utils.query(mysql,new MyHander<>(Com_relation.class),username);
        if(query.isEmpty()){
            return null;
        }else{

           ArrayList<String> list = new ArrayList<>();
            for (Com_relation con : query) {
                list.add(con.getComName());
            }
            return list;
        }

    }

    @Override
    public boolean pay(String username, BigDecimal amount,String ComName) {
        boolean b= true;
        Connection connection = CRUD_Utils.getConnection();
        if(connection!=null) {
            String mysql = "update permission set per_amount=per_amount-? where username=?";
            String mysql2 = "insert into temp_fund (ComName,amount)values(?,?)";

            try {
                connection.setAutoCommit(false);
                int i = CRUD_Utils.executeUpdate(mysql, connection,amount,username);
                int j =CRUD_Utils.executeUpdate(mysql2,connection,ComName,amount);
                if(i==1&&j==1){
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
    public boolean createRelation(String ComName) {
        return false;
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
        String mysql = "select * from trade where bank=?";
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
        String mysql = "update user company is_frozen=true where ComName=?";
        int result = CRUD_Utils.executeUpdate(mysql,ComName);
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
        String mysql = "update user company is_frozen=false where ComName=?";
        int result = CRUD_Utils.executeUpdate(mysql,ComName);
        return result==1;
    }





    /**
     * 先删除公司的relations 再删除公司
     * @param ComName
     * @return
     */
    @Override
    public boolean destroyCom(String ComName) {
        String mysql ="delete from com_relation where ComName=?";
        String mysql2 ="delete from company where ComName = ?";
        Connection connection = CRUD_Utils.getConnection();
        if(connection!=null) {
            try {
                connection.setAutoCommit(false);
                int i = CRUD_Utils.executeUpdate(mysql, connection,ComName);
                int j =CRUD_Utils.executeUpdate(mysql2,connection,ComName);
                if(i==1&&j==1){
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
