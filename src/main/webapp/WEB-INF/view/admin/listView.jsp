<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="dmtags"%>

<dmtags:base title="nav.lists" activesection="lists">
	<dmtags:widget title="widget.performanceInformation" style="table"
		icon="icon-list">
		<display:table name="courses" id="course"
			class="table table-striped table-bordered displaytag" pagesize="15"
			requestURI="/admin/lists" defaultsort="1">
			<display:column sortable="true" titleKey="label.coursename">
				<c:out value="${course.name}" />
			</display:column>
			<display:column sortable="true" titleKey="label.ageGroup">
				<c:out value="${course.ageGroup}" />
			</display:column>
			<display:column sortable="true" titleKey="label.level">
				<c:out value="${course.level}" />
			</display:column>
			<display:column sortable="true" titleKey="label.amountParticipants">
				<c:out value="${course.courseParticipants.size()}" />
			</display:column>
			<display:column sortable="true" titleKey="label.estimatedSpectators">
				<c:out value="${course.estimatedSpectators}" />
			</display:column>
			<display:column sortable="true" titleKey="label.amountPerformances">
				<c:out value="${course.amountPerformances}" />
			</display:column>
		</display:table>

	</dmtags:widget>

	<c:if test="${!empty participantsByNumberOfCourses}">
		<dmtags:widget title="widget.participantCourseAmount" style="table"
			icon="icon-list">
			<table class="table table-striped table-bordered">
				<thead>
					<tr>
						<th><spring:message code="label.amountCourses" /></th>
						<th><spring:message code="label.courses" /></th>
						<th><spring:message code="label.firstname" /></th>
						<th><spring:message code="label.lastname" /></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${participantsByNumberOfCourses}"
						var="participant" varStatus="loop">
						<c:if test="${fn:length(participant.courseParticipants)>1 }">
							<tr>
								<td>${participant.courseParticipants.size()}</td>
								<td><c:forEach items="${participant.courseParticipants}"
										var="courseParticipant" varStatus="loop">
	                                ${courseParticipant.key.course.name}
	                                ${!loop.last ? ', ' : ''}
	                                </c:forEach></td>
								<td>${participant.lastname}</td>
								<td>${participant.firstname}</td>
							</tr>
						</c:if>
					</c:forEach>
				</tbody>
			</table>
		</dmtags:widget>
	</c:if>

	<c:if test="${!empty participantsByNumberOfSiblings}">
		<dmtags:widget title="widget.siblingRelationships" style="table"
			icon="icon-list">
			<table class="table table-striped table-bordered">
				<thead>
					<tr>
						<th><spring:message code="label.firstname" /></th>
						<th><spring:message code="label.lastname" /></th>
						<th><spring:message code="label.siblings" /></th>
						<th><spring:message code="label.courses" /></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${participantsByNumberOfSiblings}"
						var="participant">
						<c:if test="${!empty participant.siblings}">
							<tr>
								<td>${participant.firstname}</td>
								<td>${participant.lastname}</td>

								<td>${participant.siblings.size()}&nbsp;-&nbsp;(<c:forEach
										items="${participant.siblings}" var="sibling" varStatus="loop">
		
		                                ${sibling.firstname}&nbsp;${sibling.lastname}
		                                ${!loop.last ? ', ' : ''}
										</c:forEach>)

								</td>

								<td><c:forEach items="${participant.courseParticipants}"
										var="courseParticipant" varStatus="loop">
	                                ${courseParticipant.key.course.name}
	                                ${!loop.last ? ', ' : ''}
	                                </c:forEach></td>
							</tr>
						</c:if>
					</c:forEach>
				</tbody>
			</table>
		</dmtags:widget>
	</c:if>

</dmtags:base>