package com.tp.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Slf4j
@Component
public class JwtUtil {

    @Value("${tp.jwt.secret}")
    private String secret;

    @Value("${tp.jwt.expiration}")
    private long expiration;

    private SecretKey signingKey;

    /**
     * 初始化 Key
     * 在PostConstruct中处理，避免每次生成Token都重复计算
     */
    @PostConstruct
    public void init() {
        // 使用 HMAC-SHA 算法，将密钥转换为 SecretKey 对象
        // 确保 secret 字符串长度足够长
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        this.signingKey = Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * 生成 Token (仅包含用户名)
     *
     * @param username 用户名
     * @return Token 字符串
     */
    public String generateToken(String username) {
        return generateToken(username, new HashMap<>());
    }

    /**
     * 生成 Token (包含自定义 Claims)
     *
     * @param username 用户名
     * @param extraClaims 自定义键值对
     * @return Token 字符串
     */
    public String generateToken(String username, Map<String, Object> extraClaims) {
        return Jwts.builder()
                .claims(extraClaims)           // 设置自定义载荷
                .subject(username)             // 设置主题
                .issuedAt(new Date())          // 签发时间
                .expiration(new Date(System.currentTimeMillis() + expiration)) // 过期时间
                .signWith(signingKey)          // 签名方式和密钥
                .compact();
    }

    /**
     * 验证 Token 是否有效
     *
     * @param token Token 字符串
     * @param username 待验证的用户名 (通常从数据库查出)
     * @return 是否有效
     */
    public boolean validateToken(String token, String username) {
        try {
            final String tokenUsername = getUsernameFromToken(token);
            return (tokenUsername.equals(username) && !isTokenExpired(token));
        } catch (JwtException | IllegalArgumentException e) {
            log.error("Token validation failed: {}", e.getMessage());
            return false;
        }
    }

    /**
     * 仅校验 Token 结构和签名是否合法 (不校验是否匹配特定用户)
     */
    public boolean validateToken(String token) {
        try {
            extractAllClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            log.error("Invalid Token: {}", e.getMessage());
            return false;
        }
    }

    /**
     * 从 Token 中获取用户名 (Subject)
     */
    public String getUsernameFromToken(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * 从 Token 中获取过期时间
     */
    public Date getExpirationDateFromToken(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * 判断 Token 是否过期
     */
    private boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    /**
     * 泛型方法：提取特定的 Claim 值
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * 解析 Token 获取所有的 Claims (载荷)
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(signingKey) // 设置验签密钥
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}