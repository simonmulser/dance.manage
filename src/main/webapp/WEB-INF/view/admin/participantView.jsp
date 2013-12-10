<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.joda.org/joda/time/tags" prefix="joda"%>
<%@taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="dmtags"%>

<dmtags:base title="${i18nNavCourseParticipants}"
	activesection="participants">
	<dmtags:span width="12">

		<spring:message var="i18nWidgetCourseParticipants"
			code="widget.courseParticipants" />
		<spring:message var="i18nNavCourseParticipants"
			code="nav.courseParticipants" />

		<dmtags:widget title="${i18nWidgetCourseParticipants}"
			icon="icon-user">
			<spring:message code="help.participant" />
			<form:form method="post" action="participant/add"
				commandName="participant" class="form-horizontal">

				<dmtags:personForm />

				<dmtags:participantForm />

				<dmtags:addressForm />

				
				<div id="find_keyword" class="control-group">
					<form:label path="tempSiblings" class="control-label">
						<spring:message code="label.siblings" />
					</form:label>
					<div class="ui-widget span6">
						
						<input id="siblingsQuery" type="text" value="" /><i
							title="<spring:message code='help.searchSibling' />"
							class="inline-tooltip icon icon-question-sign"></i>
							<div id="duplicateSibling" class="duplicateError"><spring:message code='error.duplicateSibling' /></div>
						<div id="showSiblings">
							<c:forEach items="${participant.siblings}" var="sib">
								<span class="siblingTag">${sib.firstname}&nbsp;${sib.lastname}&nbsp;<i
									id="${sib.pid}" class="icon icon-remove"></i></span>
							</c:forEach>
							<span id="selectedSiblings"> </span>
						</div>
					</div>

					<form:input path="tempSiblings" id="tempSiblings" type="hidden" />
				</div>

				<div id="find_keyword" class="control-group">
					<form:label path="tempCourses" class="control-label">
						<spring:message code="label.courses" />
					</form:label>
					<div class="ui-widget span6">
						<input id="coursesQuery" type="text" value="" /><i
							title="<spring:message code='help.searchCourse' />"
							class="inline-tooltip icon icon-question-sign"></i>
							<div id="duplicateCourse" class="duplicateError"><spring:message code='error.duplicateCourse' /></div>
						<div id="showCourses">
							<c:forEach items="${participant.courseParticipants}"
								var="courseParticipant">
								<c:if test="${courseParticipant.enabled}">
									<span class="courseTag">${courseParticipant.key.course.name}&nbsp;<i
										id="${courseParticipant.key.course.cid}"
										class="icon icon-remove"></i></span>
								</c:if>
							</c:forEach>
							<span id="selectedCourses"> </span>
						</div>
					</div>

					<form:input path="tempCourses" id="tempCourses" type="hidden" />
				</div>


				<div class="form-actions">
					<input type="submit" value="<spring:message code="label.save"/>"
						class="btn btn-primary" />
				</div>

			</form:form>
		</dmtags:widget>

		<c:if test="${!empty participantList}">
			<spring:message var="i18nOverview" code="widget.overview" />
			<dmtags:widget title="${i18nOverview}" style="table" icon="icon-list">
				<display:table name="participantList" id="row"
					class="table table-striped table-bordered displaytag" pagesize="15"
					requestURI="/admin/participant" defaultsort="1">
					<display:column sortable="true" titleKey="label.name"
						class="colName">
						<c:out value="${row.firstname} ${row.lastname}" />
					</display:column>
					<display:column sortable="true" titleKey="label.email">
						<c:out value="${row.email}" />
					</display:column>
					<display:column sortable="true" titleKey="label.telephone">
						<c:out value="${row.telephone}" />
					</display:column>
					<display:column sortable="true" titleKey="label.birthday"
						class="colBirthday">
						<joda:format value="${row.birthday}" pattern="dd.MM.yyyy" />
					</display:column>
					<display:column sortable="true" titleKey="label.contactPerson"
						class="colContact">
						<c:out value="${row.contactPerson}, ${row.emergencyNumber}" />
					</display:column>
					<display:column sortable="true" titleKey="label.street">
						<c:out
							value="${row.address.street} ${row.address.number}" />
						<c:if test="${!empty row.address.stair}">
							<c:out value="/${row.address.stair }" />
						</c:if>	
						<c:if test="${!empty row.address.door}">
							<c:out value="/${row.address.door }" />
						</c:if>
					</display:column>
					<display:column sortable="true" titleKey="label.zip">
						<c:out value="${row.address.zip}" />
					</display:column>
					<display:column sortable="true" titleKey="label.city"
						class="colCity">
						<c:out value="${row.address.city}" />
					</display:column>
					<display:column titleKey="label.siblings">
						<c:if test="${!empty row.siblings}">
							<c:forEach items="${row.siblings}" var="sib" varStatus="loop">
								${sib.firstname}
								${!loop.last ? ', ' : ''}
						</c:forEach>
						</c:if>
					</display:column>
					<display:column titleKey="label.courses">
						<c:if test="${!empty row.courseParticipants}">
							<c:forEach items="${row.courseParticipants}"
								var="courseParticipant" varStatus="loop">
								<c:if test="${courseParticipant.enabled}">
								${courseParticipant.key.course.name}
								</c:if>
								${!loop.last ? ', ' : ''}
						</c:forEach>
						</c:if>
					</display:column>
					<display:column>
						<c:set var="pid" value="${row.pid}" />
						<a href="participant/edit/${pid}"><spring:message
								code="label.edit" /></a>
						<br />
						<a href="participant/delete/${pid}" class="openDialog" id="${pid }"><spring:message
								code="label.delete" /></a>
						<div id="deleteId" style="display:none;"></div>			
					</display:column>
				</display:table>
				<div id="dialog-confirm"
					title="<spring:message code="delete.title" />">
					<p>
						<span class="ui-icon ui-icon-alert"
							style="float: left; margin: 0 7px 20px 0;"></span>
						<spring:message code="delete.participant" />
					</p>
				</div>
			</dmtags:widget>
		</c:if>
	</dmtags:span>
</dmtags:base>
<script type="text/javascript">
	$('i').tooltip();
	$("#duplicateSibling").hide();
	$("#duplicateCourse").hide();
	$(".openDialog").click(function() {
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
				document.location = "participant/delete/"+$("#deleteId").text();

				$(this).dialog("close");
			},
			Cancel : function() {
				$(this).dialog("close");
			}
		}
	});
	$("#datepicker").datepicker({
		showOn : "button",
		buttonImage : "/dancemanage/css/ui/images/calendar.gif",
		buttonImageOnly : true,
		dateFormat : "dd.mm.yy"
	});
	$(document)
			.ready(
					function() {
						$("#showSiblings").on("click", "i", function() {
							$("#duplicateSibling").hide();
							var id = $(this).attr("id");
							$(this).parent().remove();
							$("#tempSiblings").val(function(i, v) {
								return v.replace(id + ";", "-" + id + ";");
							}).val();
						});

						//attach autocomplete
						$("#siblingsQuery")
								.autocomplete(
										{
											minLength : 1,
											delay : 500,
											//define callback to format results
											source : function(request, response) {
												$
														.getJSON(
																"/dancemanage/admin/participant/getSiblings",
																request,
																function(result) {
																	response($
																			.map(
																					result,
																					function(
																							item) {
																						return {
																							// following property gets displayed in drop down
																							label : item.firstname
																									+ " "
																									+ item.lastname,
																							// following property gets entered in the textbox
																							value : item.firstname
																							+ " "
																							+ item.lastname,
																							pid : item.pid
																						};
																					}));
																});
											},

											//define select handler
											select : function(event, ui) {
												if (ui.item) {
													event.preventDefault();
													var input = $("#tempSiblings");
													var tempSiblings = input.val();
													var itemId = ui.item.pid;
													if(tempSiblings.indexOf(itemId) == -1){ //neues Element
														$("#duplicateSibling").hide();
														$("#selectedSiblings")
														.append(
																"<span class='siblingTag'>"
																		+ ui.item.label
																		+ "&nbsp;<i id='" + itemId + "' class='icon icon-remove'></i></span>");
												
														input.val(input.val() + itemId + ";");
														
													}else if(tempSiblings.indexOf("-"+itemId) != -1){ //bereits einmal gelöscht
														$("#duplicateSibling").hide();
														$("#selectedSiblings")
														.append(
																"<span class='siblingTag'>"
																		+ ui.item.label
																		+ "&nbsp;<i id='" + itemId + "' class='icon icon-remove'></i></span>");
														
														input.val(function(i, v) {
															return v.replace("-"+itemId + ";", itemId + ";");
														}).val();

													}else{ //bereits vorhanden
														$("#duplicateSibling").show();
													}
													
													//$("#tagQuery").value = $("#tagQuery").defaultValue
													var defValue = $(
															"#siblingsQuery")
															.prop(
																	'defaultValue');
													$("#siblingsQuery").val(
															defValue);
													$("#siblingsQuery").blur();
													return false;
												}
											}
										});
					});
	$(document)
			.ready(
					function() {
						$("#showCourses").on("click", "i", function() {
							$("#duplicateCourse").hide();
							var id = $(this).attr("id");
							$(this).parent().remove();
							$("#tempCourses").val(function(i, v) {
								return v.replace(id + ";", "-" + id + ";");
							}).val();
						});

						//attach autocomplete
						$("#coursesQuery")
								.autocomplete(
										{
											minLength : 1,
											delay : 500,
											//define callback to format results
											source : function(request, response) {
												$
														.getJSON(
																"/dancemanage/admin/participant/getCourses",
																request,
																function(result) {
																	response($
																			.map(
																					result,
																					function(
																							item) {
																						return {
																							// following property gets displayed in drop down
																							label : item.name,
																							// following property gets entered in the textbox
																							value : item.name,
																							cid: item.cid
																						};
																					}));
																});
											},

											//define select handler
											select : function(event, ui) {
												if (ui.item) {
													event.preventDefault();
													
													var input = $("#tempCourses");
													var tempCourses = input.val();
													var itemId = ui.item.cid;
													if(tempCourses.indexOf(itemId) == -1){ //neues Element
														$("#duplicateCourse").hide();
														$("#selectedCourses")
														.append(
																"<span class='courseTag'>"
																		+ ui.item.label
																		+ "&nbsp;<i id='" + itemId + "' class='icon icon-remove'></i></span>");
												
														input.val(input.val() + itemId + ";");
														
													}else if(tempCourses.indexOf("-"+itemId) != -1){ //bereits einmal gelöscht
														$("#duplicateCourse").hide();
														$("#selectedCourses")
														.append(
																"<span class='courseTag'>"
																		+ ui.item.label
																		+ "&nbsp;<i id='" + itemId + "' class='icon icon-remove'></i></span>");
														
														input.val(function(i, v) {
															return v.replace("-"+itemId + ";", itemId + ";");
														}).val();

													}else{ //bereits vorhanden
														$("#duplicateCourse").show();
													}
					
													//$("#tagQuery").value = $("#tagQuery").defaultValue
													var defValue = $(
															"#coursesQuery")
															.prop(
																	'defaultValue');
													$("#coursesQuery").val(
															defValue);
													$("#coursesQuery").blur();
													return false;
												}
											}
										});
					});
</script>
