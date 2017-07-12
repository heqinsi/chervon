package com.chervon.iot.common.exception;

/**
 * @Author: Mike Xu.
 * @Date: Created in 20:58 2017/6/26
 * @Description:
 * @Modified By:
 */
public class VerifyException extends Exception{
    private ErrorInfo errorInfo;

    public ErrorInfo getErrorInfo() {
        return errorInfo;
    }

    public VerifyException(ErrorInfo errorInfo){
        super(errorInfo.getMessage());
        this.errorInfo = errorInfo;
    }
}
