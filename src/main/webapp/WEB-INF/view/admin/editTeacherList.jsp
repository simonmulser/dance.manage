<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.joda.org/joda/time/tags" prefix="joda"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="dmtags"%>

<dmtags:base title="Lehrer" activesection="teacher">

	<dmtags:widget title="Lehrer" icon="icon-user">
		<spring:message code="help.teacher" />
		<form:form method="post" action="teacher/add" commandName="teacher"
			class="form-horizontal">

			<form:input path="pid" type="hidden" />
			<form:input path="enabled" type="hidden" />
			<form:input path="address.aid" type="hidden" />

			<div class="control-group">
				<form:label path="firstname" class="control-label">
					<spring:message code="label.firstname" />*
				</form:label>
				<div class="span6">
					<form:input path="firstname" />
				</div>
				<form:errors path="firstname" cssClass="error" />
			</div>
			<div class="control-group">
				<form:label path="lastname" class="control-label">
					<spring:message code="label.lastname" />*
				</form:label>
				<div class="span6">
					<form:input path="lastname" />
				</div>
				<form:errors path="lastname" cssClass="error" />
			</div>
			<div class="control-group">
				<form:label path="email" class="control-label">
					<spring:message code="label.email" />*
				</form:label>
				<div class="span6">
					<form:input path="email" />
				</div>
				<form:errors path="email" cssClass="error" />
			</div>
			<div class="control-group">
				<form:label path="telephone" class="control-label">
					<spring:message code="label.telephone" />*
				</form:label>
				<div class="span6">
					<form:input path="telephone" />
				</div>
				<form:errors path="telephone" cssClass="error" />
			</div>
			<div class="control-group">
				<form:label path="birthday" class="control-label">
					<spring:message code="label.birthday" />*
				</form:label>
				<div class="span6">
					<form:input path="birthday" />
				</div>
				<form:errors path="birthday" cssClass="error" />
			</div>
			<div class="control-group">
				<form:label path="svnr" class="control-label">
					<spring:message code="label.svnr" />*
				</form:label>
				<div class="span6">
					<form:input path="svnr" />
				</div>
				<form:errors path="svnr" cssClass="error" />
			</div>
			<div class="control-group">
				<form:label path="salary" class="control-label">
					<spring:message code="label.salary" />*
				</form:label>
				<div class="span6">
					<form:input path="salary" />
				</div>
				<form:errors path="salary" cssClass="error" />
			</div>
			<div class="control-group">
				<form:label path="address.street" class="control-label">
					<spring:message code="label.street" />*
				</form:label>
				<div class="span6">
					<form:input path="address.street" />
				</div>
				<form:errors path="address.street" cssClass="error" />
			</div>
			<div class="control-group">
				<form:label path="address.number" class="control-label">
					<spring:message code="label.number" />*
				</form:label>
				<div class="span6">
					<form:input path="address.number" />
				</div>
				<form:errors path="address.number" cssClass="error" />
			</div>
			<div class="control-group">
				<form:label path="address.stair" class="control-label">
					<spring:message code="label.stair" />
				</form:label>
				<div class="span6">
					<form:input path="address.stair" />
				</div>
				<form:errors path="address.stair" cssClass="error" />
			</div>
			<div class="control-group">
				<form:label path="address.door" class="control-label">
					<spring:message code="label.door" />
				</form:label>
				<div class="span6">
					<form:input path="address.door" />
				</div>
				<form:errors path="address.door" cssClass="error" />
			</div>
			<div class="control-group">
				<form:label path="address.zip" class="control-label">
					<spring:message code="label.zip" />*
				</form:label>
				<div class="span6">
					<form:input path="address.zip" />
				</div>
				<form:errors path="address.zip" cssClass="error" />
			</div>
			<div class="control-group">
				<form:label path="address.city" class="control-label">
					<spring:message code="label.city" />*
				</form:label>
				<div class="span6">
					<form:input path="address.city" />
				</div>
				<form:errors path="address.city" cssClass="error" />
			</div>
			
			<div id="find_keyword" class="control-group">
				<form:label path="tempStyles" class="control-label">
					<spring:message code="label.styles" />
				</form:label>
				<div class="ui-widget span6">
					<input id="stylesQuery" type="text" value="" /><i
						title="<spring:message code='help.searchStyle' />"
						class="inline-tooltip icon icon-question-sign"></i>
					<div id="showStyles">
						<c:forEach items="${teacher.styles}" var="sty">
							<span class="styleTag">${sty.name}&nbsp;<i
								id="${sty.sid}" class="icon icon-remove"></i></span>
						</c:forEach>
						<span id="selectedStyles"> </span>
					</div>
				</div>

				<form:input path="tempStyles" id="tempStyles" type="hidden" />
			</div>
			
			
			<div id="find_keyword" class="control-group">
				<form:label path="tempCourses" class="control-label">
					<spring:message code="label.courses" />
				</form:label>
				<div class="ui-widget span6">
					<input id="coursesQuery" type="text" value="" /><i
						title="<spring:message code='help.searchCourse' />"
						class="inline-tooltip icon icon-question-sign"></i>
					<div id="showCourses">
						<c:forEach items="${teacher.courses}" var="cou">
							<span class="courseTag">${cou.name}&nbsp;<i
								id="${cou.cid}" class="icon icon-remove"></i></span>
						</c:forEach>
						<span id="selectedCourses"> </span>
					</div>
				</div>

				<form:input path="tempCourses" id="tempCourses" type="hidden" />
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
		<dmtags:widget title="Übersicht" style="table" icon="icon-list">
			<table class="table table-striped table-bordered">
				<tr>
					<th><spring:message code="label.name" /></th>
					<th><spring:message code="label.email" /></th>
					<th><spring:message code="label.telephone" /></th>
					<th><spring:message code="label.birthday" /></th>
					<th><spring:message code="label.svnr" /></th>
					<th><spring:message code="label.salary" /></th>
					<th><spring:message code="label.street" /></th>
					<th><spring:message code="label.zip" /></th>
					<th><spring:message code="label.city" /></th>
					<th>&nbsp;</th>
				</tr>

				<c:forEach items="${teacherList}" var="teacher">

					<tr>
						<td>${teacher.lastname}&nbsp;${teacher.firstname}</td>
						<td>${teacher.email}</td>
						<td>${teacher.telephone}</td>
						<td><joda:format value="${teacher.birthday}"
								pattern="dd.MM.yyyy" /></td>
						<td>${teacher.svnr}</td>
						<td>&euro;${teacher.salary}</td>
						<c:choose>
							<c:when test="${!empty teacher.address}">
								<td>${teacher.address.street}&nbsp;
									${teacher.address.number}/${teacher.address.stair}/${teacher.address.door}</td>
								<td>${teacher.address.zip}</td>
								<td>${teacher.address.city}</td>
							</c:when>
							<c:otherwise>
								<td></td>
								<td></td>
								<td></td>
							</c:otherwise>
						</c:choose>
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
						<td><a href="teacher/edit/${teacher.pid}"><spring:message
									code="label.edit" /></a></td>
						<td><a href="teacher/delete/${teacher.pid}"><spring:message
									code="label.delete" /></a></td>
					</tr>

				</c:forEach>

			</table>
		</dmtags:widget>
	</c:if>
</dmtags:base>
<script type="text/javascript">
	$('i').tooltip();
	$(document)
			.ready(
					function() {
						$("#showStyles").on("click", "i", function() {
							var id = $(this).attr("id");
							$(this).parent().remove();
							$("#tempStyles").val(function(i, v) {
								return v.replace(id + ";", "-" + id + ";");
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
																							value : item.sid
																						};
																					}));
																});
											},

											//define select handler
											select : function(event, ui) {
												if (ui.item) {
													event.preventDefault();
													$("#selectedStyles")
															.append(
																	"<span class='styleTag'>"
																			+ ui.item.label
																			+ "&nbsp;<i id='" + ui.item.value + "' class='icon icon-remove'></i></span>");
													var input = $("#tempStyles");
													input.val(input.val()
															+ ui.item.value
															+ ";");
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
				$("#showCourses").on("click", "i", function() {
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
														"/dancemanage/admin/teacher/getCourses",
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
																					value : item.cid
																				};
																			}));
														});
									},

									//define select handler
									select : function(event, ui) {
										if (ui.item) {
											event.preventDefault();
											$("#selectedCourses")
													.append(
															"<span class='courseTag'>"
																	+ ui.item.label
																	+ "&nbsp;<i id='" + ui.item.value + "' class='icon icon-remove'></i></span>");
											var input = $("#tempCourses");
											input.val(input.val()
													+ ui.item.value
													+ ";");
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