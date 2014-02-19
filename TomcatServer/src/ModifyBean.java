import java.net.HttpURLConnection;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;


@ManagedBean
@SessionScoped
public class ModifyBean {
	private String itemName;
	private String itemPrice;
	private String itemStock;
	private String itemDes;
	private String itemURL;
	private String itemID;

	private Namespace n = Namespace.getNamespace("http://www.cs.au.dk/dWebTek/2014");
	
	public String createItem(){
		
		itemID = "";

		Document d = XMLHandler.toXML(itemName, itemPrice, itemStock, itemDes, itemURL, itemID);

		Document d1 = XMLHandler.createItem(d.getRootElement().getChild("itemName", n));

		CloudHandler c = new CloudHandler(Namespace.getNamespace("http://www.cs.au.dk/dWebTek/2014"));
		HttpURLConnection con = c.connect("/createItem");
		Document id = c.getResponse(con, d1, XMLHandler.getOutputter(), XMLHandler.getSAXBuilder());
		
		if(id == null){
			return "Failure";
		}
		
		itemID = id.getRootElement().getText();

		modifyItem();
		return "Succes";
	}
	
	public String modifyItem(){
		
		Document d = XMLHandler.toXML(itemName, itemPrice, itemStock, itemDes, itemURL, itemID);
		
		Element e = new Element("itemID");
		e.addContent(itemID);
		
		Document d1 = XMLHandler.modifyItem(e, d.getRootElement());
		
		CloudHandler c = new CloudHandler(Namespace.getNamespace("http://www.cs.au.dk/dWebTek/2014"));
		HttpURLConnection con = c.connect("/modifyItem");
		
		Document id = c.getResponse(con, d1, XMLHandler.getOutputter(), XMLHandler.getSAXBuilder());
		
		
		
		return "Succes";
	}
	
	public String adjustItemStock(){
		Document d = XMLHandler.StockXML(itemStock);
		
		Element e = new Element("itemID");
		e.addContent(itemID);
		
		Document d1 = XMLHandler.modifyItem(e, d.getRootElement());
		
		CloudHandler c = new CloudHandler(Namespace.getNamespace("http://www.cs.au.dk/dWebTek/2014"));
		HttpURLConnection con = c.connect("/adjustItemStock");
		
		Document id = c.getResponse(con, d1, XMLHandler.getOutputter(), XMLHandler.getSAXBuilder());
		
		return "Succes";
	}


 	public String getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(String itemPrice) {
		this.itemPrice = itemPrice;
	}

	public String getItemStock() {
		return itemStock;
	}

	public void setItemStock(String itemStock) {
		this.itemStock = itemStock;
	}

	public String getItemDes() {
		return itemDes;
	}

	public void setItemDes(String itemDes) {
		this.itemDes = itemDes;
	}

	public String getItemURL() {
		return itemURL;
	}

	public void setItemURL(String itemURL) {
		this.itemURL = itemURL;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemID() {
		return itemID;
	}

	public void setItemID(String itemID) {
		this.itemID = itemID;
	}
	
	
}
