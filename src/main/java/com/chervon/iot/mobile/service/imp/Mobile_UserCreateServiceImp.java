package com.chervon.iot.mobile.service.imp;

import com.chervon.iot.mobile.model.Mobile_User;
import com.chervon.iot.mobile.service.Mobile_UserCreateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * Created by Boy on 2017/6/24.
 */
@Service
public class Mobile_UserCreateServiceImp implements Mobile_UserCreateService {
  //  public Map<String,String> userMap =new HashMap<String,String>();
    public Object[] included=new Object[0];

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public ResponseEntity<?> createUser(Mobile_User user)throws Exception{
        return null;
    }

    public ResponseEntity<?> getCurrentUser(String user_id)throws  Exception{
        ValueOperations<String, Object> operations = redisTemplate.opsForValue();
        return null;
    }

    @Override
    public ResponseEntity<?> updateUser(Mobile_User user) throws Exception {
        System.out.println("1");
        return null;
    }
}
