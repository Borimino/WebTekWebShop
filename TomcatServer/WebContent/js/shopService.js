/**
 * Tag info fra ShopService, lav td og tr elementer fra dette.
 */
window.onload = function() {
	sendRequest("GET", "rest/shop/items", null, function(itemsText) {
		var items = JSON.parse(itemsText);
		addItemsToTable(items);
	});

	var updateButton = document.getElementById("update");
	addEventListener(updateButton, "click", function() {
		sendRequest("GET", "rest/shop/items", null, function(itemsText) {
			var items = JSON.parse(itemsText);
			addItemsToTable(items);
		});
	});
};

function addItemsToTable(items) {

	var tableBody = document.getElementById("itemTableBody");

	tableBody.innerHTML = "";

	var tr = document.createElement("tr");
	var itemName = document.createElement("th");
	itemName.textContent = "item Name";
	tr.appendChild(itemName);

	var itemPrice = document.createElement("th");
	itemPrice.textContent = "item Price";
	tr.appendChild(itemPrice);

	var itemURL = document.createElement("th");
	itemURL.textContent = "item Picture";
	tr.appendChild(itemURL);

	var itemDescription = document.createElement("th");
	itemDescription.textContent = "item Description";
	tr.appendChild(itemDescription);

	
	var buyItem = document.createElement("th");
	buyItem.textContent = "Buy Item";
	tr.appendChild(buyItem);

	var itemRating = document.createElement("th");
	itemRating.textContent = "Rate item";
	tr.appendChild(itemRating);

	
	


	tableBody.appendChild(tr);
	
	//Used to keep track od how many rows is actually printet
	
	var blockcount = 0;
	
	for (var i = 0; i < items.length; i++) {
		var item = items[i];

		if (item.itemStock > 0) {
			
			blockcount ++;
			
			var tr = document.createElement("tr");

			var itemName = document.createElement("td");
			itemName.textContent = item.itemName;
			tr.appendChild(itemName);

			var itemPrice = document.createElement("td");
			itemPrice.textContent = item.itemPrice;
			tr.appendChild(itemPrice);

			var itemURL = document.createElement("td");
			var itemIMG = document.createElement("img");
			itemIMG.src = item.itemURL;
			itemURL.appendChild(itemIMG);
			tr.appendChild(itemURL);

			var itemDescription = document.createElement("td");
			itemDescription.innerHTML = item.itemDescription;
			tr.appendChild(itemDescription);
			

			var button = document.createElement("td");
			var itemButton = document.createElement("button");
			itemButton.type = "button";
			itemButton.innerHTML = "Buy";
			button.appendChild(itemButton);
			itemButton.setAttribute('onclick', "add_to_basket('" + item.itemID + "','" + "1" + "','" + item.itemPrice + "','" + item.itemName + "')");
			tr.appendChild(button);
			
			var itemRating = document.createElement("td");

			var ratingBlock =
			"<div class='ratingblock'>" +
			"<span onclick='rate(" + item.itemID + ", 1)' class='star rate1 " + "block" + blockcount + "'></span>" +
			"<span onclick='rate(" + item.itemID + ", 2)' class='star rate2 " + "block" + blockcount + "'></span>" +
			"<span onclick='rate(" + item.itemID + ", 3)' class='star rate3 " + "block" + blockcount + "'></span>" +
			"<span onclick='rate(" + item.itemID + ", 4)' class='star rate4 " + "block" + blockcount + "'></span>" +
			"<span onclick='rate(" + item.itemID + ", 5)' class='star rate5 " + "block" + blockcount + "'></span>" +
			"</div>" +
			"<div id='rating" + item.itemID + "'></div>";
			
			setavgrating(item.itemID);
			
			itemRating.innerHTML =	ratingBlock; 
			tr.appendChild(itemRating);
			
			

			tableBody.appendChild(tr);
			addStarfunctionality();
		}
	}

}
