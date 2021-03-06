import java.io.File;
import java.io.IOException;

import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
//import javax.faces.bean.*;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;

//@ManagedBean
public class XMLHandler {
	
	private static SAXBuilder b = new SAXBuilder();
	private static XMLOutputter out = new XMLOutputter(Format.getPrettyFormat());
	private static Namespace n = Namespace.getNamespace("http://www.cs.au.dk/dWebTek/2014");

	public static Document addDoc(String filename) {
		try {
			return b.build(new File(filename));
		} catch (IOException ioe) {
			return null;
		} catch (JDOMException jdome) {
			return null;
		}
	}
	
	public static Document createItem(Element itemName)
	{
		Document createItem = new Document();
		Element root = new Element("createItem", n);
		Element shopKey = new Element("shopKey", n);
		shopKey.addContent("1E3D5BA1FD481ECFC983D4B3");
		root.addContent(shopKey);
		root.addContent(itemName.clone());
		createItem.setRootElement(root);

		return createItem;
	}

	//Creates modifyItem XML-document
	public static Document modifyItem(Element itemID, Element item)
	{
		Document modifyItem = new Document();
		Element root = new Element("modifyItem", n);
		Element shopKey = new Element("shopKey", n);
		shopKey.addContent("1E3D5BA1FD481ECFC983D4B3");
		root.addContent(shopKey);
		root.addContent(itemID);
		for(Element c : item.getChildren())
		{
			if(!c.getName().equals("itemStock") && !c.getName().equals("itemID"))
			{
				root.addContent(c.clone());
			}
		}

		modifyItem.setRootElement(root);
		

		return modifyItem;
	}

	public static Document adjustStock(Element itemID, Element item, String oldItemStock)
	{
		int iOldStock = Integer.parseInt(oldItemStock);
		int iStock = Integer.parseInt(item.getChildText("itemStock", n));
		int stock = iStock-iOldStock;

		Document adjustStock = new Document();
		Element root = new Element("adjustItemStock", n);
		Element shopKey = new Element("shopKey", n);
		shopKey.addContent("1E3D5BA1FD481ECFC983D4B3");
		Element adjust = new Element("adjustment", n);
		adjust.addContent(stock + "");
		root.addContent(shopKey);
		root.addContent(itemID);
		root.addContent(adjust);
		adjustStock.setRootElement(root);

		return adjustStock;
	}

	public static Document login(String username, String password)
	{
		Document login = new Document();
		Element root = new Element("login", n);
		Element customerName = new Element("customerName", n);
		customerName.addContent(username);
		Element customerPass = new Element("customerPass", n);
		customerPass.addContent(password);
		root.addContent(customerName);
		root.addContent(customerPass);
		login.addContent(root);

		return login;
	}

	public static Document createCustomer(String username, String password)
	{
		Document createCustomer = new Document();
		Element root = new Element("createCustomer", n);
		Element customerName = new Element("customerName", n);
		customerName.addContent(username);
		Element customerPass = new Element("customerPass", n);
		customerPass.addContent(password);
		Element shopKey = new Element("shopKey", n);
		shopKey.addContent("1E3D5BA1FD481ECFC983D4B3");
		root.addContent(customerName);
		root.addContent(customerPass);
		root.addContent(shopKey);
		createCustomer.addContent(root);
		
		return createCustomer;
	}
	
	public static Document buyItem(String itemID, String amount, String customerID){
		
		Element root = new Element("sellItems", n);
		Document buydoc = new Document(root);

		Element shopIDElement = new Element("shopKey", n);
		shopIDElement.addContent("1E3D5BA1FD481ECFC983D4B3");

		Element itemIDElement = new Element("itemID", n);
		itemIDElement.addContent(itemID);

		Element customerIDElement = new Element("customerID", n);
		customerIDElement.addContent(customerID);
		
		Element saleAmountElement = new Element("saleAmount", n);
		saleAmountElement.addContent(amount);

		root.addContent(shopIDElement);
		root.addContent(itemIDElement);
		root.addContent(customerIDElement);
		root.addContent(saleAmountElement);
		

		return buydoc;
		
		
	}
	
	
	public static Document convertCloudXML(){
		
		return null;
		
		
	}

	public static XMLOutputter getOutputter(){
		return out; 
	}
	public static SAXBuilder getSAXBuilder(){
		return b;
	}
	
	public static Document toXML(String in, String ip, String is, String des, String url, String id){
		
		Document d = new Document();
		Element root = new Element("item", n);
		
		Element itemName = new Element("itemName", n);
		Element itemPrice = new Element("itemPrice", n);
		Element itemStock = new Element("itemStock", n);
		Element itemDes = new Element("itemDescription", n);
		Element itemURL = new Element("itemURL", n);
		Element itemID = new Element("itemID", n);
		
		itemName.addContent(in);
		itemPrice.addContent(ip);
		itemStock.addContent(is);
		itemDes.addContent(des);
		itemURL.addContent(url);
		itemID.addContent(id);
		
		root.addContent(itemName);
		root.addContent(itemPrice);
		root.addContent(itemStock);
		root.addContent(itemDes);
		root.addContent(itemURL);
		root.addContent(itemID);
		
		d.setRootElement(root);
		return d;
	}
	
	public static Document StockXML(String s){
		
		Document d = new Document();
		Element root = new Element("item", n);
		
		Element stock = new Element("itemStock");
		
		stock.addContent(s);
		
		root.addContent(stock);
		
		d.setRootElement(root);
		return d;
		
	}

}
