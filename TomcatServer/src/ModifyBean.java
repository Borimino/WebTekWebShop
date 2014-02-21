import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.jdom2.Content;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;

@ManagedBean(name = "modify")
@SessionScoped
public class ModifyBean {
	private String itemName;
	private String itemPrice;
	private String itemStock;
	private String itemDes;
	private String itemURL;
	private String itemID;

	private Namespace n = Namespace
			.getNamespace("http://www.cs.au.dk/dWebTek/2014");

	public String createItem() {

		itemID = "";

		if (!isInt(itemPrice)) {
			return "FAILURE";
		}

		Document d = XMLHandler.toXML(itemName, itemPrice, itemStock, itemDes,
				itemURL, itemID);

		Document d1 = XMLHandler.createItem(d.getRootElement().getChild(
				"itemName", n));

		CloudHandler c = new CloudHandler(
				Namespace.getNamespace("http://www.cs.au.dk/dWebTek/2014"));
		HttpURLConnection con = c.connect("/createItem");
		Document id = c.getResponse(con, d1, XMLHandler.getOutputter(),
				XMLHandler.getSAXBuilder());

		if (id == null) {
			return "Failure";
		}

		itemID = id.getRootElement().getText();

		modifyItem();
		return "Succes";
	}

	public String modifyItem() {

		// Checks if itemPrice and itemStock are integers
		if (!isInt(itemPrice)) {
			return "FAILURE";
		}

		// Converts itemDes from String to InputStream
		InputStream is;
		Element eItemDes = new Element("document", n);
		try {
			is = new ByteArrayInputStream(itemDes.getBytes("UTF-8"));
			eItemDes = XMLHandler.getSAXBuilder().build(is).getRootElement();
		} catch (UnsupportedEncodingException uee) {
		} catch (JDOMException jdome) {
		} catch (IOException ioe) {
		}

		// Constructs the item document
		Document d = XMLHandler.toXML(itemName, itemPrice, itemStock, "",
				itemURL, itemID);
		d.getRootElement().getChild("itemDescription", n).addContent(eItemDes);

		Element e = new Element("itemID", n);
		e.addContent(itemID);

		// Constructs the modifyItem document
		Document d1 = XMLHandler.modifyItem(e, d.getRootElement());

		// Sends the modifyItem to the server
		CloudHandler c = new CloudHandler(
				Namespace.getNamespace("http://www.cs.au.dk/dWebTek/2014"));
		HttpURLConnection con = c.connect("/modifyItem");

		Document id = c.getResponse(con, d1, XMLHandler.getOutputter(),
				XMLHandler.getSAXBuilder());

		return "SUCCESS";
	}

	public String adjustItemStock() {

		if (!isInt(itemStock)) {
			return "FAILURE";
		}

		Document d = XMLHandler.StockXML(itemStock);

		Element e = new Element("itemID");
		e.addContent(itemID);

		Document d1 = XMLHandler.modifyItem(e, d.getRootElement());

		CloudHandler c = new CloudHandler(
				Namespace.getNamespace("http://www.cs.au.dk/dWebTek/2014"));
		HttpURLConnection con = c.connect("/adjustItemStock");

		Document id = c.getResponse(con, d1, XMLHandler.getOutputter(),
				XMLHandler.getSAXBuilder());

		return "SUCCESS";
	}

	private boolean isInt(String s) {
		try {
			Integer.parseInt(s);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	public String loadModifyData(ActionEvent ae) {
		Map<String, String> params = FacesContext.getCurrentInstance()
				.getExternalContext().getRequestParameterMap();
		String id = params.get("id");
		// String id = "0";
		System.out.println(id);

		CloudHandler ch = new CloudHandler(
				Namespace.getNamespace("http://www.cs.au.dk/dWebTek/2014"));

		Document d = ch.getItemDoc();

		Element res = new Element("item");

		for (Content c : d.getRootElement().getDescendants()) {
			if (!(c instanceof Element)) {
				continue;
			}
			Element e = (Element) c;

			if (e.getName().equals("itemID") && e.getText().equals(id)) {
				res = (Element) e.getParent();
			}
		}

		if (res.getChildren().size() == 0) {
			return "FAILURE";
		}

		itemName = res.getChild("itemName", n).getText();
		itemPrice = res.getChild("itemPrice", n).getText();
		itemStock = res.getChild("itemStock", n).getText();
		itemDes = res.getChild("itemDes", n).getText();
		itemURL = res.getChild("itemURL", n).getText();
		itemID = res.getChild("itemID", n).getText();

		return "SUCCESS";
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
		System.out.println(itemID);
	}

}
