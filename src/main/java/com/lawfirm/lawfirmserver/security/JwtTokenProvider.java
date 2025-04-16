package com.lawfirm.lawfirmserver.security;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;

/**
 * JWT令牌提供者，用于生成、验证和解析JWT令牌
 */
@Component
@Service
public class JwtTokenProvider {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    @Value("${security.jwt.token.secret-key:secretKey}")
    private String secretKey;

    @Value("${security.jwt.token.expire-length:86400000}")
    private long tokenValidityInMilliseconds = 86400000; // 24小时

    @Value("${security.jwt.token.refresh-expire-length:2592000000}")
    private long refreshTokenValidityInMilliseconds = 2592000000L; // 30天

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    /**
     * 创建访问令牌
     *
     * @param userId 用户ID
     * @return 令牌
     */
    public String generateToken(String userId) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + tokenValidityInMilliseconds);

        return Jwts.builder()
                .setSubject(userId)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    /**
     * 创建刷新令牌
     *
     * @param userId 用户ID
     * @return 刷新令牌
     */
    public String generateRefreshToken(String userId) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + refreshTokenValidityInMilliseconds);

        return Jwts.builder()
                .setSubject(userId)
                .setIssuedAt(now)
                .setExpiration(validity)
                .claim("type", "refresh")
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    /**
     * 验证令牌
     *
     * @param token 令牌
     * @return 是否有效
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            logger.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            logger.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            logger.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            logger.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            logger.error("JWT claims string is empty");
        }
        return false;
    }

    /**
     * 验证刷新令牌
     *
     * @param token 刷新令牌
     * @return 是否有效
     */
    public boolean validateRefreshToken(String token) {
        try {
            Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
            return "refresh".equals(claims.get("type"));
        } catch (SignatureException ex) {
            logger.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            logger.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            logger.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            logger.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            logger.error("JWT claims string is empty");
        }
        return false;
    }

    /**
     * 从令牌中获取用户ID
     *
     * @param token 令牌
     * @return 用户ID
     */
    public String getUserIdFromToken(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    /**
     * 从刷新令牌中获取用户ID
     *
     * @param token 刷新令牌
     * @return 用户ID
     */
    public String getUserIdFromRefreshToken(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    /**
     * 从请求中解析令牌
     *
     * @param req 请求
     * @return 令牌
     */
    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    /**
     * 获取令牌过期时间
     *
     * @return 过期时间
     */
    public Long getTokenExpireTime() {
        return System.currentTimeMillis() + tokenValidityInMilliseconds;
    }

    /**
     * 获取刷新令牌过期时间
     *
     * @return 过期时间
     */
    public Long getRefreshTokenExpireTime() {
        return System.currentTimeMillis() + refreshTokenValidityInMilliseconds;
    }

    /**
     * 创建认证对象
     *
     * @param token 令牌
     * @return 认证对象
     */
    public Authentication getAuthentication(String token) {
        String userId = getUserIdFromToken(token);
        return new UsernamePasswordAuthenticationToken(userId, "", Collections.emptyList());
    }
} 