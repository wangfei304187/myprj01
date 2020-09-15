package com.test.jwt;

import java.util.Date;

public class User {

    private String userId;
    private String userName;
    private String password;
    private String token;
    private Date lastLoginTime;
    private int errNum;
    private String errMsg;

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getToken() {
        return token;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }


    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public int getErrNum()
    {
        return errNum;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrNum(int errNum) {
        this.errNum = errNum;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

}
