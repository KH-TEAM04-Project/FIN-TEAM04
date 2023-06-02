package com.kh.team4.jwt;

import com.kh.team4.service.MemberService;
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
    private MemberService memberService;

    private String resolveToken(HttpServletRequest request) { // request header에서 토큰 정보를 꺼내오는 메소드
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = resolveToken(request);

        if (StringUtils.hasText(accessToken) && tokenProvider.validateToken(accessToken)) {
            // 레디스에 해당 accessToken 로그아웃 여부 확인
            String isLogout = (String) redisTemplate.opsForValue().get(accessToken);

            // 로그아웃이 없는(되어 있지 않은) 경우 해당 토큰은 정상적으로 작동하기
            if (ObjectUtils.isEmpty(isLogout)) {
                // 토큰이 유효하면 토큰으로부터 유저 정보를 받아옵니다.
                Authentication authentication = tokenProvider.getAuthentication(accessToken);
                // SecurityContext 에 Authentication 객체를 저장합니다.
                SecurityContextHolder.getContext().setAuthentication(authentication);

                // 여기에 재발급을 시키는 코드를 집어넣으면?
                // memberService.reissue(accessToken);  HTTP 확인하고 넣어보자. 될거같다.

            } else { System.out.println("로그아웃 된녀석!"); }
        } else {
            System.out.println("토큰이 없거나 인가되지 않은 토큰!");
        }
        filterChain.doFilter(request, response);
    }
}