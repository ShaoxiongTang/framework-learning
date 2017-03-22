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
 * PayInfoDetailEntity
 *
 * @author: code.generator Date: 2017-02-27 14:16:16
 * @version: \$Id$
 */
@Generated(value = "com.xiaomi.api.order.tools.mybatis.velocity.CodeGenerator", date = "2017-02-27 14:16:16")
@Alias("PayInfoDetailEntity")
public class PayInfoDetailEntity implements Entity<Long>, Serializable {

    private static final long serialVersionUID = -4967546912622030997L;

    private Long payDetailId;
    private Long payId;
    private Integer payTool;
    private String status;
    private Date createTime;

    @Override
    public Long getPk() {
        return getPayDetailId();
    }

    public Long getPayDetailId() {
        return payDetailId;
    }

    public void setPayDetailId(Long payDetailId) {
        this.payDetailId = payDetailId;
    }

    public Long getPayId() {
        return payId;
    }

    public void setPayId(Long payId) {
        this.payId = payId;
    }

    public Integer getPayTool() {
        return payTool;
    }

    public void setPayTool(Integer payTool) {
        this.payTool = payTool;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}
