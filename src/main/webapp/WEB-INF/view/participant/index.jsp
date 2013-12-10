<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.joda.org/joda/time/tags" prefix="joda"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="dmtags"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<spring:message var="i18nTitle" code="nav.home" />
<dmtags:base title="${i18nTitle}" activesection="dashboard">
    <spring:message var="i18nCourses" code="widget.courses" />
	<c:choose>
		<c:when test="${user.courseParticipants.size() gt 0}">
			<dmtags:widget title="${i18nCourses}" style="table" icon="icon-list">
				<table class="table table-striped table-bordered">
					<thead>
						<tr>
							<th><spring:message code="label.year" /></th>
							<th><spring:message code="label.name" /></th>
							<th><spring:message code="participant.registered" /></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${user.courseParticipants}"
							var="courseParticipant" varStatus="loop">
							<tr>
								<td>${courseParticipant.key.course.year}</td>
								<td>${courseParticipant.key.course.name}</td>
								<td>${courseParticipant.duration}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</dmtags:widget>
		</c:when>
		<c:otherwise>
			<dmtags:widget title="${i18nCourses}" style="noTable" icon="icon-list">
				<spring:message code="participant.noCourses" />
			</dmtags:widget>
		</c:otherwise>
	</c:choose>

	<div class="table">
		<div class="widget-header">
			<i class="icon-list"></i>
			<h3>
				<spring:message code="widget.accountInfo" />
			</h3>
			<a href="<c:url value='/participant/edit' />"><button
					type="submit" class="btn btn-primary">Edit</button></a>
		</div>
		<!-- /widget-header -->
		<div class="widget-content">
			<table class="table table-striped table-bordered">
				<thead />
				<dmtags:personInformation />
				<tr>
					<td><spring:message code="label.emergencyNumber" /></td>
					<td>${user.emergencyNumber}</td>
				</tr>
				<tr>
					<td><spring:message code="label.contactPerson" /></td>
					<td>${user.contactPerson}</td>
				</tr>

				<dmtags:addressInformation />
			</table>
		</div>
		<!-- /widget-content -->
	</div>
	<!-- /widget -->


	<dmtags:widget title="${i18nCourses}" style="nopad" icon="icon-calendar">
		<div id='calendar'></div>
	</dmtags:widget>


	<spring:message var="i18nSiblings" code="widget.siblings" />
	<c:choose>
		<c:when test="${user.siblings.size() gt 0}">
			<dmtags:widget title="${i18nSiblings}" style="table" icon="icon-list">
				<table class="table table-striped table-bordered">
					<thead />
					<tbody>
						<c:forEach items="${user.siblings}" var="sibling" varStatus="loop">
							<tr>
								<td>${sibling.firstname}&nbsp;${sibling.lastname}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</dmtags:widget>
		</c:when>
		<c:otherwise>

			<dmtags:widget title="${i18nSiblings}" style="noTable"
				icon="icon-list">
				<spring:message code="participant.noSiblings" />
			</dmtags:widget>
		</c:otherwise>
	</c:choose>
	
    <spring:message var="i18nInvoices" code="widget.invoices" />
	<c:choose>
		<c:when test="${user.invoices.size() gt 0}">
			<dmtags:widget title="${i18nInvoices}" style="table" icon="icon-list">
                TODO show invoices
            </dmtags:widget>
		</c:when>
		<c:otherwise>
			<dmtags:widget title="${i18nInvoices}" style="noTable"
				icon="icon-list">
				<spring:message code="user.noInvoices" />
			</dmtags:widget>
		</c:otherwise>
	</c:choose>
</dmtags:base>
<script>



$(document).ready(function() {
	var courseData = [
	<c:forEach items="${user.courseParticipants}" var="courseParticipant"
		varStatus="loop">
		        {
	              title: '${courseParticipant.key.course.name}',
	              start: '<joda:format value="${courseParticipant.key.course.getStartDateTimeCurrentWeekRepresentation()}" pattern="yyyy-MM-dd HH:mm:ss" />',
	              end: '<joda:format value="${courseParticipant.key.course.getEndDateTimeCurrentWeekRepresentation()}" pattern="yyyy-MM-dd HH:mm:ss" />',
	              allDay: false,
	              color: '#FF8106',
		        },
	</c:forEach>
	];
	
    var calendar = $('#calendar').fullCalendar({
      header: {
        left: '',
        center: '',
        right: '',
      },
      defaultView: 'agendaWeek',
      contentHeight: 450,
      allDaySlot: false,
      axisFormat: 'HH:mm',
      timeFormat: 'HH:mm{ - HH:mm}',
      minTime: 10,
      firstHour: 15,
      firstDay: 1,
      slotMinutes: 15,
      columnFormat: 'dddd',
      editable: false,
      events: courseData,
    });
  });
</script>
