package com.kh.team4.jwt;


import com.kh.team4.config.RedisUtil;
import com.kh.team4.dto.TokenDTO;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;


@Component
@Slf4j
public class TokenProvider {

    // 토큰 생성 및 검증할 때 쓰이는 String값
    private static final String AUTHORITIES_KEY = "auth";
    private static final String BEARER_TYPE = "bearer";

    // 토큰 만료 시간 설정
    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 30;            // 30분
    // private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 1;            //  실험용 1분
    // private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7;  // 7일
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 30;  // 30 임의로 지정
    private final Key key;
    private final RedisUtil redisUtil;


    // @value 어노테이션으로 yml에 있는 secretKey가져온다음 디코딩 해서 의존성 주입된 키 값으로 설정
    public TokenProvider(@Value("${spring.jwt.secret}") String secretKey, RedisUtil redisUtil) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.redisUtil = redisUtil;
    }

    public TokenDTO generateTokenDto(Authentication authentication, Long mno) { // 매개변수 받아서 String으로 변환
        // 권한 가져오기
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = (new Date()).getTime();


        Date tokenExpiresIn = new Date(now + ACCESS_TOKEN_EXPIRE_TIME); // 만료시간 설정

        System.out.println(tokenExpiresIn);

        //payload 부분 설정
        Map<String, Object> payloads = new HashMap<>();
        payloads.put("mno", mno);
        payloads.put("sub", authentication.getName());
        payloads.put("auth", authorities);

        String accessToken = Jwts.builder() // 토큰dto에 정보 담아
                .setClaims(payloads)
                .setExpiration(tokenExpiresIn)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();

        // Refresh Token 생성
        String refreshToken = Jwts.builder()
                .setExpiration(new Date(now + REFRESH_TOKEN_EXPIRE_TIME))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();

        System.out.println("토큰생성 ing~");
        return TokenDTO.builder()
                .grantType(BEARER_TYPE)
                .accessToken(accessToken)
                .tokenExpiresIn(tokenExpiresIn.getTime())
                .refreshToken(refreshToken)
                .build();
    }

    public Authentication getAuthentication(String accessToken) { // 토큰의 인증을 꺼내는 메소드
        // 토큰 복호화
        Claims claims = parseClaims(accessToken); // String형태의 토큰을 claims형태로 생성
        // 클레임 : json 형태로 키-벨류의 한쌍, 등록된클레임, 공개클레임, 비공개클레임 세종류.
        System.out.println("클레임? : " + claims.toString());
        if (claims.get(AUTHORITIES_KEY) == null) {
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }

        Collection<? extends GrantedAuthority> authorities = // 클래임에서 권한 정보 가져오기
                // GrantedAuthority상속 받고 사용가능한 collection 반환
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        // 스트림 사용해서 클래임 형태의 토큰을 정렬해서 SimpleGrantedAuthority형태의 리스트 생성(인가 있음.)
                        .collect(Collectors.toList());
        System.out.println("권한 정보 : " + authorities.toString());

        UserDetails principal = new User(claims.getSubject(), "", authorities);
        // userDetails(스프링시큐리티에서 유저정보 담는 인터페이스)에 토큰에서 가져온 정보랑 인가 넣어서

        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
        // 반환시켜버려, 이거 유저디테일 생성 후 securityContext에 사용하기 위한 절차임(securityContext Authentication객체를 저장)
    }

    public boolean validateToken(String token) { //validateToken 토큰 검증하기 위한 메소드
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }

    private Claims parseClaims(String accessToken) { // parseClaims : 토큰 -> claims형태로 만드는 메소드
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build().parseClaimsJws(accessToken)
                    .getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    public Long getExpiration(String accessToken) {
        Date expiration = Jwts.parserBuilder().setSigningKey(key)
                .build().parseClaimsJws(accessToken).getBody().getExpiration();

        // 현재시간
        long now = new Date().getTime();
        return (expiration.getTime() - now);
    }

}