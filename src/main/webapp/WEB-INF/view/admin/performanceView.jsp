<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.joda.org/joda/time/tags" prefix="joda"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="dmtags"%>
<%@taglib prefix="joda" uri="http://www.joda.org/joda/time/tags"%>

<style>
 table { table-layout: fixed; }
 table th, table td { overflow: hidden; }
</style>
<spring:message var="i18nTitle" code="nav.performances" />
<dmtags:base title="${i18nTitle}" activesection="performances">
	
<spring:message var="i18nCreatePlan" code="widget.createPlan" />
	<dmtags:widget title="${i18nCreatePlan}" icon="icon-camera">
		<form:form method="post" action="performance/build"
			commandName="performance" class="form-horizontal">
			<div>
				<p>
					Drücken Sie auf 'Erstellen', um einen Aufführungsplan generieren zu lassen.
				</p>
			</div>

			<div class="form-actions">
				<input type="submit" value="<spring:message code="label.create"/>"
					class="btn btn-primary" />
			</div>

		</form:form>
	</dmtags:widget>
	
	<c:if test="${!empty performanceList1}">
	<dmtags:widget title="widget.plan" style="table" icon="icon-list">
			<table class="table table-striped table-bordered">
				<thead>
					<tr>
						<th style="width: 50%"><spring:message code="label.coursename" /></th>
						<th style="width: 25%"><spring:message code="label.ageGroup" /></th>
						<th style="width: 25%"><spring:message code="label.courselevel" /></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${performanceList1}" var="course">
						<tr>
							<td>${course.name}</td>
							<td>${course.ageGroup}</td>
							<td>${course.level}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		
			<table class="table table-striped table-bordered">
				<thead>
					<tr>
						<th style="width: 50%"><spring:message code="label.coursename" /></th>
						<th style="width: 25%"><spring:message code="label.ageGroup" /></th>
						<th style="width: 25%"><spring:message code="label.courselevel" /></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${performanceList2}" var="course">
						<tr>
							<td>${course.name}</td>
							<td>${course.ageGroup}</td>
							<td>${course.level}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<table class="table table-striped table-bordered">
				<thead>
					<tr>
						<th style="width: 50%"><spring:message code="label.coursename" /></th>
						<th style="width: 25%"><spring:message code="label.ageGroup" /></th>
						<th style="width: 25%"><spring:message code="label.courselevel" /></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${performanceList3}" var="course">
						<tr>
							<td>${course.name}</td>
							<td>${course.ageGroup}</td>
							<td>${course.level}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		
	</dmtags:widget>
	</c:if>

</dmtags:base>