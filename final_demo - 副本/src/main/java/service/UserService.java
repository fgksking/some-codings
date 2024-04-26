package service;

import po.User;
import po.trade;

import java.math.BigDecimal;
import java.util.List;

public interface UserService {

    User login(String username,String password);

    Boolean register(String username,String password, String email);
    Boolean change(String username,String password);

    boolean clear_temp_fun(String bigDecimal);
    Boolean register_user_Bank(String username, Integer amount);
    User findUser(String username);

    String path(String username);
   /* User FindUser(String email);*/

    BigDecimal fund(String username);

    boolean pay(BigDecimal bigDecimal,String username);
    boolean changPath(String username,String url);

    boolean frozen(String username);
    boolean de_frozen(String username);
    public List<trade> get_trade(String username);



}
