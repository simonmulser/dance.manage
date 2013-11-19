<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.joda.org/joda/time/tags" prefix="joda"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="dmtags"%>
<%@taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>

<dmtags:base title="Kurse">

	<form:form method="post" action="course/add"
		commandName="course">

		<table>
			<tr>
				<td><form:input path="cid" type="hidden" /> <form:input
						path="active" type="hidden" /> </td>
			</tr>
			<tr>
				<td><form:label path="name">
						<spring:message code="label.coursename" />
					</form:label></td>
				<td><form:input path="name" /></td>
			</tr>
			<tr>
				<td><form:label path="duration">
						<spring:message code="label.courseduration" />
					</form:label></td>
				<td><form:input path="duration" /></td>
			</tr>
			<tr>
				<td><form:label path="price">
						<spring:message code="label.courseprice" />
					</form:label></td>
				<td><form:input path="price" /></td>
			</tr>
			<tr>
				<td><form:label path="weekday">
						<spring:message code="label.courseweekday" />
					</form:label></td>
				<td><form:input path="weekday" /></td>
			</tr>
			<tr>
				<td><form:label path="time">
						<spring:message code="label.coursetime" />
					</form:label></td>
				<td><form:input path="time" /></td>
			</tr>
			<tr>
				<td><form:label path="estimatedSpectators">
						<spring:message code="label.estimatedSpectators" />
					</form:label></td>
				<td><form:input path="estimatedSpectators" /></td>
			</tr>
			<tr>
				<td><form:label path="ageGroup">
						<spring:message code="label.ageGroup" />
					</form:label></td>
				<td><form:input path="ageGroup" /></td>
			</tr>
			<tr>
				<td><form:label path="amountPerformances">
						<spring:message code="label.amountPerformances" />
					</form:label></td>
				<td><form:input path="amountPerformances" /></td>
			</tr>
			<tr>
				<td><form:label path="level">
						<spring:message code="label.courselevel" />
					</form:label></td>
				<td><form:input path="level" /></td>
			</tr>
			<tr>
				<td colspan="2"><input type="submit"
					value="<spring:message code="label.save"/>" /></td>
			</tr>
		</table>
	</form:form>
	
	<dmtags:widget title="Übersicht" style="table">
		<c:if test="${!empty courseList}">
			<table class="table table-striped table-bordered">
				<thead>
					<tr>
						<th><spring:message code="label.coursename" /></th>
						<th><spring:message code="label.courseduration" /></th>
						<th><spring:message code="label.courseprice" /></th>
						<th><spring:message code="label.courseweekday" /></th>
						<th><spring:message code="label.coursetime" /></th>
						<th><spring:message code="label.estimatedSpectators" /></th>
						<th><spring:message code="label.ageGroup" /></th>
						<th><spring:message code="label.amountPerformances" /></th>
						<th><spring:message code="label.courselevel" /></th>
						<th>&nbsp;</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${courseList}" var="course">
						<tr>
							<td>${course.name}</td>
							<td>${course.duration}</td>
							<td>${course.price}</td>
							<td>${course.weekday}</td>
							<td>${course.time}</td>
							<td>${course.estimatedSpectators}</td>
							<td>${course.ageGroup}</td>
							<td>${course.amountPerformances}</td>
							<td>${course.level}</td>
							<td><a href="course/edit/${course.cid}"><spring:message
										code="label.edit" /></a> &nbsp; <a
								href="course/delete/${course.cid}"><spring:message
										code="label.delete" /></a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:if>
	</dmtags:widget>
	
</dmtags:base>