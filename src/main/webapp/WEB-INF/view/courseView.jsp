<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.joda.org/joda/time/tags" prefix="joda"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="dmtags"%>

<dmtags:base title="Kurse" activesection="courses">

	<dmtags:widget icon="icon-calendar" title="Kurs">
		<spring:message code="help.course" />
		<form:form method="post" action="course/add" commandName="course"
			class="form-horizontal">
			<form:input path="cid" type="hidden" />
			<form:input path="enabled" type="hidden" />
			<div class="control-group">
				<form:label path="name" class="control-label">
					<spring:message code="label.coursename" />*
				</form:label>
				<div class="span6">
					<form:input path="name" />
				</div>
				<form:errors path="name" cssClass="error" />
			</div>
			<div class="control-group">
				<form:label path="duration" class="control-label">
					<spring:message code="label.courseduration" />*
				</form:label>
				<div class="span6">
					<form:input path="duration" />
				</div>
				<form:errors path="duration" cssClass="error" />
			</div>
			<div class="control-group">
				<form:label path="price" class="control-label">
					<spring:message code="label.courseprice" />*
				</form:label>
				<div class="span6">
					<form:input path="price" />
				</div>
				<form:errors path="price" cssClass="error" />
			</div>
			<div class="control-group">
				<form:label path="weekday" class="control-label">
					<spring:message code="label.courseweekday" />*
				</form:label>
				<div class="span6">
					<form:select path="weekday">
						<form:option value="NONE" label="--- Select ---" />
						<form:options items="${WeekDay}" />
					</form:select>
				</div>
				<form:errors path="weekday" cssClass="error" />
			</div>
			<div class="control-group">
				<form:label path="time" class="control-label">
					<spring:message code="label.coursetime" />*
				</form:label>
				<div class="span6">
					<form:input path="time" />
				</div>
				<form:errors path="time" cssClass="error" />
			</div>
			<div class="control-group">
				<form:label path="estimatedSpectators" class="control-label">
					<spring:message code="label.estimatedSpectators" />*
				</form:label>
				<div class="span6">
					<form:select path="estimatedSpectators">
						<form:option value="NONE" label="--- Select ---" />
						<form:options items="${SpectatorAmount}" />
					</form:select>
				</div>
				<form:errors path="estimatedSpectators" cssClass="error" />
			</div>
			<div class="control-group">
				<form:label path="ageGroup" class="control-label">
					<spring:message code="label.ageGroup" />*
				</form:label>
				<div class="span6">
					<form:select path="ageGroup">
						<form:option value="NONE" label="--- Select ---" />
						<form:options items="${AgeGroup}" />
					</form:select>
				</div>
				<form:errors path="ageGroup" cssClass="error" />
			</div>
			<div class="control-group">
				<form:label path="amountPerformances" class="control-label">
					<spring:message code="label.amountPerformances" />*
				</form:label>
				<div class="span6">
					<form:input path="amountPerformances" />
				</div>
				<form:errors path="amountPerformances" cssClass="error" />
			</div>
			<div class="control-group">
				<form:label path="level" class="control-label">
					<spring:message code="label.courselevel" />*
				</form:label>
				<div class="span6">
					<form:select path="level">
						<form:option value="NONE" label="--- Select ---" />
						<form:options items="${CourseLevel}" />
					</form:select>
				</div>
				<form:errors path="level" cssClass="error" />
			</div>
			<div class="form-actions">
				<input type="submit"
					value="<spring:message code="label.save"/>" class="btn btn-primary" />
			</div>
		</form:form>
	</dmtags:widget>
	<dmtags:widget title="Übersicht" style="table" icon="icon-list">
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