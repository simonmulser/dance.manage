
<%@tag description="dance.manage base template" language="java"
	pageEncoding="UTF-8"%>
<%@attribute name="title" required="true" type="java.lang.String"%>
<%@attribute name="style" required="false" type="java.lang.String"%>
<%@attribute name="icon" required="true" type="java.lang.String"%>
<%@attribute name="id" required="false" type="java.lang.String"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<c:set var="widgetStyle" value="widget" />
<c:choose>
	<c:when test="${style eq 'table'}">
		<c:set var="widgetStyle" value="widget widget-table action-table" />
	</c:when>
	<c:when test="${style eq 'nopad'}">
		<c:set var="widgetStyle" value="widget widget-nopad" />
	</c:when>
</c:choose>

<div class="${widgetStyle}" id="${not empty id ? id : ''}">
	<div class="widget-header">
		<i class="${icon}"></i>
		<h3>
			${title}
		</h3>
	</div>
	<!-- /widget-header -->
	<div class="widget-content">
		<jsp:doBody />
	</div>
	<!-- /widget-content -->
</div>
<!-- /widget -->