<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="dmtags"%>


<dmtags:base title="Kursteilnehmer">

	<c:if test="${!empty participantList}">
		<table class="table table-striped table-bordered">
			<thead>
				<tr>
					<th>Name</th>
					<th>Email</th>
					<th>Telephone</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${participantList}" var="emp">
					<tr>
						<td>${emp.firstname} ${emp.lastname}</td>
						<td>${emp.email}</td>
						<td>${emp.telephone}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</c:if>

	<form:form method="post" action="participant/add"
		commandName="participant">

		<table>
			<tr>
				<td><form:label path="firstname">
						<spring:message code="label.firstname" />
					</form:label></td>
				<td><form:input path="firstname" /></td>
			</tr>
			<tr>
				<td><form:label path="lastname">
						<spring:message code="label.lastname" />
					</form:label></td>
				<td><form:input path="lastname" /></td>
			</tr>
			<tr>
				<td><form:label path="email">
						<spring:message code="label.email" />
					</form:label></td>
				<td><form:input path="email" /></td>
			</tr>
			<tr>
				<td><form:label path="telephone">
						<spring:message code="label.telephone" />
					</form:label></td>
				<td><form:input path="telephone" /></td>
			</tr>
			<tr>
				<td colspan="2"><input type="submit"
					value="<spring:message code="label.add"/>" /></td>
			</tr>
		</table>
	</form:form>

</dmtags:base>