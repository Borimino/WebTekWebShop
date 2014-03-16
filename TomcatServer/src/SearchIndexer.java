import java.util.*;

import javax.ws.rs.*;

import org.json.*;

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
		des = des.replaceAll("\\<.*?\\>", "");
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

		//for(String k : res.keySet())
		//{
			//System.out.println(k + "=" + res.get(k));
		//}

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

	public ArrayList<Integer> match(String query)
	{
		if(inverseIndex == null)
		{
			rebuildII();
		}

		ArrayList<Integer> res = new ArrayList<Integer>();

		HashMap<Integer, Integer> allMatches = new HashMap<Integer, Integer>();

		ArrayList<String> words = new ArrayList<String>(Arrays.asList(query.split(" ")));
		
		for(int i = 0; i < words.size(); i++)
		{
			words.set(i, words.get(i).trim());
		}

		for(String s : words)
		{
			if(inverseIndex.containsKey(s))
			{
				for(Map.Entry<String, HashMap<Integer, Integer>> hm : inverseIndex.entrySet())
				{
					if(!hm.getKey().equals(s))
					{
						continue;
					}
					for(Integer itemID : hm.getValue().keySet())
					{
						if(allMatches.containsKey(itemID))
						{
							allMatches.put(itemID, allMatches.get(itemID)+1);
						} else {
							allMatches.put(itemID, 1);
						}
					}
				}
			}
		}

		for(Integer i : allMatches.keySet())
		{
			if(words.size() == 1)
			{
				res.add(i);
			}
			if(allMatches.get(i) > 1)
			{
				res.add(i);
			}
		}

		return res;
	}

	@GET
	@Path("search")
	public String search(@QueryParam("query") String query)
	{
		String res = "";

		for(Integer i : match(query))
		{
			res += i + "\n";
		}

		return res;
	}

	@GET
	@Path("search2")
	public String search2(@QueryParam("query") String query)
	{
		JSONArray res = new JSONArray();

		LinkedList<Item> items = (new ShopService()).createList();

		if(query == null)
		{
			query = "";
		}

		ArrayList<Integer> matches = match(query);
		//System.out.println(matches);

		for(Item i : items)
		{
			if(matches.contains(i.getId()))
			{
				JSONObject jsonItemObjects = new JSONObject();

				jsonItemObjects.put("itemName", i.getName());
				jsonItemObjects.put("itemPrice", i.getPrice());
				jsonItemObjects.put("itemStock", i.getStock());
				jsonItemObjects.put("itemURL", i.getUrl());
				jsonItemObjects.put("itemDescription", i.getCleanHTMLDescription());
				jsonItemObjects.put("itemID", i.getId());

				res.put(jsonItemObjects);
			}
		}

		return res.toString();
	}
}
