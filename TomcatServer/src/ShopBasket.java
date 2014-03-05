import java.util.HashMap;
import java.util.Map;

import org.jdom2.Namespace;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.http.HttpSession;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;

@Path("basket")
public class ShopBasket {

    @Context HttpSession session;
	private CloudHandler cloudHandler;
	private Namespace n;
	
	
	public ShopBasket() {

		this(Namespace.getNamespace("http://www.cs.au.dk/dWebTek/2014"));
	
	}
	
	public ShopBasket(Namespace n) {
		this.n = n;
		cloudHandler = new CloudHandler(n);	
	}
	
	@POST
	@Path("update")
	public void updateBasket(@FormParam("items") String items){

		
		JSONObject res = new JSONObject(items);
		
		System.out.println(res);
		
		
		//HVAD FANDEN ER DET???
		
		session.getAttribute("id");
		
		
	}
	
	
	
	
	
	
	
}
