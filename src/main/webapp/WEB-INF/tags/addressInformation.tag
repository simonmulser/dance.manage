<%@ tag language="java" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<tr>
    <td><spring:message code="label.street" /></td>
    <td>${user.address.street}</td>
</tr>
<tr>
    <td><spring:message code="label.number" /></td>
    <td>${user.address.number}</td>
</tr>
<tr>
	<td><spring:message code="label.stair" /></td>
	<td>${user.address.stair}</td>
</tr>
<tr>
    <td><spring:message code="label.door" /></td>
    <td>${user.address.door}</td>
</tr>
<tr>
    <td><spring:message code="label.zip" /></td>
    <td>${user.address.zip}</td>
</tr>
<tr>
    <td><spring:message code="label.city" /></td>
    <td>${user.address.city}</td>
</tr>