/**
 * Responsible for initiating the chat session
 */

$(document).ready(function(){
				
	
	
	$.ajax({
		type : "POST",
		url : "rest/chat/checkcustomerbought",
		data : {
			id : id
		},
		success : function(status) {

			
			if(status){
				
				$("#chatlink").append("<a style='color:green;' href='#' onclick='startchat()'>Klik her for at chatte med en medarbejder</a>");			
				}			
		}
	});


});


function startchat(){
	window.open("customerchat.html","_blank", "height=600, width=450");
	
}
