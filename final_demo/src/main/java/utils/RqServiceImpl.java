package utils;

import java.math.BigDecimal;

public class RqServiceImpl {




    public static boolean executeRequest(Request request){
        // 模拟发起请求
        //生成资金库在第三方平台（冻结资金）
        BigDecimal amount = request.getAmount();
        String receiver = request.getReceiver();
        String sender = request.getSender();
        String bank_type = request.getBank_type();

        String s = "update user_fund set user_amount=user_amount+? where username =?";
        int i = CRUD_Utils.executeUpdate(s, amount, receiver);
        return i==1;
    }
    public static boolean response(Request request){
        BigDecimal amount = request.getAmount();
        String receiver = request.getReceiver();
        String sender = request.getSender();
        String bank_type = request.getBank_type();
        String s = "delete from third_fund where amount=? and username=? and traget=?";
        int i = CRUD_Utils.executeUpdate(s, amount, sender, receiver);
        return i==1;
    }


}
