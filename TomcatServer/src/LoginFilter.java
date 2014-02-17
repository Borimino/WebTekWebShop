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

		String id = (String) session.getAttribute("customerID");
		if(id != null)
		{
			chain.doFilter(request, response);
			return;
		}
		httpRes.sendRedirect("login.jsp");
	}

	public void destroy()
	{

	}
}
