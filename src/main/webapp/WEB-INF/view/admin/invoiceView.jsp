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
							<spring:message code="label.invoiceAddress" />
							<form:input path="participant.pid" id="participantPid" type="hidden" />
							<c:choose>
								<c:when test="${!empty invoice.parent.pid}">
									<div>
										<form:input path="parent.pid" id="parentPid" type="hidden" />
										${invoice.parent.firstname }&nbsp;${invoice.parent.lastname }<br />
										${invoice.parent.address.street}&nbsp;${invoice.parent.address.number}
										<c:if test="${!empty invoice.parent.address.stair}">
											/ ${invoice.parent.address.stair }
										</c:if>	
										<c:if test="${!empty invoice.parent.address.door}">
											/ ${invoice.parent.address.door }
										</c:if>
										<br />
										${invoice.parent.address.zip},&nbsp;${invoice.parent.address.city }
									</div>
								</c:when>
								<c:when test="${!empty invoice.participant.pid}">
									<div>
										${invoice.participant.firstname }&nbsp;${invoice.participant.lastname }<br />
										${invoice.participant.address.street}&nbsp;${invoice.participant.address.number}
										<c:if test="${!empty invoice.participant.address.stair}">
											/ ${invoice.participant.address.stair }
										</c:if>	
										<c:if test="${!empty invoice.participant.address.door}">
											/ ${invoice.participant.address.door }
										</c:if>
										<br />
										${invoice.participant.address.zip},&nbsp;${invoice.participant.address.city }
									</div>
								</c:when>
								<c:otherwise>
								</c:otherwise>
							</c:choose>
							<c:forEach items="${invoice.positions}" var="position" varStatus="status">
								<div class="control-group">
									<form:label path="positions[${status.index }].key.course.name" class="control-label">
										${position.key.course.name}
									</form:label>
									<form:input path="positions[${status.index }].key.course.name" type="hidden" />
									<form:input path="positions[${status.index }].key.course.cid" type="hidden" />
									<div class="span6">
										<form:select path="positions[${status.index }].duration">
											<form:options items="${Duration}" />
										</form:select>
									</div>
									<c:if test="${!empty position.amount }">
										<form:label path="positions[${status.index }].amount" class="control-label">
										${position.amount}
									</form:label>
									</c:if>
									<c:if test="${!empty position.errorMessage }">
										<div class="errorMessage">${position.errorMessage }</div>
									</c:if>
									
								</div>
							</c:forEach>
							<div class="control-group">
								<form:label path="reduction" class="control-label">
									<spring:message code="label.reduction" />
							    </form:label>
								<div class="span6">
									<form:input path="reduction" /> %
								</div>
								<form:errors path="reduction" cssClass="error" />
							</div>
							
								<c:if test="${!empty invoice.totalAmount }">
									<div class="control-group">
										<form:label path="totalAmount" class="control-label">
											<spring:message code="label.totalAmount" />
												${invoice.totalAmount}
										</form:label>
									</div>	
									<div class="control-group">
									<form:label path="vatAmount" class="control-label">
										<spring:message code="label.vatAmount" />
											${invoice.vatAmount}
									</form:label>	
									</div>	
								</c:if>
							
							<div class="form-actions">
								<input type="submit" value="<spring:message code="label.preview"/>"
									class="btn btn-primary" />
							</div>
						</c:when>
						<c:otherwise>
						</c:otherwise>
					</c:choose>
				</div>
				
			</form:form>	
			
		</dmtags:widget>
		<spring:message var="i18nOverview" code="widget.overview" />
		<dmtags:widget title="${i18nOverview}" style="table" icon="icon-list">

		</dmtags:widget>
	</dmtags:span>
</dmtags:base>
<script type="text/javascript">
	$('i').tooltip();
	$(document).ready(function() {
		$("#showParticipant").on("click", "i", function() { //Participant löschen
			$(this).parent().remove();
			$("#participantPid").val(null);
		});

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