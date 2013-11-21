<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="dmtags"%>

<dmtags:base title="Listen">
	<c:if test="${!empty participantsByNumberOfCourses}">
		<dmtags:widget title="Liste" style="table">
			<table class="table table-striped table-bordered">
				<thead>
					<tr>
						<th><spring:message code="label.amountCourses" /></th>
						<th><spring:message code="label.firstname" /></th>
						<th><spring:message code="label.lastname" /></th>
					</tr>
				</thead>
				<tbody>				
					<c:forEach items="${participantsByNumberOfCourses}"
						var="participant" varStatus="loop">
						<tr>
							<td>${participant.courses.size()}</td>
							<td>${participant.lastname}</td>
							<td>${participant.firstname}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</dmtags:widget>
	</c:if>
	<c:if test="${!empty participantsByNumberOfSiblings}">
		<dmtags:widget title="Teilnehmer" style="table">
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
						<tr>
							<td>${participant.firstname}</td>
							<td>${participant.lastname}</td>
							<td>${participant.siblings.size()}<c:if
									test="${!empty participant.siblings}">&nbsp;-&nbsp;(<c:forEach
										items="${participant.siblings}" var="sibling" varStatus="loop">
                                ${sibling.firstname}&nbsp;${sibling.lastname}
                                ${!loop.last ? ', ' : ''}
								</c:forEach>)
								</c:if>
							</td>
							<td><c:forEach items="${participant.courses}" var="course"
									varStatus="loop">
                                ${course.name}
                                ${!loop.last ? ', ' : ''}
                                </c:forEach></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</dmtags:widget>
	</c:if>
	<c:if test="${!empty courses}">
		<dmtags:widget title="Kursinformation" style="table">
			<table class="table table-striped table-bordered">
				<thead>
					<tr>
						<th><spring:message code="label.coursename" /></th>
						<th><spring:message code="label.ageGroup" /></th>
						<th><spring:message code="label.level" /></th>
						<th><spring:message code="label.amountParticipants" /></th>
						<th><spring:message code="label.estimatedSpectators" /></th>
						<th><spring:message code="label.amountPerformances" /></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${courses}" var="course">
						<tr>
							<td>${course.name}</td>
							<td>${course.ageGroup}</td>
							<td>${course.level}</td>
							<td>${course.participants.size()}</td>
							<td>${course.estimatedSpectators}</td>
							<td>${course.amountPerformances}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</dmtags:widget>
	</c:if>
</dmtags:base>