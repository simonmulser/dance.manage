<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.joda.org/joda/time/tags" prefix="joda"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="dmtags"%>
<spring:message var="i18nTitle" code="nav.teachers" />
<dmtags:base title="${i18nTitle}" activesection="teachers">
	<dmtags:span width="12">

		<spring:message var="i18nWidgetTeachers" code="widget.teachers" />

		<dmtags:widget title="${i18nWidgetTeachers}" icon="icon-user" id="add"
			retractable="true" retractedPerDefault="true">
			<spring:message code="help.teacher" />
			<br />
			<spring:message code="help.required" />
			<form:form method="post" action="teacher/add" commandName="teacher"
				class="form-horizontal">

				<dmtags:personForm />

				<dmtags:teacherForm />

				<dmtags:addressForm />

				<div id="find_keyword" class="control-group">
					<form:label path="tempStyles" class="control-label">
						<spring:message code="label.styles" />
					</form:label>
					<div class="ui-widget span6">
						<input id="stylesQuery" type="text" value="" /><i
							title="<spring:message code='help.searchStyle' />"
							class="inline-tooltip icon icon-question-sign"></i>
						<div id="duplicateStyle" class="duplicateError">
							<spring:message code='error.duplicateStyle' />
						</div>
						<div id="showStyles">
							<span id="selectedStyles"> <c:forEach
									items="${teacher.styles}" var="sty">
									<span class="styleTag">${sty.name}&nbsp;<i
										id="${sty.sid}" class="icon icon-remove"></i></span>
								</c:forEach>
							</span>
						</div>
					</div>

					<form:input path="tempStyles" id="tempStyles" type="hidden" />
					<form:input path="tempStyleNames" id="tempStyleNames" type="hidden" />
				</div>


				<div id="find_keyword" class="control-group">
					<form:label path="tempCourses" class="control-label">
						<spring:message code="label.courses" />
					</form:label>
					<div class="ui-widget span6">
						<input id="coursesQuery" type="text" value="" /><i
							title="<spring:message code='help.searchCourse' />"
							class="inline-tooltip icon icon-question-sign"></i>
						<div id="duplicateCourse" class="duplicateError">
							<spring:message code='error.duplicateCourse' />
						</div>
						<div id="showCoursesss">
							<span id="selectedCourses"> <c:forEach
									items="${teacher.courses}" var="cou">
									${cou.name} ${cou.cid} ${cou.enabled}
									<c:if test="${cou.enabled }">
										<span class="courseTaggg">${cou.name}&nbsp;<i
											id="${cou.cid}" class="icon icon-remove"></i></span>
									</c:if>
								</c:forEach>
							</span>
						</div>
					</div>

					<form:input path="tempCourses" id="tempCourses" type="hidden" />
					<form:input path="tempCourseNames" id="tempCourseNames"
						type="hidden" />
				</div>

				<div class="control-group">
					<form:label path="comment" class="control-label">
						<spring:message code="label.comment" />
					</form:label>
					<div class="span6">
						<form:textarea path="comment" rows="5" />
					</div>
					<form:errors path="comment" cssClass="error" />
				</div>

				<div class="form-actions">
					<input type="submit" value="<spring:message code="label.save"/>"
						class="btn btn-primary" />
				</div>

			</form:form>
		</dmtags:widget>
		<c:if test="${!empty teacherList}">
			<spring:message var="i18nOverview" code="widget.overview" />
			<dmtags:widget title="${i18nOverview}" style="table" icon="icon-list"
				pdfLink="teacher/viewTeacherListPdf">
				<table class="table table-striped table-bordered">
					<tr>
						<th><spring:message code="label.name" /></th>
						<th><spring:message code="label.email" /></th>
						<th><spring:message code="label.telephone" /></th>
						<th><spring:message code="label.svnr" /></th>
						<th><spring:message code="label.salary" /></th>
						<th><spring:message code="label.engagementDate" /></th>
						<th><spring:message code="label.styles" /></th>
						<th><spring:message code="label.courses" /></th>
						<th>&nbsp;</th>
					</tr>

					<c:forEach items="${teacherList}" var="teacher">
						<tr>
							<td>${teacher.firstname}&nbsp;${teacher.lastname}</td>
							<td>${teacher.email}</td>
							<td>${teacher.telephone}</td>
							<td>${teacher.svnr}</td>
							<td><c:if test="${!empty teacher.salary }">&euro;</c:if>${teacher.salary}</td>
							<td><joda:format value="${teacher.engagementDate}"
									pattern="dd.MM.yyyy" /></td>
							<c:choose>
								<c:when test="${!empty teacher.styles}">
									<td><c:forEach items="${teacher.styles}" var="style"
											varStatus="loop">
											${style.name}
											${!loop.last ? ', ' : ''}
										</c:forEach></td>
								</c:when>
								<c:otherwise>
									<td></td>
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${!empty teacher.courses}">
									<td><c:forEach items="${teacher.courses}" var="cou"
											varStatus="loop">
											${cou.name}
											${!loop.last ? ', ' : ''}
										</c:forEach></td>
								</c:when>
								<c:otherwise>
									<td></td>
								</c:otherwise>
							</c:choose>
							<td><a href="teacher/edit/${teacher.pid}#add"><spring:message
										code="label.edit" /></a><br /> <a
								href="teacher/delete/${teacher.pid}" class="openDialog"
								id="${teacher.pid }"><spring:message code="label.delete" /></a>

								<div id="deleteId" style="display: none;"></div></td>
						</tr>
					</c:forEach>
				</table>
				<div id="dialog-confirm"
					title="<spring:message code="delete.title" />">
					<p>
						<span class="ui-icon ui-icon-alert"
							style="float: left; margin: 0 7px 20px 0;"></span>
						<spring:message code="delete.teacher" />
					</p>
				</div>
			</dmtags:widget>
		</c:if>
	</dmtags:span>
</dmtags:base>

<script src="<c:url value="/js/searchBoxAutoComplete.js" />"></script>
<script type="text/javascript">
	// this section is needed if the url contains an anchor hash to a widget which is retracted by default
	$(document).ready(
			function() {
				if (window.location.hash) {
					$(window.location.hash).children("[id*='widget-content-']")
							.removeAttr('style');
				}
			});

	$('i').tooltip();
	$("#duplicateStyle").hide();
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
				document.location = "teacher/delete/" + $("#deleteId").text();

				$(this).dialog("close");
			},
			Cancel : function() {
				$(this).dialog("close");
			}
		}
	});

	$(".datepicker").datepicker({
		showOn : "button",
		buttonImage : "/dancemanage/css/ui/images/calendar.gif",
		buttonImageOnly : true,
		dateFormat : dateformat,
		changeMonth : true,
		changeYear : true,
		yearRange : "1960:2000"
	});

	$(document)
			.on(
					"saveStyleCourse",
					function() { //damit Styles und Courses erhalten bleiben
						if ($("#tempStyleNames").val() != '') {
							$("#selectedStyles").empty();
							var styles = $("#tempStyleNames").val().split(";");
							var i;
							for (i = 0; i < styles.length - 1; i++) {
								var parts = styles[i].split(",");
								$("#selectedStyles")
										.append(
												"<span class='styleTag'>"
														+ parts[0]
														+ "&nbsp;<i id='" + parts[1] + "' class='icon icon-remove'></i><span style='display:none;'>"
														+ parts[0]
														+ "</span></span>");
							}

						}

						if ($("#tempCourseNames").val() != '') {
							$("#selectedCourses").empty();
							var courses = $("#tempCourseNames").val()
									.split(";");
							var i;
							for (i = 0; i < courses.length - 1; i++) {
								var parts = courses[i].split(",");
								$("#selectedCourses")
										.append(
												"<span class='courseTag'>"
														+ parts[0]
														+ "&nbsp;<i id='" + parts[1] + "' class='icon icon-remove'></i><span style='display:none;'>"
														+ parts[0]
														+ "</span></span>");
							}

						}
					});
	$(document).trigger("saveStyleCourse", [ "" ]);

	$(document)
			.ready(
					function() {
						$("#showStyles").on(
								"click",
								"i",
								function() {
									$("#duplicateStyle").hide();
									var id = $(this).attr("id");
									var styleName = $(this).siblings("span")
											.text();
									$(this).parent().remove();
									$("#tempStyles").val(
											function(i, v) {
												return v.replace(id + ";", "-"
														+ id + ";");
											}).val();
									$("#tempStyleNames").val(
											function(i, v) {
												return v.replace(styleName
														+ "," + id + ";", "");
											}).val();
								});

						//attach autocomplete
						$("#stylesQuery")
								.autocomplete(
										{
											minLength : 1,
											delay : 500,
											//define callback to format results
											source : function(request, response) {
												$
														.getJSON(
																"/dancemanage/admin/teacher/getStyles",
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
																							sid : item.sid
																						};
																					}));
																});
											},

											//define select handler
											select : function(event, ui) {
												if (ui.item) {
													event.preventDefault();
													var input = $("#tempStyles");
													var inputName = $("#tempStyleNames");
													var tempStyles = input
															.val();
													var itemId = ui.item.sid;
													if (tempStyles
															.indexOf(itemId) == -1) { //neues Element
														$("#duplicateStyle")
																.hide();
														$("#selectedStyles")
																.append(
																		"<span class='styleTag'>"
																				+ ui.item.label
																				+ "&nbsp;<i id='" + itemId + "' class='icon icon-remove'></i><span style='display:none;'>"
																				+ ui.item.label
																				+ "</span></span>");

														input.val(input.val()
																+ itemId + ";");
														inputName.val(inputName
																.val()
																+ ui.item.label
																+ ","
																+ itemId
																+ ";");

													} else if (tempStyles
															.indexOf("-"
																	+ itemId) != -1) { //bereits einmal gelöscht
														$("#duplicateStyle")
																.hide();
														$("#selectedStyles")
																.append(
																		"<span class='styleTag'>"
																				+ ui.item.label
																				+ "&nbsp;<i id='" + itemId + "' class='icon icon-remove'></i><span style='display:none;'>"
																				+ ui.item.label
																				+ "</span></span>");

														input
																.val(
																		function(
																				i,
																				v) {
																			return v
																					.replace(
																							"-"
																									+ itemId
																									+ ";",
																							itemId
																									+ ";");
																		})
																.val();
														inputName.val(inputName
																.val()
																+ ui.item.label
																+ ","
																+ itemId
																+ ";");

													} else { //bereits vorhanden
														$("#duplicateStyle")
																.show();
													}

													var defValue = $(
															"#stylesQuery")
															.prop(
																	'defaultValue');
													$("#stylesQuery").val(
															defValue);
													$("#stylesQuery").blur();
													return false;
												}
											}
										});
					});
	$(document)
			.ready(
					function() {
						$("#showCourses").on(
								"click",
								"i",
								function() {
									$("#duplicateCourse").hide();
									var id = $(this).attr("id");
									var courseName = $(this).siblings("span")
											.text();
									$(this).parent().remove();
									$("#tempCourses").val(
											function(i, v) {
												return v.replace(id + ";", "-"
														+ id + ";");
											}).val();
									$("#tempCourseNames").val(
											function(i, v) {
												return v.replace(courseName
														+ "," + id + ";", "");
											}).val();
								});

						$("#coursesQuery")
								.autocomplete(
										{
											minLength : 1,
											delay : 500,
											source : function(request, response) {
												$
														.getJSON(
																"/dancemanage/admin/teacher/getCourses",
																request,
																function(result) {
																	response($
																			.map(
																					result,
																					function(
																							item) {
																						return {
																							label : item.name,
																							value : item.name,
																							cid : item.cid
																						};
																					}));
																});
											},

											select : function(event, ui) {
												if (ui.item) {
													event.preventDefault();

													var input = $("#tempCourses");
													var inputName = $("#tempCourseNames");
													var tempCourses = input
															.val();
													var itemId = ui.item.cid;
													if (tempCourses
															.indexOf(itemId) == -1) { //neues Element
														$("#duplicateCourse")
																.hide();
														$("#selectedCourses")
																.append(
																		"<span class='courseTag'>"
																				+ ui.item.label
																				+ "&nbsp;<i id='" + itemId + "' class='icon icon-remove'></i><span style='display:none;'>"
																				+ ui.item.label
																				+ "</span></span>");

														input.val(input.val()
																+ itemId + ";");
														inputName.val(inputName
																.val()
																+ ui.item.label
																+ ","
																+ itemId
																+ ";");

													} else if (tempCourses
															.indexOf("-"
																	+ itemId) != -1) { //bereits einmal gelöscht
														$("#duplicateCourse")
																.hide();
														$("#selectedCourses")
																.append(
																		"<span class='courseTag'>"
																				+ ui.item.label
																				+ "&nbsp;<i id='" + itemId + "' class='icon icon-remove'></i><span style='display:none;'>"
																				+ ui.item.label
																				+ "</span></span>");

														input
																.val(
																		function(
																				i,
																				v) {
																			return v
																					.replace(
																							"-"
																									+ itemId
																									+ ";",
																							itemId
																									+ ";");
																		})
																.val();
														inputName.val(inputName
																.val()
																+ ui.item.label
																+ ","
																+ itemId
																+ ";");

													} else { //bereits vorhanden
														$("#duplicateCourse")
																.show();
													}

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