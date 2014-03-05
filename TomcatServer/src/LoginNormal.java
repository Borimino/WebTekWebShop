
import javax.ws.rs.*;
import javax.ws.rs.core.*;

import javax.servlet.http.*;
import org.jdom2.*;
import java.net.*;

@Path("login")
public class LoginNormal 
{
	@Context HttpSession session;

	Namespace n = Namespace.getNamespace("http://www.cs.au.dk/dWebTek/2014");
	String id;

	@POST
	@Path("login")
	public String login(@QueryParam("username") String username, @QueryParam("password") String password)
	{
		if(username.equals("") || password.equals(""))
		{
			return "You did not enter a username or password";
		}

		Document login = XMLHandler.login(username, password);

		CloudHandler chandler = new CloudHandler(n);
		HttpURLConnection con = chandler.connect(true, "/login");
		Document loginResponse = chandler.getResponse(true, con, login, XMLHandler.getOutputter(), XMLHandler.getSAXBuilder());

		if(loginResponse == null || loginResponse.getRootElement() == null || loginResponse.getRootElement().getChild("customerID", n) == null)
		{
			this.id = "";
			return "You entered a wrong username or password";
		}

		Element ID = loginResponse.getRootElement().getChild("customerID", n);
		id = ID.getText();
		session.setAttribute("id", id);

		return "You are now logged in";
	}

	@GET
	@Path("login")
	public String login2()
	{
		return "Please post to this URL";
	}

	@POST
	@Path("createCustomer")
	public String create(@QueryParam("username") String username, @QueryParam("password") String password)
	{
		if(username.equals("") || password.equals(""))
		{
			return "You did not enter a username or password";
		}

		Document customer = XMLHandler.createCustomer(username, password);

		CloudHandler chandler = new CloudHandler(n);
		HttpURLConnection con = chandler.connect(true, "/createCustomer");
		Document customerResponse = chandler.getResponse(true, con, customer, XMLHandler.getOutputter(), XMLHandler.getSAXBuilder());

		return "A new customer has now been created";
	}
}