import java.net.HttpURLConnection;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;

public class ItemCreator
{

	private static Namespace n = Namespace.getNamespace("http://www.cs.au.dk/dWebTek/2014");
	private static CloudHandler handler = new CloudHandler(n);

	public static void main(String[] args)
	{
		//Reads the item file
		Document item = null;
		if(args.length >= 1 && validFilename(args[0]))
		{
			item = XMLHandler.addDoc(args[0]);
		}
		if(item == null)
		{
			return;
		}

		//Gets the name of the item from the item file
		Element itemName = item.getRootElement().getChild("itemName", n).clone();

		//Creates a createItem with a shopKey and the itemName from before
		Document createItem = XMLHandler.createItem(itemName);

		//Connects to the cloud
		HttpURLConnection con = handler.connect("/createItem");

		//Sends the createItem to the cloud and gets the itemID
		Document itemID = handler.getResponse(con, createItem, XMLHandler.getOutputter(), XMLHandler.getSAXBuilder());

		//Creates a modifyItem with most of the elements from the item + the
		//itemID
		Document modifyItem = XMLHandler.modifyItem(itemID.getRootElement().clone(), item.getRootElement().clone());
		
		//Connects to the cloud
		con = handler.connect("/modifyItem");

		//Sends the modifyItem to the cloud
		handler.getResponse(con, modifyItem, XMLHandler.getOutputter(), XMLHandler.getSAXBuilder());

		//SUCCESS!!!!
		System.out.println("SUCCESS!!!!");
	}

	

	public static boolean validFilename(String filename)
	{
		return filename.matches("[^/?*:;{}\\\\]+(\\.xml)");
	}

	
	


}
