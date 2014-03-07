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
	
	var itemStock = document.createElement("td");
	itemStock.textContent = "Number of items";
	tr.appendChild(itemStock);
	
	var inTotal = document.createElement("td");
	inTotal.textContent = "In Total";
	tr.appendChild(inTotal);
	
	
	tableBody.appendChild(tr);


	for (var i = 0; i < items.length; i++) {
		var item = items[i];

		if(item.itemStock > 0){
		
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
			tableBody.appendChild(tr);
			
			var itemNumber = document.createElement("td");
			var inputField = document.createElement("input");
			inputField.type = "text";
			inputField.className = "css-class-name";
			itemNumber.appendChild(inputField);
			tr.appendChild(itemNumber);
			
			var totalPrice = document.createElement("td");
			var totalField = document.createElement("input");
			totalField.type = "text";
			totalField.className = "css-class-name";
			totalPrice.appendChild(totalField);
			tr.appendChild(totalPrice);
			
			var button = document.createElement("td");
			var itemButton = document.createElement("button");
			itemButton.type="button";
			itemButton.innerHTML="Buy";
			button.appendChild(itemButton);
			itemButton.onClick = "add_to_basket";
			tr.appendChild(button);

		}
	}
	
	


}

