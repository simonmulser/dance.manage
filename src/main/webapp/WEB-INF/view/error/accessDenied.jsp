<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.joda.org/joda/time/tags" prefix="joda"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="dmtags"%>

<spring:message var="i18nTitle" code="nav.accessDenied" />
<dmtags:scaffold title="${i18nTitle}">
	<dmtags:upperNavigation />
	<dmtags:center>
		<dmtags:span width="12">
			<br />
			<br />
			<spring:message var="i18nAccessDenied" code="widget.accessDenied" />
			<dmtags:widget icon="icon-warning-sign" title="${i18nAccessDenied}">
				<i class="icon-ban-circle"></i>&nbsp;&nbsp;
			<spring:message code="user.accessDenied" />
			</dmtags:widget>
		</dmtags:span>
	</dmtags:center>
</dmtags:scaffold>