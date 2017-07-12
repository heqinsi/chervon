package com.chervon.iot.mobile.service;

import com.chervon.iot.mobile.model.Mobile_User;
import org.springframework.http.ResponseEntity;

/**
 * Created by Boy on 2017/6/24.
 */
public interface Mobile_UserCreateService {
    ResponseEntity<?> createUser(Mobile_User user) throws Exception;

    ResponseEntity<?> getCurrentUser(String user_id) throws Exception;


    ResponseEntity<?> updateUser(Mobile_User user) throws Exception;

   //int userLoginOut(String Authorzation);
}
