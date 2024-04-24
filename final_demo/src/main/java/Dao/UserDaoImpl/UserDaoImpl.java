package Dao.UserDaoImpl;

import Dao.UserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import po.User;
import po.User_fund;
import po.User_pwd;
import po.trade;
import utils.CRUD_Utils;
import utils.MyHander;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class UserDaoImpl implements UserDao {
    public Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public User_pwd findUser(String username, String password) {
        String mysql = "select * from user where username=? and password=?";
        List<User_pwd> query = CRUD_Utils.query(mysql, new MyHander<>(User_pwd.class), username, password);
        if(query.isEmpty()){
            logger.info("查不到此用户名为"+username+" 的用户");
            return null;
        }else {
            logger.info("用户信息存在");
            return query.get(0);

        }
    }

    @Override
    public User findUser(String username) {
        String mysql = "select * from user where username=?";
        List<User> query = CRUD_Utils.query(mysql, new MyHander<>(User.class), username);
        if(query.isEmpty()){
            logger.info("查不到此用户名为"+username+" 的用户");
            return null;
        }else {
            logger.info("用户信息存在");
            return query.get(0);

        }
    }
    public User FindUser(String username,String email){
        String mysql = "select * from user where username=? and email=?";
        List<User> query = CRUD_Utils.query(mysql, new MyHander<>(User.class), username,email);
        if(query.isEmpty()){
            logger.info("查不到此用户名为"+username+" 的用户");
            return null;
        }else {
            logger.info("用户信息存在");
            return query.get(0);

        }
    }

    @Override
    public BigDecimal fund(String username) {
        String mysql ="select * from user_fund where username=?";
        List<User_fund>  userFunds = CRUD_Utils.query(mysql,new MyHander<>(User_fund.class),username);
        if(userFunds.isEmpty()){
            logger.debug("用户资金不存在");
            return null;
        }else{
            return userFunds.get(0).getUser_amount();
        }
    }

    @Override
    public Boolean register(String username, String password,String email ) {
        String mysql="insert into user (username,password,email) values(?,?,?)";
        int result = CRUD_Utils.executeUpdate(mysql,username,password,email);
        //成功插入则返回1
        return result==1;
    }

    @Override
    public Boolean register_user_Bank(String username, String password, Integer amount) {
        String mysql ="insert into user_fund (username,bank_user_pwd,user_amount) values(?,?,?)";
        int result= CRUD_Utils.executeUpdate(mysql,username,password,amount);
        return result==1;
    }

    @Override
    public boolean pay(BigDecimal bigDecimal,String username) {
        boolean b= true;
        Connection connection = CRUD_Utils.getConnection();
        if(connection!=null) {
            String mysql = "update user_fund set amount=amount-? where username=?";
            String mysql2 = "insert into temp_fund (username,amount)values(?,?)";

            try {
                connection.setAutoCommit(false);

                int i = CRUD_Utils.executeUpdate(mysql, connection, mysql,bigDecimal,username);
                int j =CRUD_Utils.executeUpdate(mysql2,connection,username,bigDecimal);
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
    public boolean frozen(String username) {
        String mysql = "update user set is_frozen=true where username=?";
        int result=CRUD_Utils.executeUpdate(mysql,username);
        return result==1;
    }

    @Override
    public boolean de_frozen(String username) {
        String mysql = "update user set is_frozen=false where username=?";
        int result=CRUD_Utils.executeUpdate(mysql,username);
        return result==1;
    }


    public List<trade> get_trade(String username) {
        String mysql= "select * from trade where username=?";
        List<trade> query = CRUD_Utils.query(mysql, new MyHander<>(trade.class), mysql);
        return query;
    }

    @Override//修改
    public Boolean change(String username, String password) {
        String mysql="update user set password=? where username=?";
        int result = CRUD_Utils.executeUpdate(mysql,password,username);
        //成功插入则返回1
        return result==1;
    }

    @Override//修改头像
    public boolean change_photo(String username, String photo_url) {
        String mysql = "update user set photo_url=? where username=?";

        return false;
    }

    /**
     *
     * @param username
     * @return   头像路径
     */
    @Override
    public String path(String username) {
        String mysql = "select * from user where username=?";
        List<User> user =CRUD_Utils.query(mysql,new MyHander<>(User.class),username);
        if(user!=null) {
            return user.get(0).getPhoto_url();
        }else {
            logger.info("用户名为空");
            return null;
        }
    }

    @Override
    public boolean changeName(String oldname, String newname) {
        String mysql = "update user set username=? where username=?";
        int i=CRUD_Utils.executeUpdate(mysql,newname,oldname);
        if(i==1){
            return true;
        }else {
            logger.debug("修改用户名失败");
            return false;
        }

    }

    @Override
    public boolean changePath(String username, String url) {
        String mysql = "update user set photo_url=? where username=?";
        int i=CRUD_Utils.executeUpdate(mysql,url,username);
        if(i==1){
            return true;
        }else {
            logger.debug("修改头像路径失败");
            return false;
        }
    }
}
