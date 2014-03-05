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
	
	console.log(items.length);

	for (var i = 0; i < items.length; i++) {

		var item = items[i];
		var tr = document.createElement("tr");

		var itemName = document.createElement("td");
		itemName.textContent = item.itemName;
		tr.appendChild(itemName);

		var itemPrice = document.createElement("td");
		itemPrice.textContent = item.itemPrice;
		tr.appendChild(itemPrice);

		var itemURL = document.createElement("td");
		itemURL.textContent = item.itemURL;
		tr.appendChild(itemURL);

		var itemDesription = document.createElement("td");
		itemDesription.textContent = item.itemDescription;
		tr.appendChild(itemURL);

		tableBody.appendChild(tr);
	}

}

