package service.Impl;

import Dao.UserDaoImpl.ComDaoImpl;
import po.Company;
import service.ComService;

import java.util.List;

public class ComServiceImpl implements ComService {
    private final ComDaoImpl comDao =new ComDaoImpl();
    @Override
    public void create_Com(String ComName, String ComSize, String ComDirection, boolean ComIspublic) {
        comDao.create_Com(ComName,  ComSize,  ComDirection, ComIspublic);
    }

    @Override
    public boolean Is_Com_exist(String ComName) {
        return comDao.Is_Com_exist(ComName);
    }

    /**
     *
     * @param ComName 公司名
     * @param username  用户
     * @param role    用户在公司角色
     * @return
     */
    @Override
    public boolean create_Com_relation(String ComName, String username, String role) {
        return comDao.create_Com_relation(ComName,username,role);
    }

    @Override
    public List<Company> getCom() {
        return comDao.getCom();
    }

    @Override
    public List<Company> getCom(String params) {
        return comDao.getCom(params);
    }
}
