<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.joda.org/joda/time/tags" prefix="joda"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="dmtags"%>

<dmtags:base title="nav.courses" activesection="courses">

	<dmtags:widget icon="icon-calendar" title="widget.courses">
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
					<form:radiobuttons path="address.aid" items="${addressList}" itemValue="aid"  />
				</div>
				<form:errors path="address" cssClass="error" />
			</div>
			<div class="control-group">
				<form:label path="duration" class="control-label">
					<spring:message code="label.courseduration" />*
				</form:label>
				<div class="span6">
					<form:select path="duration">
						<form:options items="${CourseDuration}" itemValue="value" itemLabel="label" />
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
			</div>
			<div class="control-group">
				<form:label path="weekday" class="control-label">
					<spring:message code="label.courseweekday" />*
				</form:label>
				<div class="span6">
					<form:select path="weekday">
						<form:options items="${WeekDay}" itemValue="value" itemLabel="label" />
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
						<form:options items="${SpectatorAmount}" itemValue="value" itemLabel="label"/>
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
						<form:options items="${AgeGroup}" itemValue="value" itemLabel="label"/>
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
						<form:options items="${CourseLevel}" itemValue="value" itemLabel="label"/>
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
							<span class="styleTag">${course.style.name}&nbsp;<i class="icon icon-remove"></i></span>
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
							<span class="teacherTag">${course.teacher.firstname}&nbsp;${course.teacher.lastname}&nbsp;<i class="icon icon-remove"></i></span>
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
	
	<dmtags:widget title="widget.overview" style="table" icon="icon-list">
		<c:if test="${!empty courseList}">
			<table class="table table-striped table-bordered">
				<thead>
					<tr>
						<th><spring:message code="label.coursename" /></th>
						<th><spring:message code="label.courseduration" /></th>
						<th><spring:message code="label.semesterPrice" /></th>
						<th><spring:message code="label.yearPrice" /></th>
						<th><spring:message code="label.courseweekday" /></th>
						<th><spring:message code="label.coursetime" /></th>
						<th><spring:message code="label.estimatedSpectators" /></th>
						<th><spring:message code="label.ageGroup" /></th>
						<th><spring:message code="label.amountPerformances" /></th>
						<th><spring:message code="label.courselevel" /></th>
						<th><spring:message code="label.teacher" /></th>
						<th><spring:message code="label.style" /></th>
						<th>&nbsp;</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${courseList}" var="course">
						<tr>
							<td>${course.name}</td>
							<td><spring:message code="${course.duration.i18nIdentifier}" /></td>
							<td>${course.semesterPrice}</td>
							<td>${course.yearPrice}</td>
							<td><spring:message code="${course.weekday.i18nIdentifier}" /></td>
							<td><joda:format value="${course.time}"
									pattern="HH:mm" /></td>
							<td><spring:message code="${course.estimatedSpectators.i18nIdentifier}" /></td>
							<td><spring:message code="${course.ageGroup.i18nIdentifier}" /></td>
							<td>${course.amountPerformances}</td>
							<td><spring:message code="${course.level.i18nIdentifier}" /></td>
							<td>${course.teacher.firstname}&nbsp;${course.teacher.lastname}</td>
							<td>${course.style.name}</td>
							<td><a href="course/edit/${course.cid}"><spring:message
										code="label.edit" /></a> &nbsp; <a
								href="course/delete/${course.cid}" id="openDialog"><spring:message
										code="label.delete" /></a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<div id="dialog-confirm" title="<spring:message code="delete.title" />">
  <p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span><spring:message code="delete.course" /></p>
</div>
		</c:if>
	</dmtags:widget>

</dmtags:base>
<script type="text/javascript">
	$('i').tooltip();
	$( "#openDialog" ).click(function() {
	      $( "#dialog-confirm" ).dialog( "open" );
	      return false;
	    });
	$( "#dialog-confirm" ).dialog({
		  autoOpen:false,
	      resizable: false,
	      modal: true,
	      buttons: {
	        "OK": function() {
	        document.location = $("#openDialog").attr("href");
	        
	          $( this ).dialog( "close" );
	        },
	        Cancel: function() {
	          $( this ).dialog( "close" );
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
