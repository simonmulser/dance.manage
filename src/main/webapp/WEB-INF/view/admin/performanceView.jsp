<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.joda.org/joda/time/tags" prefix="joda"%>
<%@taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="dmtags"%>
<%@taglib prefix="joda" uri="http://www.joda.org/joda/time/tags"%>

<spring:message var="i18nTitle" code="nav.performances" />
<spring:message var="i18nCreatePlan" code="widget.createPlan" />
<spring:message var="i18nShowPlan" code="widget.showPlan" />
<spring:message var="i18nScheduleProposal"
	code="widget.scheduleproposal" />


<dmtags:base title="${i18nTitle}" activesection="performances">


	<dmtags:span width="6">
		<dmtags:widget title="${i18nCreatePlan}" icon="icon-camera">
			<form:form method="post" action="performance/build"
				commandName="performancePlan" class="form-horizontal">
				<div>
					<spring:message code="label.createPerformancePlan" />
				</div>

				<div style="margin-top: 20px">
					<label class="checkbox"> <input type="checkbox"
						value="Ballet" id="CheckboxBallet" name="CheckboxBallet"
						<c:if test="${balletRestriction}">checked</c:if>>
						<div style="float: left; margin-left: 4px">
							<i class="icon-female"></i>
						</div>
						<div style="margin-left: 25px">
							<spring:message code='help.restriction.noconsecutiveballett' />
						</div>
					</label> <label class="checkbox"> <input type="checkbox"
						value="Break" id="CheckboxTwoCourseBreak"
						name="CheckboxTwoCourseBreak"
						<c:if test="${twoBreaksRestriction}">checked</c:if>>
						<div style="float: left; margin-left: 4px">
							<i class="icon-coffee"></i>
						</div>
						<div style="margin-left: 25px">
							<spring:message code='help.restriction.twoCoursesBreak' />
						</div>
					</label> <label class="checkbox"> <input type="checkbox"
						value="Advanced" id="CheckboxAdvancedAtEnd"
						name="CheckboxAdvancedAtEnd"
						<c:if test="${advancedAtEndRestriction}">checked</c:if>>
						<div style="float: left; margin-left: 4px">
							<i class="icon-star"></i>
						</div>
						<div style="margin-left: 25px">
							<spring:message code='help.restriction.advancedAtEnd' />
						</div>
					</label> <label class="checkbox"> <input type="checkbox"
						value="Spectators" id="CheckboxBalancedSpectators"
						name="CheckboxBalancedSpectators"
						<c:if test="${balancedAmountOfSpectators}">checked</c:if>>
						<div style="float: left; margin-left: 4px">
							<i class="icon-group"></i>
						</div>
						<div style="margin-left: 25px">
							<spring:message code='help.restriction.balancedSpectators' />
						</div>
					</label> <label class="checkbox"> <input type="checkbox"
						value="AgeGroupd" id="CheckboxBalancedAgeGroup"
						name="CheckboxBalancedAgeGroup"
						<c:if test="${balancedAgeGroup}">checked</c:if>>
						<div style="float: left; margin-left: 4px">
							<i class="icon-adn"></i>
						</div>
						<div style="margin-left: 25px">
							<spring:message code='help.restriction.balancedAgeGroup' />
						</div>
					</label> <label class="checkbox"> <input type="checkbox"
						value="MultipleGroups" id="CheckboxMultipleGroupsSamePerformance"
						name="CheckboxMultipleGroupsSamePerformance"
						<c:if test="${multipleGroupsSamePerformance}">checked</c:if>>
						<div style="float: left; margin-left: 4px">
							<i class="icon-sitemap"></i>
						</div>
						<div style="margin-left: 25px">
							<spring:message
								code='help.restriction.multipleGroupsSamePerformance' />
						</div>
					</label> <label class="checkbox"> <input type="checkbox"
						value="Sibs" id="CheckboxSibsSamePerformance"
						name="CheckboxSibsSamePerformance"
						<c:if test="${sibsSamePerformance}">checked</c:if>>
						<div style="float: left; margin-left: 4px">
							<i class="icon-heart"></i>
						</div>
						<div style="margin-left: 25px">
							<spring:message code='help.restriction.sibsSamePerformance' />
						</div>
					</label>
				</div>

				<br />
				
				<spring:bind path="dateTime">
					<c:set var="divClass" value="control-group" />
					<c:if test="${status.error}">
						<c:set var="divClass" value="control-group error" />
					</c:if>
					<div class="${divClass}">
						<div class="span3">
							<form:input path="dateTime" id="datepicker"
								cssErrorClass="has-error" />
						</div>
						<form:errors path="dateTime" cssClass="help-inline" />
					</div>
				</spring:bind>

				<div style="margin-top: 20px">
					<input type="submit" value="<spring:message code="label.create"/>"
						class="btn btn-primary" />
				</div>
			</form:form>
		</dmtags:widget>
	</dmtags:span>

	<div>
		<dmtags:span width="6">
			<dmtags:widget title="${i18nShowPlan}" icon="icon-camera" style="table" >
				<spring:message var="i18nOverview" code="widget.overview" />
				<display:table name="performancePlanList" id="row"
					class="table table-striped table-bordered displaytag" pagesize="5"
					requestURI="/admin/performance" defaultsort="1">
					<display:column sortable="true" titleKey="label.performanceCreated">
						<fmt:formatDate value="${row.created}"
							pattern="dd.MM.yyyy HH:mm:ss" />
					</display:column>
					<display:column sortable="true" titleKey="label.date"
						class="colName" style="width: 45%">
						<joda:format value="${row.dateTime}" pattern="dd.MM.yyyy" />
					</display:column>
					<display:column style="width: 10%">
						<c:set var="planid" value="${row.planid}" />
						<a href="performance/show/${planid}"><spring:message
								code="label.show" /></a>
						<br />
						<a href="performance/delete/${planid}" class="openDialog"
							id="${planid}"><spring:message code="label.delete" /></a>
						<div id="deleteId" style="display: none;"></div>
					</display:column>
				</display:table>
				<div id="dialog-confirm"
					title="<spring:message code="delete.title" />">
					<p>
						<span class="ui-icon ui-icon-alert"
							style="float: left; margin: 0 7px 20px 0;"></span>
						<spring:message code="delete.performanceplan" />
					</p>
				</div>
			</dmtags:widget>
		</dmtags:span>
	</div>

	<dmtags:span width="12">
		<c:if test="${!empty performanceList1}">
			<dmtags:widget
				title="${i18nScheduleProposal}&nbsp;&nbsp;${dateTime.toString('dd.MM.yyyy')}"
				style="table" id="plan" icon="icon-list" pdfLink="${isSavedPlan ? 'performance/viewPerformancePdf/'.concat(currentPlanId) : ''}" >
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
							<table id="plan1" class="table table-bordered table-striped">
								<thead>
									<tr>
										<th style="width: 60%"><spring:message
												code="label.coursename" /></th>
										<th style="width: 4%"><i
											title="<spring:message code='help.restriction.noconsecutiveballett' />"
											class="inline-tooltip icon icon-female"></i></th>
										<th style="width: 4%"><i
											title="<spring:message code='help.restriction.twoCoursesBreak' />"
											class="inline-tooltip icon icon-coffee"></i></th>
										<th style="width: 4%"><i
											title="<spring:message code='help.restriction.advancedAtEnd' />"
											class="inline-tooltip icon icon-star"></i></th>
										<th style="width: 4%"><i
											title="<spring:message code='help.restriction.balancedSpectators' />"
											class="inline-tooltip icon icon-group"></i></th>
										<th style="width: 4%"><i
											title="<spring:message code='help.restriction.balancedAgeGroup' />"
											class="inline-tooltip icon icon-adn"></i></th>
										<th style="width: 4%"><i
											title="<spring:message code='help.restriction.multipleGroupsSamePerformance' />"
											class="inline-tooltip icon icon-sitemap"></i></th>
										<th style="width: 4%"><i
											title="<spring:message code='help.restriction.sibsSamePerformance' />"
											class="inline-tooltip icon icon-heart"></i></th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${performanceList1}" var="validatedCourse"
										varStatus="loop">
										<tr id="${loop.index}">
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
													<td><c:choose>
															<c:when test="${validatedCourse.balletRestriction}">
																<i class="icon-frown restriction-icon-bad"></i>
															</c:when>
															<c:otherwise>
																<i class="icon-smile restriction-icon-good"></i>
															</c:otherwise>
														</c:choose></td>
													<td><c:choose>
															<c:when test="${validatedCourse.twoBreaksRestriction}">
																<i class="icon-frown restriction-icon-bad"></i>
															</c:when>
															<c:otherwise>
																<i class="icon-smile restriction-icon-good"></i>
															</c:otherwise>
														</c:choose></td>
													<td><c:choose>
															<c:when
																test="${validatedCourse.advancedAtEndRestriction}">
																<i class="icon-frown restriction-icon-bad"></i>
															</c:when>
															<c:otherwise>
																<i class="icon-smile restriction-icon-good"></i>
															</c:otherwise>
														</c:choose></td>
													<td><c:choose>
															<c:when
																test="${validatedCourse.balancedAmountOfSpectators}">
																<i class="icon-frown restriction-icon-bad"></i>
															</c:when>
															<c:otherwise>
																<i class="icon-smile restriction-icon-good"></i>
															</c:otherwise>
														</c:choose></td>
													<td><c:choose>
															<c:when test="${validatedCourse.balancedAgeGroup}">
																<i class="icon-frown restriction-icon-bad"></i>
															</c:when>
															<c:otherwise>
																<i class="icon-smile restriction-icon-good"></i>
															</c:otherwise>
														</c:choose></td>
													<td><c:choose>
															<c:when
																test="${validatedCourse.multipleGroupsSamePerformance}">
																<i class="icon-frown restriction-icon-bad"></i>
															</c:when>
															<c:otherwise>
																<i class="icon-smile restriction-icon-good"></i>
															</c:otherwise>
														</c:choose></td>
													<td><c:choose>
															<c:when test="${validatedCourse.sibsSamePerformance}">
																<i class="icon-frown restriction-icon-bad"></i>
															</c:when>
															<c:otherwise>
																<i class="icon-smile restriction-icon-good"></i>
															</c:otherwise>
														</c:choose></td>
												</c:otherwise>
											</c:choose>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>

						<div class="tab-pane" id="hall2">
							<table id="plan2" class="table table-bordered table-striped">
								<thead>
									<tr>
										<th style="width: 60%"><spring:message
												code="label.coursename" /></th>
										<th style="width: 4%"><i
											title="<spring:message code='help.restriction.noconsecutiveballett' />"
											class="inline-tooltip icon icon-female"></i></th>
										<th style="width: 4%"><i
											title="<spring:message code='help.restriction.twoCoursesBreak' />"
											class="inline-tooltip icon icon-coffee"></i></th>
										<th style="width: 4%"><i
											title="<spring:message code='help.restriction.advancedAtEnd' />"
											class="inline-tooltip icon icon-star"></i></th>
										<th style="width: 4%"><i
											title="<spring:message code='help.restriction.balancedSpectators' />"
											class="inline-tooltip icon icon-group"></i></th>
										<th style="width: 4%"><i
											title="<spring:message code='help.restriction.balancedAgeGroup' />"
											class="inline-tooltip icon icon-adn"></i></th>
										<th style="width: 4%"><i
											title="<spring:message code='help.restriction.multipleGroupsSamePerformance' />"
											class="inline-tooltip icon icon-sitemap"></i></th>
										<th style="width: 4%"><i
											title="<spring:message code='help.restriction.sibsSamePerformance' />"
											class="inline-tooltip icon icon-heart"></i></th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${performanceList2}" var="validatedCourse"
										varStatus="loop">
										<tr id="${loop.index}">
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
													<td><c:choose>
															<c:when test="${validatedCourse.balletRestriction}">
																<i class="icon-frown restriction-icon-bad"></i>
															</c:when>
															<c:otherwise>
																<i class="icon-smile restriction-icon-good"></i>
															</c:otherwise>
														</c:choose></td>
													<td><c:choose>
															<c:when test="${validatedCourse.twoBreaksRestriction}">
																<i class="icon-frown restriction-icon-bad"></i>
															</c:when>
															<c:otherwise>
																<i class="icon-smile restriction-icon-good"></i>
															</c:otherwise>
														</c:choose></td>
													<td><c:choose>
															<c:when
																test="${validatedCourse.advancedAtEndRestriction}">
																<i class="icon-frown restriction-icon-bad"></i>
															</c:when>
															<c:otherwise>
																<i class="icon-smile restriction-icon-good"></i>
															</c:otherwise>
														</c:choose></td>
													<td><c:choose>
															<c:when
																test="${validatedCourse.balancedAmountOfSpectators}">
																<i class="icon-frown restriction-icon-bad"></i>
															</c:when>
															<c:otherwise>
																<i class="icon-smile restriction-icon-good"></i>
															</c:otherwise>
														</c:choose></td>
													<td><c:choose>
															<c:when test="${validatedCourse.balancedAgeGroup}">
																<i class="icon-frown restriction-icon-bad"></i>
															</c:when>
															<c:otherwise>
																<i class="icon-smile restriction-icon-good"></i>
															</c:otherwise>
														</c:choose></td>
													<td><c:choose>
															<c:when
																test="${validatedCourse.multipleGroupsSamePerformance}">
																<i class="icon-frown restriction-icon-bad"></i>
															</c:when>
															<c:otherwise>
																<i class="icon-smile restriction-icon-good"></i>
															</c:otherwise>
														</c:choose></td>
													<td><c:choose>
															<c:when test="${validatedCourse.sibsSamePerformance}">
																<i class="icon-frown restriction-icon-bad"></i>
															</c:when>
															<c:otherwise>
																<i class="icon-smile restriction-icon-good"></i>
															</c:otherwise>
														</c:choose></td>
												</c:otherwise>
											</c:choose>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>

						<div class="tab-pane" id="hall3">
							<table id="plan3" class="table table-bordered table-striped">
								<thead>
									<tr>
										<th style="width: 60%"><spring:message
												code="label.coursename" /></th>
										<th style="width: 4%"><i
											title="<spring:message code='help.restriction.noconsecutiveballett' />"
											class="inline-tooltip icon icon-female"></i></th>
										<th style="width: 4%"><i
											title="<spring:message code='help.restriction.twoCoursesBreak' />"
											class="inline-tooltip icon icon-coffee"></i></th>
										<th style="width: 4%"><i
											title="<spring:message code='help.restriction.advancedAtEnd' />"
											class="inline-tooltip icon icon-star"></i></th>
										<th style="width: 4%"><i
											title="<spring:message code='help.restriction.balancedSpectators' />"
											class="inline-tooltip icon icon-group"></i></th>
										<th style="width: 4%"><i
											title="<spring:message code='help.restriction.balancedAgeGroup' />"
											class="inline-tooltip icon icon-adn"></i></th>
										<th style="width: 4%"><i
											title="<spring:message code='help.restriction.multipleGroupsSamePerformance' />"
											class="inline-tooltip icon icon-sitemap"></i></th>
										<th style="width: 4%"><i
											title="<spring:message code='help.restriction.sibsSamePerformance' />"
											class="inline-tooltip icon icon-heart"></i></th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${performanceList3}" var="validatedCourse"
										varStatus="loop">
										<tr id="${loop.index}">
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
													<td><c:choose>
															<c:when test="${validatedCourse.balletRestriction}">
																<i class="icon-frown restriction-icon-bad"></i>
															</c:when>
															<c:otherwise>
																<i class="icon-smile restriction-icon-good"></i>
															</c:otherwise>
														</c:choose></td>
													<td><c:choose>
															<c:when test="${validatedCourse.twoBreaksRestriction}">
																<i class="icon-frown restriction-icon-bad"></i>
															</c:when>
															<c:otherwise>
																<i class="icon-smile restriction-icon-good"></i>
															</c:otherwise>
														</c:choose></td>
													<td><c:choose>
															<c:when
																test="${validatedCourse.advancedAtEndRestriction}">
																<i class="icon-frown restriction-icon-bad"></i>
															</c:when>
															<c:otherwise>
																<i class="icon-smile restriction-icon-good"></i>
															</c:otherwise>
														</c:choose></td>
													<td><c:choose>
															<c:when
																test="${validatedCourse.balancedAmountOfSpectators}">
																<i class="icon-frown restriction-icon-bad"></i>
															</c:when>
															<c:otherwise>
																<i class="icon-smile restriction-icon-good"></i>
															</c:otherwise>
														</c:choose></td>
													<td><c:choose>
															<c:when test="${validatedCourse.balancedAgeGroup}">
																<i class="icon-frown restriction-icon-bad"></i>
															</c:when>
															<c:otherwise>
																<i class="icon-smile restriction-icon-good"></i>
															</c:otherwise>
														</c:choose></td>
													<td><c:choose>
															<c:when
																test="${validatedCourse.multipleGroupsSamePerformance}">
																<i class="icon-frown restriction-icon-bad"></i>
															</c:when>
															<c:otherwise>
																<i class="icon-smile restriction-icon-good"></i>
															</c:otherwise>
														</c:choose></td>
													<td><c:choose>
															<c:when test="${validatedCourse.sibsSamePerformance}">
																<i class="icon-frown restriction-icon-bad"></i>
															</c:when>
															<c:otherwise>
																<i class="icon-smile restriction-icon-good"></i>
															</c:otherwise>
														</c:choose></td>
												</c:otherwise>
											</c:choose>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>

					</div>
				</div>
					<div style="float: left; margin-left: 15px">
						<form:form method="post" action="performance/save/${planid}"
							commandName="performance" class="form-horizontal">

							<div style="margin-top: 20px">
								<input type="submit" value="<spring:message code="label.save"/>"
									class="btn btn-primary" />
							</div>
						</form:form>
					</div>
					<div style="margin-left: 120px">
						<form:form method="post" action="performance/validate"
							commandName="performance" class="form-horizontal">

							<div style="margin-top: 20px">
								<input type="submit"
									value="<spring:message code="label.validate"/>"
									class="btn btn-primary" />
							</div>
						</form:form>
					</div>
			</dmtags:widget>
		</c:if>
	</dmtags:span>
</dmtags:base>

<script src="<c:url value="/js/jquery.tablednd.js" />"></script>
<script type="text/javascript">
	$(document)
			.ready(
					function() {
						$("#plan1")
								.tableDnD(
										{
											onDrop : function(table, row) {
												var rows = table.tBodies[0].rows;
												var newPos;
												for (var i = 0; i < rows.length; i++) {
													if (row.id == rows[i].id) {
														newPos = i;
													}
												}
												$
														.post(
																"performance/swap",
																{
																	performance : "1",
																	posSource : row.id,
																	posTarget : newPos
																},
																function(theResponse) {window.location.replace("/dancemanage/admin/performance/jumpToPlan/1");
																});

											}
										});

						$("#plan2")
								.tableDnD(
										{
											onDrop : function(table, row) {
												var rows = table.tBodies[0].rows;
												var newPos;
												for (var i = 0; i < rows.length; i++) {
													if (row.id == rows[i].id) {
														newPos = i;
													}
												}
												$
														.post(
																"performance/swap",
																{
																	performance : "2",
																	posSource : row.id,
																	posTarget : newPos
																},
																function(theResponse) {window.location.replace("/dancemanage/admin/performance/jumpToPlan/2");
																});

											}
										});

						$("#plan3")
								.tableDnD(
										{
											onDrop : function(table, row) {
												var rows = table.tBodies[0].rows;
												var newPos;
												for (var i = 0; i < rows.length; i++) {
													if (row.id == rows[i].id) {
														newPos = i;
													}
												}
												$
														.post(
																"performance/swap",
																{
																	performance : "3",
																	posSource : row.id,
																	posTarget : newPos
																},
																function(theResponse) {window.location.replace("/dancemanage/admin/performance/jumpToPlan/3");
																});

											}
										});
					});

	$(".openDialog").click(function() { //Löschen rückbestätigen
		$("#deleteId").text($(this).attr("id"));
		$("#dialog-confirm").dialog("open");

		var data = "test";

		$.post("performance/swap", data, function(theResponse) {
			$("#response").html(theResponse);
		});

		return false;
	});

	$("#dialog-confirm").dialog(
			{
				autoOpen : false,
				resizable : false,
				modal : true,
				buttons : {
					"OK" : function() {
						document.location = "performance/delete/"
								+ $("#deleteId").text();
						$(this).dialog("close");
					},
					Cancel : function() {
						$(this).dialog("close");
					}
				}
			});

	$("#datepicker").datepicker({
		showOn : "button",
		buttonImage : "/dancemanage/css/ui/images/calendar.gif",
		buttonImageOnly : true,
		dateFormat : dateformat,
		changeMonth : true,
		changeYear : true,
		yearRange : "2014:2025",
		defaultDate : 0
	});
</script>
<script src="<c:url value="/js/searchBoxAutoComplete.js" />"></script>
