import java.awt.List;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;

@Path("basket")
public class ShopBasket {

	HttpSession session;
	private CloudHandler cloudHandler;
	private Namespace n;

	public ShopBasket(@Context HttpServletRequest sessionrequest) {

		this(Namespace.getNamespace("http://www.cs.au.dk/dWebTek/2014"),
				sessionrequest);

	}

	public ShopBasket(Namespace n, @Context HttpServletRequest sessionrequest) {
		this.n = n;
		cloudHandler = new CloudHandler(n);
		session = sessionrequest.getSession();
	}

	@POST
	@Path("update")
	public String updateBasket(@FormParam("itemID") String itemID,
			@FormParam("amount") String amount) {

		// Correct to some condition that checks login!
		if (false) {

			return "Not logged in!";

		}

		// TODO: Please correct med!!!!!
		// String customerID = (String) session.getAttribute("id");

		// Temp ID until connection to login is established
		String customerID = "69";

		Document buydoc = XMLHandler.buyItem(itemID, amount, customerID);

		HttpURLConnection con = cloudHandler.connect(true, "/sellItems");
		Document responseDoc = cloudHandler.getResponse(true, con, buydoc,
				XMLHandler.getOutputter(), XMLHandler.getSAXBuilder());


		try {
			XMLHandler.getOutputter().output(responseDoc, System.out);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		java.util.List<Element> children = responseDoc.getRootElement().getChildren();
		
		if(children.get(0).getName().equals("ok")){
			
			return "SUCCESSS!!!!!!!";
			
		} else if(children.get(0).getName().equals("itemSoldOut")){
			
			return "Der æ fler tilbag!!!";
			
		} else {
			
			return "Der skete en fejl";			
		}
		
	}

}
