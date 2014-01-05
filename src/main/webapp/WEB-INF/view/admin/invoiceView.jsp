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
					<form:label path="participant.pid" class="control-label">
						<spring:message code="label.participant" />
					</form:label>
					<div class="ui-widget span6">
						<input id="participantQuery" type="text" value="" /><i
							title="<spring:message code='help.searchParticipant' />"
							class="inline-tooltip icon icon-question-sign"></i>
							
						<div id="showParticipant">			
							
						</div>
						<div id="courses"></div>
					</div>

					<form:input path="participant.pid" id="participantPid" />
				</div>
				<div id="showDetails">
					
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
							label : item.firstname
									+ " "
									+ item.lastname,
							value : item.firstname
									+ " "
									+ item.lastname,
							pid: item.pid,
							first: item.firstname,
							last: item.lastname,
							course: item.tempCourseDuration
						};
					}));
				});
			},

			select : function(event, ui) {
				if (ui.item) {
					event.preventDefault();
					$("#showParticipant").empty();
					$("#showParticipant").append("<span class='participantTag'>"
											+ ui.item.label
											+ "&nbsp;<i class='icon icon-remove'></i></span>");
					$("#participantPid").val(ui.item.pid);
					<c:forEach items="${ui.item.course}" var="course">
						var course = "${course}";
						var courseSplit = course.split(":");
						var courseInfo = courseSplit[0].split("/");
						var courseCid = courseInfo[0];
						var courseName = courseInfo[1];
						var detailsSplit = courseSplit[1].split("/");
						var duration = detailsSplit[0];
						var semesterPrice = detailsSplit[1];
						var yearPrice = detailsSplit[2];
						
					</c:forEach>
					//$("#participantFirst").val(ui.item.first); notwendig?
					//$("#participantLast").val(ui.item.last);
					
					var defValue = $("#participantQuery").prop('defaultValue');
					$("#participantQuery").val(defValue);
					$("#participantQuery").blur();
					return false;
				}
			}
		});
	});
</script>