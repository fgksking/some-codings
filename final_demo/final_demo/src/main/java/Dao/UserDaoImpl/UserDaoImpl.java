package Dao.UserDaoImpl;

import Dao.UserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import po.User;
import utils.CRUD_Utils;
import utils.MyHander;

import java.util.List;

public class UserDaoImpl implements UserDao {
    public Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public User findUser(String username, String password) {
        String mysql = "select * from user where username=? and password=?";
        List<User> query = CRUD_Utils.query(mysql, new MyHander<>(User.class), username, password);
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

    @Override
    public Boolean register(String username, String password,String email ) {
        String mysql="insert into user (username,password,email) values(?,?,?)";
        int result = CRUD_Utils.executeUpdate(mysql,username,password,email);
        //成功插入则返回1
        return result==1;
    }

    @Override
    public Boolean register_user_Bank(String username, String password, Integer amount) {
        String mysql ="insert into user (username,password,amount) values(?,?,?)";
        int result= CRUD_Utils.executeUpdate(mysql,username,password,amount);
        return result==1;
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
}
