<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.joda.org/joda/time/tags" prefix="joda"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="dmtags"%>
<%@taglib prefix="joda" uri="http://www.joda.org/joda/time/tags"%>

<style>
table {
	table-layout: fixed;
}

table th,table td {
	overflow: hidden;
}
</style>

<spring:message var="i18nTitle" code="nav.performances" />
<spring:message var="i18nCreatePlan" code="widget.createPlan" />
<spring:message var="i18nScheduleProposal"
	code="widget.scheduleproposal" />

<dmtags:base title="${i18nTitle}" activesection="performances">
	<dmtags:span width="12">


		<dmtags:widget title="${i18nCreatePlan}" icon="icon-camera">
			<form:form method="post" action="performance/build"
				commandName="performance" class="form-horizontal">
				<div>
					<p>Drücken Sie auf 'Erstellen', um einen Aufführungsplan
						generieren zu lassen.</p>
				</div>

				<div class="form-actions">
					<input type="submit" value="<spring:message code="label.create"/>"
						class="btn btn-primary" />
				</div>
			</form:form>
		</dmtags:widget>

		<c:if test="${!empty performanceList1}">
			<dmtags:widget title="${i18nScheduleProposal}" style="table"
				icon="icon-list">
				<br />
				<div class="tabbable">
					<ul class="nav nav-tabs">
						<li class="active"><a href="#hall1" data-toggle="tab">Saal
								1</a></li>
						<li><a href="#hall2" data-toggle="tab">Saal 2</a></li>
						<li><a href="#hall3" data-toggle="tab">Saal 3</a></li>
					</ul>

					<div class="tab-content">
						<div class="tab-pane active" id="hall1">
							<table class="table table-striped table-bordered">
								<thead>
									<tr>
										<th style="width: 50%"><spring:message
												code="label.coursename" /></th>
										<th style="width: 23%"><spring:message
												code="label.ageGroup" /></th>
										<th style="width: 23%"><spring:message
												code="label.courselevel" /></th>
										<th style="width: 4%"><i
											title="<spring:message code='help.restriction.noconsecutiveballett' />"
											class="inline-tooltip icon icon-question-sign"></i></th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${validatedList1}" var="validatedCourse">
										<tr>
											<td>${validatedCourse.course.name}</td>
											<td><spring:message code ="${validatedCourse.course.ageGroup.i18nIdentifier}" /></td>
											<td><spring:message code ="${validatedCourse.course.level.i18nIdentifier}" /></td>
											<c:choose>
												<c:when test="${validatedCourse.balletRestriktion}">
													<td><i class="icon-smile restriction-icon-bad"></i></td>
												</c:when>
												<c:otherwise>
													<td><i class="icon-smile restriction-icon-good"></i></td>
												</c:otherwise>
											</c:choose>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>

						<div class="tab-pane" id="hall2">
							<table class="table table-striped table-bordered">
								<thead>
									<tr>
										<th style="width: 50%"><spring:message
												code="label.coursename" /></th>
										<th style="width: 25%"><spring:message
												code="label.ageGroup" /></th>
										<th style="width: 25%"><spring:message
												code="label.courselevel" /></th>
										<th style="width: 4%"><i
											title="<spring:message code='help.restriction.noconsecutiveballett' />"
											class="inline-tooltip icon icon-question-sign"></i></th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${validatedList2}" var="validatedCourse">
										<tr>
											<td>${validatedCourse.course.name}</td>
											<td><spring:message code ="${validatedCourse.course.ageGroup.i18nIdentifier}" /></td>
											<td><spring:message code ="${validatedCourse.course.level.i18nIdentifier}" /></td>
											<c:choose>
												<c:when test="${validatedCourse.balletRestriktion}">
													<td><i class="icon-smile restriction-icon-bad"></i></td>
												</c:when>
												<c:otherwise>
													<td><i class="icon-smile restriction-icon-good"></i></td>
												</c:otherwise>
											</c:choose>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>

						<div class="tab-pane" id="hall3">
							<table class="table table-striped table-bordered">
								<thead>
									<tr>
										<th style="width: 50%"><spring:message
												code="label.coursename" /></th>
										<th style="width: 25%"><spring:message
												code="label.ageGroup" /></th>
										<th style="width: 25%"><spring:message
												code="label.courselevel" /></th>
										<th style="width: 4%"><i
											title="<spring:message code='help.restriction.noconsecutiveballett' />"
											class="inline-tooltip icon icon-question-sign"></i></th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${validatedList3}" var="validatedCourse">
										<tr>
											<td>${validatedCourse.course.name}</td>
											<td><spring:message code ="${validatedCourse.course.ageGroup.i18nIdentifier}" /></td>
											<td><spring:message code ="${validatedCourse.course.level.i18nIdentifier}" /></td>
											<c:choose>
												<c:when test="${validatedCourse.balletRestriktion}">
													<td><i class="icon-smile restriction-icon-bad"></i></td>
												</c:when>
												<c:otherwise>
													<td><i class="icon-smile restriction-icon-good"></i></td>
												</c:otherwise>
											</c:choose>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>

					</div>
				</div>
			</dmtags:widget>
		</c:if>
	</dmtags:span>
</dmtags:base>