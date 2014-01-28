<%@ tag language="java" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="localeCode" value="${pageContext.response.locale}" />

<div class="navbar navbar-fixed-top">
	<div class="navbar-inner">
		<div class="container">
			<a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse"><span class="icon-bar"></span><span class="icon-bar"></span><span class="icon-bar"></span> </a>
			<a class="brand"
				href="<c:url value="${homeLink}" />"><spring:message code="nav.title" /></a>
			<c:if test="${user != null}">
				<div class="nav-collapse">
					<ul class="nav pull-right">
						<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="icon-flag"></i>&nbsp;<spring:message code="nav.language" />&nbsp;<b class="caret"></b></a>
							<ul class="dropdown-menu">
								<li <c:if test="${localeCode eq 'en'}">class="active"</c:if>><a href="?language=en">English</a></li>
								<li <c:if test="${localeCode eq 'de'}">class="active"</c:if>><a href="?language=de">Deutsch</a></li>
							</ul></li>
						<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="icon-user"></i>&nbsp;${user.firstname}&nbsp;<b class="caret"></b></a>
							<ul class="dropdown-menu">
								<li><a href="<c:url value="${editProfileLink}" />"><spring:message code="nav.editProfile" /></a></li>
								<li><a href="<c:url value="${editPasswordLink}" />"><spring:message code="nav.changePassword" /></a></li>
								<li><a href="<c:url value='/j_spring_security_logout' />"><spring:message code="nav.logout" /></a></li>
							</ul></li>
					</ul>
					<c:if test="${userType eq 'admin'}">
						<form action="<c:url value='/admin/search/request' />" class="navbar-search pull-right">
							<input type="text" id="searchQuery" value="" name="query" class="search-query" placeholder="<spring:message code="nav.search" />" />
						</form>
					</c:if>
				</div>
				<!--/.nav-collapse -->
			</c:if>
		</div>
		<!-- /container -->
	</div>
	<!-- /navbar-inner -->
</div>
<!-- /navbar -->

