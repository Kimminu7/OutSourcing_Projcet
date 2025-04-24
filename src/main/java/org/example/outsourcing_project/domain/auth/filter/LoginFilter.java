package org.example.outsourcing_project.domain.auth.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

import java.io.IOException;

@Slf4j
public class LoginFilter implements Filter {
    // WHITE_LIST 설정
    private static final String[] WHITE_LIST = {"/", "/users/signup", "/authors/login"};

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // 다운 캐스팅
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;

        String requestURL = httpServletRequest.getRequestURI();

        // 로그
        log.info("로그인 필터 로직 실행");

        // 로그인을 체크 해야 하는 URL인지 검사
        // WHITE LIST URL에 포함된 경우 true 반환 -> !true = false
        if (!isWhiteList(requestURL)) {

            // 로그인 확인 -> 로그인하면 sessions에 값이 저장되어 있다는 가정.
            // 세션이 존재하면 가져온다. 세션이 없으면 session = null
            HttpSession session = httpServletRequest.getSession(false);

            // 로그인 하지 않은 사용자인 경우
            if (session == null || session.getAttribute("userId") == null) {
                throw new RuntimeException("로그인 해주세요.");
            }
        }

        // 1번 경우 : whiteListURL에 등록된 URL 요청이면 바로 chain.doFilter()
        // 2번 경우 : 필터 로직 통과 후 다음 필터 호출 chain.doFilter()
        // 다음 필터 없으면 Servlet -> Controller 호출
        filterChain.doFilter(servletRequest, servletResponse);
    }

    // 로그인 여부를 확인하는 URL인지 체크
    private boolean isWhiteList (String requestURL) {
        // requestURL이 WHITE_LIST에 포함되는지 확인
        return PatternMatchUtils.simpleMatch(WHITE_LIST, requestURL);
    }
}

