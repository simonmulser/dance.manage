<%@tag description="dance.manage base template" language="java"
	pageEncoding="UTF-8"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="dmtags"%>
<%@attribute name="title" required="true" type="java.lang.String"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>${title}</title>
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
	<jsp:doBody />
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
	<script type="text/javascript"
		src="<c:url value="/js/full-calendar/fullcalendar.min.js" />"></script>

</body>
</html>
