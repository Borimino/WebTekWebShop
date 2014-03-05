import java.util.LinkedList;

import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.json.JSONArray;
import org.json.JSONObject;


@Path("shop")
public class ShopService {
	
	private CloudHandler cloudHandler;
	private Namespace nameSpace = Namespace.getNamespace("http://www.cs.au.dk/dWebTek/2014");

	@GET
	@Path("items")
	public String getItems(){
		
		JSONArray jsonArrayItems = new JSONArray();
		
		LinkedList<Item> items = createList();
		
		
		for(Item i: items){
			JSONObject jsonItemObjects = new JSONObject();

			jsonItemObjects.put("itemName", i.getName());
			jsonItemObjects.put("itemPrice", i.getPrice());
			jsonItemObjects.put("itemStock", i.getStock());
			jsonItemObjects.put("itemURL", i.getUrl());
			jsonItemObjects.put("itemDescription", i.getXMLdescription());
			jsonItemObjects.put("itemID", i.getId());
			
			jsonArrayItems.put(jsonItemObjects);
		}
		

		return jsonArrayItems.toString();
		
	}
	
	public LinkedList<Item> createList() {

		LinkedList<Item> res = new LinkedList<Item>();

		Document doc = cloudHandler.getItemDoc();

		for (Element e : doc.getRootElement().getChildren()) {

			addToList(e, res);

		}
		

		return res;

	}

	private void addToList(Element e, LinkedList<Item> target) {

		String name = e.getChildText("itemName", nameSpace);

		int id = Integer.parseInt(e.getChildText("itemID", nameSpace));

		Element descriptionElement = e.getChild("itemDescription", nameSpace);
		
		String XMLdescription = XMLHandler.getOutputter().outputString(descriptionElement);
		
		Element eDescription = convertToHTML(descriptionElement.getChild("document", nameSpace));

		String HTMLdescription = XMLHandler.getOutputter().outputString(eDescription);

		String url = e.getChildText("itemURL", nameSpace);

		String tempstock = e.getChildText("itemStock", nameSpace);
		int stock;
		// Checks weather or not stock is set
		if (tempstock == "") {
			stock = 0;
		} else {
			stock = Integer.parseInt(tempstock);
		}

		int price = Integer.parseInt(e.getChildText("itemPrice", nameSpace));

		Item temp = new Item(name, id, HTMLdescription, XMLdescription, url, stock, price);

		target.add(temp);

	}

	private Element convertToHTML(Element element) {

		Namespace xhtml = Namespace.getNamespace("http://www.w3.org/1999/xhtml");
		
		for(Element e : element.getChildren())
		{
			convertToHTML(e);
		}
		if(element.getName().equals("document"))
		{
			element.setName("div");
			element.setNamespace(xhtml);
		}
		if(element.getName().equals("bold"))
		{
			element.setName("strong");
			element.setNamespace(xhtml);
		}
		if(element.getName().equals("italics"))
		{
			element.setName("i");
			element.setNamespace(xhtml);
		}
		if(element.getName().equals("list"))
		{
			element.setName("ul");
			element.setNamespace(xhtml);
		}
		if(element.getName().equals("item"))
		{
			element.setName("li");
			element.setNamespace(xhtml);
		}
		
		return element;

	}
}
