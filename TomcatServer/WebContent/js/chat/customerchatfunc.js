/**
 * 
 */

setTimeout(initID, 500);

function initID(){

	setInterval(checkFormessages, 2000);
	
	
} 

function checkFormessages(){
	
	console.log("Checking for new messages");
	$.ajax({
		type : "POST",
		url : "rest/chat/custMes",
		data : {
		},
		success : function(answer) {
			
			if(answer != ""){
				
				$(".messagewindow").append("<b style='color:red'>Employee:</b><span> "+ answer + "</span><br>");

				
			}
			
			
		}
	});
	
	
}

function senddata() {

	// Gets the message from the textarea
	var message = $("#messagearea").val();
	
	$.ajax({
		type : "POST",
		url : "rest/chat/customersend",
		data : {
			message : message
		},
		success : function(answer) {

			
			if (answer = "SUCESS!") {
				
				$(".messagewindow").append("<b style='color:red'>You:</b><span> "+ message + "</span><br>");
				$("#messagearea").val("");
				
			} else {
				
				alert("answer");
				
			}

		}
	});

}
