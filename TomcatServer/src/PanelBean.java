import java.net.HttpURLConnection;
import java.util.LinkedList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jdom2.output.XMLOutputter;

@ManagedBean
@SessionScoped
public class PanelBean {

	private LinkedList<Item> items;
	private CloudHandler cloudHandler;
	private Namespace nameSpace;
	
	public PanelBean() {

		this(Namespace.getNamespace("http://www.cs.au.dk/dWebTek/2014"));

	}

	public PanelBean(org.jdom2.Namespace n) {

		this.cloudHandler = new CloudHandler(n);
		
		this.nameSpace = n;

		items = createList();

	}

	public LinkedList<Item> createList() {

		LinkedList<Item> res = new LinkedList<Item>();

		Document doc = getDoc();

		for (Element e : doc.getRootElement().getChildren()) {

			addToList(e, res);

		}

		return res;

	}

	private void addToList(Element e, LinkedList<Item> target) {

		String name = e.getChildText("itemName", nameSpace);
		
		int id = Integer.parseInt(e.getChildText("itemID" , nameSpace));
		
		Element descriptionElement = e.getChild("itemDescription", nameSpace);
		
		XMLOutputter out = XMLHandler.getOutputter();		
		String description = out.outputString(descriptionElement);
		
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

		Item temp = new Item(name, id, description, url, stock, price);

		target.add(temp);

	}

	private Document getDoc() {

		Document res = new Document();

		Element e = new Element("HRELLOW");
		res.setRootElement(e);
		HttpURLConnection con = cloudHandler.connect(false,
				"/listItems?shopID=488");

		res = cloudHandler.getResponse(false, con, res,
				XMLHandler.getOutputter(), XMLHandler.getSAXBuilder());

		return res;

	}

	public LinkedList<Item> getItems() {
		return items;
	}

	public void setItems(LinkedList<Item> items) {
		this.items = items;
	}

}
