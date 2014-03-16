import java.util.*;

import javax.ws.rs.*;

@Path("indexer")
public class SearchIndexer 
{
	public HashMap<String, HashMap<Integer, Integer>> inverseIndex;

	public static HashMap<String, Integer> makeIndex(String des)
	{
		HashMap<String, Integer> res = new HashMap<String, Integer>();
		des = des.replaceAll("\\.", "");
		des = des.replaceAll("\\?", "");
		des = des.replaceAll("\\!", "");
		des = des.replaceAll("\\<", " \\<");
		des = des.replaceAll("\\>", "\\> ");
		des = des.replaceAll("\\<.*\\>", "");
		ArrayList<String> words = new ArrayList<String>(Arrays.asList(des.split(" ")));

		for(int i = 0; i < words.size(); i++)
		{
			words.set(i, words.get(i).trim().toLowerCase());
		}

		for (int i = 0; i < words.size(); i++) 
		{
			if(res.containsKey(words.get(i)))
			{
				res.put(words.get(i), res.get(words.get(i))+1);
			} else {
				res.put(words.get(i), 1);
			}
		}

		return res;
	}

	public static HashMap<String, HashMap<Integer, Integer>> makeII(ArrayList<HashMap<String, Integer>> indexes, ArrayList<Integer> itemIDs)
	{
		HashMap<String, HashMap<Integer, Integer>> res = new HashMap<String, HashMap<Integer, Integer>>();

		if(indexes.size() != itemIDs.size())
		{
			System.out.println("There is not exactly 1 itemID pr. index");
			return null;
		}

		for(int i = 0; i < indexes.size(); i++)
		{
			for(String word : indexes.get(i).keySet())
			{
				if(res.containsKey(word))
				{
					res.get(word).put(itemIDs.get(i), indexes.get(i).get(word));
				} else {
					res.put(word, new HashMap<Integer, Integer>());
					res.get(word).put(itemIDs.get(i), indexes.get(i).get(word));
				}
			}
		}

		return res;
	}

	public static HashMap<String, HashMap<Integer, Integer>> rebuildII(ArrayList<Item> items)
	{
		ArrayList<HashMap<String, Integer>> indexes = new ArrayList<HashMap<String, Integer>>();
		ArrayList<Integer> itemIDs = new ArrayList<Integer>();
		for(Item item : items)
		{
			indexes.add(makeIndex(item.getHTMLdescription()));
			itemIDs.add(item.getId());
		}

		HashMap<String, HashMap<Integer, Integer>> res = makeII(indexes, itemIDs);

		for(String k : res.keySet())
		{
			System.out.println(k + "=" + res.get(k));
		}

		return res;
	}

	@GET
	@Path("rebuild")
	public String rebuildII()
	{
		ArrayList<Item> items = new ArrayList<Item>((new ShopService()).createList());
		
		inverseIndex = rebuildII(items);

		return "Index has been rebuild";
	}
}
