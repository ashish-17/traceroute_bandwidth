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
<Script>
function drawResult () {
	$.ajax({
		  url: "getPingData/0",
		})
		  .done(function( data ) {
		    if ( console && console.log ) {
		      console.log( data);
		    }
		    
		    var xData = [];
		    var yData = [];
		    var xyData = [];
		    for (var i = 0; i < data.length; ++i) {
		    	xData.push(data[i].icmpSeq);
		    	yData.push(data[i].rtt);
		    	if (i < data.length-1) {
			    	var vals = [];
			    	vals.push(data[i].rtt);
			    	vals.push(data[i+1].rtt);
			    	xyData.push(vals);
		    	}
		    }
		    
		    $('#container1').highcharts({


	            rangeSelector : {
	                selected : 2
	            },

	            title : {
	                text : 'Phase Plot'
	            },
		        yAxis: {
		            title: {
		                text: 'RTT (n+1)'
		            },
		        },
		        
	            series : [{
	                name : 'RTT',
	                data : xyData,
	                lineWidth : 0,
	                marker : {
	                    enabled : true,
	                    radius : 2
	                },
	                tooltip: {
	                    valueDecimals: 2
	                }
	            }]
	        });
		    
		    $('#container').highcharts({
		        title: {
		            text: 'Packet Sequence number',
		            x: -20 //center
		        },
		        subtitle: {
		            text: 'Round trip time',
		            x: -20
		        },
		        xAxis: {
		            categories: xData
		        },
		        yAxis: {
		            title: {
		                text: 'Round trip time'
		            },
		            plotLines: [{
		                value: 0,
		                width: 1,
		                color: '#808080'
		            }]
		        },
		        tooltip: {
		            valueSuffix: 'ms'
		        },
		        legend: {
		            layout: 'vertical',
		            align: 'right',
		            verticalAlign: 'middle',
		            borderWidth: 0
		        },
		        series: [{
		            name: 'google.com',
		            data: yData
		        }]
		    });
		    
		  });
};
</Script>
</head>
<body>
<div id="container" style="min-width: 310px; height: 400px; margin: 0 auto"></div>
<div id="container1" style="min-width: 310px; height: 400px; margin: 0 auto"></div>
<input type="button" onclick="drawResult()">
</body>
</html>