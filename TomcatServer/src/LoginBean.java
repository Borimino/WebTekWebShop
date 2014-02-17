import java.io.*;
import java.net.*;

import javax.faces.*;
import javax.faces.bean.*;

import org.jdom2.*;

@ManagedBean(name="login")
@SessionScoped
public class LoginBean implements Serializable
{
	private String username;
	private String password;

	private String[] admins = {"20"};

	public String login()
	{
		Namespace n = Namespace.getNamespace("http://www.cs.au.dk/dWebTek/2014");
		CloudHandler chandler = new CloudHandler(n);
		Document login = XMLHandler.login(username, password);

		HttpURLConnection con = chandler.connect("/login");

		Document loginResponse = chandler.getResponse(con, login, XMLHandler.getOutputter(), XMLHandler.getSAXBuilder());

		Element ID = loginResponse.getRootElement().getChild("customerID", n);
		String id = ID.getText();

		if(Arrays.asList(admins).contains(id))
		{
			return "LOGGED IN";
		}

		return "FAILED";
	}

	public String getUsername() { return username; }
	public void setUsername(String username) { this.username = username; }
	public String getPassword() { return password; }
	public void setPassword(String password) { this.password = password; }
}
