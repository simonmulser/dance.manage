<%@ tag language="java" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@attribute name="activesection" required="true"
	type="java.lang.String"%>

<div class="navbar navbar-fixed-top">
	<div class="navbar-inner">
		<div class="container">
			<a class="btn btn-navbar" data-toggle="collapse"
				data-target=".nav-collapse"><span class="icon-bar"></span><span
				class="icon-bar"></span><span class="icon-bar"></span> </a><a
				class="brand" href="dashboard"><spring:message code="nav.title" /></a>
			<div class="nav-collapse">
				<ul class="nav pull-right">
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown"><i class="icon-cog"></i> <spring:message
								code="nav.account" /> <b class="caret"></b></a>
						<ul class="dropdown-menu">
							<li><a href="javascript:;"><spring:message
										code="nav.settings" /></a></li>
							<li><a href="javascript:;"><spring:message
										code="nav.help" /></a></li>
						</ul></li>
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown"><i class="icon-user"></i> Management
							<b class="caret"></b></a>
						<ul class="dropdown-menu">
							<li><a href="javascript:;"><spring:message
										code="nav.profile" /></a></li>
							<li><a href="<c:url value='/j_spring_security_logout' />"><spring:message
										code="nav.logout" /></a></li>
						</ul></li>
				</ul>
				<form class="navbar-search pull-right">
					<input type="text" class="search-query"
						placeholder="<spring:message code="nav.search" />">
				</form>
			</div>
			<!--/.nav-collapse -->
		</div>
		<!-- /container -->
	</div>
	<!-- /navbar-inner -->
</div>
<!-- /navbar -->


<div class="subnavbar">
	<div class="subnavbar-inner">
		<div class="container">
			<ul class="mainnav">
				<li
					<c:if test="${activesection eq 'dashboard'}">class="active"</c:if>><a
					href="/dancemanage/admin"><i class="icon-dashboard"></i><span><spring:message
								code="nav.dashboard" /></span> </a></li>
				<li <c:if test="${activesection eq 'courses'}">class="active"</c:if>><a
					href="/dancemanage/admin/course"><i class="icon-calendar"></i><span><spring:message
								code="nav.courses" /></span> </a></li>
				<li
					<c:if test="${activesection eq 'invoices'}">class="active"</c:if>><a
					href="/dancemanage/admin/invoice"><i class="icon-envelope"></i><span><spring:message
								code="nav.invoices" /></span> </a></li>
				<li
					<c:if test="${activesection eq 'statistics'}">class="active"</c:if>><a
					href="/dancemanage/admin/lists"><i class="icon-bar-chart"></i><span><spring:message
								code="nav.statistics" /></span> </a></li>
				<li
					class="<c:if test="${activesection eq 'participants' or activesection eq 'teachers'}">active</c:if> dropdown"><a
					href="javascript:;" class="dropdown-toggle" data-toggle="dropdown">
						<i class="icon-user"></i><span><spring:message
								code="nav.persons" /></span> <b class="caret"></b>
				</a>
					<ul class="dropdown-menu">
						<li><a href="/dancemanage/admin/participant"><spring:message
									code="nav.participants" /></a></li>
						<li><a href="/dancemanage/admin/teacher"><spring:message code="nav.teachers" /></a></li>
					</ul></li>
				<li <c:if test="${activesection eq 'performances'}">class="active"</c:if>><a
					href="/dancemanage/admin/performance"><i class="icon-camera"></i><span><spring:message
								code="nav.performances" /></span> </a></li>
			</ul>
		</div>
		<!-- /container -->
	</div>
	<!-- /subnavbar-inner -->
</div>
<!-- /subnavbar -->