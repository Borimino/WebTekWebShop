/**
 * HRELLOEW LITTLE BASKET!
 */

var basket = [];
var item_count = 0;

function add_to_basket(itemID, itemAmount) {

	var res = {};

	res[itemID] = itemAmount;
	basket.push(res);
	item_count += itemAmount;
	
	
}


function empty_basket() {

	$.ajax({
		type : "POST",
		url : "rest/basket/update",
		data : {
			itemList : basket

		},
		success : function(answer) {

			alert(answer);

		}
	});

}
