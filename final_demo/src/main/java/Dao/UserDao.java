package Dao;

import po.JoinComMsg;
import po.User;
import po.User_pwd;
import po.trade;

import java.math.BigDecimal;
import java.util.List;

public interface UserDao {
    //登陆查询
    User_pwd findUser(String username, String password);

    //注册
    Boolean register(String username,String password,String email);

    User findUser(String username);
    Boolean change(String username,String password);
    Boolean register_user_Bank(String username,Integer amount);

    BigDecimal fund(String username);

    boolean clear_temp_fun(String bigDecimal);
 /*   User FindUser(String username,String email);*/
    //修改头像
    boolean change_photo(String username,String photo_url);

    //修改用户名
    boolean changeName(String oldname,String newname);
    String path(String username);

    boolean changePath(String username,String url);

    boolean pay(BigDecimal bigDecimal,String username);

    List<trade> get_trade(String username);

    public List<User> getAlluser();

    boolean frozen(String username);
    boolean de_frozen(String username);

}
