/**
 * 
 */

var id;

setTimeout(initID, 1500);

function initID(){

	id = $("#customerid").text();
	setInterval(checkFormessages, 2000);
	
	
} 

function checkFormessages(){
	
	console.log("Checking for new messages");
	$.ajax({
		type : "POST",
		url : "../rest/chat/emplmes",
		data : {
			id : id
		},
		success : function(answer) {
			
			if(answer != ""){
				
				$(".messagewindow").append("<b style='color:red'>Costumer:</b><span> "+ answer + "</span><br>");

				
			}
			
			
		}
	});
	
	
}



function senddata() {

	// Gets the message from the textarea
	var message = $("#messagearea").val();
	
	$.ajax({
		type : "POST",
		url : "../rest/chat/employeesend",
		data : {
			message : message
			custID : id
		},
		success : function(answer) {

			
			if (answer = "SUCESS!") {
				
				$(".messagewindow").append("<b style='color:red'>You:</b><span> "+ message + "</span>");
				
			} else {
				
				
			}

		}
	});

}
