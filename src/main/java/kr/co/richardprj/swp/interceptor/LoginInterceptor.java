package kr.co.richardprj.swp.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LoginInterceptor extends HandlerInterceptorAdapter implements SessionNames {
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		HttpSession sess = request.getSession();
		if(sess.getAttribute(LOGIN) != null) {
			sess.removeAttribute(LOGIN);
		}
		return true;
	}

	/**
	 * This implementation is empty.
	 */
	@Override
	public void postHandle(
			HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
			throws Exception {
		HttpSession sess = request.getSession();
		//Object member = modelAndView.getModelMap().get("member");
		//loginInfo 라는 이름으로 세션에 회원객체를 가지고 있는 경우(즉, 로그인 상태인경우)
		Object member = request.getSession().getAttribute(LOGIN);
		//사용자 정보를 session에 담는 처리할 수 있다. 
		//그러나 controller에서 세션에 담았기 때문에 생략.
		//쿠키에 값을 담는 것을 처리한다.
		if (member != null) {
			//사용자가 remember me를 체크한 경우에 쿠키에 저장한다.
			if(StringUtils.isNotEmpty(request.getParameter("useCookie"))) {
				Cookie loginCookie = new Cookie(LOGIN_COOKIE, sess.getId());
				loginCookie.setPath("/");
				loginCookie.setMaxAge(7 * 24 * 60 * 60);
				
				response.addCookie(loginCookie);
			}
			
			String attempted = (String)sess.getAttribute(ATTEMPTED);
			
			if (StringUtils.isNotEmpty(attempted)) {
				response.sendRedirect(attempted);
				sess.removeAttribute(ATTEMPTED);
			}else {
				response.sendRedirect("/");
			}
		}
		
		
	}
}
