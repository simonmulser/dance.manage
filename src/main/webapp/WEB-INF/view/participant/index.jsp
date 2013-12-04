<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.joda.org/joda/time/tags" prefix="joda"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="dmtags"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<dmtags:base title="nav.home" activesection="dashboard">
	<c:choose>
		<c:when test="${user.courseParticipants.size() gt 0}">
			<dmtags:widget title="widget.courses" style="table" icon="icon-list">
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
			<dmtags:widget title="widget.courses" style="noTable"
				icon="icon-list">
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



	<dmtags:widget title="widget.courses" style="nopad" icon="icon-list">
		<div id='calendar'></div>
	</dmtags:widget>




	<c:choose>
		<c:when test="${user.siblings.size() gt 0}">

			<dmtags:widget title="widget.courses" style="table" icon="icon-list">
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
			<dmtags:widget title="widget.courses" style="noTable"
				icon="icon-list">
				<spring:message code="participant.noSiblings" />
			</dmtags:widget>
		</c:otherwise>
	</c:choose>

	<c:choose>
		<c:when test="${user.invoices.size() gt 0}">
			<dmtags:widget title="widget.invoices" style="table" icon="icon-list">
                TODO show invoices
            </dmtags:widget>
		</c:when>
		<c:otherwise>
			<dmtags:widget title="widget.invoices" style="noTable"
				icon="icon-list">
				<spring:message code="user.noInvoices" />
			</dmtags:widget>
		</c:otherwise>
	</c:choose>
</dmtags:base>

<script>
var courseData = [
<c:forEach items="${user.courseParticipants}" var="courseParticipant"
	varStatus="loop">
	        {
              title: '${courseParticipant.key.course.name}',
              start: 'T<fmt:formatDate value="${courseParticipant.key.course.time}" pattern="HH:mm" />',
            },
</c:forEach>
];

$(document).ready(function() {
    var date = new Date();
    var d = date.getDate();
    var m = date.getMonth();
    var y = date.getFullYear();
    var calendar = $('#calendar').fullCalendar({
      header: {
        left: 'prev,next today',
        center: 'title',
        right: 'month,agendaWeek,agendaDay'
      },
      selectable: true,
      selectHelper: true,
      select: function(start, end, allDay) {
        var title = prompt('Event Title:');
        if (title) {
          calendar.fullCalendar('renderEvent',
            {
              title: title,
              start: start,
              end: end,
              allDay: allDay
            },
            true // make the event "stick"
          );
        }
        calendar.fullCalendar('unselect');
      },
      editable: true,
      events: courseData,
    });
  });
</script>
