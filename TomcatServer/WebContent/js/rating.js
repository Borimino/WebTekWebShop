//Rate function
function rate(id, rate) {

	$.ajax({
		type : "GET",
		url : "rest/rating/addnew",
		data : {
			id : id,
			rating : rate
		},
		success : function(answer) {

			alert("Gennemsnit " + answer);

		}
	});

}

function setavgrating(id){
	

	
	$.ajax({
		type : "GET",
		url : "rest/rating/getavgrate",
		data : {
			id : id
		},
		success : function(answer) {

			console.log(answer);
			$("#rating" + id).text("Average rating" + answer);

		}
	});

	
	
	
	
}



/** ********Star functionality:******* */

function addStarfunctionality() {

	$(".ratingblock").each(function(i) {

		$(this).children(".star").mouseenter(function() {

			star_hover($(this).index() + 1, i + 1);

		});

		$(this).children(".star").mouseleave(function() {

			clear_star_hover(i+1);
			
		});

	});

}
// Makes the stars after the current one change BG
function star_hover(num, blocknumber) {


	for (var i = 1; i <= 5; i++) {

		if (i <= num) {

			$('.block' + blocknumber + '.rate' + (i)).css(
					"background-position", "left");

		} else {

			$('.block' + blocknumber + '.rate' + (i)).css(
					"background-position", "right");

		}
	}
}



function clear_star_hover(blocknumber){
	
	$('.block' + blocknumber).css("background-position", "right");
	
}
