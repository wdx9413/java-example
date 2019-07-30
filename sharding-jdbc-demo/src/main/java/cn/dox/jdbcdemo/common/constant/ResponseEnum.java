package cn.dox.jdbcdemo.common.constant;
/**
 * @author: weidx
 * @date: 2019/7/28
 */

public enum ResponseEnum {
    // 成功
    OK(1000, "");


    private int code;
    private String desc;
    private ResponseEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }


}
