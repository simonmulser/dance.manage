<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.joda.org/joda/time/tags" prefix="joda"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="dmtags"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<spring:message var="i18nTitle" code="nav.absence" />
<dmtags:base title="${i18nTitle}" activesection="absence">
	<dmtags:span width="6">
		<spring:message var="i18nAbsence" code="widget.absence" />
		<dmtags:widget title="${i18nAbsence}" style="noTable" icon="icon-edit">
			<spring:message code="admin.absence" /><br/><br/>
				<div id="find_keyword" class="control-group">
					<div class="span6">
						<spring:message code="label.course" />&nbsp;&nbsp;<input id="courseQuery" type="text" value="" />
					</div>
				</div>
				<c:if test="${actualCourse.cid != null}">
				<br/><br/>
				<c:choose>
					<c:when test="${actualCourse.appointments.size() gt 0}">
						<c:forEach items="${actualCourse.appointments}" var="appointment"
							varStatus="loop">
							<div class="accordion-group">
								<div class="accordion-heading">
									<a class="accordion-toggle" data-toggle="collapse"
										data-parent="#accordion2" href="#collapse${loop.index}">
										${appointment.number}.&nbsp;<spring:message
											code="label.appointment" /> <c:if
											test="${not empty appointment.appointmentDate}">&nbsp;(${appointment.appointmentDate})</c:if>
									</a>
								</div>
								<div id="collapse${loop.index}" class="accordion-body collapse"
									style="height: 0px;">
									<div class="accordion-inner">
										<c:choose>
											<c:when test="${appointment.absences.size() gt 0}">
												<table class="table table-striped table-bordered">
													<tr>
														<th><spring:message code="label.participant" /></th>
														<th><spring:message code="label.reason" /></th>
													</tr>
													<c:forEach items="${appointment.absences}" var="absence">
														<tr>
															<td>${absence.key.participant.firstname }&nbsp;${absence.key.participant.lastname }</td>
															<td>${absence.reason}</td>
														</tr>
													</c:forEach>
												</table>
											</c:when>
											<c:otherwise>
												<spring:message code="appointment.noAbsences" />
											</c:otherwise>
										</c:choose>
									</div>
								</div>
							</div>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<spring:message code="admin.noAppointments" />
					</c:otherwise>
				</c:choose>
			</c:if>
	</dmtags:widget>
	</dmtags:span>
	<dmtags:span width="6">
		<spring:message var="i18nInfo" code="widget.courseInformation" />
		<c:if test="${actualCourse.cid != null}">
		<dmtags:widget icon="icon-info-sign" title="${i18nInfo}" style="noTable">
				<table class="table table-striped table-bordered">
					<tbody>
						<tr>
							<td><spring:message code="label.name" /></td>
							<td>${actualCourse.name}</td>
						</tr>
						<tr>
							<td><spring:message code="label.at" /></td>
							<td><spring:message
									code="${actualCourse.weekday.i18nIdentifier}" />,&nbsp; <joda:format
									value="${actualCourse.time}" pattern="HH:mm" /></td>
						</tr>
						<tr>
							<td><spring:message code="label.courselevel" /></td>
							<td><spring:message
									code="${actualCourse.level.i18nIdentifier}" /></td>
						</tr>
						<tr>
							<td><spring:message code="label.teacher" /></td>
							<td>${actualCourse.teacher.firstname}&nbsp;${actualCourse.teacher.lastname}</td>
						</tr>
					</tbody>
				</table>
		</dmtags:widget>
		</c:if>
	</dmtags:span>
</dmtags:base>
<script type="text/javascript">
	$(document).ready(function() {
		$("#courseQuery").autocomplete({
			minLength : 1,
			delay : 500,
			source : function(request, response) {
				$.getJSON("/dancemanage/admin/absence/getCourses",request,function(result) {
					response($.map(result,function(item) {
							return {
								label : item.name,
								value : item.name,
								cid: item.cid
							};
					}));
				});
			},

			select : function(event, ui) {
				if (ui.item) {
					window.location.href = '/dancemanage/admin/absence/getDetailsForCourse/' + ui.item.cid;
				}
			}
		});
	});
</script>
