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
			<spring:message code="help.absence" />
			<c:choose>
				<c:when test="${enabledCourses.size() gt 0}">
					<div class="tabbable">
						<ul class="nav nav-tabs">
							<c:forEach items="${enabledCourses}" var="enabledCourse" varStatus="loop">
								<li <c:if test="${loop.first}">class="active"</c:if>><a href="#${enabledCourse.slug}" data-toggle="tab">${enabledCourse.name}</a></li>
							</c:forEach>
						</ul>

						<br />

					<div class="tab-content">
							<c:forEach items="${enabledCourses}" var="enabledCourse" varStatus="loopOuter">
								<div class="tab-pane <c:if test="${loopOuter.first}">active</c:if>" id="${enabledCourse.slug}">
									<c:choose>
										<c:when test="${enabledCourse.appointments.size() gt 0}">
											<div class="accordion" id="accordion2">
												<c:forEach items="${enabledCourse.appointments}" var="appointment" varStatus="loopInner">
													<div class="accordion-group">
														<div class="accordion-heading">
															<a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion2" href="#collapse${loopOuter.index}_${loopInner.index}"> ${appointment.number}.&nbsp;<spring:message
																	code="label.appointment" /> <c:if test="${not empty appointment.appointmentDate}">&nbsp;(${appointment.appointmentDate})</c:if>
															</a>
														</div>
													
													<div id="collapse${loopOuter.index}_${loopInner.index}" class="accordion-body collapse" style="height: 0px;">
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
										</div>
									</c:when>
									<c:otherwise>
										<spring:message code="teacher.noAppointments" />
									</c:otherwise>
								</c:choose>
							</div>
						</c:forEach>
					</div>
				</div>
			</c:when>
			<c:otherwise>
				<spring:message code="teacher.noCourses" />
			</c:otherwise>
		</c:choose>
	</dmtags:widget>
	</dmtags:span>
	<dmtags:span width="6">
		<spring:message var="i18nInfo" code="widget.courseInformation" />
		<dmtags:widget icon="icon-info-sign" title="${i18nInfo}" style="noTable">
			<c:choose>
				<c:when test="${enabledCourses.size() gt 0}">
					<div class="tabbable">
						<ul class="nav nav-tabs">
							<c:forEach items="${enabledCourses}" var="enabledCourse" varStatus="loop">
								<li <c:if test="${loop.first}">class="active"</c:if>><a href="#${enabledCourse.slug}Info" data-toggle="tab">${enabledCourse.name}</a></li>
							</c:forEach>
						</ul>

						<br />

						<div class="tab-content">
							<c:forEach items="${enabledCourses}" var="enabledCourse" varStatus="loop">
								<div class="tab-pane <c:if test="${loop.first}">active</c:if>" id="${enabledCourse.slug}Info">
									<table class="table table-striped table-bordered">
										<tbody>
											<tr>
												<td><spring:message code="label.name" /></td>
												<td>${enabledCourse.name}</td>
											</tr>
											<tr>
												<td><spring:message code="label.at" /></td>
												<td><spring:message code="${enabledCourse.weekday.i18nIdentifier}" />,&nbsp; <joda:format value="${enabledCourse.time}" pattern="HH:mm" /></td>
											</tr>
											<tr>
												<td><spring:message code="label.courselevel" /></td>
												<td><spring:message code="${enabledCourse.level.i18nIdentifier}" /></td>
											</tr>
										</tbody>
									</table>
								</div>
							</c:forEach>
						</div>
					</div>
				</c:when>
				<c:otherwise>
					<spring:message code="teacher.noCourses" />
				</c:otherwise>
			</c:choose>
		</dmtags:widget>
	</dmtags:span>
</dmtags:base>
