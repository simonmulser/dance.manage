<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.joda.org/joda/time/tags" prefix="joda"%>
<%@taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="dmtags"%>

<spring:message var="i18nNavTitle" code="nav.courses" />
<dmtags:base title="${i18nNavTitle}" activesection="courses">
	<dmtags:span width="12">

		<spring:message var="i18nWidgetTitle" code="widget.courses" />

		<dmtags:widget icon="icon-calendar" title="${i18nWidgetTitle}">
			<spring:message code="help.course" />
			<form:form method="post" action="course/add" commandName="course"
				class="form-horizontal">
				<form:input path="cid" type="hidden" />
				<form:input path="enabled" type="hidden" />
				<div class="control-group">
					<form:label path="name" class="control-label">
						<spring:message code="label.coursename" />*
				</form:label>
					<div class="span6">
						<form:input path="name" />
					</div>
					<form:errors path="name" cssClass="error" />
				</div>
				<div class="control-group">
					<form:label path="address" class="control-label">
						<spring:message code="label.coursePlace" />*
				</form:label>
					<div class="span6">
						<form:radiobuttons path="address.aid" items="${addressList}"
							itemValue="aid" />
					</div>
					<form:errors path="address" cssClass="error" />
				</div>
				<div class="control-group">
					<form:label path="duration" class="control-label">
						<spring:message code="label.courseduration" />*
				</form:label>
					<div class="span6">
						<form:select path="duration">
							<form:options items="${CourseDuration}" itemLabel="label" />
						</form:select>
					</div>
					<form:errors path="duration" cssClass="error" />
				</div>
				<div class="control-group">
					<form:label path="semesterPrice" class="control-label">
						<spring:message code="label.semesterPrice" />*
				</form:label>
					<div class="span6">
						<form:input path="semesterPrice" />
					</div>
					<form:errors path="semesterPrice" cssClass="error" />
				</div>
				<div class="control-group">
					<form:label path="yearPrice" class="control-label">
						<spring:message code="label.yearPrice" />*
				</form:label>
					<div class="span6">
						<form:input path="yearPrice" />
					</div>
					<form:errors path="yearPrice" cssClass="error" />
					<form:errors cssClass="error" />
				</div>
				<div class="control-group">
					<form:label path="weekday" class="control-label">
						<spring:message code="label.courseweekday" />*
				</form:label>
					<div class="span6">
						<form:select path="weekday">
							<form:options items="${WeekDay}" itemLabel="label" />
						</form:select>
					</div>
					<form:errors path="weekday" cssClass="error" />
				</div>
				<div class="control-group">
					<form:label path="time" class="control-label">
						<spring:message code="label.coursetime" />*
				</form:label>
					<div class="span6">
						<form:input path="time" />
					</div>
					<form:errors path="time" cssClass="error" />
				</div>
				<div class="control-group">
					<form:label path="estimatedSpectators" class="control-label">
						<spring:message code="label.estimatedSpectators" />
					</form:label>
					<div class="span6">
						<form:select path="estimatedSpectators">
							<form:options items="${SpectatorAmount}" itemLabel="label" />
						</form:select>
					</div>
					<form:errors path="estimatedSpectators" cssClass="error" />
				</div>
				<div class="control-group">
					<form:label path="ageGroup" class="control-label">
						<spring:message code="label.ageGroup" />
					</form:label>
					<div class="span6">
						<form:select path="ageGroup">
							<form:options items="${AgeGroup}" itemLabel="label" />
						</form:select>
					</div>
					<form:errors path="ageGroup" cssClass="error" />
				</div>
				<div class="control-group">
					<form:label path="amountPerformances" class="control-label">
						<spring:message code="label.amountPerformances" />
					</form:label>
					<div class="span6">
						<form:input path="amountPerformances" />
					</div>
					<form:errors path="amountPerformances" cssClass="error" />
				</div>
				<div class="control-group">
					<form:label path="level" class="control-label">
						<spring:message code="label.courselevel" />
					</form:label>
					<div class="span6">
						<form:select path="level">
							<form:options items="${CourseLevel}" itemLabel="label" />
						</form:select>
					</div>
					<form:errors path="level" cssClass="error" />
				</div>

				<div id="find_keyword" class="control-group">
					<form:label path="style.sid" class="control-label">
						<spring:message code="label.styles" />*
				</form:label>
					<div class="ui-widget span6">
						<input id="stylesQuery" type="text" value="" /><i
							title="<spring:message code='help.searchStyle' />"
							class="inline-tooltip icon icon-question-sign"></i>
						<div id="showStyles">
							<c:if test="${!empty course.style.sid }">
								<span class="styleTag">${course.style.name}&nbsp;<i
									class="icon icon-remove"></i></span>
							</c:if>
						</div>
						<form:input path="style.sid" id="styleSid" type="hidden" />
					</div>
					<form:errors path="style.sid" cssClass="error" />
				</div>

				<div id="find_keyword" class="control-group">
					<form:label path="teacher.pid" class="control-label">
						<spring:message code="label.teacher" />
					</form:label>
					<div class="ui-widget span6">
						<input id="teacherQuery" type="text" value="" /><i
							title="<spring:message code='help.searchTeacher' />"
							class="inline-tooltip icon icon-question-sign"></i>
						<div id="showTeacher">
							<c:if test="${!empty course.teacher.pid }">
								<span class="teacherTag">${course.teacher.firstname}&nbsp;${course.teacher.lastname}&nbsp;<i
									class="icon icon-remove"></i></span>
							</c:if>
						</div>
						<form:input path="teacher.pid" id="teacherPid" type="hidden" />
					</div>
				</div>

				<div class="form-actions">
					<input type="submit" value="<spring:message code="label.save"/>"
						class="btn btn-primary" />
					<button class="btn">
						<spring:message code="label.cancel" />
					</button>
				</div>
			</form:form>
		</dmtags:widget>

		<spring:message var="i18nOverview" code="widget.overview" />
		<dmtags:widget title="${i18nOverview}" style="table" icon="icon-list">
			<c:if test="${!empty courseList}">

				<display:table name="courseList" id="course"
					class="table table-striped table-bordered displaytag" pagesize="15"
					requestURI="/admin/course" defaultsort="1">
					<display:column sortable="true" titleKey="label.coursename">
						<c:out value="${course.name}" />
					</display:column>
					<display:column sortable="true" titleKey="label.semesterPrice">
					&euro; <c:out value="${course.semesterPrice}" />
					</display:column>
					<display:column sortable="true" titleKey="label.yearPrice">
					&euro; <c:out value="${course.yearPrice}" />
					</display:column>
					<display:column sortable="true" titleKey="label.courseweekday">
						<spring:message code="${course.weekday.i18nIdentifier}" />,&nbsp;
					<joda:format value="${course.time}" pattern="HH:mm" />
					</display:column>
					<display:column sortable="true" titleKey="label.courseduration">
						<spring:message code="${course.duration.i18nIdentifier}" />
					</display:column>
					<display:column sortable="true"
						titleKey="label.estimatedSpectators">
						<spring:message
							code="${course.estimatedSpectators.i18nIdentifier}" />
					</display:column>
					<display:column sortable="true" titleKey="label.amountPerformances">
					${course.amountPerformances}
				</display:column>
					<display:column sortable="true" titleKey="label.teacher">
					${course.teacher.firstname}
				</display:column>
					<display:column>
						<c:set var="cid" value="${course.cid}" />
						<a href="course/edit/${cid}"><spring:message code="label.edit" /></a>
						<br />
						<a href="course/delete/${cid}" class="openDialog"><spring:message
								code="label.delete" /></a>
					</display:column>
				</display:table>


				<div id="dialog-confirm"
					title="<spring:message code="delete.title" />">
					<p>
						<span class="ui-icon ui-icon-alert"
							style="float: left; margin: 0 7px 20px 0;"></span>
						<spring:message code="delete.course" />
					</p>
				</div>
			</c:if>
		</dmtags:widget>
	</dmtags:span>
</dmtags:base>


<script type="text/javascript">
	$('i').tooltip();
	$(".openDialog").click(function() {
		$("#dialog-confirm").dialog("open");
		return false;
	});
	$("#dialog-confirm").dialog({
		autoOpen : false,
		resizable : false,
		modal : true,
		buttons : {
			"OK" : function() {
				document.location = $(".openDialog").attr("href");

				$(this).dialog("close");
			},
			Cancel : function() {
				$(this).dialog("close");
			}
		}
	});
	$(document)
			.ready(
					function() {
						$("#showStyles").on("click", "i", function() {
							$(this).parent().remove();
							$("#styleSid").val(null);
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
																"/dancemanage/admin/course/getStyles",
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
																							value : item.sid
																						};
																					}));
																});
											},

											//define select handler
											select : function(event, ui) {
												if (ui.item) {
													event.preventDefault();
													$("#showStyles").empty();
													$("#showStyles")
															.append(
																	"<span class='styleTag'>"
																			+ ui.item.label
																			+ "&nbsp;<i class='icon icon-remove'></i></span>");

													var input = $("#styleSid");
													input.val(ui.item.value);
													//$("#tagQuery").value = $("#tagQuery").defaultValue
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
						$("#showTeacher").on("click", "i", function() {
							var id = $(this).attr("id");
							$(this).parent().remove();
							$("#teacherPid").val(null);
						});

						//attach autocomplete
						$("#teacherQuery")
								.autocomplete(
										{
											minLength : 1,
											delay : 500,
											//define callback to format results
											source : function(request, response) {
												$
														.getJSON(
																"/dancemanage/admin/course/getTeachers",
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
																							value : item.pid
																						};
																					}));
																});
											},

											//define select handler
											select : function(event, ui) {
												if (ui.item) {
													event.preventDefault();
													$("#showTeacher").empty();
													$("#showTeacher")
															.append(
																	"<span class='teacherTag'>"
																			+ ui.item.label
																			+ "&nbsp;<i class='icon icon-remove'></i></span>");
													var input = $("#teacherPid");
													input.val(ui.item.value);
													//$("#tagQuery").value = $("#tagQuery").defaultValue
													var defValue = $(
															"#teacherQuery")
															.prop(
																	'defaultValue');
													$("#teacherQuery").val(
															defValue);
													$("#teacherQuery").blur();
													return false;
												}
											}
										});
					});
</script>
