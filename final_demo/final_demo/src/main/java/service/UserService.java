package service;

import po.User;

public interface UserService {

    User login(String username,String password);

    Boolean register(String username,String password, String email);
    Boolean change(String username,String password);


    Boolean register_user_Bank(String username, String password, Integer amount);
    User findUser(String username);

}
