package hello.login.web.session;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Enumeration;

@Slf4j
@RestController
public class SessionInfoController {

  @GetMapping("/session-info")
  public String sessionInfo(HttpServletRequest request) {
    HttpSession session = request.getSession(false);
    if (session == null) {
      return "세션이 없습니다.";
    }
    Enumeration<String> attributeNames = session.getAttributeNames();
    String attributeString = attributeNames.toString();
    System.out.println("attributeString = " + attributeString);

    log.info("sessionId = {}", session.getId());
    log.info("getMaxInactiveInterval = {}", session.getMaxInactiveInterval());
    log.info("creationTime = {}", new Date(session.getCreationTime()));
    log.info("lastAccessedTime = {}", new Date(session.getLastAccessedTime()));
    log.info("inNew = {}", session.isNew());

    return "세션션션";


  }
}
