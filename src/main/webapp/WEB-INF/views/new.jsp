<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, maximum-scale=1">
<title>Bandwidth Analysis</title>
<link rel="icon" href="favicon.png" type="image/png">
<link href="css/bootstrap.css" rel="stylesheet" type="text/css">
<link href="css/style.css" rel="stylesheet" type="text/css">
<link href="css/linecons.css" rel="stylesheet" type="text/css">
<link href="css/font-awesome.css" rel="stylesheet" type="text/css">
<link href="css/responsive.css" rel="stylesheet" type="text/css">
<link href="css/animate.css" rel="stylesheet" type="text/css">

<link
	href='http://fonts.googleapis.com/css?family=Lato:400,900,700,700italic,400italic,300italic,300,100italic,100,900italic'
	rel='stylesheet' type='text/css'>
<link
	href='http://fonts.googleapis.com/css?family=Dosis:400,500,700,800,600,300,200'
	rel='stylesheet' type='text/css'>

<!--[if IE]><style type="text/css">.pie {behavior:url(PIE.htc);}</style><![endif]-->

<script type="text/javascript" src="js/jquery.1.8.3.min.js"></script>
<script type="text/javascript" src="js/bootstrap.js"></script>
<script type="text/javascript" src="js/jquery-scrolltofixed.js"></script>
<script type="text/javascript" src="js/jquery.easing.1.3.js"></script>
<script type="text/javascript" src="js/jquery.isotope.js"></script>
<script type="text/javascript" src="js/wow.js"></script>
<script type="text/javascript" src="js/classie.js"></script>
<script src="https://code.highcharts.com/stock/highstock.js"></script>
<script src="https://code.highcharts.com/stock/modules/exporting.js"></script>
<script type="text/javascript" src="js/script.js"></script>

<!--[if lt IE 9]>
    <script src="js/respond-1.1.0.min.js"></script>
    <script src="js/html5shiv.js"></script>
    <script src="js/html5element.js"></script>
<![endif]-->

<script type="text/javascript">
	$(document).ready(function(e) {
		$('.res-nav_click').click(function() {
			$('ul.toggle').slideToggle(600)
		});

		$(document).ready(function() {
			$(window).bind('scroll', function() {
				if ($(window).scrollTop() > 0) {
					$('#header_outer').addClass('fixed');
				} else {
					$('#header_outer').removeClass('fixed');
				}
			});

		});

		$(".loaderImage").hide();
	});
	function resizeText() {
		var preferredWidth = 767;
		var displayWidth = window.innerWidth;
		var percentage = displayWidth / preferredWidth;
		var fontsizetitle = 25;
		var newFontSizeTitle = Math.floor(fontsizetitle * percentage);
		$(".divclass").css("font-size", newFontSizeTitle)
	}
</script>
</head>
<body>

	<div class="loaderImage"></div>
	<!--Header_section-->
	<header id="header_outer">
		<div class="container">
			<div class="header_section">
				<div class="logo">
					<a href="<c:url value="/" />">B/W Analysis</a>
				</div>
				<nav class="nav" id="nav">
					<ul class="toggle">
						<li><a href="<c:url value="/" />">Ping</a></li>
						<li><a href="<c:url value="/tracert" />">Traceroute</a></li>
						<li><a href="<c:url value="/analysis" />">Analysis</a>
						<li><a href="#">Contact Us</a>
					</ul>
					<ul class="">
						<li><a href="<c:url value="/" />">Ping</a></li>
						<li><a href="<c:url value="/tracert" />">Traceroute</a></li>
						<li><a href="<c:url value="/analysis" />">Analysis</a>
						<li><a href="#">Contact Us</a>
					</ul>
				</nav>
				<a class="res-nav_click animated wobble wow"
					href="javascript:void(0)"><i class="fa-bars"></i></a>
			</div>
		</div>
	</header>
	<!--Header_section-->

	<!--Top_content-->
	<section id="top_content" class="top_cont_outer">
		<div class="top_cont_inner">
			<div class="container">
				<div class="top_content">
					<div class="row">
						<div id="keyword_search" class="wow fadeInLeft delay-05s">
							<form role="form" class="col-sm-7"> 
								<fieldset class="form-group">
									<label for="ping_id">Analysis ID</label> 
									<input id="ping_id" class="form-control" type="text" placeholder="Ping ID">
								</fieldset>
								<fieldset class="form-group">
									<label for="address">Address</label> 
									<input id="address" class="form-control" type="text" placeholder="Address">
								</fieldset>
								<fieldset class="form-group">
									<label for="echoRequests">Number of requests</label> 
									<input id="echoRequests" class="form-control" type="text" placeholder="Number of Requests">
								</fieldset>
								<fieldset class="form-group">
									<label for="packetSize">Packet Size</label> 
									<input id="packetSize" class="form-control" type="text" placeholder="Bytes">
								</fieldset>
								<fieldset class="form-group">
									<label for="interval">Interval</label> 
									<input id="interval" class="form-control" type="text" placeholder="micro seconds">
								</fieldset>
								
								<button type="button" id="performAnalysis" class="btn btn-default" onclick="performPingAnalysis()">Submit</button>
							</form>
						</div>
					</div>
					<div class = "row" id="results">
					</div>
				</div>
			</div>
		</div>
	</section>
	<!--Top_content-->

	<section class="twitter-feed">
		<!--twitter-feed-->
		<div class="container  animated fadeInDown delay-07s wow">
			<div class="twitter_bird">
				<span><i class="fa-twitter"></i></span>
			</div>
		</div>
	</section>
	<!--twitter-feed-end-->
	<footer class="footer_section" id="contact">
		<div class="container">
			<div class="footer_bottom">
				<span>Copyright &copy; 2015 | <a
					href="http://ashish-jindal.com/">by B/W </a></span>
			</div>
		</div>
	</footer>
</body>
</html>