package hello.login.web.filter;

import hello.login.web.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Slf4j
public class LoginCheckFilter implements Filter {

  private static final String[] whitelist = {"/", "/members/add", "/login", "/logout", "/css/*"};
  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    HttpServletRequest httpRequest = (HttpServletRequest) request;
    String requestURI = httpRequest.getRequestURI();

    HttpServletResponse httpResponse = (HttpServletResponse) response;

    try {
      log.info("인증 필터 시작 {}", requestURI);
      if (isLoginCheckPath(requestURI)) {
        log.info("인증 체크 로직 실행 {} ", requestURI);
        HttpSession session = httpRequest.getSession(false);
        if (session == null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null) {
          log.info("미인증 사용자 요청 {}", requestURI);
          httpResponse.sendRedirect("/login?redirectURL="+ requestURI);

          //너는 미인증이니 돌아가는 URL만 세팅 해줄게. 더 이상 메모리(서블릿,컨트롤러 호출하지마) 쓰지마. return 할게.
          return;
        }
      }
      chain.doFilter(request, response);
    } catch (Exception e) {
      throw  e;
    } finally {
      log.info("finally 실행 {} ", requestURI);
    }
  }

  /**
   * whitelist -> 인증 Check X
   */
  private boolean isLoginCheckPath(String requestURI) {
    return !PatternMatchUtils.simpleMatch(whitelist, requestURI);
  }
}
