package kr.co.richardprj.swp.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class AuthInterceptor extends HandlerInterceptorAdapter implements SessionNames {
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		HttpSession sess = request.getSession();
		if(sess.getAttribute(LOGIN) == null) {
			String uri = request.getRequestURI();
			String query = request.getQueryString();
			if( StringUtils.isNoneEmpty(query))
				uri = uri + "?" + query;
			
			System.out.println("At AuthInterceptor.... uri===="+ uri);
			
			sess.setAttribute(ATTEMPTED, uri);
			
			response.sendRedirect("/loginPage.do");
			
		}
		
		return true;
	}

	
}
