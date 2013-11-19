<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="dmtags"%>
<dmtags:base title="Listen">
	<c:if test="${!empty participantsByNumberOfCourses}">
		<div class="widget widget-table action-table">
			<div class="widget-header">
				<i class="icon-th-list"></i>
				<h3>Teilnehmer mit der Anzahl ihrer Kurse</h3>
			</div>
			<div class="widget-content">
				<table class="table table-striped table-bordered">
					<thead>
						<tr>
							<th>Anzahl der Kurse</th>
							<th>Vorname</th>
							<th>Nachname</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${participantsByNumberOfCourses}" var="entry">
							<c:forEach items="${entry.value}" var="participant"
								varStatus="loop">
								<td>${loop.first ? entry.key : ''}</td>
								<td>${participant.lastname}</td>
								<td>${participant.firstname}</td>
							</c:forEach>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<!-- /widget-content -->
		</div>
	</c:if>
	<c:if test="${!empty participantsByNumberOfSiblings}">
		<div class="widget widget-table action-table">
			<div class="widget-header">
				<i class="icon-th-list"></i>
				<h3>Teilnehmer mit der Anzahl ihrer Geschwister</h3>
			</div>
			<div class="widget-content">
				<table class="table table-striped table-bordered">
					<thead>
						<tr>
							<th>Vorname</th>
							<th>Nachname</th>
							<th>Geschwister</th>
							<th>Kurse</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${participantsByNumberOfSiblings}"
							var="participant">
							<tr>
								<td>${participant.firstname}</td>
								<td>${participant.lastname}</td>
								<td>${participant.siblings.size()}<c:if
										test="${!empty participant.siblings}">
								 - (<c:forEach items="${participant.siblings}" var="sibling"
											varStatus="loop">
                                ${sibling.firstname} ${sibling.lastname}
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
			</div>
			<!-- /widget-content -->
		</div>
	</c:if>
	<c:if test="${!empty courses}">
		<div class="widget widget-table action-table">
			<div class="widget-header">
				<i class="icon-th-list"></i>
				<h3>Kursinformation</h3>
			</div>
			<div class="widget-content">
				<table class="table table-striped table-bordered">
					<thead>
						<tr>
							<th>Kursname</th>
							<th>Alter der Teilnehmer</th>
							<th>Können</th>
							<th>Teilnehmeranzahl</th>
							<th>Zu erwartende Zuschaueranzahl</th>
							<th>Auftrittsanzahl</th>
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
			</div>
			<!-- /widget-content -->
		</div>
	</c:if>
</dmtags:base>