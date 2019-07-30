package cn.dox.jdbcdemo.model.vo;

import cn.dox.jdbcdemo.common.constant.ResponseEnum;
import lombok.Data;

/**
 * @author: weidx
 * @date: 2019/7/28
 */
@Data
public class Response<T> {
    private int code;
    private String desc;
    private T content;

    public static <T> Response getInstance(ResponseEnum responseEnum, T content) {
        Response<T> response = new Response<>();
        response.setCode(responseEnum.getCode());
        response.setDesc(responseEnum.getDesc());
        response.setContent(content);
        return response;
    }

}
