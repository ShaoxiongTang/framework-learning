/*
 * Copyright (c) 2017 xiaomi.com. All Rights Reserved.
 */
package com.xiaomi.be.beans.pay;

import com.xiaomi.api.order.common.bean.base.Entity;
import org.apache.ibatis.type.Alias;

import javax.annotation.Generated;
import java.io.Serializable;

import java.util.Date;

/**
 * PayInfoEntity
 *
 * @author: code.generator Date: 2017-02-27 14:16:59
 * @version: \$Id$
 */
@Generated(value = "com.xiaomi.api.order.tools.mybatis.velocity.CodeGenerator", date = "2017-02-27 14:16:59")
@Alias("PayInfoEntity")
public class PayInfoEntity implements Entity<Long>, Serializable {

    private static final long serialVersionUID = -8790409578656084161L;

    private Long id;
    private Long payId;
    private Integer payMode;
    private String orderId;
    private Date createTime;

    @Override
    public Long getPk() {
        return getId();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPayId() {
        return payId;
    }

    public void setPayId(Long payId) {
        this.payId = payId;
    }

    public Integer getPayMode() {
        return payMode;
    }

    public void setPayMode(Integer payMode) {
        this.payMode = payMode;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}
