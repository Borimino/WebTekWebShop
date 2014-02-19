import java.io.*;
import java.net.*;
import java.util.Arrays;

import javax.faces.*;
import javax.faces.bean.*;

import org.jdom2.*;

@ManagedBean(name="login")
@SessionScoped
public class LoginBean implements Serializable
{
	private String username;
	private String password;
	private String id;

	//A list of userID's that have admin rights
	private String[] admins = {"20"};

	public String login()
	{
		Namespace n = Namespace.getNamespace("http://www.cs.au.dk/dWebTek/2014");
		CloudHandler chandler = new CloudHandler(n);

		//Makes a xml-document with <login> as the root tag
		Document login = XMLHandler.login(username, password);

		//Creates a connection to the server
		HttpURLConnection con = chandler.connect("/login");

		//Sends the xml-document to the server and recieves a login-response
		Document loginResponse = chandler.getResponse(con, login, XMLHandler.getOutputter(), XMLHandler.getSAXBuilder());

		if(loginResponse == null || loginResponse.getRootElement() == null || loginResponse.getRootElement().getChild("customerID", n) == null)
		{
			this.id = "";
			return "FAILED";
		}

		//Extracts and stores the customerID
		Element ID = loginResponse.getRootElement().getChild("customerID", n);
		String id = ID.getText();
		this.id = id;

		//Checks if the logged-in customer has admin rights
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
	public String getId() { return id; }
	public void setId(String id) { this.id = id; }
}
