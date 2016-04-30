function performPingAnalysis() {
	var ping_id = $("#ping_id").val();
	var address = $("#address").val();
	var echoRequests = $("#echoRequests").val();
	var packetSize = $("#packetSize").val();
	var interval = $("#interval").val();

	$('.loaderImage').show();
	$.ajax(
			{
				url : "analysis/" + ping_id + "/" + address + "/"
						+ echoRequests + "/" + packetSize + "/" + interval,
			}).done(function(data) {
		draw("analysis/getPingData/" + ping_id);
		$('.loaderImage').hide();
	});
}

function draw(url) {
	$.get(url, function(data) {
		if (console && console.log) {
			console.log(data);
		}

		var xData = [];
		var yData = [];
		var xyData = [];
		for (var i = 0; i < data.length; ++i) {
			xData.push(data[i].icmpSeq);
			yData.push(data[i].rtt);
			if (i < data.length - 1) {
				var vals = [];
				vals.push(data[i].rtt);
				vals.push(data[i + 1].rtt);
				xyData.push(vals);
			}
		}

		$('#phase-plot').highcharts({

			rangeSelector : {
				selected : 2
			},

			title : {
				text : 'Phase Plot'
			},
			yAxis : {
				title : {
					text : 'RTT (n+1)'
				},
			},

			series : [ {
				name : 'RTT',
				data : xyData,
				lineWidth : 0,
				marker : {
					enabled : true,
					radius : 2
				},
				tooltip : {
					valueDecimals : 2
				}
			} ]
		});

		$('#line-chart').highcharts({
			title : {
				text : 'ICMP Seq number vs RTT',
				x : -20
			//center
			},
			subtitle : {
				text : 'Packet size = ' + data[0].packetSize + ' Interval = ' + data[0].inteval,
				x : -20
			},
			xAxis : {
				categories : xData
			},
			yAxis : {
				title : {
					text : 'Round trip time'
				},
				plotLines : [ {
					value : 0,
					width : 1,
					color : '#808080'
				} ]
			},
			tooltip : {
				valueSuffix : 'ms'
			},
			legend : {
				layout : 'vertical',
				align : 'right',
				verticalAlign : 'middle',
				borderWidth : 0
			},
			series : [ {
				name : data[0].address,
				data : yData
			} ]
		});
	});
}