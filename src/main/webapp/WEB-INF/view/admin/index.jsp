<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.joda.org/joda/time/tags" prefix="joda"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="dmtags"%>

<link href="<c:url value="/css/pages/dashboard.css" />" rel="stylesheet">

<spring:message var="title" code="nav.adminArea" />
<spring:message var="i18nWidgetShortcuts" code="widget.shortcuts" />
<spring:message var="i18nWidgetLatestFeedback"
	code="widget.latestfeedback" />
<spring:message var="i18nCourses" code="widget.courses" />
<spring:message var="i18nSCNewParticipant"
	code="shortcut.newparticipant" />
<spring:message var="i18nSCNewInvoice" code="shortcut.newinvoice" />
<spring:message var="i18nSCNewCourse" code="shortcut.newcourse" />
<spring:message var="i18nSCEditPerformance"
	code="shortcut.editperformance" />
<spring:message var="i18nSCCourseList" code="shortcut.courselist" />
<spring:message var="i18nSCDailyStats" code="shortcut.dailystats" />
<spring:message var="i18nSCFeedback" code="shortcut.feedback" />


<dmtags:base title="${title}" activesection="dashboard">

	<dmtags:span width="6">
		<dmtags:widget icon="icon-comment"
			title="${i18nWidgetLatestFeedback}">
			<ul class="messages_layout">
				<li class="from_user left"><a href="#" class="avatar"><img
						src="img/message_avatar1.png" /></a>
					<div class="message_wrap">
						<span class="arrow"></span>
						<div class="info">
							<a class="name">John Smith</a> <span class="time">1 hour
								ago</span>
							<div class="options_arrow">
								<div class="dropdown pull-right">
									<a class="dropdown-toggle " id="dLabel" role="button"
										data-toggle="dropdown" data-target="#" href="#"> <i
										class=" icon-caret-down"></i>
									</a>
									<ul class="dropdown-menu " role="menu" aria-labelledby="dLabel">
										<li><a href="#"><i class=" icon-share-alt icon-large"></i>
												Reply</a></li>
										<li><a href="#"><i class=" icon-trash icon-large"></i>
												Delete</a></li>
									</ul>
								</div>
							</div>
						</div>
						<div class="text">As an interesting side note, as a head
							without a body, I envy the dead. There's one way and only one way
							to determine if an animal is intelligent. Dissect its brain! Man,
							I'm sore all over. I feel like I just went ten rounds with mighty
							Thor.</div>
					</div></li>
				<li class="by_myself right"><a href="#" class="avatar"><img
						src="img/message_avatar2.png" /></a>
					<div class="message_wrap">
						<span class="arrow"></span>
						<div class="info">
							<a class="name">Elisabeth (ich) </a> <span class="time">4
								hours ago</span>
							<div class="options_arrow">
								<div class="dropdown pull-right">
									<a class="dropdown-toggle " id="dLabel" role="button"
										data-toggle="dropdown" data-target="#" href="#"> <i
										class=" icon-caret-down"></i>
									</a>
									<ul class="dropdown-menu " role="menu" aria-labelledby="dLabel">
										<li><a href="#"><i class=" icon-share-alt icon-large"></i>
												Reply</a></li>
										<li><a href="#"><i class=" icon-trash icon-large"></i>
												Delete</a></li>
									</ul>
								</div>
							</div>
						</div>
						<div class="text">All I want is to be a monkey of moderate
							intelligence who wears a suit… that's why I'm transferring to
							business school! I had more, but you go ahead. Man, I'm sore all
							over. I feel like I just went ten rounds with mighty Thor. File
							not found.</div>
					</div></li>
				<li class="from_user left"><a href="#" class="avatar"><img
						src="img/message_avatar1.png" /></a>
					<div class="message_wrap">
						<span class="arrow"></span>
						<div class="info">
							<a class="name">Celeste Holm </a> <span class="time">1 Day
								ago</span>
							<div class="options_arrow">
								<div class="dropdown pull-right">
									<a class="dropdown-toggle " id="dLabel" role="button"
										data-toggle="dropdown" data-target="#" href="#"> <i
										class=" icon-caret-down"></i>
									</a>
									<ul class="dropdown-menu " role="menu" aria-labelledby="dLabel">
										<li><a href="#"><i class=" icon-share-alt icon-large"></i>
												Reply</a></li>
										<li><a href="#"><i class=" icon-trash icon-large"></i>
												Delete</a></li>
									</ul>
								</div>
							</div>
						</div>
						<div class="text">And I'd do it again! And perhaps a third
							time! But that would be it. Are you crazy? I can't swallow that.
							And I'm his friend Jesus. No, I'm Santa Claus! And from now on
							you're all named Bender Jr.</div>
					</div></li>
				<li class="from_user left"><a href="#" class="avatar"><img
						src="img/message_avatar1.png" /></a>
					<div class="message_wrap">
						<span class="arrow"></span>
						<div class="info">
							<a class="name">Mark Jobs </a> <span class="time">2 Days
								ago</span>
							<div class="options_arrow">
								<div class="dropdown pull-right">
									<a class="dropdown-toggle " id="dLabel" role="button"
										data-toggle="dropdown" data-target="#" href="#"> <i
										class=" icon-caret-down"></i>
									</a>
									<ul class="dropdown-menu " role="menu" aria-labelledby="dLabel">
										<li><a href="#"><i class=" icon-share-alt icon-large"></i>
												Reply</a></li>
										<li><a href="#"><i class=" icon-trash icon-large"></i>
												Delete</a></li>
									</ul>
								</div>
							</div>
						</div>
						<div class="text">That's the ONLY thing about being a slave.
							Now, now. Perfectly symmetrical violence never solved anything.
							Uh, is the puppy mechanical in any way? As an interesting side
							note, as a head without a body, I envy the dead.</div>
					</div></li>
			</ul>
		</dmtags:widget>
	</dmtags:span>

	<dmtags:span width="6">
		<dmtags:widget icon="icon-bookmark" title="${i18nWidgetShortcuts}">
			<div class="shortcuts">
				<a href="admin/participant" class="shortcut"><i
					class="shortcut-icon icon-user"></i><span class="shortcut-label">${i18nSCNewParticipant}</span>
				</a><a href="admin/invoice" class="shortcut"><i
					class="shortcut-icon icon-envelope"></i><span
					class="shortcut-label">${i18nSCNewInvoice}</span> </a><a
					href="admin/course" class="shortcut"><i
					class="shortcut-icon icon-calendar"></i> <span
					class="shortcut-label">${i18nSCNewCourse}</span> </a><a
					href="admin/performance" class="shortcut"> <i
					class="shortcut-icon icon-camera"></i><span class="shortcut-label">${i18nSCEditPerformance}</span>
				</a><a href="admin/course" class="shortcut"><i
					class="shortcut-icon icon-list"></i><span class="shortcut-label">${i18nSCCourseList}</span>
				</a><a href="admin/statistics" class="shortcut"><i
					class="shortcut-icon icon-signal"></i><span class="shortcut-label">${i18nSCDailyStats}</span>
				</a><a href="javascript:;" class="shortcut"><i
					class="shortcut-icon icon-comment"></i> <span
					class="shortcut-label">${i18nSCFeedback}</span> </a><a
					href="javascript:;" class="shortcut"> <i
					class="shortcut-icon icon-tag"></i><span class="shortcut-label">Tags</span>
				</a>
			</div>
		</dmtags:widget>
	</dmtags:span>

	<dmtags:span width="12">
		<dmtags:widget title="${i18nCourses}" style="nopad"
			icon="icon-calendar">
			<div id='calendar'></div>
		</dmtags:widget>
	</dmtags:span>

</dmtags:base>


<script>
$(document).ready(function() {
	var courseData = [
	<c:forEach items="${courseList}" var="course"
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