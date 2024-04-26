package utils;

import java.math.BigDecimal;
import java.util.Random;

public class PayServiceImpl implements PayService{



    public PayServiceImpl() {
    }

    @Override
    public boolean executePay(Request request) {
        BigDecimal amount = request.getAmount();
        String receiver = request.getReceiver();
        String sender = request.getSender();
        String bank_type = request.getBank_type();
        //if()验签成功

        //模拟入失败的情况
        Random random = new Random();
        int i1 = random.nextInt(10);
        PayResponse response;
        if(i1%3==0){
             response = new PayResponse(false,"入账第三方失败");
        }else {
             response = new PayResponse(true, "入账成功");
        }
      //  PayResponse response1 = new PayResponse(false, "入账失败");

        if(response.isSuccess()){
            //资金入账到third_fund
            String s =  "insert into third_fund(amount,username,traget) values(?,?,?)";
            int i = CRUD_Utils.executeUpdate(s, amount,sender,receiver);
            if(i==1){

                //发起收款请求
                //调用 request 接口


            }


            //调用request向receiver发起收款请求
           // boolean request1 = request(request);//
            //操作是否成功
            return i==1;
        }else{
            //给外界发出入帐失败的操作
            //外界 回滚冻结 的资金
            return false;

            //并在外界提示是否继续再次入账
        }





    }




}
