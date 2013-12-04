<%@tag description="dance.manage base template" language="java"
	pageEncoding="UTF-8"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="dmtags"%>
<%@attribute name="title" required="true" type="java.lang.String"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@attribute name="activesection" required="true"
	type="java.lang.String"%>

<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title><spring:message code="${title}" /></title>
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
<meta name="apple-mobile-web-app-capable" content="yes">
<link href="<c:url value="/css/bootstrap.min.css"/>" rel="stylesheet">
<link href="<c:url value="/css/bootstrap-responsive.min.css"/>"
	rel="stylesheet">
<link
	href="http://fonts.googleapis.com/css?family=Open+Sans:400italic,600italic,400,600"
	rel="stylesheet">
<link href="<c:url value="/css/font-awesome.css" />" rel="stylesheet">
<link href="<c:url value="/css/style.css" />" rel="stylesheet">
<link href="<c:url value="/css/ui/jquery-ui.css" />" rel="stylesheet">

<!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
      <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->


</head>
<body>
	<dmtags:navigation activesection="${activesection}"/>

	<div class="main">
		<div class="main-inner">
			<div class="container">
				<div class="row">
					<div class="span12">
						<jsp:doBody />
					</div>
					<!-- /span12 -->
				</div>
				<!-- /row -->
			</div>
			<!-- /container -->
		</div>
		<!-- /main-inner -->
	</div>
	<!-- /main -->

	<div class="footer">
		<div class="footer-inner">
			<div class="container">
				<div class="row">
					<div class="span12">
						&copy; 2013 Dance & Fun</a>.
					</div>
					<!-- /span12 -->
				</div>
				<!-- /row -->
			</div>
			<!-- /container -->
		</div>
		<!-- /footer-inner -->
	</div>
	<!-- /footer -->



	<!-- Le javascript
================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script src="<c:url value="/js/jquery-1.7.2.js" />"></script>
	<script src="<c:url value="/js/jquery-ui.js" />"></script>
	<script src="<c:url value="/js/excanvas.min.js" />"></script>
	<script src="<c:url value="/js/chart.min.js" />" type="text/javascript"></script>
	<script src="<c:url value="/js/bootstrap.js" />"></script>
	<script src="<c:url value="/js/base.js" />"></script>

	<script>
		var doughnutData = [ {
			value : 30,
			color : "#F7464A"
		}, {
			value : 50,
			color : "#46BFBD"
		}, {
			value : 100,
			color : "#FDB45C"
		}, {
			value : 40,
			color : "#949FB1"
		}, {
			value : 120,
			color : "#4D5360"
		}

		];

		var myDoughnut = new Chart(document.getElementById("donut-chart")
				.getContext("2d")).Doughnut(doughnutData);

		var lineChartData = {
			labels : [ "January", "February", "March", "April", "May", "June",
					"July" ],
			datasets : [ {
				fillColor : "rgba(220,220,220,0.5)",
				strokeColor : "rgba(220,220,220,1)",
				pointColor : "rgba(220,220,220,1)",
				pointStrokeColor : "#fff",
				data : [ 65, 59, 90, 81, 56, 55, 40 ]
			}, {
				fillColor : "rgba(151,187,205,0.5)",
				strokeColor : "rgba(151,187,205,1)",
				pointColor : "rgba(151,187,205,1)",
				pointStrokeColor : "#fff",
				data : [ 28, 48, 40, 19, 96, 27, 100 ]
			} ]

		}

		var myLine = new Chart(document.getElementById("area-chart")
				.getContext("2d")).Line(lineChartData);

		var barChartData = {
			labels : [ "January", "February", "March", "April", "May", "June",
					"July" ],
			datasets : [ {
				fillColor : "rgba(220,220,220,0.5)",
				strokeColor : "rgba(220,220,220,1)",
				data : [ 65, 59, 90, 81, 56, 55, 40 ]
			}, {
				fillColor : "rgba(151,187,205,0.5)",
				strokeColor : "rgba(151,187,205,1)",
				data : [ 28, 48, 40, 19, 96, 27, 100 ]
			} ]

		}

		var myLine = new Chart(document.getElementById("bar-chart").getContext(
				"2d")).Bar(barChartData);

		var pieData = [ {
			value : 30,
			color : "#F38630"
		}, {
			value : 50,
			color : "#E0E4CC"
		}, {
			value : 100,
			color : "#69D2E7"
		}

		];

		var myPie = new Chart(document.getElementById("pie-chart").getContext(
				"2d")).Pie(pieData);

		var chartData = [ {
			value : Math.random(),
			color : "#D97041"
		}, {
			value : Math.random(),
			color : "#C7604C"
		}, {
			value : Math.random(),
			color : "#21323D"
		}, {
			value : Math.random(),
			color : "#9D9B7F"
		}, {
			value : Math.random(),
			color : "#7D4F6D"
		}, {
			value : Math.random(),
			color : "#584A5E"
		} ];
		var myPolarArea = new Chart(document.getElementById("line-chart")
				.getContext("2d")).PolarArea(chartData);
	</script>
</body>
</html>
