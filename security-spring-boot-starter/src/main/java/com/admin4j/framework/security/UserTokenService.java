package com.admin4j.framework.security;

import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 用户令牌服务
 *
 * @author andanyang
 * @since 2023/5/30 13:27
 */

public interface UserTokenService {


    /**
     * 创建令牌
     *
     * @param claims 用户信息
     * @return 令牌
     */
    String createToken(Map<String, Object> claims);

    String createToken(UserDetails userDetails);

    /**
     * 获取请求token(令牌)
     *
     * @param request
     * @return token
     */
    String getToken(HttpServletRequest request);

    /**
     * 获取登录用户名
     *
     * @param token
     * @return 登录用户名
     */
    String getUserName(String token);
}