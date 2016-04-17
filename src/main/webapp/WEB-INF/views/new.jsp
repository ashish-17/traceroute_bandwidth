<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Bandwidth analysis</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.min.js"></script>
<script src="https://code.highcharts.com/stock/highstock.js"></script>
<script src="https://code.highcharts.com/stock/modules/exporting.js"></script>
<script>
function performAnalysis() {
	var ping_id = $("#ping_id").val();
	var address = $("#address").val();
	var echoRequests = $("#echoRequests").val();
	var packetSize = $("#packetSize").val();
	var interval = $("#interval").val();
	
	$.ajax({
		url : "analysis/" + ping_id + "/" + address + "/" + echoRequests + "/" + packetSize + "/" + interval,
	}).done(
			function(data) {
				$("#result").html("Done!");
			});
}
</script>
</head>
<body>
Analysis Id: <input id="ping_id" type="text" />
Address: <input id="address" type="text" />
Number of Requests: <input id="echoRequests" type="text" />
Packet Size: <input id="packetSize" type="text" />
Interval (ms): <input id="interval" type="text" />
<input id="performAnalysis" type="button" value="Submit" onclick="performAnalysis()"></button>
<div id="result"></div>
</body>
</html>