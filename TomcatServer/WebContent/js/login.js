/*
 * 
 * Jeg vil meget gerne have følgende:
 *
 * En form  
 * Et felt i den form med name="username"
 * Et andet felt i samme form med name="password"
 * En button (<input type="button" ...) med onclick="login(this.form)"
 * En anden button med onclick="create(this.form)"
 * En label med id="loginResponse"
 *
 */


function login()
{

	var username = $('#username').val();
	var password = $('#password').val();

	$.ajax({
		type: "POST",
		url: "rest/login/login",
		data: { username: username, password: password},
		success: function(response) {
			$('#loginResponse').text(response);
			if(response == "You are now logged in"){
				update_basket();
			}
		}
	});

}

function create()
{
	var username = $('#username').val();
	var password = $('#password').val();

	$.ajax({
		type: "POST",
		url: "rest/login/createCustomer",
		data: { username: username, password: password},
		success: function(response) {
			$('#loginResponse').text(response);
		}
	});
}

$(document).ready(function() {
	$('#login').click(login);
	$('#create').click(create);
});
