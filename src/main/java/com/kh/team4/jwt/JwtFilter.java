package com.kh.team4.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";
    private final TokenProvider tokenProvider;
    private final RedisTemplate  redisTemplate;




    private String resolveToken(HttpServletRequest request) { // request header에서 토큰 정보를 꺼내오는 메소드
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwt = resolveToken(request);
        // resolveToken을 통해 토큰 정보 꺼내온 다음, validateToken으로 토큰 유효한지 검사.
        // 유효해 -> Authentication을 가져와서 securityContext에 저장(securityContext에 허가된 uri이외의 모든 요청은 이 필터를 거치게 됨.)

        // if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
        if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)){
            // 레디스에 해당 어세스토큰 로그아웃 여부 확인
            String isLogout = (String) redisTemplate.opsForValue().get(jwt);

            // 로그아웃이 없는(되어 있지 않은) 경우 해당 토큰은 정상적으로 작동하기
            if (ObjectUtils.isEmpty(isLogout)) {

                // 토큰이 유효하면 토큰으로부터 유저 정보를 받아옵니다.
                Authentication authentication = tokenProvider.getAuthentication(jwt);
                // SecurityContext 에 Authentication 객체를 저장합니다.
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }
}