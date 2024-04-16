package service.Impl;

import Dao.UserDaoImpl.UserDaoImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import po.User;
import service.UserService;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UserServiceImpl implements UserService {

    //定义固定的加盐
    private static final String salut = "sault";
    public Logger logger = LoggerFactory.getLogger(getClass());
    private UserDaoImpl userDao = new UserDaoImpl();
    @Override
    public User login(String username, String password) {
        //对密码进行加密处理
        //加盐处理
        MessageDigest md;
        try {

            md = MessageDigest.getInstance("MD5");

        } catch (NoSuchAlgorithmException e) {
            logger.debug("加密出错了");
            throw new RuntimeException(e);
        }
        md.update((password+salut).getBytes(StandardCharsets.UTF_8));
        byte[] result = md.digest();
        //转16进制
        password = new BigInteger(1,result).toString(16);
        //如何再调用Dao层
        User user = userDao.findUser(username, password);
        return user;
    }

    @Override
    public User findUser(String username) {
        return userDao.findUser(username);
    }

    @Override//修改密码
    public Boolean change(String username, String password) {
        return userDao.change(username,password);
    }

    @Override//注册
    public Boolean register(String username, String password,String email) {
        //判断用户是否存在
        User user = userDao.findUser(username);

        if(user==null) {
            //对密码进行加密处理
            //        //加盐处理
            //        String salut = "sault";
            String salut = "sault";
            MessageDigest md;
            try {

                md = MessageDigest.getInstance("MD5");

            } catch (NoSuchAlgorithmException e) {
                logger.debug("加密出错了");
                throw new RuntimeException(e);
            }
            md.update((password + salut).getBytes(StandardCharsets.UTF_8));
            byte[] result = md.digest();
            //转16进制
            password = new BigInteger(1, result).toString(16);
            //调用Dao
            logger.info("加密后的"+password);
            logger.info(username);
            Boolean b =userDao.register(username, password,email);
            //默认注册密码，初始资金100
            register_user_Bank(username,password,100);
            return b;
        }else{
            logger.info("用户名已存在");
            return false;
        }

    }

    @Override
    public Boolean register_user_Bank(String username, String password, Integer amount) {
        Boolean b= userDao.register_user_Bank(username,password,amount);
        if(b==false){
            logger.debug("用户资金表初始化错误");
            throw new RuntimeException();
        }
        return b;
    }


}
