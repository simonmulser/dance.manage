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
	<spring:message var="i18nAbsence" code="widget.absence" />
	<dmtags:widget title="${i18nMyCourses}" style="noTable" icon="icon-edit">
		<c:choose>
			<c:when test="${user.courseParticipants.size() gt 0}">
				<div class="tabbable">
					<ul class="nav nav-tabs">
						<c:forEach items="${user.courseParticipants}" var="courseParticipant" varStatus="loop">
							<li <c:if test="${loop.first}">class="active"</c:if>><a href="#${courseParticipant.course.slug}" data-toggle="tab">${courseParticipant.course.name}</a></li>
						</c:forEach>
					</ul>

					<br />

					<div class="tab-content">
						<c:forEach items="${user.courseParticipants}" var="courseParticipant" varStatus="loop">
							<div class="tab-pane <c:if test="${loop.first}">active</c:if>" id="${courseParticipant.course.slug}">
								<c:choose>
									<c:when test="${courseParticipant.course.appointments.size() gt 0}">
										<div class="accordion" id="accordion2">
											<c:forEach items="${courseParticipant.course.appointments}" var="appointment" varStatus="loop">
												<c:set var="isAbsent" value="false" />
												<div class="accordion-group">
													<div class="accordion-heading">
														<a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion2" href="#collapse${loop.index}"> ${appointment.number}.&nbsp;<spring:message code="label.appointment" /> <c:if
																test="${not empty appointment.appointmentDate}">&nbsp;(${appointment.appointmentDate})</c:if>
														</a>
													</div>

													<!-- searching the absence object from the user for the appointment -->
													<c:forEach items="${appointment.absences}" var="absence">
														<c:if test="${absence.key.participant.pid == user.pid && absence.enabled == true}">
															<c:set var="isAbsent" value="true" />
															<c:set var="absenceForAppointment" value="${absence}" />
														</c:if>
													</c:forEach>

													<div id="collapse${loop.index}" class="accordion-body collapse" style="height: 0px;">
														<div class="accordion-inner">
															<c:choose>
																<c:when test="${isAbsent}">
																	<spring:message code="user.absence" />
																	<c:if test="${not empty absenceForAppointment.reason}">&nbsp;(${absenceForAppointment.reason})</c:if>.
																	<br />
																	<br />
																	<spring:message code="user.changeReason" />
																	<form method="post" action="<c:url value='/participant/absence/update/${courseParticipant.course.slug}' />" class="form-horizontal">
																		<input id="appointmentId" name="appointmentId" type="hidden" value="${appointment.apid}">
																		<div class="control-group">
																			<label for="reason" class="control-label"> <spring:message code="label.reason" />:
																			</label>
																			<div class="controls">
																				<input name="reason" type="text" value="${absenceForAppointment.reason}" />
																			</div>
																		</div>
																		<div class="form-actions">
																			<input type="submit" value="<spring:message code="label.report" />" class="btn btn-primary">
																		</div>
																	</form>
																	<spring:message code="user.presentQuestion" />
																	<form method="post" action="<c:url value='/participant/absence/update/${courseParticipant.course.slug}' />" class="form-horizontal">
																		<input id="appointmentId" name="appointmentId" type="hidden" value="${appointment.apid}"> <input id="enabled" name="enabled" type="hidden" value="false">
																		<div class="form-actions">
																			<input type="submit" value="<spring:message code="label.report" />" class="btn btn-primary">
																		</div>
																	</form>
																</c:when>
																<c:otherwise>
																	<spring:message code="user.absentQuestion" />
																	<br />
																	<br />
																	<form method="post" action="<c:url value='/participant/absence/save/${courseParticipant.course.slug}' />" class="form-horizontal">
																		<input id="appointmentId" name="appointmentId" type="hidden" value="${appointment.apid}">
																		<div class="control-group">
																			<label for="reason" class="control-label"> <spring:message code="label.reason" />:
																			</label>
																			<div class="controls">
																				<input name="reason" type="text" />
																			</div>
																		</div>
																		<div class="form-actions">
																			<input type="submit" value="<spring:message code="label.report" />" class="btn btn-primary">
																		</div>
																	</form>
																</c:otherwise>
															</c:choose>
														</div>
													</div>
												</div>
											</c:forEach>
										</div>
									</c:when>
									<c:otherwise>
										<spring:message code="user.noAppointments" />
									</c:otherwise>
								</c:choose>
							</div>
						</c:forEach>
					</div>
				</div>
			</c:when>
			<c:otherwise>
				<spring:message code="participant.noCourses" />
			</c:otherwise>
		</c:choose>

	</dmtags:widget>
</dmtags:base>
