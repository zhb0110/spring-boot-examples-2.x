package com.example.springbootshiroauthentication.pojo;

import java.io.Serializable;
import java.util.Date;

// TODO:不序列化有问题吗？
// 最好写：Serializable:序列化给我们提供了一种技术，用于保存对象的变量和传输。虽然也可以使用别的一些方法实现同样的功能，但是Java给我们提供的方法使用起来是非常方便的。
public class User implements Serializable {

    private static final long serialVersionUID = -5440372534300871944L;

    private Integer id;
    private String userName;
    private String password;
    private Date createTime;
    private String status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
