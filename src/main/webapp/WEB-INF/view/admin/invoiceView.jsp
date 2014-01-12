<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.joda.org/joda/time/tags" prefix="joda"%>
<%@taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="dmtags"%>
<spring:message var="i18nTitle" code="nav.invoices" />
<dmtags:base title="${i18nTitle}" activesection="invoices">
	<dmtags:span width="12">

		<spring:message var="i18nInvoices" code="widget.invoices" />

		<dmtags:widget icon="icon-envelope" title="${i18nInvoices}">
			<spring:message code="help.invoice" />
			<form:form method="post" action="invoice/preview"
				commandName="invoice" class="form-horizontal">
				
				<div id="find_keyword" class="control-group">
					<form:label path="participant.pid" class="control-label">
						<spring:message code="label.participant" />
					</form:label>
					<div class="ui-widget span6">
						<input id="participantQuery" type="text" value="" /><i
							title="<spring:message code='help.searchParticipant' />"
							class="inline-tooltip icon icon-question-sign"></i>
					</div>
				</div>
				<div id="showDetails">
					<c:choose>
						<c:when test="${!empty invoice.positions }">
							<table cellpadding="10">
								
								<form:input path="participant.pid" id="participantPid" type="hidden" />
								<tr>
									<c:choose>
										<c:when test="${!empty invoice.parent.pid}">
											
												<td colspan="2">
													<address>
														<em><spring:message code="label.invoiceAddress" /></em><br />
														<form:input path="parent.pid" id="parentPid" type="hidden" />
														<strong>${invoice.parent.firstname }&nbsp;${invoice.parent.lastname }</strong><br />
														${invoice.parent.address.street}&nbsp;${invoice.parent.address.number}
														<c:if test="${!empty invoice.parent.address.stair}">
															/ ${invoice.parent.address.stair }
														</c:if>	
														<c:if test="${!empty invoice.parent.address.door}">
															/ ${invoice.parent.address.door }
														</c:if>
														<br />
														${invoice.parent.address.zip},&nbsp;${invoice.parent.address.city }
													</address>
												</td>
												<td colspan="2">
													<em><spring:message code="label.senderAddress" /></em><br />
													Dance &amp; Fun<br />
													${user.address.street}&nbsp;${user.address.number }<br />
													${user.address.zip }&nbsp;${user.address.city }
												</td>
										</c:when>
										<c:when test="${!empty invoice.participant.pid}">
												<td colspan="2">
													<em><spring:message code="label.invoiceAddress" /></em><br />
													<strong>${invoice.participant.firstname }&nbsp;${invoice.participant.lastname }</strong><br />
													${invoice.participant.address.street}&nbsp;${invoice.participant.address.number}
													<c:if test="${!empty invoice.participant.address.stair}">
														/ ${invoice.participant.address.stair }
													</c:if>	
													<c:if test="${!empty invoice.participant.address.door}">
														/ ${invoice.participant.address.door }
													</c:if>
													<br />
													${invoice.participant.address.zip},&nbsp;${invoice.participant.address.city }
												</td>
												<td colspan="2">
													<em><spring:message code="label.senderAddress" /></em><br />
													Dance &amp; Fun<br />
													${user.address.street}&nbsp;${user.address.number }<br />
													${user.address.zip }&nbsp;${user.address.city }
												</td>
										</c:when>
										<c:otherwise>
										</c:otherwise>
									</c:choose>
								</tr>
								<tr>
								<td colspan="4">&nbsp;</td>
								</tr>
								<tr>
									<td>Bezeichnung</td>
									<td>Position</td>
									<td>Preis</td>
									<td>Status</td>
								</tr>
								<c:forEach items="${invoice.positions}" var="position" varStatus="status">
									<tr>
										<td>
											<form:label path="positions[${status.index }].key.course.name">
												${position.key.course.name}
											</form:label>
											<form:input path="positions[${status.index }].key.course.name" type="hidden" />
											<form:input path="positions[${status.index }].key.course.cid" type="hidden" />
										</td>
										<td>
												<c:set var="index" value="${status.index }" />
												<form:input path="positions[${status.index }].possibleDurations" type="hidden"/>
												<form:select path="positions[${status.index }].duration">
													<form:options items="${invoice.positions[index].possibleDurations}" itemLabel="label" />
												</form:select>
										</td>
										<td>
											<c:if test="${!empty position.amount }">
												<form:label path="positions[${status.index }].amount"	>
												${position.amount} &euro;
											</form:label>
											</c:if>
										</td>
										<td>
											<c:if test="${!empty position.errorMessage }">
												<div class="errorMessage">${position.errorMessage }</div>
											</c:if>
										</td>
										
									</tr>
								</c:forEach>
								<tr>
								<td colspan="4">&nbsp;</td>
								</tr>
								<tr>
									<td>
										<form:label path="reduction">
											<spring:message code="label.reduction" />
									    </form:label>
									    </td>
								    <td class="input-append">
								    	<form:input class="span1" path="reduction" />
									 	<span class="add-on">%</span>
										<form:errors path="reduction" cssClass="error" />
									</td>
								</tr>
								<tr>
									<td></td>
									<c:if test="${!empty invoice.reductionAmount }">
										<td>
											<form:label path="reductionAmount">
												<spring:message code="label.reduction" />
											</form:label>
										</td>
										<td>	
											${invoice.reductionAmount} &euro;
										</td>
									</c:if>
									<td></td>
								</tr>
								<tr>
									<td></td>
									<c:if test="${!empty invoice.vatAmount }">
										<td>
											<form:label path="vatAmount">
												<spring:message code="label.vatAmount" />
											</form:label>
										</td>
										<td>	
											${invoice.vatAmount} &euro;
										</td>
									</c:if>
									<td></td>
								</tr>
								<tr>
									<td></td>
									<c:if test="${!empty invoice.totalAmount }">
											<td>
												<form:label path="totalAmount">
													<spring:message code="label.totalAmount" />	
												</form:label>
											</td>
											<td>
												${invoice.totalAmount} &euro;
											</td>
									</c:if>
									<td></td>
								</tr>
							</table>
								<c:choose>
									<c:when test="${status eq 0}">
										<div class="form-actions">
											<input type="submit" value="<spring:message code="label.preview"/>"
												class="btn btn-primary" />
										</div>
									</c:when>
									<c:otherwise>
										<div class="form-actions">
											<input type="submit" value="<spring:message code="label.save"/>"
												class="btn btn-primary" />
										</div>
									</c:otherwise>
								</c:choose>
						</c:when>
						<c:otherwise>
						</c:otherwise>
					</c:choose>
				</div>
				
			</form:form>	
			
		</dmtags:widget>
		<spring:message var="i18nOverview" code="widget.overview" />
		<dmtags:widget title="${i18nOverview}" style="table" icon="icon-list">
			<display:table name="invoiceList" id="invoice"
					class="table table-striped table-bordered displaytag" pagesize="15"
					requestURI="/admin/invoice" defaultsort="1">
					<display:column sortable="true" titleKey="label.invoiceID">
						${invoice.iid}
					</display:column>
					<display:column sortable="true" titleKey="label.invoiceDate">
						<joda:format value="${invoice.date}" pattern="dd.MM.yyyy" />
					</display:column>
					
					<display:column sortable="true" titleKey="label.invoiceRecipient">
						<c:choose>
							<c:when test="${!empty invoice.parent.pid }">
								${invoice.parent.firstname}&nbsp;${invoice.parent.lastname}
							</c:when>
							<c:otherwise>
								${invoice.participant.firstname}&nbsp;${invoice.participant.lastname}
							</c:otherwise>
						</c:choose>
					</display:column>
					<display:column sortable="true" titleKey="label.participant">
						${invoice.participant.firstname}&nbsp;${invoice.participant.lastname}
					</display:column>
					<display:column titleKey="label.totalAmount">
						${invoice.totalAmount}&euro;
					</display:column>
					<display:column titleKey="label.vatAmount">
						${invoice.vatAmount}&euro;
					</display:column>
					<display:column titleKey="label.reduction">
						<c:choose>
							<c:when test="${!empty invoice.reduction }">
								${invoice.reductionAmount }&euro;&nbsp;(${invoice.reduction}%)
							</c:when>
							<c:otherwise>
								-
							</c:otherwise>
						</c:choose>
					</display:column>
					<display:column>
						<c:set var="iid" value="${invoice.iid}" />
						<br />
						<a href="invoice/delete/${iid}" class="openDialog" id="${iid}"><spring:message
								code="label.cancelInvoice" /></a>
								<i title="<spring:message code='help.invoiceDelete' />" class="inline-tooltip icon icon-question-sign"></i>
						<div id="deleteId" style="display:none;"></div>		
					</display:column>
			</display:table>
			<div id="dialog-confirm"
				title="<spring:message code="delete.title" />">
				<p>
					<span class="ui-icon ui-icon-alert"
						style="float: left; margin: 0 7px 20px 0;"></span>
					<spring:message code="delete.invoice" />
				</p>
			</div>
			<div id="dialog-cancelError" title="<spring:message code="message.deleteTitle" />"><spring:message code="message.cannotBeDeleted" /></div>
		</dmtags:widget>
	</dmtags:span>
</dmtags:base>
<script type="text/javascript">
	
	$("#dialog-cancelError").dialog({ // Fehler bei Stornierung
		autoOpen : false,
		resizable : false,
		modal: true,
	      buttons: {
	        "OK": function() {
	          $( this ).dialog( "close" );
	        }
	      }
	});
	var cEM = "${cancelErrorMessage}";
	if( cEM == 'true' ){
		$("#dialog-cancelError").dialog("open");
	}
	$('i').tooltip();
	$(".openDialog").click(function() { // Rückbestätigung bei Löschen 
		$("#deleteId").text($(this).attr("id"));
		$("#dialog-confirm").dialog("open");
		return false;
	});
	$("#dialog-confirm").dialog({
		autoOpen : false,
		resizable : false,
		modal : true,
		buttons : {
			"OK" : function() {
				document.location = "invoice/delete/"+$("#deleteId").text();
				$("#deleteId").text("");

				$(this).dialog("close");
			},
			Cancel : function() {
				$(this).dialog("close");
			}
		}
	});
	$(document).ready(function() {
		$("#participantQuery").autocomplete({
			minLength : 1,
			delay : 500,
			source : function(request, response) {
				$.getJSON("/dancemanage/admin/invoice/getParticipants",request,function(result) {
					response($.map(result,function(item) {
							return {
								label : item.firstname + " " + item.lastname,
								value : item.firstname + " " + item.lastname,
								pid: item.pid
							};
					}));
				});
			},

			select : function(event, ui) {
				if (ui.item) {
					window.location.href = '/dancemanage/admin/invoice/getDetailsForParticipant/' + ui.item.pid;
				}
			}
		});
	});
</script>