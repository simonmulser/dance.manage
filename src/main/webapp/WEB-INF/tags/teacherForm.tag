<%@tag description="dance.manage address form fields" language="java"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.joda.org/joda/time/tags" prefix="joda"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<div class="control-group">
	<form:label path="svnr" class="control-label">
		<spring:message code="label.svnr" />*
                </form:label>
	<div class="span6">
		<form:input path="svnr" />
	</div>
	<form:errors path="svnr" cssClass="error" />
</div>
<div class="control-group">
	<form:label path="salary" class="control-label">
		<spring:message code="label.salary" />*
                </form:label>
	<div class="span6">
		<form:input path="salary" />
	</div>
	<form:errors path="salary" cssClass="error" />
</div>