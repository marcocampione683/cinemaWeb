package control;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.UserBean;

@WebFilter(urlPatterns = {"/admin/*", "/user/*"})
public class AuthFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest hrequest = (HttpServletRequest) request;
		HttpServletResponse hresponse = (HttpServletResponse) response;
		
		String uri = hrequest.getRequestURI();
		
		if(uri.contains("/user/")) {
			HttpSession session = hrequest.getSession(false);
			
			if(session != null) {
				UserBean user = (UserBean) session.getAttribute("utente");
				
				if(user != null && (user.isAdmin() || user.isUser())) {
					System.out.println("è utente");
					chain.doFilter(request, response);
				}
				else
					hresponse.sendRedirect(hrequest.getContextPath() + "/home.jsp");
			}
			
			else
				hresponse.sendRedirect(hrequest.getContextPath() + "/home.jsp");
		}
		
		else if(uri.contains("/admin/")) {
			HttpSession session = hrequest.getSession(false);
			
			if(session != null) {
				UserBean user = (UserBean) session.getAttribute("utente");
				
				if(user != null && user.isAdmin()) {
					System.out.println("è admin");
					chain.doFilter(request, response);
				}
				else
					hresponse.sendRedirect(hrequest.getContextPath() + "/home.jsp");
			}
				
			else {
				hresponse.sendRedirect(hrequest.getContextPath() + "/home.jsp");
			}
		}
	}
}
