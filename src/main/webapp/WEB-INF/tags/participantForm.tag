<%@tag description="dance.manage person form fields" language="java"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.joda.org/joda/time/tags" prefix="joda"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>


<div class="control-group">
	<form:label path="birthday" class="control-label">
		<spring:message code="label.birthday" />*
                </form:label>
	<div class="span6">
		<form:input path="birthday" id="datepicker" />
	</div>
	<form:errors path="birthday" cssClass="error" />
	<form:errors cssClass="error" />
</div>

