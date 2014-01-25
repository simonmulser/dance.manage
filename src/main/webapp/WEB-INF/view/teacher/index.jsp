<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.joda.org/joda/time/tags" prefix="joda"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="dmtags"%>

<spring:message var="i18nTitle" code="nav.home" />
<dmtags:base title="${i18nTitle}" activesection="dashboard">
	<dmtags:span width="6">
		<spring:message var="i18nMyAccount" code="widget.myaccount" />
		<spring:message var="i18nEdit" code="label.edit" />
		<spring:message var="i18nMyCourses" code="widget.mycourses" />
		<spring:message var="i18nStyles" code="widget.styles" />
		<spring:message var="i18nAgenda" code="widget.agenda" />
		<c:choose>
			<c:when test="${teacher.courses.size() gt 0}">
				<dmtags:widget title="${i18nMyCourses}" style="table" icon="icon-list">
					<table class="table table-striped table-bordered">
						<thead>
							<tr>
								<th><spring:message code="label.year" /></th>
								<th><spring:message code="label.name" /></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${teacher.courses}" var="course" varStatus="loop">
								<tr>
									<td>${course.year}</td>
									<td>${course.name}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</dmtags:widget>
			</c:when>
			<c:otherwise>
				<dmtags:widget title="${i18nMyCourses}" style="noTable" icon="icon-list">
					<spring:message code="teacher.noCourses" />
				</dmtags:widget>
			</c:otherwise>
		</c:choose>
		
		<c:choose>
			<c:when test="${teacher.styles.size() gt 0}">
				<dmtags:widget title="${i18nStyles}" style="table" icon="icon-list">
					<table class="table table-striped table-bordered">
						<thead />
						<tbody>
							<c:forEach items="${teacher.styles}" var="style" varStatus="loop">
								<tr>
									<td>${style.name}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</dmtags:widget>
			</c:when>
			<c:otherwise>

				<dmtags:widget title="${i18nStyles}" style="noTable" icon="icon-list">
					<spring:message code="teacher.noStyles" />
				</dmtags:widget>
			</c:otherwise>
		</c:choose>
	</dmtags:span>
	
	<dmtags:span width="6">
		<div class="table">
			<div class="widget-header">
				<i class="icon-user"></i>
				<h3>${i18nMyAccount}</h3>
				<a href="<c:url value='/teacher/edit' />"><button type="submit" class="btn btn-primary">${i18nEdit}</button></a>
				<a href="<c:url value='/parent/editPassword' />"><button type="submit" class="btn btn-primary">${i18nChangePassword}</button></a>		
			</div>
			<!-- /widget-header -->
			<div class="widget-content">
				<table class="table table-striped table-bordered">
					<thead />
					<dmtags:personInformation />

					<dmtags:addressInformation />
				</table>
			</div>
		</div>
	</dmtags:span>
	
	<dmtags:span width="12">
		<dmtags:widget title="${i18nAgenda}" style="nopad" icon="icon-calendar">
			<div id='calendar'></div>
		</dmtags:widget>
	</dmtags:span>
</dmtags:base>

<script>

$(document).ready(function() {
	var courseData = [
	<c:forEach items="${enabledCourses}" var="course"
		varStatus="loop">
		        {
	              title: '${course.name}',
	              start: '<joda:format value="${course.getStartDateTimeCurrentWeekRepresentation()}" pattern="yyyy-MM-dd HH:mm:ss" />',
	              end: '<joda:format value="${course.getEndDateTimeCurrentWeekRepresentation()}" pattern="yyyy-MM-dd HH:mm:ss" />',
	              allDay: false,
	              color: '#FF8106',
		        },
	</c:forEach>
	];
	
    $('#calendar').fullCalendar({
      header: {
        left: '',
        center: '',
        right: '',
      },
      defaultView: 'agendaWeek',
      weekends: false,
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
