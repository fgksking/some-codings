package service;

import po.Company;
import po.User;

import java.util.List;

public interface adminService {
    List<Company> getNewCom();
    List<User> getAllUser();
    boolean is_froze_user(String username,boolean b);
}
