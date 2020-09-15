package com.test.jwt;

public class UserResponse {

    private int errNum;
    private String errMsg;

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
