package com.group3.entity;

import java.util.Date;

public class Chat {
    private Integer id;

    private String producerId;

    private String suscriberId;

    private Date createTime;

    private Integer sign;

    private String content;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProducerId() {
        return producerId;
    }

    public void setProducerId(String producerId) {
        this.producerId = producerId;
    }

    public String getSuscriberId() {
        return suscriberId;
    }

    public void setSuscriberId(String suscriberId) {
        this.suscriberId = suscriberId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getSign() {
        return sign;
    }

    public void setSign(Integer sign) {
        this.sign = sign;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}