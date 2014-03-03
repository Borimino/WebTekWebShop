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


function login(form)
{
	var username = form.username.value;
	var password = form.password.value;

	sendRequest("POST", "/login/login", "username=" + username + "&password=" + password, function(response) {

		$('#loginResponse').text(response);

	});
}

function create(form)
{
	var username = form.username.value;
	var password = form.password.value;

	sendRequest("POST", "/login/createCustomer", "username=" + username + "&password=" + password, function(response) {

		$('#loginResponse').text(response);

	});
}
