package com.xiaomi.be.service;

import com.google.common.collect.ImmutableMap;
import com.xiaomi.api.order.common.bean.base.BasicQuery;
import com.xiaomi.be.beans.account.UserBalanceEntity;
import com.xiaomi.be.beans.pay.PayInfoEntity;
import com.xiaomi.be.constant.PayMode;
import com.xiaomi.be.mapper.account.UserBalanceMapper;
import com.xiaomi.be.mapper.pay.PayInfoMapper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.http.annotation.Immutable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Component
public class PayInfoService {
    @Resource
    PayInfoMapper payInfoMapper;

    @Resource
    UserBalanceMapper userBalanceMapper;

    @Transactional
    public long createPayInfo() {
        PayInfoEntity entity = new PayInfoEntity();
        entity.setPayId(RandomUtils.nextLong());
        entity.setOrderId(String.valueOf(RandomUtils.nextLong()));
        entity.setPayMode(PayMode.BALANCE | PayMode.CHANNEL);
        entity.setCreateTime(new Date());
        payInfoMapper.insert(entity);
        return entity.getId() == null ? -1 : entity.getId();
    }

    @Transactional
    public void payTrade(){
        createPayInfo();
        payWithBalance();
        throw new RuntimeException();
    }

    private void payWithBalance() {
        List<UserBalanceEntity> balanceLists = userBalanceMapper.queryListByConditions(BasicQuery.buildQuery(ImmutableMap.of("userId", 172341L)));
        if (CollectionUtils.isNotEmpty(balanceLists)) {
            UserBalanceEntity entity = balanceLists.get(0);
            entity.setFrozenBalance(entity.getFrozenBalance() == null ? 0L : (entity.getFrozenBalance() + 1));
            entity.setUsableBalance(entity.getUsableBalance() == null ? 0L : entity.getUsableBalance() - 1);
            userBalanceMapper.update(entity);
        } else {
            UserBalanceEntity balanceEntity = new UserBalanceEntity();
            balanceEntity.setUserId(172341L);
            balanceEntity.setFrozenBalance(RandomUtils.nextLong());
            balanceEntity.setUsableBalance(RandomUtils.nextLong());
            balanceEntity.setCreateTime(new Date());
            userBalanceMapper.insert(balanceEntity);
        }
    }
}
