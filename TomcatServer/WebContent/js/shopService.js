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
	var itemName = document.createElement("td");
	itemName.textContent = "item Name";
	tr.appendChild(itemName);

	var itemPrice = document.createElement("td");
	itemPrice.textContent = "item Price";
	tr.appendChild(itemPrice);

	var itemURL = document.createElement("td");
	itemURL.textContent = "item Picture";
	tr.appendChild(itemURL);

	var itemDescription = document.createElement("td");
	itemDescription.textContent = "item Description";
	tr.appendChild(itemDescription);


	tableBody.appendChild(tr);

	for (var i = 0; i < items.length; i++) {
		var item = items[i];

		if (item.itemStock > 0) {

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
			itemDescription.textContent = item.itemDescription;
			tr.appendChild(itemDescription);
			console.log(item.itemDescription);


			var button = document.createElement("td");
			var itemButton = document.createElement("button");
			itemButton.type = "button";
			itemButton.innerHTML = "Buy";
			button.appendChild(itemButton);
			itemButton.onClick = "add_to_basket";
			tr.appendChild(button);

			tableBody.appendChild(tr);
		}
	}

}
