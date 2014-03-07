/**
 * HRELLOEW LITTLE BASKET!
 */

var basket = [];
var productlist = [];
var item_count = 0;
var total = 0;

function add_to_basket(itemID, itemAmount, priceprItem, itemname){

	var res = {};

	res["itemID"] = itemID;
	res["amount"] = itemAmount;
	basket.push(res);
	
	basketlist.push(itemname);
	
	total += priceprItem;
	item_count += itemAmount;
	
	basket_update();

}

function empty_basket() {
	
	
	if(basket.length > 0){
	$.ajax({
		type : "POST",
		url : "rest/basket/update",
		data : {
			itemList : JSON.stringify(basket)

		},
		success : function(answer) {

			alert(answer);
			total = 0;
			basket = [];
			item_count = 0;
		}
	});

	} else {
		
		alert("Du skal smidde et item i kurven inden du kan købe det!!!!!");
		
	}
	
}

function basket_update(){
	
	
	
}
