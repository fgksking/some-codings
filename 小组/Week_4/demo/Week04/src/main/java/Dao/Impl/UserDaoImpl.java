package Dao.Impl;

import Dao.UserDao;
import po.User;
import utils.CRUD_Utils;
import utils.MyHander;

import java.util.List;

public class UserDaoImpl implements UserDao {

    @Override
    public User findisUser(String username,String password) {
        String mysql = "select * from user where username=? and password=?";
        List<User> query = CRUD_Utils.query(mysql, new MyHander<>(User.class), username, password);
        if(query.isEmpty()){
            return null;
        }else {
            return query.get(0);
        }


    }

    @Override
    public boolean addUser(String username, String password) {
        String mysql="insert into user values(username=?,password=?)";
        int result = CRUD_Utils.executeUpdate(mysql,username,password);
        //result==1 则插入成功
        return result==1;
    }
}
