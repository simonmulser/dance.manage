<%@ tag language="java" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<tr>
	<td><spring:message code="label.firstname" /></td>
	<td>${user.firstname}</td>
</tr>
<tr>
	<td><spring:message code="label.lastname" /></td>
	<td>${user.lastname}</td>
</tr>
<tr>
	<td><spring:message code="label.email" /></td>
	<td>${user.email}</td>
</tr>
<tr>
	<td><spring:message code="label.telephone" /></td>
	<td>${user.telephone}</td>
</tr>
<tr>
	<td><spring:message code="label.birthday" /></td>
	<td>${user.birthday}</td>
</tr>