package com.test.jwt;

import java.util.Date;

public class User {

    private String userId;
    private String userName;
    private String password;
    private Date lastLoginTime;
    
//    private String token;
//    private int errNum;
//    private String errMsg;

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
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

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }
    
//    public String getToken() {
//        return token;
//    }
//    
//    public void setToken(String token) {
//        this.token = token;
//    }
//    
//    public int getErrNum()
//    {
//        return errNum;
//    }
//
//    public String getErrMsg() {
//        return errMsg;
//    }
//
//    public void setErrNum(int errNum) {
//        this.errNum = errNum;
//    }
//
//    public void setErrMsg(String errMsg) {
//        this.errMsg = errMsg;
//    }

}
