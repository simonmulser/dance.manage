<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="dmtags"%>


<dmtags:base title="Lehrer">

	
	<form:form method="post" action="teacher/add"
		commandName="teacher">

		<table>
			<tr>
				<td><form:input path="pid" type="hidden" /> <form:input
						path="active" type="hidden" /> <form:input path="address.aid"
						type="hidden" /></td>
			</tr>
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
				<td><form:label path="birthday">
						<spring:message code="label.birthday" />
					</form:label></td>
				<td><form:input path="birthday" /></td>
			</tr>
			<tr>
				<td><form:label path="address.street">
						<spring:message code="label.street" />
					</form:label></td>
				<td><form:input path="address.street" /></td>
			</tr>
			<tr>
				<td><form:label path="address.number">
						<spring:message code="label.number" />
					</form:label></td>
				<td><form:input path="address.number" /></td>
			</tr>
			<tr>
				<td><form:label path="address.stair">
						<spring:message code="label.stair" />
					</form:label></td>
				<td><form:input path="address.stair" /></td>
			</tr>
			<tr>
				<td><form:label path="address.door">
						<spring:message code="label.door" />
					</form:label></td>
				<td><form:input path="address.door" /></td>
			</tr>
			<tr>
				<td><form:label path="address.zip">
						<spring:message code="label.zip" />
					</form:label></td>
				<td><form:input path="address.zip" /></td>
			</tr>
			<tr>
				<td><form:label path="address.city">
						<spring:message code="label.city" />
					</form:label></td>
				<td><form:input path="address.city" /></td>
			</tr>
			<tr>
				<td colspan="2"><input type="submit"
					value="<spring:message code="label.save"/>" /></td>
			</tr>
		</table>
	</form:form>
	
	<c:if test="${!empty teacherList}">
		<table class="data">
			<tr>
				<th>Name</th>
				<th>Email</th>
				<th>Telephone</th>
				<th>Birthday</th>
				<th>Street</th>
				<th>ZIP</th>
				<th>City</th>
				<th>&nbsp;</th>
			</tr>

			<c:forEach items="${teacherList}" var="teacher">

				<tr>
					<td>${teacher.lastname}${teacher.firstname}</td>
					<td>${teacher.email}</td>
					<td>${teacher.telephone}</td>
					<td>${teacher.birthday}</td>
					<c:if test="${!empty teacher.address}">
						<td>${teacher.address.street}
							${teacher.address.number}/${teacher.address.stair}/${teacher.address.door}</td>
						<td>${teacher.address.zip}</td>
						<td>${teacher.address.city}</td>
					</c:if>
					<td><a href="teacher/edit/${teacher.pid}">Edit</a></td>
					<td><a href="teacher/delete/${teacher.pid}">Delete</a></td>
				</tr>

			</c:forEach>

		</table>
	</c:if>
	

</dmtags:base>