import java.text.DecimalFormat;
import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;

@Path("rating")
public class RatingService {

	private HttpSession session;
	private ServletContext context;
	private HashMap<String, Integer[]> ratingMap;

	public RatingService(@Context HttpServletRequest sessionrequest) {

		session = sessionrequest.getSession();
		context = session.getServletContext();

		HashMap<String, Integer[]> contextList = (HashMap<String, Integer[]>) context
				.getAttribute("ratingList");

		// Creates new map to store ratings if none have been made yet
		if (contextList == null) {
			ratingMap = new HashMap<String, Integer[]>();
		} else {
			ratingMap = contextList;
		}

	}

	@GET
	@Path("addnew")
	public String saveRating(@QueryParam("id") String id,
			@QueryParam("rating") int rating) {

		Integer[] itemRatings = ratingMap.get(id);

		// Adds empty array if none ratings of the item has been made yet
		if (itemRatings == null) {
			itemRatings = makeEmptyRatingArray();
		}

		// adds rating
		itemRatings[rating - 1]++;

		// Puts the moodified rating integer into the map of ratings
		ratingMap.put(id, itemRatings);

		// Saves the map to the context
		context.setAttribute("ratingList", ratingMap);

		DecimalFormat formatter = new DecimalFormat("#.##");

		return "" + formatter.format(calculateRatingaverage(itemRatings));

	}

	private double calculateRatingaverage(Integer[] ratings) {

		int numberOfRatings = 0;
		int valueOfAllRatings = 0;

		for (int i = 0; i < ratings.length; i++) {

			numberOfRatings += ratings[i];
			valueOfAllRatings += (ratings[i] * (i + 1));

		}

		if(numberOfRatings == 0)
		{
			return 0;
		}

		return (double) valueOfAllRatings / (double) numberOfRatings;

	}

	private Integer[] makeEmptyRatingArray() {

		Integer[] res = new Integer[5];

		for (int i = 0; i < 5; i++) {

			res[i] = 0;

		}

		return res;

	}

	@GET
	@Path("getavgrate")
	public String getAverageRating(@QueryParam("id") String id) {

		Integer[] itemRatings = ratingMap.get(id);

		// Adds empty array if none ratings of the item has been made yet
		if (itemRatings == null) {
			itemRatings = makeEmptyRatingArray();
		}

		DecimalFormat formatter = new DecimalFormat("#.##");

		return "" + formatter.format(calculateRatingaverage(itemRatings));

	}

}
