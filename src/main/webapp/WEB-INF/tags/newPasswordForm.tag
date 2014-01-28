<%@tag description="dance.manage password change form fields" language="java"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.joda.org/joda/time/tags" prefix="joda"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<c:if test="${!empty password.errorMessage }">
	<div class="errorMessage"><i class="icon-remove-sign"></i>&nbsp;&nbsp;${password.errorMessage}</div><br />
</c:if>
			
<div class="control-group">
	<form:label path="oldPassword" class="control-label">
		<spring:message code="label.oldPassword" />*
                </form:label>
	<div class="span6">
		<form:password path="oldPassword" />
	</div>
	<form:errors path="oldPassword" cssClass="error" />
</div>

<div class="control-group">
	<form:label path="newPasswordFirst" class="control-label">
		<spring:message code="label.newPasswordFirst" />*
                </form:label>
	<div class="span6">
		<form:password path="newPasswordFirst" />
	</div>
	<form:errors path="newPasswordFirst" cssClass="error" />
</div>

<div class="control-group">
	<form:label path="newPasswordSecond" class="control-label">
		<spring:message code="label.newPasswordSecond" />*
	</form:label>
	<div class="span6">
		<form:password path="newPasswordSecond" />
	</div>
	<form:errors path="newPasswordSecond" cssClass="error" />
</div>