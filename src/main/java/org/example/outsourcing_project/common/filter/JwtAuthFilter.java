package org.example.outsourcing_project.common.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.outsourcing_project.common.jwt.JwtProvider;
import org.springframework.util.PatternMatchUtils;

import java.io.IOException;
import java.util.List;


public class JwtAuthFilter implements Filter {

    private final JwtProvider jwtProvider;
    private final List<String> WHITE_LIST = List.of("/", "/authors/**");

    public JwtAuthFilter(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        // 0. WHITE_LIST에 포함된 경로 검증 없이 통과
        if(isWhiteListed(request)) {

            filterChain.doFilter(request, response);
            return;
        }

        // 1. 토큰을 가져온다
        String token = extractedTokenFromHeader(request);
        // 2. 토큰을 검증
        if(token != null && jwtProvider.validateToken(token)) {
            // 3. 토큰에서 사용자 정보를 가져온다.
            Long userIdFromToken = jwtProvider.getUserIdFromToken(token);
            request.setAttribute("userId",userIdFromToken);
            filterChain.doFilter(request, response);
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("토큰 오류 발생"); 
        }
    }

    //  와일드 카드가 포함된 문자열 패턴과 요청 URI가 일치 하는지 확인
    private boolean isWhiteListed(HttpServletRequest request) {
        return WHITE_LIST.stream().anyMatch(uriPattern -> PatternMatchUtils.simpleMatch(uriPattern, request.getRequestURI()));
    }

    private String extractedTokenFromHeader(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        // 예외처리 null 처리
        String token = bearerToken.substring(7);

        return token;
    }
}
