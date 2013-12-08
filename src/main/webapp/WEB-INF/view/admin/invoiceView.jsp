<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.joda.org/joda/time/tags" prefix="joda"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="dmtags"%>

<dmtags:base title="nav.invoices" activesection="invoices">
	<dmtags:widget icon="icon-envelope" title="widget.invoices">
		<spring:message code="help.invoice" />
	</dmtags:widget>
	<dmtags:widget title="widget.overview" style="table" icon="icon-list">
		
	</dmtags:widget>
</dmtags:base>