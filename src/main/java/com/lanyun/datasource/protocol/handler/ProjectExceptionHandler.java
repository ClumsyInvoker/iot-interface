package com.lanyun.datasource.protocol.handler;

import com.lanyun.datasource.advice.CommonResponse;
import com.lanyun.datasource.exception.ProjectException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * @author weilai
 * @email 352342845@qq.com
 * @date 2019-06-21 20:03
 */
@Slf4j
@RestControllerAdvice
public class ProjectExceptionHandler {

    /**
     * 项目异常捕捉
     *
     * @param e
     * @return
     * @author weilai
     */
    @ExceptionHandler(value = ProjectException.class)
    @ResponseBody
    public CommonResponse handleProjectException(ProjectException e) {
        log.error("系统异常", e);
        CommonResponse commonResponse = new CommonResponse();
        commonResponse.setCode(e.getCode());
        commonResponse.setMsg(e.getMessage());
        return commonResponse;
    }

    /**
     * method not allowed exception.
     *
     * @param e
     * @return
     * @author weilai
     */
    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    public CommonResponse handleMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        CommonResponse commonResponse = new CommonResponse();
        commonResponse.setCode(HttpStatus.METHOD_NOT_ALLOWED.value());
        commonResponse.setMsg(e.getMessage());
        return commonResponse;
    }

    /**
     * 捕获参数校验异常
     *
     * @param e
     * @return
     * @author weilai
     */
    @ResponseBody
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public CommonResponse handleValidException(MethodArgumentNotValidException e) {
        CommonResponse commonResponse = new CommonResponse();
        commonResponse.setCode(HttpStatus.BAD_REQUEST.value());
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        commonResponse.setMsg(!CollectionUtils.isEmpty(fieldErrors) ? fieldErrors.get(0).getField() + fieldErrors.get(0).getDefaultMessage() : e.getMessage());
        return commonResponse;
    }

    /**
     * 通用错误
     *
     * @param t
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = Throwable.class)
    public CommonResponse handerProjectException(Throwable t) {
        log.error("系统异常", t);
        CommonResponse commonResponse = new CommonResponse();
        commonResponse.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        commonResponse.setMsg("服务器开小差了");
        return commonResponse;
    }
}
