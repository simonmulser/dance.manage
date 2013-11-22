<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.joda.org/joda/time/tags" prefix="joda"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="dmtags"%>

<dmtags:base title="Kursteilnehmer" activesection="participants">

	<dmtags:widget title="Kursteilnehmer" icon="icon-user">
		<form:form method="post" action="participant/add"
			commandName="participant" class="form-horizontal">

			<form:input path="pid" type="hidden" />
			<form:input path="enabled" type="hidden" />
			<form:input path="address.aid" type="hidden" />

			<div class="control-group">
				<form:label path="firstname" class="control-label">
					<spring:message code="label.firstname" />
				</form:label>
				<div class="span6">
					<form:input path="firstname" />
				</div>
			</div>

			<div class="control-group">
				<form:label path="lastname" class="control-label">
					<spring:message code="label.lastname" />
				</form:label>
				<div class="span6">
					<form:input path="lastname" />
				</div>
			</div>

			<div class="control-group">
				<form:label path="email" class="control-label">
					<spring:message code="label.email" />
				</form:label>
				<div class="span6">
					<form:input path="email" />
				</div>
			</div>

			<div class="control-group">
				<form:label path="telephone" class="control-label">
					<spring:message code="label.telephone" />
				</form:label>
				<div class="span6">
					<form:input path="telephone" />
				</div>
			</div>

			<div class="control-group">
				<form:label path="birthday" class="control-label">
					<spring:message code="label.birthday" />
				</form:label>
				<div class="span6">
					<form:input path="birthday" />
				</div>
			</div>

			<div class="control-group">
				<form:label path="contactPerson" class="control-label">
					<spring:message code="label.contactPerson" />
				</form:label>
				<div class="span6">
					<form:input path="contactPerson" />
				</div>
			</div>

			<div class="control-group">
				<form:label path="emergencyNumber" class="control-label">
					<spring:message code="label.emergencyNumber" />
				</form:label>
				<div class="span6">
					<form:input path="emergencyNumber" />
				</div>
			</div>

			<div class="control-group">
				<form:label path="address.street" class="control-label">
					<spring:message code="label.street" />
				</form:label>
				<div class="span6">
					<form:input path="address.street" />
				</div>
			</div>

			<div class="control-group">
				<form:label path="address.number" class="control-label">
					<spring:message code="label.number" />
				</form:label>
				<div class="span6">
					<form:input path="address.number" />
				</div>
			</div>

			<div class="control-group">
				<form:label path="address.stair" class="control-label">
					<spring:message code="label.stair" />
				</form:label>
				<div class="span6">
					<form:input path="address.stair" />
				</div>
			</div>

			<div class="control-group">
				<form:label path="address.door" class="control-label">
					<spring:message code="label.door" />
				</form:label>
				<div class="span6">
					<form:input path="address.door" />
				</div>
			</div>

			<div class="control-group">
				<form:label path="address.zip" class="control-label">
					<spring:message code="label.zip" />
				</form:label>
				<div class="span6">
					<form:input path="address.zip" />
				</div>
			</div>

			<div class="control-group">
				<form:label path="address.city" class="control-label">
					<spring:message code="label.city" />
				</form:label>
				<div class="span6">
					<form:input path="address.city" />
				</div>
			</div>

			<div class="form-actions">
				<input type="submit" value="<spring:message code="label.save"/>"
					class="btn btn-primary" />
				<button class="btn">
					<spring:message code="label.cancel" />
				</button>
			</div>

		</form:form>
	</dmtags:widget>

	<c:if test="${!empty participantList}">
		<dmtags:widget title="Übersicht" style="table" icon="icon-list">
			<table class="table table-striped table-bordered">
				<thead>
					<tr>
						<th><spring:message code="label.name" /></th>
						<th><spring:message code="label.email" /></th>
						<th><spring:message code="label.telephone" /></th>
						<th><spring:message code="label.birthday" /></th>
						<th><spring:message code="label.contactPerson" /></th>
						<th><spring:message code="label.emergencyNumber" /></th>
						<th><spring:message code="label.street" /></th>
						<th><spring:message code="label.zip" /></th>
						<th><spring:message code="label.city" /></th>
						<th>&nbsp;</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${participantList}" var="emp">
						<tr>
							<td>${emp.firstname}&nbsp;${emp.lastname}</td>
							<td>${emp.email}</td>
							<td>${emp.telephone}</td>
							<td><joda:format value="${emp.birthday}"
									pattern="yyyy-MM-dd" /></td>
							<td>${emp.contactPerson}</td>
							<td>${emp.emergencyNumber}</td>
							<c:choose>
								<c:when test="${!empty emp.address}">
									<td>${emp.address.street}
										${emp.address.number}/${emp.address.stair}/${emp.address.door}</td>
									<td>${emp.address.zip}</td>
									<td>${emp.address.city}</td>
								</c:when>
								<c:otherwise>
									<td></td>
									<td></td>
									<td></td>
								</c:otherwise>
							</c:choose>
							<td><a href="participant/edit/${emp.pid}"><spring:message
										code="label.edit" /></a> &nbsp; <a
								href="participant/delete/${emp.pid}"><spring:message
										code="label.delete" /></a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</dmtags:widget>
	</c:if>

</dmtags:base>