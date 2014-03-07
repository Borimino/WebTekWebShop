import javax.print.DocFlavor.STRING;

import org.jdom2.Element;

public class Item {

	private String name;
	private int id;
	private String HTMLdescription;
	private String XMLdescription;
	private String url;
	private int stock;
	private int price;
	private String cleanHTMLDescription;

	public Item(String name, int id, String HTMLdescription, String XMLdescription, String cleanHTMLDescription, String url, int stock,
			int price) {

		this.name = name;
		this.setHTMLdescription(HTMLdescription);
		this.XMLdescription = XMLdescription;
		this.url = url;
		this.stock = stock;
		this.price = price;
		this.setId(id);
		this.cleanHTMLDescription = cleanHTMLDescription;
		
	}
	
	public Item(String name, int id, String HTMLdescription, String XMLdescription, String url, int stock,
			int price) {

		this(name, id, HTMLdescription, XMLdescription, "", url, stock, price);		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getXMLdescription() {
		return XMLdescription;
	}

	public void setXMLdescription(String xMLdescription) {
		XMLdescription = xMLdescription;
	}

	public String getHTMLdescription() {
		return HTMLdescription;
	}

	public void setHTMLdescription(String hTMLdescription) {
		HTMLdescription = hTMLdescription;
	}

	public String getCleanHTMLDescription(){
		return cleanHTMLDescription;
	}
	
	public void setCleanHTMLDescription(String newCleanHTMLDescription){
		cleanHTMLDescription = newCleanHTMLDescription;
	}
	
	
}
