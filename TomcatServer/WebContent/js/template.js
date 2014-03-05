var http;
if (navigator.appName == "Microsoft Internet Explorer")
	http = new ActiveXObject("Microsoft.XMLHTTP");
else
	http = new XMLHttpRequest();

function sendRequest(httpMethod, url, body, responseHandler) {
	http.open(httpMethod, url);
	http.onreadystatechange = function () {
		if (http.readyState == 4 && http.status == 200) {
			responseHandler(http.responseText);
		}
	};
	http.send(body);
} 
 
function addEventListener(myNode, eventType, myHandlerFunc) {
    if (myNode.addEventListener)
        myNode.addEventListener(eventType, myHandlerFunc, false);
    else
        myNode.attachEvent("on" + eventType,
            function (event) {
                myHandlerFunc.call(myNode, event);
            });
}
