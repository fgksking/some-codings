package Dao.UserDaoImpl;

import Dao.adminDao;
import po.Company;
import po.User;
import utils.CRUD_Utils;
import utils.MyHander;

import java.util.List;

public class adminDaoImpl implements adminDao {
    @Override
    public List<Company> getNewCom() {
        String mysql = "select * from company";
        List<Company> query = CRUD_Utils.query(mysql, new MyHander<>(Company.class));
        if(query.isEmpty()){
            return null;
        }else {
            return query;
        }
    }
    @Override
    public List<User> getAllUser(){
        String mysql ="select * from user";
        List<User> query = CRUD_Utils.query(mysql, new MyHander<>(User.class));
        if(query.isEmpty()){
            return null;
        }else{
            return query;
        }
    }

    @Override
    public boolean is_froze_user(String username,boolean b) {
        String mysql = "update user set is_frozen=? where username=?";
        int i = CRUD_Utils.executeUpdate(mysql, b, username);
        return i==1;
    }
}
