package com.chervon.iot.mobile.service;

import com.chervon.iot.mobile.model.Mobile_User;
import com.chervon.iot.mobile.model.entity.ResponseBody;
import org.springframework.http.ResponseEntity;

/**
 * Created by 喷水君 on 2017/6/27.
 */
public interface Mobile_UserLoginService {
    ResponseEntity<?> userLogin(String email, String password) throws Exception;
    ResponseEntity<?> getCurrentUser(String Authorzation) throws Exception;
    void  userLoginOut(String Authorzation)throws Exception;
    Mobile_User loadUserByUsername(String email)throws Exception;
    Mobile_User loadUserByEmailAndPassword(String email,String password)throws Exception;
    Mobile_User getUserByEmail(String email)throws Exception;
    ResponseBody loginReturn(Mobile_User mobile_user);
}
