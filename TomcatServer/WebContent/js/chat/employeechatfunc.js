/**
 * 
 */

var id;

setTimeout(initID(), 1500);

function initID(){

	id = $("#customerid").text();
	
	alert(id);
	
}



function senddata() {

	// Gets the message from the textarea
	var message = $("#messagearea").val();
	
	$.ajax({
		type : "POST",
		url : "rest/chat/employeesend",
		data : {
			message : message
		},
		success : function(answer) {

			
			if (answer = "SUCESS!") {
				
				$(".messagewindow").append("<b style='color:red'>You:</b><span> "+ message + "</span>");
				
			} else {
				
				
			}

		}
	});

}
