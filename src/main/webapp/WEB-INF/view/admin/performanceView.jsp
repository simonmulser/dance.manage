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

[draggable] {
  -moz-user-select: none;
  -khtml-user-select: none;
  -webkit-user-select: none;
  user-select: none;
  /* Required to make elements draggable in old WebKit */
  -khtml-user-drag: element;
  -webkit-user-drag: element;
}
</style>

<spring:message var="i18nTitle" code="nav.performances" />
<spring:message var="i18nCreatePlan" code="widget.createPlan" />
<spring:message var="i18nScheduleProposal"
	code="widget.scheduleproposal" />
	



<dmtags:base title="${i18nTitle}" activesection="performances">
	<dmtags:span width="12">

	<dmtags:span width="6">
		<dmtags:widget title="${i18nCreatePlan}" icon="icon-camera">
			<form:form method="post" action="performance/build"
				commandName="performance" class="form-horizontal">
				<div>
					<spring:message code="label.createPerformancePlan" />
				</div>
				<div style="margin-top: 20px">
					<label class="checkbox">
						<input type="checkbox" value="Ballet"	id="CheckboxBallet" name="CheckboxBallet" <c:if test="${balletRestriction}">checked</c:if>>
						<div style="float:left; margin-left: 4px"><i class="icon-female"></i></div><div style="margin-left: 25px"><spring:message code='help.restriction.noconsecutiveballett' /></div>
					</label>
					<label class="checkbox">
						<input type="checkbox" value="Break"	id="CheckboxTwoCourseBreak" name="CheckboxTwoCourseBreak" <c:if test="${twoBreaksRestriction}">checked</c:if>>
						<div style="float:left; margin-left: 4px"><i class="icon-coffee"></i></div><div style="margin-left: 25px"><spring:message code='help.restriction.twoCoursesBreak' /></div>
					</label>
					<label class="checkbox">
						<input type="checkbox" value="Advanced"	id="CheckboxAdvancedAtEnd" name="CheckboxAdvancedAtEnd" <c:if test="${advancedAtEndRestriction}">checked</c:if>>
						<div style="float:left; margin-left: 4px"><i class="icon-star"></i></div><div style="margin-left: 25px"><spring:message code='help.restriction.advancedAtEnd' /></div>
					</label>
					<label class="checkbox">
						<input type="checkbox" value="Spectators"	id="CheckboxBalancedSpectators" name="CheckboxBalancedSpectators" <c:if test="${balancedAmountOfSpectators}">checked</c:if>>
						<div style="float:left; margin-left: 4px"><i class="icon-group"></i></div><div style="margin-left: 25px"><spring:message code='help.restriction.balancedSpectators' /></div>
					</label>
					
				</div>
				<div style="margin-top: 20px">
					<input type="submit" value="<spring:message code="label.create"/>"
						class="btn btn-primary" />
				</div>
			</form:form>
		</dmtags:widget>
	</dmtags:span>

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
										<th style="width: 60%"><spring:message
												code="label.coursename" /></th>
										<th style="width: 25%"><spring:message
												code="label.courselevel" /></th>
										<th style="width: 4%"><i
											title="<spring:message code='help.restriction.noconsecutiveballett' />"
											class="inline-tooltip icon icon-female"></i></th>
										<th style="width: 4%"><i
											title="<spring:message code='help.restriction.twoCoursesBreak' />"
											class="inline-tooltip icon icon-coffee"></i></th>
										<th style="width: 4%"><i
											title="<spring:message code='help.restriction.advancedAtEnd' />"
											class="inline-tooltip icon icon-star"></i></th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${performanceList1}" var="validatedCourse">
										<tr>
											<c:choose>
												<c:when test="${validatedCourse.dummyCourse}">
													<td>-</td>
													<td>-</td>
													<td>-</td>
													<td>-</td>
													<td>-</td>
												</c:when>
												<c:otherwise>
													<td>${validatedCourse.name}</td>
													<td><spring:message code ="${validatedCourse.level.i18nIdentifier}" /></td>
													<td>
														<c:choose>
															<c:when test="${validatedCourse.balletRestriction}">
																<i class="icon-frown restriction-icon-bad"></i>
															</c:when>
															<c:otherwise>
																<i class="icon-smile restriction-icon-good"></i>
															</c:otherwise>
														</c:choose>
													</td>
													<td>
														<c:choose>
															<c:when test="${validatedCourse.twoBreaksRestriction}">
																<i class="icon-frown restriction-icon-bad"></i>
															</c:when>
															<c:otherwise>
																<i class="icon-smile restriction-icon-good"></i>
															</c:otherwise>
														</c:choose>
													</td>
													<td>
														<c:choose>
															<c:when test="${validatedCourse.advancedAtEndRestriction}">
																<i class="icon-frown restriction-icon-bad"></i>
															</c:when>
															<c:otherwise>
																<i class="icon-smile restriction-icon-good"></i>
															</c:otherwise>
														</c:choose>
													</td>
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
										<th style="width: 60%"><spring:message
												code="label.coursename" /></th>
										<th style="width: 25%"><spring:message
												code="label.courselevel" /></th>
										<th style="width: 4%"><i
											title="<spring:message code='help.restriction.noconsecutiveballett' />"
											class="inline-tooltip icon icon-female"></i></th>
										<th style="width: 4%"><i
											title="<spring:message code='help.restriction.twoCoursesBreak' />"
											class="inline-tooltip icon icon-coffee"></i></th>
										<th style="width: 4%"><i
											title="<spring:message code='help.restriction.advancedAtEnd' />"
											class="inline-tooltip icon icon-star"></i></th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${performanceList2}" var="validatedCourse">
										<tr>
											<c:choose>
												<c:when test="${validatedCourse.dummyCourse}">
													<td>-</td>
													<td>-</td>
													<td>-</td>
													<td>-</td>
													<td>-</td>
												</c:when>
												<c:otherwise>
													<td>${validatedCourse.name}</td>
													<td><spring:message code ="${validatedCourse.level.i18nIdentifier}" /></td>
													<td>
														<c:choose>
															<c:when test="${validatedCourse.balletRestriction}">
																<i class="icon-frown restriction-icon-bad"></i>
															</c:when>
															<c:otherwise>
																<i class="icon-smile restriction-icon-good"></i>
															</c:otherwise>
														</c:choose>
													</td>
													<td>
														<c:choose>
															<c:when test="${validatedCourse.twoBreaksRestriction}">
																<i class="icon-frown restriction-icon-bad"></i>
															</c:when>
															<c:otherwise>
																<i class="icon-smile restriction-icon-good"></i>
															</c:otherwise>
														</c:choose>
													</td>
													<td>
														<c:choose>
															<c:when test="${validatedCourse.advancedAtEndRestriction}">
																<i class="icon-frown restriction-icon-bad"></i>
															</c:when>
															<c:otherwise>
																<i class="icon-smile restriction-icon-good"></i>
															</c:otherwise>
														</c:choose>
													</td>
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
										<th style="width: 60%"><spring:message
												code="label.coursename" /></th>
										<th style="width: 25%"><spring:message
												code="label.courselevel" /></th>
										<th style="width: 4%"><i
											title="<spring:message code='help.restriction.noconsecutiveballett' />"
											class="inline-tooltip icon icon-female"></i></th>
										<th style="width: 4%"><i
											title="<spring:message code='help.restriction.twoCoursesBreak' />"
											class="inline-tooltip icon icon-coffee"></i></th>
										<th style="width: 4%"><i
											title="<spring:message code='help.restriction.advancedAtEnd' />"
											class="inline-tooltip icon icon-star"></i></th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${performanceList3}" var="validatedCourse">
										<tr>
											<c:choose>
												<c:when test="${validatedCourse.dummyCourse}">
													<td>-</td>
													<td>-</td>
													<td>-</td>
													<td>-</td>
													<td>-</td>
												</c:when>
												<c:otherwise>
													<td>${validatedCourse.name}</td>
													<td><spring:message code ="${validatedCourse.level.i18nIdentifier}" /></td>
													<td>
														<c:choose>
															<c:when test="${validatedCourse.balletRestriction}">
																<i class="icon-frown restriction-icon-bad"></i>
															</c:when>
															<c:otherwise>
																<i class="icon-smile restriction-icon-good"></i>
															</c:otherwise>
														</c:choose>
													</td>
													<td>
														<c:choose>
															<c:when test="${validatedCourse.twoBreaksRestriction}">
																<i class="icon-frown restriction-icon-bad"></i>
															</c:when>
															<c:otherwise>
																<i class="icon-smile restriction-icon-good"></i>
															</c:otherwise>
														</c:choose>
													</td>
													<td>
														<c:choose>
															<c:when test="${validatedCourse.advancedAtEndRestriction}">
																<i class="icon-frown restriction-icon-bad"></i>
															</c:when>
															<c:otherwise>
																<i class="icon-smile restriction-icon-good"></i>
															</c:otherwise>
														</c:choose>
													</td>
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