package com.tp.interceptor;

import com.tp.common.context.BaseContext;
import com.tp.common.context.UserContext;
import com.tp.common.result.Result;
import com.tp.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * JWT拦截器，用于验证请求中的Token
 */
@Slf4j
@Component
public class JwtInterceptor implements HandlerInterceptor {

    private final JwtUtil jwtUtil;

    public JwtInterceptor(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 从请求头中获取token
        String token = request.getHeader("Authorization");

        // 如果是OPTIONS请求，直接放行
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }

        // 如果没有token，则返回错误
        if (token == null || token.isEmpty()) {
            log.warn("Missing token in request from IP: {}", getClientIP(request));
            responseUnAuthorized(response, "Token is missing");
            return false;
        }

        // 如果token以Bearer开头，则去掉前缀
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        try {
            // 验证token是否有效
            if (!jwtUtil.validateToken(token)) {
                log.warn("Invalid token: {} from IP: {}", token, getClientIP(request));
                responseUnAuthorized(response, "Invalid token");
                return false;
            }
            
            // 放入用户信息
            String username = jwtUtil.getUsernameFromToken(token);
            String userId = jwtUtil.extractClaim(token, claims -> claims.get("userId", String.class));
            Integer role = jwtUtil.extractClaim(token, claims -> claims.get("role", Integer.class));
            if(userId == null || role == null) {
                log.warn("Invalid token: {} from IP: {}", token, getClientIP(request));
                responseUnAuthorized(response, "Invalid token, userId or role do not exist");
                return false;
            }
            UserContext userContext = UserContext.builder()
                    .username(username)
                    .userId(Long.parseLong(userId))
                    .role(role)
                    .build();
            BaseContext.setUserContext(userContext);

            // 放入request
            request.setAttribute("username", username);
            
            return true;
        } catch (ExpiredJwtException e) {
            log.warn("Token expired: {} from IP: {}", token, getClientIP(request));
            responseUnAuthorized(response, "Token has expired");
            return false;
        } catch (MalformedJwtException e) {
            log.warn("Malformed token: {} from IP: {}", token, getClientIP(request));
            responseUnAuthorized(response, "Malformed token");
            return false;
        } catch (SignatureException e) {
            log.warn("Token signature mismatch: {} from IP: {}", token, getClientIP(request));
            responseUnAuthorized(response, "Token signature mismatch");
            return false;
        } catch (IllegalArgumentException e) {
            log.warn("Empty or null token from IP: {}", getClientIP(request));
            responseUnAuthorized(response, "Token is empty or null");
            return false;
        } catch (Exception e) {
            log.error("Error validating token: {} from IP: {}", token, getClientIP(request), e);
            responseUnAuthorized(response, "Error validating token");
            return false;
        }
    }

    /**
     * 返回401未授权错误
     *
     * @param response HttpServletResponse对象
     * @param message 错误消息
     * @throws IOException IO异常
     */
    private void responseUnAuthorized(HttpServletResponse response, String message) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        
        Result<Object> result = Result.error(401, message);
        PrintWriter writer = response.getWriter();
        writer.write(result.toString());
        writer.flush();
        writer.close();
    }

    /**
     * 获取客户端IP地址
     *
     * @param request HttpServletRequest对象
     * @return 客户端IP地址
     */
    private String getClientIP(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}