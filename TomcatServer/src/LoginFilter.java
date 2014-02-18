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
		HttpServletRequest httpReq = (HttpServletRequest) request;
		HttpServletResponse httpRes = (HttpServletResponse) response;
		HttpSession session = httpReq.getSession();

		LoginBean login = (session != null) ? (LoginBean) session.getAttribute("login") : null;

		if(login != null && login.getId() != null && login.getId() != "")
		{
			chain.doFilter(request, response);
			return;
		}
		httpRes.sendRedirect("../login.jsf");
	}

	public void destroy()
	{

	}
}
