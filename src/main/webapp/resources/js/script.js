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
	});
}

function performTracertAnalysis() {
	var tracert_id = $("#tracert_id").val();
	var address = $("#address").val();
	var echoRequests = $("#echoRequests").val();
	var packetSize = $("#packetSize").val();
	var interval = $("#interval").val();

	$('.loaderImage').show();
	$.ajax(
			{
				url : "analysis/tr/" + tracert_id + "/" + address + "/"
						+ echoRequests + "/" + packetSize + "/" + interval,
			}).done(function(data) {
		draw_tr("analysis/getTracertData/" + tracert_id);
	});
}

function draw(url) {
	$.get(url, function(data) {
		if (console && console.log) {
			console.log(data);
		}
		
		$('#results').empty();
		var line_shart_div = "line_shart_"+data.ping_id;
		var phase_plot_div = "phase_plot_"+data.ping_id;
		var html = '<div class="row analysis-chart" id="' + line_shart_div + '" style="min-width: 310px; height: 400px;"></div>';
		$('#results').append(html);
		html = '<div class="row analysis-chart" id="' + phase_plot_div + '" style="min-width: 310px; height: 400px;"></div>';
		$('#results').append(html);
		
		drawPlots(line_shart_div, phase_plot_div, data);
		$('.loaderImage').hide();
		$("#intercept-calc").show();
	});
}

function draw_tr(url) {
	$.get(url, function(data) {
		if (console && console.log) {
			console.log(data);
		}
		$('#results').empty();
		for (var ping_id in data) {
			if (data.hasOwnProperty(ping_id)) {
				var line_shart_div = "line_shart_"+ping_id;
				var phase_plot_div = "phase_plot_"+ping_id;
				var html = '<div class="row analysis-chart" id="' + line_shart_div + '" style="min-width: 310px; height: 400px;"></div>';
				$('#results').append(html);
				html = '<div class="row analysis-chart" id="' + phase_plot_div + '" style="min-width: 310px; height: 400px;"></div>';
				$('#results').append(html);
				
				drawPlots(line_shart_div, phase_plot_div, data[ping_id]);
			}
		}
		$('.loaderImage').hide();

		$("#intercept-calc").show();
	});
}

function drawPlots(line_shart_div, phase_plot_div, data) {
	if (data.length <= 0 || !data[0].hasOwnProperty('packetSize'))
		return;
	
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

	$('#'+phase_plot_div).highcharts({

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

	$('#'+line_shart_div).highcharts({
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
}

function init() {
	$.ajax({
		url : "analysis/getPings",
	}).done(
			function(data) {
				for (var i = 0; i < data.length; ++i) {
					$("#pings ul").append(
							'<li><a class="drawLink" href="analysis/getPingData/' + data[i] + '">'
									+ data[i] + '</a></li>');
				}

				bind_ping();
			});
	
	$.ajax({
		url : "analysis/getTracerts",
	}).done(
			function(data) {
				for (var i = 0; i < data.length; ++i) {
					$("#tracerts ul").append(
							'<li><a class="drawLinkTr" href="analysis/getTracertData/' + data[i] + '">'
									+ data[i] + '</a></li>');
				}

				bind_tr();
			});
};

function bind_ping() {
	$('.drawLink').click(function(event) {
		event.preventDefault();
		$('.loaderImage').show();
		
		draw($(this).attr('href'));
	});

	$('#calculateBw').click(function () {calculateBw()});
}

function bind_tr() {
	$('.drawLinkTr').click(function(event) {
		event.preventDefault();
		$('.loaderImage').show();
		draw_tr($(this).attr('href'));
	});
	
	$('#calculateBw').click(function () {calculateBw()});	
}

function calculateBw() {
	var x1 = $('#x1').val();
	var y1 = $('#y1').val();
	var x2 = $('#x2').val();
	var y2 = $('#y2').val();
	
	var slope = (y2-y1) / (x2-x1);
	var x = slope*x1 - y1;
	console.log("slope = " + slope);
	console.log("intercept = " + x);

	$('#bandwidth').empty();
	$('#bandwidth').append("Intersect x = " + x);
	
}