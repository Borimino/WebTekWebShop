/**
 * 
 */

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
				
			} else {
				
				alert("answer");
				
			}

		}
	});

}