import java.io.File;
import java.io.IOException;

//import javax.faces.bean.*;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

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

	public static XMLOutputter getOutputter(){
		return out; 
	}
	public static SAXBuilder getSAXBuilder(){
		return b;
	}

}
