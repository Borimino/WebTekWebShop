/**
 * 
 */

var id;

setTimeout(initID, 500);

function initID(){

	id = $("#customerid").text();
	setInterval(checkFormessages, 2000);
	
	
} 

function checkFormessages(){
	
	id = $("#customerid").text();

	console.log("Checking for new messages");
	$.ajax({
		type : "POST",
		url : "../rest/chat/employeemessage",
		data : {
			id : id
		},
		success : function(answer) {
			
			alert(answer);
			if(answer != ""){
				
				$(".messagewindow").append("<b style='color:red'>Costumer:</b><span> "+ answer + "</span><br>");

				
			}
			
			
		}
	});
	
	
}



function senddata() {

	// Gets the message from the textarea
	var message = $("#messagearea").val();
	
	id = $("#customerid").text();

	$.ajax({
		type : "POST",
		url : "../rest/chat/employeesend",
		data : {
			message : message,
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
