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
	   <br/>
	   <br/>
	   <spring:message var="accessDenied" code="user.accessDenied" />
		<dmtags:widget icon="icon-warning-sign" title="${accessDenied}">
			<i class="shortcut-icon "></i>&nbsp;&nbsp;
			<spring:message code="user.accessDenied" />
		</dmtags:widget>
	</dmtags:center>
</dmtags:scaffold>