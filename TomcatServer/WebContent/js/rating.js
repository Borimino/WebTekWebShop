
//Rate funvtion
function rate(id, rate) {
	alert(id);

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

/** ********Star functionality:******* */

//Attaches hover event to each star
$(document).ready(function() {

	$(".star").each(function(i) {

		$(this).hover(function() {

			star_hover(i + 1);
		});
	});

});

// Makes the stars after the current one change BG
function star_hover(num) {

	for(var i = 1; i <= 5; i++){
		
		if(i <= num){
		
		$('.rate'+(i)).css("background-position" , "left");
		console.log('FARVET: ' + '.rate'+(i));
		
		} else{
			
			$('.rate'+(i)).css("background-position" , "right");
			console.log('IKKE FARVET:' + '.rate'+(i));
				
			
		}
		
		
	}
	
	

}
