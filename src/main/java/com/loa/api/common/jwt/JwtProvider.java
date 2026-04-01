package com.loa.api.common.jwt;

import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtProvider {

    private final Key key;
    private final long expirationTime;

    // 생성자: 스프링이 실행될 때 application.properties에 적어둔 값을 쏙 뽑아서 기계(객체)를 세팅해줌!
    public JwtProvider(
        @Value("${jwt.secret}") String secretKey,
        @Value("${jwt.expiration_time}") long expirationTime
    ) {
        // 비밀번호를 Base64로 안전하게 디코딩해서 암호화 전용 키(Key) 객체로 변환하는 과정
        byte[] keyBytes = Base64.getDecoder().decode(Base64.getEncoder().encode(secretKey.getBytes()));
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.expirationTime = expirationTime;
    }

    // 1. 토큰 생성 메서드
    public String createToken(String email) {
        // 현재 시간 가져오기
        Date now = new Date();
        // 만료 시간 계산 (현재 시간 + yml에 적은 24시간)
        Date validity = new Date(now.getTime() + this.expirationTime);

        // JWT
        return io.jsonwebtoken.Jwts.builder()
                                   .setSubject(email) // 페이로드(Payload)
                                   // .claim("role", "USER") // 💡나중에 role 추가
                                   .setIssuedAt(now) // jwt 토큰 발급 시간 기록
                                   .setExpiration(validity) // jwt 토큰 만료 시간 기록
                                   .signWith(key, io.jsonwebtoken.SignatureAlgorithm.HS256)
                                   .compact();
    }

    // 2. 토큰에서 정보(페이로드) 꺼내는 메서드
    public String getSubject(String token) {
        return io.jsonwebtoken.Jwts.parserBuilder()
                                   .setSigningKey(key) // 1. key 넣기
                                   .build()
                                   .parseClaimsJws(token) // 2. 토큰 분해
                                   .getBody()
                                   .getSubject(); // 3. 페이로드를 꺼내옴
    }

    // 3. 토큰 유효성 검사 메서드 (가짜 토큰, 기간 지난 토큰)
    public boolean validateToken(String token) {
        try {
            // jwt 토큰 해석
            io.jsonwebtoken.Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | io.jsonwebtoken.MalformedJwtException e) {
            System.out.println("잘못된 JWT 서명입니다.");
        } catch (io.jsonwebtoken.ExpiredJwtException e) {
            System.out.println("만료된 JWT 토큰입니다.");
        } catch (io.jsonwebtoken.UnsupportedJwtException e) {
            System.out.println("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            System.out.println("JWT 토큰이 잘못되었습니다.");
        }
        return false; // 위 에러 중 하나라도 걸리면 false 반환
    }
}
