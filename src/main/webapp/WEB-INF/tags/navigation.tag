<%@ tag language="java" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@attribute name="activesection" required="true" type="java.lang.String"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="dmtags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<dmtags:upperNavigation />

<div class="subnavbar">
	<div class="subnavbar-inner">
		<div class="container">
			<ul class="mainnav">
				<c:if test="${userType eq 'admin'}">

					<li <c:if test="${activesection eq 'dashboard'}">class="active"</c:if>><a href="/dancemanage/admin"><i class="icon-dashboard"></i><span><spring:message code="nav.dashboard" /></span> </a></li>
					<li <c:if test="${activesection eq 'courses'}">class="active"</c:if>><a href="/dancemanage/admin/course"><i class="icon-calendar"></i><span><spring:message code="nav.courses" /></span>
					</a></li>
					<li <c:if test="${activesection eq 'invoices'}">class="active"</c:if>><a href="/dancemanage/admin/invoice"><i class="icon-envelope"></i><span><spring:message code="nav.invoices" /></span>
					</a></li>
					<li <c:if test="${activesection eq 'lists'}">class="active"</c:if>><a href="/dancemanage/admin/lists"><i class="icon-list"></i><span><spring:message code="nav.lists" /></span> </a></li>
					<li <c:if test="${activesection eq 'statistics'}">class="active"</c:if>><a href="/dancemanage/admin/statistics"><i class="icon-bar-chart"></i><span><spring:message
									code="nav.statistics" /></span> </a></li>
					<li class="<c:if test="${activesection eq 'participants' or activesection eq 'teachers'}">active</c:if> dropdown"><a href="javascript:;" class="dropdown-toggle" data-toggle="dropdown"> <i
							class="icon-user"></i><span><spring:message code="nav.persons" /></span> <b class="caret"></b>
					</a>
						<ul class="dropdown-menu">
							<li><a href="/dancemanage/admin/participant"><spring:message code="nav.participants" /></a></li>
							<li><a href="/dancemanage/admin/parent"><spring:message code="nav.parents" /></a></li>
							<li><a href="/dancemanage/admin/teacher"><spring:message code="nav.teachers" /></a></li>
						</ul></li>

					<li <c:if test="${activesection eq 'performances'}">class="active"</c:if>><a href="/dancemanage/admin/performance"><i class="icon-camera"></i><span><spring:message
									code="nav.performances" /></span> </a></li>
				</c:if>

				<c:if test="${userType eq 'participant' }">
					<li <c:if test="${activesection eq 'dashboard'}">class="active"</c:if>><a href="/dancemanage/participant/${participant.pid}"><i class="icon-dashboard"></i><span><spring:message
									code="nav.dashboard" /></span> </a></li>
					<li <c:if test="${activesection eq 'absence'}">class="active"</c:if>><a href="/dancemanage/participant/absence/${participant.pid}"><i class="icon-edit"></i><span><spring:message
									code="nav.absence" /></span> </a></li>
				</c:if>
				
				<c:if test="${userType eq 'parent'}">
					<li <c:if test="${activesection eq 'dashboard' && !requestScope['javax.servlet.forward.request_uri'].contains('participant')}">class="active"</c:if>><a href="/dancemanage/parent"><i class="icon-dashboard"></i><span><spring:message code="nav.dashboard" /></span> </a></li>
				</c:if>
				
				<c:if test="${userType eq 'teacher' }">
					<li <c:if test="${activesection eq 'dashboard'}">class="active"</c:if>><a href="/dancemanage/teacher/"><i class="icon-dashboard"></i><span><spring:message
									code="nav.dashboard" /></span> </a></li>
					<li <c:if test="${activesection eq 'absence'}">class="active"</c:if>><a href="/dancemanage/teacher/absence/"><i class="icon-edit"></i><span><spring:message
									code="nav.absence" /></span> </a></li>
				</c:if>
				
			</ul>
		</div>
		<!-- /container -->
	</div>
	<c:if test="${userType eq 'parent' && requestScope['javax.servlet.forward.request_uri'].contains('participant')}">
		<div class="subnavbar-inner">
			<div class="container">
				<ul class="mainnav">
                    <li <c:if test="${activesection eq 'dashboard'}">class="active"</c:if>><a href="/dancemanage/participant/${participant.pid}"><i class="icon-dashboard"></i><span><spring:message
                                    code="nav.dashboard" /></span> </a></li>
                    <li <c:if test="${activesection eq 'absence'}">class="active"</c:if>><a href="/dancemanage/participant/absence/${participant.pid}"><i class="icon-edit"></i><span><spring:message
                                    code="nav.absence" /></span> </a></li>
				</ul>
			</div>
		</div>
	</c:if>
</div>
<!-- /subnavbar -->