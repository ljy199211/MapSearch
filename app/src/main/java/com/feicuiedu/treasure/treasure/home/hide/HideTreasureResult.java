package com.feicuiedu.treasure.treasure.home.hide;

/**
 * Created by ${ljy} on 2016/11/15.
 */

public class HideTreasureResult {

    /**
     * errcode : 0
     * errmsg : 参数格式不正确!请检测传入参数格式
     */

    private int errcode;
    private String errmsg;

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }
}
