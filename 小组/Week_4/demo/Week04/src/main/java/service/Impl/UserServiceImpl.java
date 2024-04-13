package service.Impl;

import Dao.Impl.UserDaoImpl;
import po.User;
import service.UserService;

public class UserServiceImpl implements UserService {


    private UserDaoImpl userDao = new UserDaoImpl();

    @Override
    public User login(String username, String password) {
        //连接Dao层
        User user = userDao.findisUser(username, password);
        return user;

    }

    @Override
    public boolean register(String username, String password) {

        //先判断用户是否存在true  or  false
        User user = userDao.findisUser(username,password);
        if(user==null){
            //用户不存在
            //insert
            userDao.addUser(username, password);
            return true;
        }else{

            return false;
        }

    }
}
