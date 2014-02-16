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
		//Reads the item file
		Document item = null;
		if(args.length >= 1 && validFilename(args[0]))
		{
			item = addDoc(args[0]);
		}
		if(item == null)
		{
			return;
		}

		//Gets the name of the item from the item file
		Element itemName = item.getRootElement().getChild("itemName", n).clone();

		//Creates a createItem with a shopKey and the itemName from before
		Document createItem = createItem(itemName);

		//Connects to the cloud
		HttpURLConnection con = connect("/createItem");

		//Sends the createItem to the cloud and gets the itemID
		Document itemID = getResponse(con, createItem);

		//Creates a modifyItem with most of the elements from the item + the
		//itemID
		Document modifyItem = modifyItem(itemID.getRootElement().clone(), item.getRootElement().clone());
		
		//Connects to the cloud
		con = connect("/modifyItem");

		//Sends the modifyItem to the cloud
		getResponse(con, modifyItem);

		//SUCCESS!!!!
		System.out.println("SUCCESS!!!!");
	}

	public static Document addDoc(String filename)
	{
		try
		{
			return b.build(new File(filename));
		} catch(IOException ioe)
		{
			return null;
		} catch(JDOMException jdome)
		{
			return null;
		}
	}

	public static boolean validFilename(String filename)
	{
		return filename.matches("[^/?*:;{}\\]+(\\.xml)");
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

	//Starts a connection
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
			return null;
		} catch (IOException ioe)
		{
			return null;
		}

	}

	//Sends a Document and returns the response
	public static Document getResponse(HttpURLConnection con, Document d)
	{
		try
		{
			out.output(d, con.getOutputStream());

			int responseCode = con.getResponseCode();
			InputStream response = con.getInputStream();

			Document itemID = b.build(response);
			return itemID;
		} catch(IOException ioe)
		{
			return null;
		} catch(JDOMException jdome)
		{
			return null;
		}

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
}
