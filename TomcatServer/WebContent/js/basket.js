/**
 * HRELLOEW LITTLE BASKET!
 */


function test(){

$.ajax({
	type : "POST",
	url : "rest/basket/update",
	data : { items : {
		"3169": "10",
		"3175": "15"
	}},
	success : function(data) {

		alert(data);

	}
});

}
