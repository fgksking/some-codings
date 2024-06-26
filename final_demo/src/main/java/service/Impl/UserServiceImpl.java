package service.Impl;

import Dao.UserDaoImpl.UserDaoImpl;
import ch.qos.logback.core.net.ssl.SSL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import po.*;
import service.UserService;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Base64;
import java.util.List;


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
        User_pwd user = userDao.findUser(username, password);
        //密码正确后
        if(user==null){
            return null;
        }
        User user2 = userDao.findUser(username);
        return user2;
    }

    public boolean putRSA(String username,String privateKey,String publicKey){
        return userDao.putRSA(username,privateKey,publicKey);
    }
    public rsaEnity getRSApk(String username){
        List<rsaEnity> rsApk = userDao.getRSApk(username);
        if(rsApk.isEmpty()){
            return null;
        }else{
            return rsApk.get(0);
        }
    }


    public User FindUser(String email){
        return userDao.FindUser(email);
    }

    @Override
    public User findUser(String username) {
        return userDao.findUser(username);
    }

    /**获取用户的账户资金
     *
     * @param username
     * @return
     */
    @Override
    public BigDecimal fund(String username) {
        return userDao.fund(username);
    }

    @Override//修改密码
    public Boolean change(String username, String password) {

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
        return userDao.change(username,password);
    }

    @Override//注册
    public Boolean register(String username, String password,String email) {
        //判断用户是否存在
        User user = userDao.FindUser(email);
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
            //生成密钥和私钥
            try {
                KeyPairGenerator kpGen = KeyPairGenerator.getInstance("RSA");
                kpGen.initialize(512);
                KeyPair kp = kpGen.generateKeyPair();
                PrivateKey sk = kp.getPrivate();
                PublicKey pk = kp.getPublic();
                String encodedPrivateKey = Base64.getEncoder().encodeToString(sk.getEncoded());
                String encodedPublicKey = Base64.getEncoder().encodeToString(pk.getEncoded());
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
            register_user_Bank(username,100);
            return b;
        }else{
            logger.info("用户名已存在");
            return false;
        }

    }

    @Override
    public boolean frozen(String username) {
        return userDao.frozen(username);
    }

    public List<Message> get_user_meg(String receiver){
        List<Message> userMsg = userDao.get_user_msg(receiver);
        if(userMsg.isEmpty()){
            return null;
        }
        return userMsg;
    }
    @Override
    public boolean de_frozen(String username) {
        return userDao.de_frozen(username);
    }

    @Override
    public List<trade> get_trade(String username) {
        List<trade> trade = userDao.get_trade(username);
        if(trade.isEmpty()){
            return null;
        }else{
            return trade;
        }
    }

    public List<User> getAlluser(){
        List<User> alluser = userDao.getAlluser();
        if(alluser.isEmpty()){
            return null;
        }
            return alluser;

    }

    @Override
    public boolean pay(BigDecimal bigDecimal,String username) {
        //此资金用作支付，需冻结，存到数据库里
        return userDao.pay(bigDecimal,username);
    }

    @Override
    public boolean clear_temp_fun(String bigDecimal) {
        return userDao.clear_temp_fun(bigDecimal);
    }


    @Override
    public Boolean register_user_Bank(String username, Integer amount) {
        Boolean b= userDao.register_user_Bank(username,amount);
        if(b==false){
            logger.debug("用户资金表初始化错误");
            throw new RuntimeException();
        }
        return b;
    }

    /**
     *
     * @param username
     * @return     头像路径
     */
    @Override
    public String path(String username) {
        return userDao.path(username);
    }

    /**
     *
     * @param username
     * @param  //修改的头像路径
     * @return
     */
    @Override
    public boolean changPath(String username, String url) {
        return userDao.changePath(username,url);
    }
}
