<%@tag description="dance.manage person form fields" language="java"
	pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<form:input path="pid" type="hidden" />
<form:input path="enabled" type="hidden" />
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
		<div class="span6">
			<form:input path="firstname" cssErrorClass="has-error" />
		</div>
		<form:errors path="firstname" cssClass="help-inline" />
	</div>
</spring:bind>

<div class="control-group">
	<form:label path="lastname" class="control-label">
		<spring:message code="label.lastname" />*
                </form:label>
	<div class="span6">
		<form:input path="lastname" />
	</div>
	<form:errors path="lastname" cssClass="error" />
</div>

<div class="control-group">
	<form:label path="email" class="control-label">
		<spring:message code="label.email" />*
                </form:label>
	<div class="span6">
		<form:input path="email" />
	</div>
	<form:errors path="email" cssClass="error" />
</div>

<div class="control-group">
	<form:label path="telephone" class="control-label">
		<spring:message code="label.telephone" />*
                </form:label>
	<div class="span6">
		<form:input path="telephone" />
	</div>
	<form:errors path="telephone" cssClass="error" />
</div>

<div class="control-group">
	<form:label path="birthday" class="control-label">
		<spring:message code="label.birthday" />*
                </form:label>
	<div class="span6">
		<form:input path="birthday" id="datepicker" />
	</div>
	<form:errors path="birthday" cssClass="error" />
</div>

