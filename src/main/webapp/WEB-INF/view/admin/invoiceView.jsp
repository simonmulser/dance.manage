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
			
			<form:form method="post" action="invoice/add"
				commandName="invoice" class="form-horizontal">
				
				<div id="find_keyword" class="control-group">
					<form:label path="owner" class="control-label">
						<spring:message code="label.participant" />
					</form:label>
					<div class="ui-widget span6">
						<input id="participantQuery" type="text" value="" /><i
							title="<spring:message code='help.searchParticipant' />"
							class="inline-tooltip icon icon-question-sign"></i>
							
						<div id="showParticipant">			
							<span id="selectedParticipant">
								
							 </span>
						</div>
					</div>

					<form:input path="owner" id="owner" type="hidden" />
				</div>
				
			</form:form>	
			
		</dmtags:widget>
		<spring:message var="i18nOverview" code="widget.overview" />
		<dmtags:widget title="${i18nOverview}" style="table" icon="icon-list">

		</dmtags:widget>
	</dmtags:span>
</dmtags:base>