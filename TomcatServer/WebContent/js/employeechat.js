/**
 * Employee chat Responsible for handling the employees side
 */

	//Tells the server that a employee is present
	$.ajax({
		type : "POST",
		url : "../rest/chat/setonline",
		data : {
			status : true
		},
		success : function() {

			console.log("Chat initialised");
			
		}
	});
	
	
	//Starts all the loops
	$(document).ready(function(){
		

		setTimeout(startCheckInLopp(), 3000);
		setInterval(windowMakerLoop, 1000);
		
		
	});
	
	function windowMakerLoop(){

		$.ajax({
			type : "GET",
			url : "../rest/chat/makewindow",
			data : {
			},
			success : function(answer) {

				console.log("ANSWER: " + answer);
				if(answer != ""){

					var empWindow = window.open("employeechat.html","_blank", "height=600, width=450");
					
					//empWindow.document.write("<p>I replaced the current window.</p>");
					
					empWindow.document.write("<div id='customerid' style='visibility:hidden'>" + answer + "</div>");
				
				}
				
			}
		});
	
		
		
	}
	
	function startCheckInLopp(){
		
		console.log("Checkin loop startet");
		setInterval(checkMein, 3000);

		
	}
	
	function checkMein(){
		console.log("Attempting to autocheckin");

		
		$.ajax({
			type : "POST",
			url : "../rest/chat/checkinemployee",
			data : {
			},
			success : function() {

				console.log("Autocheckin Done Session extended with 3 seconds");
				
			}
		});
		
	}
	