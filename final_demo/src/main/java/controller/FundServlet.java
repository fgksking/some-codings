package controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import po.*;
import service.Impl.ComServiceImpl;
import service.Impl.UserServiceImpl;
import utils.*;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static ch.qos.logback.core.encoder.ByteArrayUtil.hexStringToByteArray;

@WebServlet("/fundServlet")
public class FundServlet extends BaseServlet{
    private final ComServiceImpl comService = new ComServiceImpl();
    private final UserServiceImpl userService = new UserServiceImpl();
    private static Map<String, Map<String,Key>> Map = new ConcurrentHashMap<>();
    private final Logger logger = LoggerFactory.getLogger(getClass());
    //支付的流程
    @Test
    public void test(){
/*        String params="{username:zhansan,"+"receiver:lisi,"+"bank:user_fund,"+"amount:100}";

        String username= params.split(":")[1].replace("\"", "").replace("}", "");

        String a = "-1100000000000000000";
        System.out.println(Integer.valueOf(a));

 */
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");// 创建AES的Key生产者
            kgen.init(128);// 利用用户密码作为随机数初始化出
            //加密没关系，SecureRandom是生成安全随机数序列，password.getBytes()是种子，只要种子相同，序列就一样，所以解密只要有password就行
            SecretKey secretKey = kgen.generateKey();
            String s = Base64.getEncoder().encodeToString(secretKey.getEncoded());
            System.out.println(s);


        /*    KeyPairGenerator kpGen = KeyPairGenerator.getInstance("RSA");
        kpGen.initialize(512);
        KeyPair kp = kpGen.generateKeyPair();
        PrivateKey sk = kp.getPrivate();
            PublicKey pk = kp.getPublic();
            String encodedPrivateKey = Base64.getEncoder().encodeToString(sk.getEncoded());
            System.out.println(encodedPrivateKey);
            String encodedPublicKey = Base64.getEncoder().encodeToString(pk.getEncoded());
            System.out.println(encodedPublicKey);
            // 待签名的消息:
        byte[] message = "Hello, I am Bob!".getBytes(StandardCharsets.UTF_8);

        // 用私钥签名:
            Signature s = Signature.getInstance("SHA1withRSA");
            s.initSign(sk);
            s.update(message);
            byte[] signed = s.sign();
            System.out.println("signature: " + HexFormat.of().formatHex(signed));

            // 用公钥验证:
            Signature v = Signature.getInstance("SHA1withRSA");
            v.initVerify(pk);
            v.update(message);
            boolean valid = v.verify(signed);
            System.out.println("valid? " + valid);*/
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } /*catch (InvalidKeyException e) {
            throw new RuntimeException(e);*/
     //   } catch (SignatureException e) {
       //     throw new RuntimeException(e);
      //  }
    }

    public void againPay(HttpServletRequest request, HttpServletResponse response) throws IOException {

    }

    public void getkey(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session  =request.getSession();
        String username = (String)session.getAttribute("username");
        Key privateKey1= (Key)session.getAttribute("privateKey");
        Key publicKey1= (Key) session.getAttribute("publicKey");
        // rsaEnity rsApk = userService.getRSApk(username);
        String privateKey;
        String publicKey;
        if(privateKey1==null&&publicKey1==null) {
            KeyPair rsa = RSA.getKey(username);
            privateKey1 = rsa.getPrivate();
            publicKey1 = rsa.getPublic();
            request.getSession().setAttribute("privateKey",privateKey1);
            request.getSession().setAttribute("publicKey",publicKey1);
           /* privateKey = RSA.getPrivateKey(aPrivate);
            publicKey = RSA.getPublicKey(aPublic);
            boolean b = userService.putRSA(username, privateKey, publicKey);
            response.getWriter().write(publicKey);*/
            String publicKey2 = RSA.getPublicKey((PublicKey)publicKey1);
            response.getWriter().write(publicKey2);
            return;
        }
        String publicKey3 = RSA.getPublicKey((PublicKey) publicKey1);
        response.getWriter().write(publicKey3);

       // byte[] decodedPublicKeyBytes = Base64.getDecoder().decode(publicKey);
       /* PublicKey pk;

       // response.getWriter().write(publicKey);

        */
    }
    public void pay(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //post得到数据
      //  String username =(String) request.getSession().getAttribute("username");
      /*  if(rsApk==null){
            KeyPair rsa = RSA.getKey(username);
            PrivateKey aPrivate = rsa.getPrivate();
            PublicKey aPublic = rsa.getPublic();
            privateKey = RSA.getPrivateKey(aPrivate);
            publicKey = RSA.getPublicKey(aPublic);
            boolean b = userService.putRSA(username, privateKey, publicKey);
        }*/
       // else {
    /*        privateKey = rsApk.getPrivateKey();
           // publicKey = rsApk.getPublicKey();
      //  }
     //   byte[] decodedPublicKeyBytes = Base64.getDecoder().decode(publicKey);
        byte[] decodedPrivateKeyBytes = Base64.getDecoder().decode(privateKey);
        PublicKey pk;
        PrivateKey sk;
        try {
        // 4. 将解码后的字节转换为Key对象
          KeyFactory keyFactory = KeyFactory.getInstance("RSA");
          PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(decodedPrivateKeyBytes);
          sk = keyFactory.generatePrivate(privateKeySpec);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }*/
        HttpSession session = request.getSession();
        String username  =(String) session.getAttribute("username");
        PrivateKey privateKey =(PrivateKey) session.getAttribute("privateKey");
        BufferedReader br = request.getReader();
        String params = br.readLine();
        logger.info(params);
        JSONObject jsonObject = JSONObject.parseObject(params);
        String encryptedData = jsonObject.getString("encryptedData");
        //byte[] decode = Base64.getDecoder().decode(encryptedData);
        String encryptedAES = jsonObject.getString("encryptedAES");
        String iv = jsonObject.getString("iv");
        System.out.println(encryptedAES);
        byte[] decode1 = Base64.getDecoder().decode(encryptedAES);
     //   System.out.println(HexFormat.of().formatHex(decode1));
        //byte[] decode1 = encryptedAES.getBytes("utf-8");
        //得到aes密钥
        byte[] decrypt = RSA.decrypt(decode1, privateKey);
        System.out.println(Base64.getEncoder().encodeToString(decrypt));
        String s = (new String(decrypt, "utf-8"));
        String s1 = AES.AESdecrypt(iv,encryptedData, s);
        logger.info(s1);
        JSONObject jsonObject1 = JSONObject.parseObject(s1);
      //  logger.info("解密后数据"+s1);
        username = jsonObject1.getString("username");
        String amount = jsonObject1.getString("amount");
        String bank = jsonObject1.getString("bank");
        String receiver = jsonObject1.getString("receiver");
        String password = jsonObject1.getString("password");
        //检验支付环境
        //前端校验是否有非法字符如   用正则表达式
        BigDecimal bigDecimal  =new BigDecimal(amount);
        User user = userService.findUser(username);
        User receiverUser = userService.findUser(receiver);
        if(receiverUser==null){
            response.getWriter().write(JSON.toJSONString(ServerResponse.createError("收款方不存在")));
            return;
        }
        boolean isFrozen = user.isIs_frozen();
        if(isFrozen){
            response.getWriter().write(JSON.toJSONString(ServerResponse.createError("被封禁中")));
            return;
        }
        String role = user.getRole();
        User login = userService.login(username, password);
        if(login==null){
            response.getWriter().write(JSON.toJSONString(ServerResponse.createError("密码错误")));
            return;
        }
        //从数据库调用余额
        if("User".equals(bank)){
            //用户资金
            BigDecimal fund = userService.fund(username);
            if(fund==null){
                response.getWriter().write(JSON.toJSONString(ServerResponse.createError("用户名不存在")));
                return;
            }else{
                int result = fund.compareTo(bigDecimal);
                if(result>=0){
                    //余额充足
                    //扣除资金到数据库里（frozen）  //冻结资金
                    boolean pay = userService.pay(bigDecimal, username);
                    if(pay){
                        //冻结成功
                        //调用接口
                        Request payRequest = new Request(username,receiver,bank,bigDecimal);
                        PayServiceImpl payService = new PayServiceImpl();
                        boolean payResponse = payService.executePay(payRequest);

                        if(payResponse){
                            //入账第三方成功
                            //清除资金库数据
                            comService.logTrade(username,receiver,"支付",bigDecimal,bank);
                            boolean b = userService.clear_temp_fun(username);
                            if(b){
                                logger.info("清除资金库数据success");
                                //
                                //接口内部第三方平台向收款者发起请求

                                //第三方入账金额到收款方
                                boolean b1 = RqServiceImpl.executeRequest(payRequest);
                                if(b1){
                                    //对余额进行修改
                                    RqServiceImpl.response(payRequest);
                                    //记录交易信息
                                    comService.logTrade(receiver,username,"收款",bigDecimal,"User");
                                    logger.info("支付成功");
                                    response.getWriter().write(JSON.toJSONString(ServerResponse.createSuccess("支付成功")));
                                }else{
                                    logger.debug("对余额进行修改失败");
                                    //记录异常交易信息
                                }
                            }else{
                                logger.error("清除资金库数据失败");

                            }

                        }else {
                            //根据success返回的结果再对数据库的资金处理
                            //此时调用接口，对收款者入账
                            response.getWriter().write(JSON.toJSONString(ServerResponse.createAGAIN("入账失败,请再次提交")));
                            //提示用户是否再次入账
                            //调用 controller
                            userService.clear_temp_fun(username);
                            //若收款者入账失败
                            //再次调用接口入账，失败后则
                            //返回星系
                        }

                    }else{
                        response.getWriter().write(JSON.toJSONString(ServerResponse.createError("冻结失败，请重新支付")));
                        return;
                    }

                }else{
                    response.getWriter().write(JSON.toJSONString(ServerResponse.createError("余额不足")));
                    return;
                }
            }

        }else{
            //群组资金
            BigDecimal comFund;
            BigDecimal getfund;
            String ComName;
            if(role.equals("Com_admin")){
                //用群主账号
              /*  ArrayList<String> comName = comService.getComName(username);
                String s = comName.get(0);
                BigDecimal comFund = comService.get_com_fund(s);*/
                List<Company> comName = comService.getComName(username);
                ComName=comName.get(0).getComName();
                boolean isFrozen1 = comName.get(0).isIs_frozen();
                if(isFrozen1){
                    response.getWriter().write(JSON.toJSONString(ServerResponse.createError("企业被封禁")));
                    return;
                }
                comFund = comService.get_com_fund(ComName);
                int result =  comFund.compareTo(bigDecimal);
                if(result<0){
                    response.getWriter().write(JSON.toJSONString(ServerResponse.createError("余额不足")));
                    return;
                }
            }else{
                List<permission> Permission = comService.getfund(username);
                //未加入企业
                if(Permission==null){
                    response.getWriter().write(JSON.toJSONString(ServerResponse.createError("余额不足")));
                    return;
                }
                ComName= Permission.get(0).getComName();
                getfund = (Permission.get(0).getPer_amount());
                comFund = comService.get_com_fund(Permission.get(0).getComName());
                int result = comFund.compareTo(bigDecimal);
                int result2 = getfund.compareTo(bigDecimal);
                if(result2<0||result<0){
                    response.getWriter().write(JSON.toJSONString(ServerResponse.createError("余额不足")));
                    return;
                }
            }
            //群组资金
            //实际的群组资金//余额充足
            boolean pay;
            if(role.equals("Com_admin")) {
                pay = comService.com_pay(ComName,bigDecimal);
            }else {
                //余额充足
                 pay = comService.pay(username, bigDecimal, ComName);
            }
                if(pay){
                    //已经冻结了
                    //调用接口
                    Request payRequest = new Request(username,receiver,bank,bigDecimal);
                    PayServiceImpl payService = new PayServiceImpl();
                    boolean payResponse = payService.executePay(payRequest);
                    if(payResponse){
                        //入账第三方成功
                        //清除资金库数据
                        comService.logTrade(username,receiver,"支付",bigDecimal,bank,ComName);
                        boolean b = userService.clear_temp_fun(username);
                        if(b){
                            logger.info("清除资金库数据success");
                            //
                            //接口内部第三方平台向收款者发起请求
                            //第三方入账金额到收款方
                            boolean b1 = RqServiceImpl.executeRequest(payRequest);
                            if(b1){
                                //对余额进行修改
                                RqServiceImpl.response(payRequest);
                                //记录交易信息
                                comService.logTrade(receiver,username,"收款",bigDecimal,bank,ComName);
                                response.getWriter().write(JSON.toJSONString(ServerResponse.createSuccess("支付成功")));
                            }else{
                                logger.debug("对余额进行修改失败");
                                //记录异常交易信息
                                //入账第三方失败
                                comService.logTrade(receiver,username,"收款失败",bigDecimal,bank,ComName);
                                response.getWriter().write(JSON.toJSONString(ServerResponse.createError("收款请求失败")));
                            }
                            return;
                        }else{
                            logger.error("清除资金库数据失败");

                        }
                    }

                }else{
                    response.getWriter().write(JSON.toJSONString(ServerResponse.createError("冻结失败")));
                    return;
                }


            //根据支付接口的response结果在进行入账或着 退账
            //并记录此 支付 信息
        }



    }



    public void get_user_com_fund(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        BigDecimal userComFund = comService.get_user_com_fund(username);
        response.getWriter().write(JSON.toJSONString(userComFund));

    }
    public void getmyfund(HttpServletRequest request, HttpServletResponse response) throws IOException{
        //用户资金中心 //自己和公司的账户
        String username = (String) request.getSession().getAttribute("username");
        List<Company> comName = comService.getComName(username);
        BigDecimal userComFund;
        if(comName!=null){
            String ComName = comName.get(0).getComName();
            userComFund = comService.get_com_fund(ComName);
        }else{
            userComFund = comService.get_user_com_fund(username);
        }
        BigDecimal fund = userService.fund(username);
        HashMap map = new HashMap<>();
        map.put("user_bank",fund);
        map.put("com_bank",userComFund);
        String jsonString = JSON.toJSONString(map);
        response.getWriter().write(jsonString);
    }

    public void getTrade(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username =(String) request.getSession().getAttribute("username");
        BigDecimal fund = userService.fund(username);
        List<permission> getfund = comService.getfund(username);
        List<permission> Permission = comService.getfund(username);
        BigDecimal getComfunds = new BigDecimal(0);
        for (permission permission : Permission) {
            getComfunds.add(permission.getPer_amount());
        }
        //response.getWriter().write();
        //未完
    }

    /**
     * 获取交易信息
     * @param request
     * @param response
     * @throws IOException
     */
    public void get_user_trade(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String username = (String)request.getSession().getAttribute("username");
        List<Com_relation> relation = comService.get_relation(username);
        List<trade> trade = userService.get_trade(username);

        Map<String, Object> map = new ConcurrentHashMap<>();
        if(trade!=null){
            map.put("user",trade);
        }
        if(relation!=null){
            Com_relation comRelation = relation.get(0);

            if(comRelation.getRole().equals("Com_admin")) {
            /*logger.info("企业主查看流水");
            for ( Com_relation r: relation ) {

            }*/
                String ComName = comRelation.getComName();
                List<trade> comTrade = comService.getComTrade(ComName);
                if(comTrade!=null) {
                    map.put("com", comTrade);
                }
            }
        }
        response.getWriter().write(JSON.toJSONString(map));

    }

    /**
     * 群组管理员查看群组的交易（主体是群组）
     * @param request
     * @param response
     * @throws IOException
     */
//公司交易信息
    public void get_com_trade(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = (String) request.getSession().getAttribute("username");

    }
//公司资金
    public void get_com_fund(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username =(String) request.getSession().getAttribute("username");
        logger.info(username);
        List<Com_relation> relation = comService.get_relation(username);
        logger.info(relation==null?"relaion为空":"不为空");
        if(relation.isEmpty()){
            return;
        }
        String comName = relation.get(0).getComName();
        BigDecimal comFund = comService.get_com_fund(comName);
        response.getWriter().write(comFund.toString());
    }


}
