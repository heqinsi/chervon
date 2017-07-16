package com.chervon.iot.mobile.sercuity.filter;

/**
 * @Author: Mike Xu.
 * @Date: Created in 20:47 2017/6/26
 * @Description:
 * @Modified By:
 */

import com.chervon.iot.common.exception.ResultMsg;
import com.chervon.iot.common.exception.ResultStatusCode;
import com.chervon.iot.mobile.mapper.Mobile_UserMapper;
import com.chervon.iot.mobile.model.Mobile_User;
import com.chervon.iot.mobile.sercuity.JwtTokenUtil;
import com.chervon.iot.mobile.service.Mobile_UserLoginService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HTTPBearerAuthorizeAttribute implements Filter {

    private final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private  Mobile_User mobile_user;

    @Autowired
    private Mobile_UserLoginService mobile_userLoginService;


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // TODO Auto-generated method stub
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
                filterConfig.getServletContext());
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // TODO Auto-generated method stub
        logger.info("进入过滤器");

        HttpServletRequest httpServletRequest =(HttpServletRequest)request;
        //用户注册，登陆请求不做过滤
        if(httpServletRequest.getMethod().equals("POST")  && (httpServletRequest.getServletPath().equals("/api/v1/sessions")||httpServletRequest.getServletPath().equals("/api/v1/resets")||httpServletRequest.getServletPath().equals("/api/v1/users"))){
            System.out.println(true);
            System.out.println();
            chain.doFilter(request, response);
            return;
        }
        String headerAuthToken = httpServletRequest.getHeader("Authorization");
        String authToken = "";
        //Authorization格式判断，格式不对，return
        if((headerAuthToken != null) && (headerAuthToken.length() > 7) && headerAuthToken.startsWith("Bearer ")){
            authToken = headerAuthToken.substring(7);
            System.out.println(authToken);
            //通过jwt解析拿到email
            String email = jwtTokenUtil.getEmailFromToken(authToken);
            logger.info("checking authentication for user= " + email);
            logger.info("checking authentication for ExpirationDateFromToken= " + jwtTokenUtil.getExpirationDateFromToken(authToken));
            //从数据拿去信息
            if(email != null){
                try {
                    mobile_user = mobile_userLoginService.getUserByEmail(email);
                }
                catch(Exception e){
                    //异常报500错误return
                    HttpServletResponse httpResponse = (HttpServletResponse) response;
                    httpResponse.setCharacterEncoding("UTF-8");
                    httpResponse.setContentType("application/json; charset=utf-8");
                    httpResponse.setStatus(ResultStatusCode.INTERNAL_SERVER_ERROR.getErrcode());
                    ObjectMapper mapper = new ObjectMapper();
                    List<ResultMsg.Error> errors = new ArrayList<ResultMsg.Error>();
                    ResultMsg.Error error = new ResultMsg.Error(ResultStatusCode.INTERNAL_SERVER_ERROR.getErrcode(),ResultStatusCode.SC_PERMISSION_DENIED.getErrmsg(),"");
                    errors.add(error);
                    ResultMsg resultMsg = new ResultMsg(errors);
                    httpResponse.getWriter().write(mapper.writeValueAsString(resultMsg));
                    return;
                }
                //符合条件过滤
                if(mobile_user != null && jwtTokenUtil.validateToken(authToken, mobile_user)){
                    System.out.println(mobile_user.getPassword());
                    System.out.println(mobile_user.getLastpasswordresetdate());
                    System.out.println("JWT放行");
                    chain.doFilter(request, response);
                    return;
                }
            }
        }
        //错误返回验证失败
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.setCharacterEncoding("UTF-8");
            System.out.println("test");
            httpResponse.setContentType("application/json; charset=utf-8");
            httpResponse.setStatus(ResultStatusCode.SC_PERMISSION_DENIED.getErrcode());
            ObjectMapper mapper = new ObjectMapper();
            List<ResultMsg.Error> errors = new ArrayList<ResultMsg.Error>();
            ResultMsg.Error error = new ResultMsg.Error(ResultStatusCode.SC_PERMISSION_DENIED.getErrcode(), ResultStatusCode.SC_PERMISSION_DENIED.getErrmsg(), "");
            errors.add(error);
            ResultMsg resultMsg = new ResultMsg(errors);
            httpResponse.getWriter().write(mapper.writeValueAsString(resultMsg));
            return;

    }
    @Override
    public void destroy() {
        // TODO Auto-generated method stub
    }
}
