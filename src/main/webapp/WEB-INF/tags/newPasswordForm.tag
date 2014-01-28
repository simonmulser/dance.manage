<%@tag description="dance.manage password change form fields"
	language="java" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.joda.org/joda/time/tags" prefix="joda"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<c:if test="${!empty password.errorMessage }">
	<div class="errorMessage">
		<i class="icon-remove-sign"></i>&nbsp;&nbsp;${password.errorMessage}
	</div>
	<br />
</c:if>

<spring:bind path="oldPassword">
	<c:set var="divClass" value="control-group" />
	<c:if test="${status.error}">
		<c:set var="divClass" value="control-group error" />
	</c:if>
	<div class="${divClass}">
		<form:label path="oldPassword" class="control-label">
			<spring:message code="label.oldPassword" />*
                </form:label>
		<div class="span3">
			<form:input path="oldPassword" cssErrorClass="has-error" />
		</div>
		<form:errors path="oldPassword" cssClass="help-inline" />
	</div>
</spring:bind>

<spring:bind path="newPasswordFirst">
	<c:set var="divClass" value="control-group" />
	<c:if test="${status.error}">
		<c:set var="divClass" value="control-group error" />
	</c:if>
	<div class="${divClass}">
		<form:label path="newPasswordFirst" class="control-label">
			<spring:message code="label.newPasswordFirst" />*
                </form:label>
		<div class="span3">
			<form:input path="newPasswordFirst" cssErrorClass="has-error" />
		</div>
		<form:errors path="newPasswordFirst" cssClass="help-inline" />
	</div>
</spring:bind>

<spring:bind path="newPasswordSecond">
	<c:set var="divClass" value="control-group" />
	<c:if test="${status.error}">
		<c:set var="divClass" value="control-group error" />
	</c:if>
	<div class="${divClass}">
		<form:label path="newPasswordSecond" class="control-label">
			<spring:message code="label.newPasswordSecond" />*
                </form:label>
		<div class="span3">
			<form:input path="newPasswordSecond" cssErrorClass="has-error" />
		</div>
		<form:errors path="newPasswordSecond" cssClass="help-inline" />
	</div>
</spring:bind>