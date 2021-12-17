package com.lanyun.datasource.exception;

import lombok.Getter;

/**
 * @author weilai
 * @email 352342845@qq.com
 * @date 2019-06-21 19:50
 */
@Getter
public class ProjectException extends RuntimeException {

    private Integer code;

    public ProjectException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public ProjectException(String message) {
        super(message);
        this.code = 400;
    }

}
