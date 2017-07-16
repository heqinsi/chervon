package com.chervon.iot.common.exception.exceptionHandle;

import com.chervon.iot.common.exception.ErrorInfo;
import com.chervon.iot.common.exception.ResultMsg;
import com.chervon.iot.common.exception.ResultStatusCode;
import com.chervon.iot.common.exception.VerifyException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Mike Xu.
 * @Date: Created in 20:58 2017/6/26
 * @Description:
 * @Modified By:
 */
@ControllerAdvice
public class GlobalExceptionHandler {



    @ExceptionHandler(value = VerifyException.class)
    @ResponseBody
    public ErrorInfo<String> jsonErrorHandler(HttpServletRequest req, VerifyException e){
//        ErrorInfo<String> r = e.getErrorInfo();
//        r.setMessage(e.getMessage());
//        r.setCode(ErrorInfo.ERROR);
//        r.setData("Some Data");
//        r.setUrl(req.getRequestURL().toString());
        return e.getErrorInfo();
    }
    @ExceptionHandler(value = {SQLException.class, MessagingException.class,IOException.class,GeneralSecurityException.class})
    @ResponseBody
  public ResponseEntity<?> partException(){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type","application/vnd.api+json");
        List<ResultMsg.Error> errors = new ArrayList<ResultMsg.Error>();
        ResultMsg.Error error = new ResultMsg.Error(ResultStatusCode.INTERNAL_SERVER_ERROR.getErrcode(),ResultStatusCode.INTERNAL_SERVER_ERROR.getTitle(),null);
        errors.add(error);
        ResultMsg resultMsg = new ResultMsg(errors);
        System.out.println(false);
        return new ResponseEntity(resultMsg,headers, HttpStatus.valueOf(ResultStatusCode.INTERNAL_SERVER_ERROR.getErrcode()));
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseEntity<?> GlobalException(){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type","application/vnd.api+json");
        List<ResultMsg.Error> errors = new ArrayList<ResultMsg.Error>();
        ResultMsg.Error error = new ResultMsg.Error(ResultStatusCode.INTERNAL_SERVER_ERROR.getErrcode(),ResultStatusCode.INTERNAL_SERVER_ERROR.getTitle(),null);
        errors.add(error);
        ResultMsg resultMsg = new ResultMsg(errors);
        return new ResponseEntity(resultMsg,headers, HttpStatus.valueOf(ResultStatusCode.INTERNAL_SERVER_ERROR.getErrcode()));

    }
}
