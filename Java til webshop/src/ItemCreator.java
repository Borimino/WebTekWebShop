import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.JDOMException;
import org.jdom2.output.XMLOutputter;
import org.jdom2.output.Format;
import org.jdom2.Namespace;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.net.URL;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;

public class ItemCreator
{
	private static SAXBuilder b = new SAXBuilder();
	private static XMLOutputter out = new XMLOutputter(Format.getPrettyFormat());
	private static String baseURL = "http://services.brics.dk/java4/cloud";
	private static Namespace n = Namespace.getNamespace("http://www.cs.au.dk/dWebTek/2014");

	public static void main(String[] args)
	{
		Document item = null;
		if(args.length >= 1 && args[0].matches(".+(\\.xml)"))
		{
			item = addDoc(args[0]);
			//System.out.println(args[0] + " is a valid document");
		}
		if(item == null)
		{
			return;
		}

		Element itemName = item.getRootElement().getChild("itemName", n).clone();

		Document createItem = createItem(itemName);

		HttpURLConnection con = connect("/createItem");

		Document itemID = getResponse(con, createItem);

		//try
		//{
			//out.output(itemID, System.out);
		//} catch (IOException ioe)
		//{
			//ioe.printStackTrace();
		//}

		Document modifyItem = modifyItem(itemID.getRootElement().clone(), item.getRootElement().clone());
		
		//try
		//{
			//out.output(modifyItem, System.out);
		//} catch (IOException ioe)
		//{
			//ioe.printStackTrace();
		//}

		con = connect("/modifyItem");

		getResponse(con, modifyItem);
	}

	public static Document addDoc(String filename)
	{
		try
		{
			return b.build(new File(filename));
		} catch(IOException ioe)
		{
			ioe.printStackTrace();
			System.exit(0);
		} catch(JDOMException jdome)
		{
			jdome.printStackTrace();
			System.exit(0);
		}
		return  null;
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

	public static HttpURLConnection connect(String addedURL)
	{
		try
		{
			URL url = new URL(baseURL + addedURL);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();

			con.setDoOutput(true);
			con.setRequestMethod("POST");
			con.connect();

			return con;
		} catch (MalformedURLException murle)
		{
			murle.printStackTrace();
		} catch (IOException ioe)
		{
			ioe.printStackTrace();
		}

		return null;
	}

	public static Document getResponse(HttpURLConnection con, Document d)
	{
		try
		{
			out.output(d, con.getOutputStream());

			int responseCode = con.getResponseCode();
			InputStream response = con.getInputStream();
			//System.out.println(responseCode);

			Document itemID = b.build(response);
			return itemID;
		} catch(IOException ioe)
		{
			ioe.printStackTrace();
		} catch(JDOMException jdome)
		{
			jdome.printStackTrace();
		}

		return null;
	}

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
}
