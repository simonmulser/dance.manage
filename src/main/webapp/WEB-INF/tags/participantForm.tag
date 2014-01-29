<%@tag description="dance.manage person form fields" language="java"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.joda.org/joda/time/tags" prefix="joda"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<spring:bind path="birthday">
	<c:set var="divClass" value="control-group" />
	<c:if test="${status.error}">
		<c:set var="divClass" value="control-group error" />
	</c:if>
	<div class="${divClass}">
		<form:label path="birthday" class="control-label">
			<spring:message code="label.birthday" />*
                </form:label>
		<div class="span3">
			<form:input path="birthday" id="datepicker" cssErrorClass="has-error" />
		</div>
		<form:errors path="birthday" cssClass="help-inline" />
	</div>
</spring:bind>

