<%@tag description="dance.manage address form fields" language="java"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.joda.org/joda/time/tags" prefix="joda"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>


<spring:bind path="address.street">
	<c:set var="divClass" value="control-group" />
	<c:if test="${status.error}">
		<c:set var="divClass" value="control-group error" />
	</c:if>
	<div class="${divClass}">
		<form:label path="address.street" class="control-label">
			<spring:message code="label.street" />*
                </form:label>
		<div class="span3">
			<form:input path="address.street" cssErrorClass="has-error" />
		</div>
		<form:errors path="address.street" cssClass="help-inline" />
	</div>
</spring:bind>

<spring:bind path="address.number">
	<c:set var="divClass" value="control-group" />
	<c:if test="${status.error}">
		<c:set var="divClass" value="control-group error" />
	</c:if>
	<div class="${divClass}">
		<form:label path="address.number" class="control-label">
			<spring:message code="label.number" />*
                </form:label>
		<div class="span3">
			<form:input path="address.number" cssErrorClass="has-error" />
		</div>
		<form:errors path="address.number" cssClass="help-inline" />
	</div>
</spring:bind>

<spring:bind path="address.stair">
	<c:set var="divClass" value="control-group" />
	<c:if test="${status.error}">
		<c:set var="divClass" value="control-group error" />
	</c:if>
	<div class="${divClass}">
		<form:label path="address.stair" class="control-label">
			<spring:message code="label.stair" />
		</form:label>
		<div class="span3">
			<form:input path="address.stair" cssErrorClass="has-error" />
		</div>
		<form:errors path="address.stair" cssClass="help-inline" />
	</div>
</spring:bind>

<spring:bind path="address.door">
	<c:set var="divClass" value="control-group" />
	<c:if test="${status.error}">
		<c:set var="divClass" value="control-group error" />
	</c:if>
	<div class="${divClass}">
		<form:label path="address.door" class="control-label">
			<spring:message code="label.door" />
		</form:label>
		<div class="span3">
			<form:input path="address.door" cssErrorClass="has-error" />
		</div>
		<form:errors path="address.door" cssClass="help-inline" />
	</div>
</spring:bind>

<spring:bind path="address.zip">
	<c:set var="divClass" value="control-group" />
	<c:if test="${status.error}">
		<c:set var="divClass" value="control-group error" />
	</c:if>
	<div class="${divClass}">
		<form:label path="address.zip" class="control-label">
			<spring:message code="label.zip" />*
		</form:label>
		<div class="span3">
			<form:input path="address.zip" cssErrorClass="has-error" />
		</div>
		<form:errors path="address.zip" cssClass="help-inline" />
	</div>
</spring:bind>

<spring:bind path="address.city">
	<c:set var="divClass" value="control-group" />
	<c:if test="${status.error}">
		<c:set var="divClass" value="control-group error" />
	</c:if>
	<div class="${divClass}">
		<form:label path="address.city" class="control-label">
			<spring:message code="label.city" />*
		</form:label>
		<div class="span3">
			<form:input path="address.city" cssErrorClass="has-error" />
		</div>
		<form:errors path="address.city" cssClass="help-inline" />
	</div>
</spring:bind>