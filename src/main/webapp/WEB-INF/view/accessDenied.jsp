<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.joda.org/joda/time/tags" prefix="joda"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="dmtags"%>

<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="utf-8">
<title><spring:message code="login.title" /> - dance.manage</title>

<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
<meta name="apple-mobile-web-app-capable" content="yes">

<link href="css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<link href="css/bootstrap-responsive.min.css" rel="stylesheet"
	type="text/css" />

<link href="css/font-awesome.css" rel="stylesheet">
<link
	href="http://fonts.googleapis.com/css?family=Open+Sans:400italic,600italic,400,600"
	rel="stylesheet">

<link href="css/style.css" rel="stylesheet" type="text/css">
<link href="css/pages/signin.css" rel="stylesheet" type="text/css">

</head>

<body>

	<dmtags:upperNavigation />

	<div class="account-container">

		<div class="content clearfix">
			<i class="shortcut-icon icon-warning-sign"></i>&nbsp;&nbsp;
			<spring:message code="user.accessDenied" />
		</div>
		<!-- /content -->
	</div>
	<!-- /account-container -->

	<script src="js/jquery-1.7.2.js"></script>
	<script src="js/bootstrap.js"></script>

	<script src="js/signin.js"></script>
</body>

</html>