import org.jdom2.*;

import java.net.*;

public class CustomerCreator 
{
	public static void main (String [] args)
	{
		if(args.length < 2)
		{
			System.out.println("You need to supply a username and a password");
			return;
		}
		
		String username = args[0];
		String password = args[1];
		
		Document createCustomer = XMLHandler.createCustomer(username, password);

		CloudHandler chandler = new CloudHandler(Namespace.getNamespace("http://www.cs.au.dk/dWebTek/2014"));

		HttpURLConnection con = chandler.connect("/createCustomer");

		Document response = chandler.getResponse(con, createCustomer, XMLHandler.getOutputter(), XMLHandler.getSAXBuilder());
	}
}
