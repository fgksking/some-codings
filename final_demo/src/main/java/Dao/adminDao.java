package Dao;

import po.Company;
import po.User;

import java.math.BigDecimal;
import java.util.List;

public interface adminDao {
    List<Company> getNewCom();
    List<User> getAllUser();

    boolean is_froze_user(String username, boolean b);



}
