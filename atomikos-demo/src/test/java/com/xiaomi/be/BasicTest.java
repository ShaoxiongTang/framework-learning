package com.xiaomi.be;

import com.xiaomi.api.order.common.test.BaseTest;
import com.xiaomi.be.service.PayInfoService;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

import javax.annotation.Resource;

/**
 * Unit test for simple App.
 */
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class BasicTest extends BaseTest {

    @Resource
    PayInfoService payInfoService;

    @Test
    public void insertTest() throws Exception {
        payInfoService.payTrade();
    }
}
