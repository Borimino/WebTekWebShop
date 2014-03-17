/**
 * Responsible for initiating the chat session
 */

$(document).ready(function(){
				
	
	
	chatlink();

});
function chatlink() {
	$.ajax({
		type : "POST",
		url : "rest/chat/checkc",
		data : {
		},
		success : function(state) {

			if(state == "true"){
				
				$("#chatlink").append("<a style='color:green;' href='#' onclick='startchat()'>Klik her for at chatte med en medarbejder</a>");			
			} else {
				$('#chatlink').text("");
			}
		}
	});
}

function startchat(){
	window.open("customerchat.html","_blank", "height=600, width=450");
	
}
