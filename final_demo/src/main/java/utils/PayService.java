package utils;

import po.PayRequest;
import po.PayResponse;

public interface PayService {

    PayResponse executePay(PayRequest request);

}
