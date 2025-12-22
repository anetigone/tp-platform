package com.tp.utils;

import io.jsonwebtoken.JwtException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilTest {

    private JwtUtil jwtUtil;
    private final String testSecret = "test-secret-key-for-unit-testing-purpose";
    private final long testExpiration = 3600000L; // 1 hour

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil();
        ReflectionTestUtils.setField(jwtUtil, "secret", testSecret);
        ReflectionTestUtils.setField(jwtUtil, "expiration", testExpiration);

        // 手动调用 init 方法初始化 signingKey
        jwtUtil.init();
    }

    @Test
    void testGenerateTokenWithUsernameOnly() {
        String username = "testUser";
        String token = jwtUtil.generateToken(username);

        assertNotNull(token);
        assertFalse(token.isEmpty());
    }

    @Test
    void testGenerateTokenWithExtraClaims() {
        String username = "testUser";
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("role", "admin");
        extraClaims.put("department", "IT");

        String token = jwtUtil.generateToken(username, extraClaims);

        assertNotNull(token);
        assertFalse(token.isEmpty());
    }

    @Test
    void testGetUsernameFromToken() {
        String username = "testUser";
        String token = jwtUtil.generateToken(username);

        String extractedUsername = jwtUtil.getUsernameFromToken(token);

        assertEquals(username, extractedUsername);
    }

    @Test
    void testGetExpirationDateFromToken() {
        String username = "testUser";
        String token = jwtUtil.generateToken(username);

        Date expirationDate = jwtUtil.getExpirationDateFromToken(token);

        assertNotNull(expirationDate);
        assertTrue(expirationDate.after(new Date()));
    }

    @Test
    void testValidateValidToken() {
        String username = "testUser";
        String token = jwtUtil.generateToken(username);

        boolean isValid = jwtUtil.validateToken(token, username);

        assertTrue(isValid);
    }

    @Test
    void testValidateTokenWithWrongUsername() {
        String username = "testUser";
        String token = jwtUtil.generateToken(username);

        boolean isValid = jwtUtil.validateToken(token, "wrongUser");

        assertFalse(isValid);
    }

    @Test
    void testValidateExpiredToken() {
        // 创建一个已过期的 token
        JwtUtil expiredJwtUtil = new JwtUtil();
        ReflectionTestUtils.setField(expiredJwtUtil, "secret", testSecret);
        ReflectionTestUtils.setField(expiredJwtUtil, "expiration", -1000L); // 已过期
        expiredJwtUtil.init();

        String username = "testUser";
        String expiredToken = expiredJwtUtil.generateToken(username);

        boolean isValid = expiredJwtUtil.validateToken(expiredToken, username);

        assertFalse(isValid);
    }

    @Test
    void testValidateMalformedToken() {
        String malformedToken = "malformed.token.string";

        assertFalse(jwtUtil.validateToken(malformedToken));
    }

    @Test
    void testExtractClaim() {
        String username = "testUser";
        String role = "admin";
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("role", role);
        String token = jwtUtil.generateToken(username, extraClaims);

        String extractedRole = jwtUtil.extractClaim(token, claims -> claims.get("role", String.class));

        assertEquals(role, extractedRole);
    }

    @Test
    void testInitCreatesValidSigningKey() {
        SecretKey signingKey = (SecretKey) ReflectionTestUtils.getField(jwtUtil, "signingKey");

        assertNotNull(signingKey);
        assertEquals("HmacSHA256", signingKey.getAlgorithm());
    }

    @Test
    void testValidateTokenStructureOnly() {
        String username = "testUser";
        String token = jwtUtil.generateToken(username);

        boolean isValid = jwtUtil.validateToken(token);

        assertTrue(isValid);
    }

    @Test
    void testValidateInvalidTokenStructure() {
        String invalidToken = "invalid.token.here";

        boolean isValid = jwtUtil.validateToken(invalidToken);

        assertFalse(isValid);
    }
}
