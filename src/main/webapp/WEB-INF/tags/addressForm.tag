<%@tag description="dance.manage address form fields" language="java"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.joda.org/joda/time/tags" prefix="joda"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>


<div class="control-group">
	<form:label path="address.street" class="control-label">
		<spring:message code="label.street" />*
                </form:label>
	<div class="span6">
		<form:input path="address.street" />
	</div>
	<form:errors path="address.street" cssClass="error" />
</div>

<div class="control-group">
	<form:label path="address.number" class="control-label">
		<spring:message code="label.number" />*
                </form:label>
	<div class="span6">
		<form:input path="address.number" />
	</div>
	<form:errors path="address.number" cssClass="error" />
</div>

<div class="control-group">
	<form:label path="address.stair" class="control-label">
		<spring:message code="label.stair" />
	</form:label>
	<div class="span6">
		<form:input path="address.stair" />
	</div>
	<form:errors path="address.stair" cssClass="error" />
</div>

<div class="control-group">
	<form:label path="address.door" class="control-label">
		<spring:message code="label.door" />
	</form:label>
	<div class="span6">
		<form:input path="address.door" />
	</div>
	<form:errors path="address.door" cssClass="error" />
</div>

<div class="control-group">
	<form:label path="address.zip" class="control-label">
		<spring:message code="label.zip" />*
                </form:label>
	<div class="span6">
		<form:input path="address.zip" />
	</div>
	<form:errors path="address.zip" cssClass="error" />
</div>

<div class="control-group">
	<form:label path="address.city" class="control-label">
		<spring:message code="label.city" />*
                </form:label>
	<div class="span6">
		<form:input path="address.city" />
	</div>
	<form:errors path="address.city" cssClass="error" />
</div>