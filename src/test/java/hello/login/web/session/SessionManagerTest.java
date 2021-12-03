package hello.login.web.session;

import hello.login.domain.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.http.HttpServletResponse;

public class SessionManagerTest {

  SessionManager sessionManager = new SessionManager();

  @Test
  void sessionTest() {

    //session 생성
    MockHttpServletResponse response = new MockHttpServletResponse();
    Member member = new Member();
    sessionManager.createSession(member, response);

    //요청에 응답 쿠키 저장 확인 코드
    MockHttpServletRequest request = new MockHttpServletRequest();
    request.setCookies(response.getCookies());

    //세션 조회
    Object result = sessionManager.getSession(request);
    Assertions.assertThat(result).isEqualTo(member);

    //세션 만료
    sessionManager.expire(request);
    Object expire = sessionManager.getSession(request);
    Assertions.assertThat(expire).isNull();

  }
}
