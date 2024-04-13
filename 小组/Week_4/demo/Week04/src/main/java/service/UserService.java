package service;

import po.User;

public interface UserService {
//登录
    User login(String username,String password);
    //注册
    boolean register(String username,String password);


}
