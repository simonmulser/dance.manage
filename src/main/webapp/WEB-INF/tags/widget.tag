
<%@tag description="dance.manage base template" language="java"
	pageEncoding="UTF-8"%>
<%@attribute name="title" required="true" type="java.lang.String"%>
<%@attribute name="style" required="false" type="java.lang.String"%>
<%@attribute name="icon" required="true" type="java.lang.String"%>
<%@attribute name="id" required="false" type="java.lang.String"%>
<%@attribute name="retractable" required="false"
	type="java.lang.Boolean"%>
<%@attribute name="retractedPerDefault" required="false"
	type="java.lang.Boolean"%>
<%@attribute name="pdfLink" required="false" type="java.lang.String"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<jsp:useBean id="random" class="java.util.Random" scope="application" />

<c:set var="widgetStyle" value="widget" />
<c:set var="widgetId" value="${random.nextInt()}" />
<c:choose>
	<c:when test="${style eq 'table'}">
		<c:set var="widgetStyle" value="widget widget-table action-table" />
	</c:when>
	<c:when test="${style eq 'nopad'}">
		<c:set var="widgetStyle" value="widget widget-nopad" />
	</c:when>
</c:choose>

<div class="${widgetStyle}" id="${not empty id ? id : ''}">
	<div class="widget-header" id="widget-header-${widgetId}"
		${retractable ? 'style="cursor: pointer;"' : ''}>
		<i class="${icon}"></i>
		<h3>${title}</h3>

		<c:if test="${not empty pdfLink}">
			<a href="${pdfLink}"><i class="widgetpdflink icon-print"></i></a>
		</c:if>

		<c:if test="${retractable}">
			<i id="widget-retractIcon-${widgetId}"
				class="widgetcaret ${retractedPerDefault ? 'icon-caret-up' : 'icon-caret-down'}"></i>
		</c:if>
	</div>
	<!-- /widget-header -->
	<div class="widget-content" id="widget-content-${widgetId}"
		${retractedPerDefault ? 'style="display:none"' : ''}>
		<jsp:doBody />
	</div>
	<!-- /widget-content -->
</div>
<!-- /widget -->

<c:if test="${retractable}">
	<script>
		$('#widget-header-${widgetId}').click(function() {
			var content = $('#widget-content-${widgetId}');
			var retractIcon = $('#widget-retractIcon-${widgetId}');
			content.toggle('slide', {
				direction : 'up'
			});
			if (retractIcon.attr('class') == 'widgetcaret icon-caret-up') {
				retractIcon.attr('class', 'widgetcaret icon-caret-down');
			} else {
				retractIcon.attr('class', 'widgetcaret icon-caret-up');
			}
		});
	</script>
</c:if>