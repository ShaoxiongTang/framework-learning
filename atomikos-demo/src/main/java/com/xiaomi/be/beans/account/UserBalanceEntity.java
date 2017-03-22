/*
 * Copyright (c) 2017 xiaomi.com. All Rights Reserved.
 */
package com.xiaomi.be.beans.account;

import com.xiaomi.api.order.common.bean.base.Entity;
import org.apache.ibatis.type.Alias;

import javax.annotation.Generated;
import java.io.Serializable;

import java.util.Date;

/**
 * UserBalanceEntity
 *
 * @author: code.generator Date: 2017-02-27 14:17:42
 * @version: \$Id$
 */
@Generated(value = "com.xiaomi.api.order.tools.mybatis.velocity.CodeGenerator", date = "2017-02-27 14:17:42")
@Alias("UserBalanceEntity")
public class UserBalanceEntity implements Entity<Long>, Serializable {

    private static final long serialVersionUID = 6462498341078713063L;

    private Long userId;
    private Long usableBalance;
    private Long frozenBalance;
    private Date createTime;

    @Override
    public Long getPk() {
        return getUserId();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getUsableBalance() {
        return usableBalance;
    }

    public void setUsableBalance(Long usableBalance) {
        this.usableBalance = usableBalance;
    }

    public Long getFrozenBalance() {
        return frozenBalance;
    }

    public void setFrozenBalance(Long frozenBalance) {
        this.frozenBalance = frozenBalance;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}
