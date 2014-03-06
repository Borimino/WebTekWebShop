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

		Element root = new Element("sellItems");
		Document buydoc = new Document(root);
		
		Element shopIDElement = new Element("shopKey");
		shopIDElement.addContent("1E3D5BA1FD481ECFC983D4B3");
		
		Element itemIDElement = new Element("itemID");
		itemIDElement.addContent(itemID);
		
		Element customerIDElement = new Element("customerID");
		String customerID = (String) session.getAttribute("id");
		customerIDElement.addContent(customerID);
		
		Element saleAmountElement = new Element("saleAmount");
		saleAmountElement.addContent(amount);
		
		
		root.addContent(shopIDElement);
		root.addContent(itemIDElement);
		root.addContent(customerIDElement);
		root.addContent(saleAmountElement);
		
		
		HttpURLConnection con = cloudHandler.connect(true, "/sellItems");
		cloudHandler.getResponse(true, con, buydoc, XMLHandler.getOutputter(),
				XMLHandler.getSAXBuilder());

		try {
			XMLHandler.getOutputter().output(buydoc, System.out);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "SUCCESSS!!!!!!!!!!!!";

		
	}

}
