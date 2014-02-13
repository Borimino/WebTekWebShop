import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;


public class ItemCreater {
	
	private static SAXBuilder b = new SAXBuilder();
	private static XMLOutputter xmlOut = new XMLOutputter(Format.getPrettyFormat());
	
	public static void main(String[] args) throws JDOMException, IOException {

		if(args.length < 1 && !valid(args[0])){
			System.out.println("args pls");
		}
		Document tempDoc = new Document();
		
		createDoc(args[0]);
	}
	
	private static boolean valid(String e){
		if(!e.endsWith("xml"))
		return false;
		return true;
	}
	
	private static Document createDoc(String e){
		try {
			return b.build(new File(e));
		} catch (JDOMException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return null;
	}
	
	private static Document docItem(Element e) throws MalformedURLException{
		
		Element root = new Element("createItem");
		Element itemName = e;
		Element shopKey =  new Element("shopKey");
		
		Document d = new Document();
		d.setRootElement(root);
		d.addContent(itemName);
		d.addContent(shopKey);
		
		try {
			URL url = new URL("http://services.brics.dk/java4/cloud");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		
		return d;
	}
}