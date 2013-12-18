<%@ tag language="java" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


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

	<div class="navbar navbar-fixed-top">

		<div class="navbar-inner">

			<div class="container">

				<a class="btn btn-navbar" data-toggle="collapse"
					data-target=".nav-collapse"> <span class="icon-bar"></span> <span
					class="icon-bar"></span> <span class="icon-bar"></span>
				</a> <a class="brand" href="#"> dance.manage </a>

			</div>
			<!-- /container -->

		</div>
		<!-- /navbar-inner -->

	</div>
	<!-- /navbar -->



	<div class="account-container">

		<div class="content clearfix">
			<c:if test="${'fail' eq param.auth}">
				${SPRING_SECURITY_LAST_EXCEPTION.message}
            </c:if>
			<form action="<c:url value='j_spring_security_check' />"
				method="post">

				<h1>
					<spring:message code="login.title" />
				</h1>

				<div class="login-fields">

					<p>
						<spring:message code="login.info" />
					</p>

					<div class="field">
						<label for="username"><spring:message
								code="login.username" /></label> <input type="text" id="username"
							name="j_username" value=""
							placeholder="<spring:message code="login.username" />"
							class="login username-field" />
					</div>
					<!-- /field -->

					<div class="field">
						<label for="password"><spring:message
								code="login.password" /></label> <input type="password" id="password"
							name="j_password" value=""
							placeholder="<spring:message code="login.password" />"
							class="login password-field" />
					</div>
					<!-- /password -->

				</div>
				<!-- /login-fields -->

				<div class="login-actions">

					<span class="login-checkbox"> <input id="Field" name="Field"
						type="checkbox" class="field login-checkbox" value="First Choice"
						tabindex="4" /> <label class="choice" for="Field"><spring:message
								code="login.keepsignedin" /></label><br /> <a href="#"><spring:message code="login.reset" /></a>
					</span>
				
					<button class="button btn btn-success btn-large">
						<spring:message code="login.signin" />
					</button>
					

				</div>
				<!-- .actions -->



			</form>

		</div>
		<!-- /content -->
	</div>
	<!-- /account-container -->



	


	<script src="js/jquery-1.7.2.js"></script>
	<script src="js/bootstrap.js"></script>

	<script src="js/signin.js"></script>
</body>

</html>
