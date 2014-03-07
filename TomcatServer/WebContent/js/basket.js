/**
 * HRELLOEW LITTLE BASKET!
 */

var basket = [];
var productlist = [];
var item_count = 0;
var total = 0;

function add_to_basket(itemID, itemAmount, priceprItem, itemname) {

	var res = {};

	res["itemID"] = itemID;
	res["amount"] = itemAmount;
	basket.push(res);

	productlist.push(itemname);

	total += priceprItem * itemAmount;
	item_count += itemAmount;

	basket_update();

}

function empty_basket() {

	if (basket.length > 0) {
		$.ajax({
			type : "POST",
			url : "rest/basket/update",
			data : {
				itemList : JSON.stringify(basket)

			},
			success : function(answer) {

				alert(answer);

				if (answer != "Not logged in!") {

					total = 0;
					basket = [];
					item_count = 0;
				}
				
				basket_update();
			}
		});

	} else {

		alert("Du skal smidde et item i kurven inden du kan købe det!!!!!");

	}

}

function basket_update() {

	$('#basketlist').text("");

	$(productlist).each(function(index, item) {

		$('#basketlist').append($(document.createElement("li")).text(item));

	});

	$('#baskettotal').html(total);

}
