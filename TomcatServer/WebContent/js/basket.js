/**
 * HRELLOEW LITTLE BASKET!
 */

function test() {

	$.ajax({
		type : "POST",
		url : "rest/basket/update",
		data : {
			itemID : "3169",
			amount : "10"

		},
		success : function(data) {

			alert(data);

		}
	});

}
