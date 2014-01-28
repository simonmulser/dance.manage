<%@tag description="dance.manage person form fields" language="java"
	pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<form:input path="pid" type="hidden" />
<form:input path="enabled" type="hidden" />
<form:input path="password" type="hidden" />
<form:input path="address.aid" type="hidden" />

<spring:bind path="firstname">
	<c:set var="divClass" value="control-group" />
	<c:if test="${status.error}">
		<c:set var="divClass" value="control-group error" />
	</c:if>
	<div class="${divClass}">
		<form:label path="firstname" class="control-label">
			<spring:message code="label.firstname" />*
                </form:label>
		<div class="span3">
			<form:input path="firstname" cssErrorClass="has-error" />
		</div>
		<form:errors path="firstname" cssClass="help-inline" />
	</div>
</spring:bind>

<spring:bind path="lastname">
	<c:set var="divClass" value="control-group" />
	<c:if test="${status.error}">
		<c:set var="divClass" value="control-group error" />
	</c:if>
	<div class="${divClass}">
		<form:label path="lastname" class="control-label">
			<spring:message code="label.lastname" />*
                </form:label>
		<div class="span3">
			<form:input path="lastname" cssErrorClass="has-error" />
		</div>
		<form:errors path="lastname" cssClass="help-inline" />
	</div>
</spring:bind>

<spring:bind path="email">
	<c:set var="divClass" value="control-group" />
	<c:if test="${status.error}">
		<c:set var="divClass" value="control-group error" />
	</c:if>
	<div class="${divClass}">
		<form:label path="email" class="control-label">
			<spring:message code="label.email" />
		</form:label>
		<div class="span3">
			<form:input path="email" cssErrorClass="has-error" />
		</div>
		<form:errors path="email" cssClass="help-inline" />
	</div>
</spring:bind>

<spring:bind path="telephone">
	<c:set var="divClass" value="control-group" />
	<c:if test="${status.error}">
		<c:set var="divClass" value="control-group error" />
	</c:if>
	<div class="${divClass}">
		<form:label path="telephone" class="control-label">
			<spring:message code="label.telephone" />*
                </form:label>
		<div class="span3">
			<form:input path="telephone" cssErrorClass="has-error" />
		</div>
		<form:errors path="telephone" cssClass="help-inline" />
	</div>
</spring:bind>