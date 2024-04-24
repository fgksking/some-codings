package controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import po.*;
import service.Impl.ComServiceImpl;
import service.Impl.UserServiceImpl;
import utils.PayServiceImpl;
import utils.ServerResponse;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Base64;
import java.util.HexFormat;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FundServlet extends BaseServlet{
    private final ComServiceImpl comService = new ComServiceImpl();
    private final UserServiceImpl userService = new UserServiceImpl();
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



    public void pay(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //post得到数据
        BufferedReader br = request.getReader();
        String params = br.readLine();
        logger.info(params);
        String username= params.split(":")[1].replace("\"", "").replace("}", "");
        String username2= username.substring(0, username.indexOf(","));
        String receiver = params.split(":")[2].replace("\"", "").replace("}", "");
        String receiver2 = receiver.substring(0, receiver.indexOf(","));
        String bank = params.split(":")[3].replace("\"", "").replace("}", "");
        String bank2 = bank.substring(0, bank.indexOf(","));
        String amount = params.split(":")[4].replace("\"", "").replace("}", "");
        //检验支付环境
        //前端校验是否有非法字符如   用正则表达式
        BigDecimal bigDecimal  =new BigDecimal(amount);
        User user = userService.findUser(username);
        String role = user.getRole();
        //从数据库调用余额
        if("user_fund".equals(bank2)){
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

                    }else{
                        response.getWriter().write(JSON.toJSONString(ServerResponse.createError("冻结失败，请重新支付")));
                        return;
                    }
                    String sign="";


                    //对关键信息进行签名


                    //发送支付请求
                    PayRequest payRequest = new PayRequest(username2,receiver2,bigDecimal,sign);
                    PayServiceImpl payService = new PayServiceImpl();
                    PayResponse payResponse = payService.executePay(payRequest);

                    //根据success返回的结果再对数据库的资金处理
                    boolean success = payResponse.isSuccess();

                    //返回星系

                }else{
                    response.getWriter().write(JSON.toJSONString(ServerResponse.createError("余额不足")));
                    return;
                }
            }

        }else{
            //群组资金
            if(role.equals("Com_admin")){

            }
            List<permission> Permission = comService.getfund(username);
            BigDecimal getfund = new BigDecimal(0);
            for (permission permission : Permission) {
                getfund.add(permission.getPer_amount());
            }
            int result = getfund.compareTo(bigDecimal);
            if(result>=0){
                //余额充足
                boolean pay = comService.pay(username, getfund, Permission.get(0).getComName());

                if(pay){
                    //已经冻结了

                    //签名返回




                }else{
                    response.getWriter().write(JSON.toJSONString(ServerResponse.createError("冻结失败")));
                    return;
                }
            }
            else{

                response.getWriter().write(JSON.toJSONString(ServerResponse.createError("余额不足")));
                return;

            }
            //根据支付接口的response结果在进行入账或着 退账
            //并记录此 支付 信息
        }



    }

    //发起收款






    public void getTrade(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username =(String) request.getSession().getAttribute("username");
        BigDecimal fund = userService.fund(username);
        List<permission> getfund = comService.getfund(username);
        List<permission> Permission = comService.getfund(username);
        BigDecimal getComfunds = new BigDecimal(0);
        for (permission permission : Permission) {
            getComfunds.add(permission.getPer_amount());
        }

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
        map.put("user",trade);
        Com_relation comRelation = relation.get(0);
        if(comRelation.getRole().equals("Com_admin")){
            /*logger.info("企业主查看流水");
            for ( Com_relation r: relation ) {

            }*/
            String ComName =comRelation.getComName();
            List<trade> comTrade = comService.getComTrade(ComName);
            map.put("com",comTrade);

        }
        response.getWriter().write(JSON.toJSONString(map));

    }
    public void get_com_trade(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = (String) request.getSession().getAttribute("username");

    }


}
