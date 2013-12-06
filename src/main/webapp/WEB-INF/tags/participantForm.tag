<%@tag description="dance.manage person form fields" language="java"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.joda.org/joda/time/tags" prefix="joda"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<div class="control-group">
	<form:label path="contactPerson" class="control-label">
		<spring:message code="label.contactPerson" />
	</form:label>
	<div class="span6">
		<form:input path="contactPerson" />
	</div>
	<form:errors path="contactPerson" cssClass="error" />
</div>

<div class="control-group">
	<form:label path="emergencyNumber" class="control-label">
		<spring:message code="label.emergencyNumber" />
	</form:label>
	<div class="span6">
		<form:input path="emergencyNumber" />
	</div>
	<form:errors path="emergencyNumber" cssClass="error" />
</div>

