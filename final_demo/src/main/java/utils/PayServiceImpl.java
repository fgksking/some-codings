package utils;

import po.PayRequest;
import po.PayResponse;

public class PayServiceImpl implements PayService{


    public PayServiceImpl() {
    }

    @Override
    public PayResponse executePay(PayRequest request) {
        String sign = request.getSign();
        //拿到公钥后对数据验签


        //if()验签成功
        return new PayResponse(true,"验签成功");


    }



}
