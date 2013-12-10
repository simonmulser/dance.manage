<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.joda.org/joda/time/tags" prefix="joda"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="dmtags"%>
<spring:message var="i18nTitle" code="nav.invoices" />
<dmtags:base title="${i18nTitle}" activesection="invoices">
	<dmtags:span width="12">

		<spring:message var="i18nInvoices" code="widget.invoices" />

		<dmtags:widget icon="icon-envelope" title="${i18nInvoices}">
			<spring:message code="help.invoice" />
		</dmtags:widget>
		<spring:message var="i18nOverview" code="widget.overview" />
		<dmtags:widget title="${i18nOverview}" style="table" icon="icon-list">

		</dmtags:widget>
	</dmtags:span>
</dmtags:base>