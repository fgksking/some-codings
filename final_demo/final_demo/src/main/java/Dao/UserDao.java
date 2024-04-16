package Dao;

import po.User;

public interface UserDao {
    //登陆查询
    User findUser(String username,String password);

    //注册
    Boolean register(String username,String password,String email);

    User findUser(String username);
    Boolean change(String username,String password);
    Boolean register_user_Bank(String username,String password,Integer amount);

    //修改头像
    boolean change_photo(String username,String photo_url);

    //修改用户名
    boolean changeName(String oldname,String newname);

}
