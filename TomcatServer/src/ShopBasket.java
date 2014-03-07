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

	/*
	 * IKKE TESTET!!!!
	 */
	@POST
	@Path("update")
	public String updateBasket(@FormParam("itemList") String itemList) {

		
		
		// Correct to some condition that checks login!
		if (session.getAttribute("id") == null || ((String) session.getAttribute("id")).equals("")) {

			return "Not logged in!";

		}

		String res = "";

		JSONArray jsonArray = new JSONArray(itemList);

		for(int i = 0; i < jsonArray.length(); i++)
		{
			JSONObject o = (JSONObject) jsonArray.get(i);
			
			String itemID = (String) o.get("itemID");
			String amount = (String) o.get("amount");

			String customerID = (String) session.getAttribute("id");

			Document buydoc = XMLHandler.buyItem(itemID, amount, customerID);

			HttpURLConnection con = cloudHandler.connect(true, "/sellItems");
			Document responseDoc = cloudHandler.getResponse(true, con, buydoc,
					XMLHandler.getOutputter(), XMLHandler.getSAXBuilder());

			
			
			java.util.List<Element> children = responseDoc.getRootElement().getChildren();
			
			if(children.get(0).getName().equals("ok")){
				
				res += "Succæ ve' "+itemID + "\n";
				
			} else if(children.get(0).getName().equals("itemSoldOut")){
				
				res += "Der æ' fler tilbage a' " + itemID + " tilbage \n";
				
			} else {
				
				res += "Dar sket' a fej wæ" + itemID + "\n";			
			}
		}
		return res;
		
	}

}
