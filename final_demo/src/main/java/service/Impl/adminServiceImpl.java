package service.Impl;

import Dao.UserDaoImpl.adminDaoImpl;
import po.Company;
import po.User;
import service.adminService;

import java.util.ArrayList;
import java.util.List;

public class adminServiceImpl implements adminService {

    private adminDaoImpl adminDao = new adminDaoImpl();
    @Override
    public List<Company> getNewCom() {
        List<Company> newCom = adminDao.getNewCom();
        List<Company> list = new ArrayList<>();
        if(newCom!=null){
            for (Company company : newCom) {
                if(!company.isApply()){
                    list.add(company);
                }
            }
        }
        return list;
    }

    @Override
    public boolean is_froze_user(String username, boolean b) {
        return adminDao.is_froze_user(username, b);
    }

    @Override
    public List<User> getAllUser() {
        return adminDao.getAllUser();
    }
}
