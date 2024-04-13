package Dao;

import po.User;

import java.util.List;

public interface UserDao {

    User findisUser(String username,String password);

    boolean addUser(String username,String password);

}
