<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.joda.org/joda/time/tags" prefix="joda"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="dmtags"%>

<dmtags:base title="nav.courseParticipants" activesection="participants">

	<dmtags:widget title="widget.courseParticipants" icon="icon-user">
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
		<dmtags:widget title="widget.overview" style="table" icon="icon-list">
			<table class="table table-striped table-bordered">
				<thead>
					<tr>
						<th><spring:message code="label.name" /></th>
						<th><spring:message code="label.email" /></th>
						<th><spring:message code="label.telephone" /></th>
						<th><spring:message code="label.birthday" /></th>
						<th><spring:message code="label.contactPerson" /></th>
						<th><spring:message code="label.emergencyNumber" /></th>
						<th><spring:message code="label.street" /></th>
						<th><spring:message code="label.zip" /></th>
						<th><spring:message code="label.city" /></th>
						<th><spring:message code="label.siblings" /></th>
						<th><spring:message code="label.courses" /></th>
						<th>&nbsp;</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${participantList}" var="emp">
						<tr>
							<td>${emp.firstname}&nbsp;${emp.lastname}</td>
							<td>${emp.email}</td>
							<td>${emp.telephone}</td>
							<td><joda:format value="${emp.birthday}"
									pattern="dd.MM.yyyy" /></td>
							<td>${emp.contactPerson}</td>
							<td>${emp.emergencyNumber}</td>
							<c:choose>
								<c:when test="${!empty emp.address}">
									<td>${emp.address.street}&nbsp;
										${emp.address.number}/${emp.address.stair}/${emp.address.door}</td>
									<td>${emp.address.zip}</td>
									<td>${emp.address.city}</td>
								</c:when>
								<c:otherwise>
									<td></td>
									<td></td>
									<td></td>
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${!empty emp.siblings}">
									<td><c:forEach items="${emp.siblings}" var="sib"
											varStatus="loop">
											${sib.firstname}
											${!loop.last ? ', ' : ''}
										</c:forEach></td>
								</c:when>
								<c:otherwise>
									<td></td>
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${!empty emp.courseParticipants}">
									<td><c:forEach items="${emp.courseParticipants}"
											var="courseParticipant" varStatus="loop">
											<c:if test="${courseParticipant.enabled}">
											${courseParticipant.key.course.name}
											</c:if>
											${!loop.last ? ', ' : ''}
											
										</c:forEach></td>
								</c:when>
								<c:otherwise>
									<td></td>
								</c:otherwise>
							</c:choose>
							<td><a href="participant/edit/${emp.pid}"><spring:message
										code="label.edit" /></a> &nbsp; <a
								href="participant/delete/${emp.pid}" id="openDialog"><spring:message
										code="label.delete" /></a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<div id="dialog-confirm" title="<spring:message code="delete.title" />">
  <p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span><spring:message code="delete.participant" /></p>
</div>
		</dmtags:widget>
	</c:if>

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
	$( "#datepicker" ).datepicker({
		showOn: "button",
	    buttonImage: "/dancemanage/css/ui/images/calendar.gif",
	    buttonImageOnly: true,
	    dateFormat: "dd.mm.yy"
	});
	$(document)
			.ready(
					function() {
						$("#showSiblings").on("click", "i", function() {
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
																							value : item.pid
																						};
																					}));
																});
											},

											//define select handler
											select : function(event, ui) {
												if (ui.item) {
													event.preventDefault();
													$("#selectedSiblings")
															.append(
																	"<span class='siblingTag'>"
																			+ ui.item.label
																			+ "&nbsp;<i id='" + ui.item.value + "' class='icon icon-remove'></i></span>");
													var input = $("#tempSiblings");
													input.val(input.val()
															+ ui.item.value
															+ ";");
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
