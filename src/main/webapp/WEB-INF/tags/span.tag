<%@tag description="dance.manage span tag" language="java"
	pageEncoding="UTF-8"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="dmtags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@attribute name="width" required="true" type="java.lang.Integer"%>

<c:set var="spanClass" value="span${width}" />

<div class="${spanClass}">
	<jsp:doBody />
</div>