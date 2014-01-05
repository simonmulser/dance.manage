<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.joda.org/joda/time/tags" prefix="joda"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="dmtags"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<spring:message var="i18nTitle" code="nav.search" />
<dmtags:base title="${i18nTitle}" activesection="">
	<dmtags:span width="6">
		<spring:message var="i18nSearchParticipants" code="widget.participants" />
		<c:choose>
			<c:when test="${searchedParticipants.size() gt 0}">
				<dmtags:widget title="${i18nSearchParticipants}" style="table"
					icon="icon-list">
					<table class="table table-striped table-bordered">
						<tbody>
							<c:forEach items="${searchedParticipants}"
								var="searchedParticipant" varStatus="loop">
								<tr>
									<td><a href="<c:url value='/admin/participant/edit/${searchedParticipant.pid}' />">${searchedParticipant.firstname}&nbsp;${searchedParticipant.lastname}</a></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</dmtags:widget>
			</c:when>
			<c:otherwise>
				<dmtags:widget title="${i18nSearchParticipants}" style="noTable"
					icon="icon-list">
					<spring:message code="search.participant.noResult" />
				</dmtags:widget>
			</c:otherwise>
		</c:choose>
		
		<spring:message var="i18nSearchCourses" code="widget.courses" />
		<c:choose>
			<c:when test="${searchedCourses.size() gt 0}">
				<dmtags:widget title="${i18nSearchCourses}" style="table"
					icon="icon-list">
					<table class="table table-striped table-bordered">
						<tbody>
							<c:forEach items="${searchedCourses}"
								var="searchedCourse" varStatus="loop">
								<tr>
									<td><a href="<c:url value='/admin/course/edit/${searchedCourse.cid}' />">${searchedCourse.name}</a></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</dmtags:widget>
			</c:when>
			<c:otherwise>
				<dmtags:widget title="${i18nSearchCourses}" style="noTable"
					icon="icon-list">
					<spring:message code="search.course.noResult" />
				</dmtags:widget>
			</c:otherwise>
		</c:choose>
	</dmtags:span>
	
	<dmtags:span width="6">
		<spring:message var="i18nSearchParents" code="widget.parents" />
		<c:choose>
			<c:when test="${searchedParents.size() gt 0}">
				<dmtags:widget title="${i18nSearchParents}" style="table"
					icon="icon-list">
					<table class="table table-striped table-bordered">
						<tbody>
							<c:forEach items="${searchedParents}"
								var="searchedParent" varStatus="loop">
								<tr>
									<td><a href="<c:url value='/admin/parent/edit/${searchedParent.pid}' />">${searchedParent.firstname}&nbsp;${searchedParent.lastname}</a></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</dmtags:widget>
			</c:when>
			<c:otherwise>
				<dmtags:widget title="${i18nSearchParents}" style="noTable"
					icon="icon-list">
					<spring:message code="search.parent.noResult" />
				</dmtags:widget>
			</c:otherwise>
		</c:choose>
		
		<spring:message var="i18nSearchTeachers" code="widget.teachers" />
		<c:choose>
			<c:when test="${searchedTeachers.size() gt 0}">
				<dmtags:widget title="${i18nSearchTeachers}" style="table"
					icon="icon-list">
					<table class="table table-striped table-bordered">
						<tbody>
							<c:forEach items="${searchedTeachers}"
								var="searchedTeacher" varStatus="loop">
								<tr>
									<td><a href="<c:url value='/admin/teacher/edit/${searchedTeacher.pid}' />">${searchedTeacher.firstname}&nbsp;${searchedTeacher.lastname}</a></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</dmtags:widget>
			</c:when>
			<c:otherwise>
				<dmtags:widget title="${i18nSearchTeachers}" style="noTable"
					icon="icon-list">
					<spring:message code="search.teacher.noResult" />
				</dmtags:widget>
			</c:otherwise>
		</c:choose>
	</dmtags:span>
</dmtags:base>



