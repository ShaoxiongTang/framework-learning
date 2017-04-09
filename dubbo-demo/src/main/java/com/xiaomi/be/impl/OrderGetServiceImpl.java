package com.xiaomi.be.impl;

import com.xiaomi.api.order.common.utils.JSONUtil;
import com.xiaomi.be.OrderGetService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class OrderGetServiceImpl implements OrderGetService , InitializingBean{


    public String getOrderInfo(String orderId) {
        Map<String, Object> info = new LinkedHashMap<String, Object>();
        info.put("order_id" , orderId);
        info.put("status", "complete");
        return JSONUtil.encode(info);
    }

    public void afterPropertiesSet() throws Exception {
        System.out.println("init..................");
    }
}
