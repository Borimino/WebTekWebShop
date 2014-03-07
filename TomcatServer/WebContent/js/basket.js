/**
 * HRELLOEW LITTLE BASKET!
 */

var basket = {};
var item_count = 0;

function add_to_basket(itemID, itemAmount) {

	basket[itemID] = itemAmount;
	item_count += itemAmount;
}

function empty_basket() {


	function basket_loop() {

		setTimeout(function() {

			id =
			buy_item(id, basket[id]);

		}, delay);

	}

}

function buy_item(itemID, itemAmount) {

	$.ajax({
		type : "POST",
		url : "rest/basket/update",
		data : {
			itemID : itemID,
			amount : itemAmount

		},
		success : function(answer) {

			alert(answer);

		}
	});

}
