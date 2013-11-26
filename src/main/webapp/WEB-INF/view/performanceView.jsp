<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.joda.org/joda/time/tags" prefix="joda"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="dmtags"%>
<%@taglib prefix="joda" uri="http://www.joda.org/joda/time/tags"%>

<dmtags:base title="Aufführungen" activesection="performances">

	<dmtags:widget title="Plan erstellen" icon="icon-camera">
		<form:form method="post" action="performance/build"
			commandName="performance" class="form-horizontal">

			<div class="form-actions">
				<input type="submit" value="<spring:message code="label.add"/>"
					class="btn btn-primary" />
			</div>

		</form:form>
	</dmtags:widget>

	<dmtags:widget title="Aufführungsplan" style="table" icon="icon-list">
		<c:if test="${!empty courseListPerformance1}">
			<table class="table table-striped table-bordered">
				<thead>
					<tr>
						<th><spring:message code="label.coursename" /></th>
						<th><spring:message code="label.ageGroup" /></th>
						<th><spring:message code="label.courselevel" /></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${courseList}" var="course">
						<tr>
							<td>${course.name}</td>
							<td>${course.ageGroup}</td>
							<td>${course.level}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:if>
		
	</dmtags:widget>

</dmtags:base>