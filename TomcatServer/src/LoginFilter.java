import javax.servlet.*;
import javax.servlet.http.*;

import java.io.*;

public class LoginFilter implements Filter
{
	public void init(FilterConfig config)
	{

	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
	{
		//Casts the request and response to Http-equivalents and gets the
		//session
		HttpServletRequest httpReq = (HttpServletRequest) request;
		HttpServletResponse httpRes = (HttpServletResponse) response;
		HttpSession session = httpReq.getSession();

		//Gets the LoginBean from the session
		LoginBean login = (session != null) ? (LoginBean) session.getAttribute("login") : null;

		//Checks wether a user has already logged in or not
		if(login != null && login.getId() != null && login.getId() != "")
		{
			chain.doFilter(request, response);
			return;
		}
		//Redirects the user to the login-page
		httpRes.sendRedirect("../login.jsf");
	}

	public void destroy()
	{

	}
}
