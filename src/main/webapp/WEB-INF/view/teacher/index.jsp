<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.joda.org/joda/time/tags" prefix="joda"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="dmtags"%>

<spring:message var="i18nTitle" code="nav.home" />
<dmtags:base title="${i18nTitle}" activesection="dashboard">
	<dmtags:span width="6">
		<spring:message var="i18nMyAccount" code="widget.myaccount" />
		<spring:message var="i18nEdit" code="label.edit" />
		<spring:message var="i18nMyCourses" code="widget.mycourses" />
		<spring:message var="i18nStyles" code="widget.styles" />
		<spring:message var="i18nAgenda" code="widget.agenda" />
		<c:choose>
			<c:when test="${user.courses.size() gt 0}">
				<dmtags:widget title="${i18nMyCourses}" style="table" icon="icon-list">
					<table class="table table-striped table-bordered">
						<thead>
							<tr>
								<th><spring:message code="label.year" /></th>
								<th><spring:message code="label.name" /></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${user.courses}" var="course" varStatus="loop">
								<tr>
									<td>${course.year}</td>
									<td>${course.name}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</dmtags:widget>
			</c:when>
			<c:otherwise>
				<dmtags:widget title="${i18nMyCourses}" style="noTable" icon="icon-list">
					<spring:message code="participant.noCourses" />
				</dmtags:widget>
			</c:otherwise>
		</c:choose>
		
		<c:choose>
			<c:when test="${user.styles.size() gt 0}">
				<dmtags:widget title="${i18nStyles}" style="table" icon="icon-list">
					<table class="table table-striped table-bordered">
						<thead />
						<tbody>
							<c:forEach items="${user.styles}" var="style" varStatus="loop">
								<tr>
									<td>${style.name}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</dmtags:widget>
			</c:when>
			<c:otherwise>

				<dmtags:widget title="${i18nStyles}" style="noTable" icon="icon-list">
					<spring:message code="teacher.noStyles" />
				</dmtags:widget>
			</c:otherwise>
		</c:choose>
	</dmtags:span>
	
	<dmtags:span width="6">
		<div class="table">
			<div class="widget-header">
				<i class="icon-user"></i>
				<h3>${i18nMyAccount}</h3>
				<a href="<c:url value='/teacher/edit' />"><button type="submit" class="btn btn-primary">${i18nEdit}</button></a>
			</div>
			<!-- /widget-header -->
			<div class="widget-content">
				<table class="table table-striped table-bordered">
					<thead />
					<dmtags:personInformation />

					<dmtags:addressInformation />
				</table>
			</div>
		</div>
	</dmtags:span>
	
</dmtags:base>