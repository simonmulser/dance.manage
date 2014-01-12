<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.joda.org/joda/time/tags" prefix="joda"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="dmtags"%>
<%@taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<spring:message var="i18nTitle" code="nav.rating" />
<dmtags:base title="${i18nTitle}" activesection="rating">
	<spring:message var="i18nRating" code="widget.rating" />
	<dmtags:widget title="${i18nRating}" style="noTable"
		icon="icon-thumbs-up">
		<form:form method="post" action="rating/add" commandName="rating"
			class="form-horizontal">
			<form:input path="rid" value="${rating.rid}" type="hidden" />
			<div class="control-group">
				<form:label path="course.cid" class="control-label">
					<spring:message code="label.course" />
				</form:label>
				<div class="span6">
					<form:input path="course.cid" value="${rating.course.cid }"
						type="hidden" />
					${rating.course.name}
				</div>
			</div>
			<div class="control-group">
				<form:label path="courseRating" class="control-label">
					<spring:message code="label.courseRating" />
				</form:label>
				<div class="span6">
					<c:forEach var="i" begin="1" end="5">
						<c:choose>
							<c:when test="${i eq rating.courseRating }">
								<input name="star_${rating.rid}_1" type="radio" class="star"
									disabled="disabled" checked="checked" />
							</c:when>
							<c:otherwise>
								<input name="star_${rating.rid}_1" type="radio" class="star"
									disabled="disabled" />
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</div>
			</div>
			<div class="control-group">
				<form:label path="teacherRating" class="control-label">
					<spring:message code="label.teacherRating" />
				</form:label>
				<div class="span6">
					<c:forEach var="i" begin="1" end="5">
						<c:choose>
							<c:when test="${i eq rating.teacherRating }">
								<input name="star_${rating.rid}_2" type="radio" class="star"
									disabled="disabled" checked="checked" />
							</c:when>
							<c:otherwise>
								<input name="star_${rating.rid}_2" type="radio" class="star"
									disabled="disabled" />
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</div>
			</div>
			<div class="control-group">
				<form:label path="serviceRating" class="control-label">
					<spring:message code="label.serviceRating" />
				</form:label>
				<div class="span6">
					<c:forEach var="i" begin="1" end="5">
						<c:choose>
							<c:when test="${i eq rating.serviceRating }">
								<input name="star_${rating.rid}_3" type="radio" class="star"
									disabled="disabled" checked="checked" />
							</c:when>
							<c:otherwise>
								<input name="star_${rating.rid}_3" type="radio" class="star"
									disabled="disabled" />
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</div>
			</div>
			<div class="control-group">
				<form:label path="proCritique" class="control-label">
					<spring:message code="label.proCritique" />
				</form:label>
				<div class="span6">${rating.proCritique}</div>
			</div>
			<div class="control-group">
				<form:label path="contraCritique" class="control-label">
					<spring:message code="label.contraCritique" />
				</form:label>
				<div class="span6">${rating.contraCritique}</div>
			</div>
			<div class="control-group">
				<form:label path="answer" class="control-label">
					<spring:message code="label.answer" />
				</form:label>
				<div class="span6">
					<form:textarea path="answer" rows="6" />
				</div>
			</div>
			<div class="form-actions">
				<input type="submit" value="<spring:message code="label.save"/>"
					class="btn btn-primary" />
			</div>
		</form:form>
	</dmtags:widget>
	<spring:message var="i18nOverview" code="widget.overview" />
	<dmtags:widget title="${i18nOverview}" style="table" icon="icon-list">
		<display:table name="ratingList" id="rating"
			class="table table-striped table-bordered displaytag" pagesize="15"
			requestURI="/admin/rating" defaultsort="1">
			<display:column sortable="true" titleKey="label.course">
						${rating.course.name}
					</display:column>
			<display:column titleKey="label.courseRating">
				<c:forEach var="i" begin="1" end="5">
					<c:choose>
						<c:when test="${i eq rating.courseRating }">
							<input name="star_${rating.rid}_1" type="radio" class="star"
								disabled="disabled" checked="checked" />
						</c:when>
						<c:otherwise>
							<input name="star_${rating.rid}_1" type="radio" class="star"
								disabled="disabled" />
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</display:column>
			<display:column titleKey="label.teacherRating">
				<c:forEach var="i" begin="1" end="5">
					<c:choose>
						<c:when test="${i eq rating.teacherRating }">
							<input name="star_${rating.rid}_2" type="radio" class="star"
								disabled="disabled" checked="checked" />
						</c:when>
						<c:otherwise>
							<input name="star_${rating.rid}_2" type="radio" class="star"
								disabled="disabled" />
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</display:column>
			<display:column titleKey="label.serviceRating">
				<c:forEach var="i" begin="1" end="5">
					<c:choose>
						<c:when test="${i eq rating.serviceRating }">
							<input name="star_${rating.rid}_3" type="radio" class="star"
								disabled="disabled" checked="checked" />
						</c:when>
						<c:otherwise>
							<input name="star_${rating.rid}_3" type="radio" class="star"
								disabled="disabled" />
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</display:column>
			<display:column titleKey="label.proCritique">
						${rating.proCritique}
					</display:column>
			<display:column titleKey="label.contraCritique">
						${rating.contraCritique}
					</display:column>
			<display:column titleKey="label.answer">
				<c:choose>
					<c:when test="${!empty rating.answer }">
								${rating.answer }
							</c:when>
					<c:otherwise>
						<spring:message code="label.noAnswerYet" />
					</c:otherwise>
				</c:choose>
			</display:column>
			<display:column>
				<c:set var="rid" value="${rating.rid}" />
				<a href="rating/addAnswer/${rid}"><spring:message
						code="label.edit" /></a>
				<br />
				<a href="rating/delete/${participant.pid }/${rid}"
					class="openDialog" id="${rid}"><spring:message
						code="label.delete" /></a>
				<div id="deleteId" style="display: none;"></div>
			</display:column>
		</display:table>
		<div id="dialog-confirm"
			title="<spring:message code="delete.title" />">
			<p>
				<span class="ui-icon ui-icon-alert"
					style="float: left; margin: 0 7px 20px 0;"></span>
				<spring:message code="delete.rating" />
			</p>
		</div>
	</dmtags:widget>
</dmtags:base>
<script type="text/javascript">
	$('i').tooltip();
	$(".openDialog").click(function() { // Rückbestätigung bei Löschen 
		$("#deleteId").text($(this).attr("id"));
		$("#dialog-confirm").dialog("open");
		return false;
	});
	$("#dialog-confirm").dialog({
		autoOpen : false,
		resizable : false,
		modal : true,
		buttons : {
			"OK" : function() {
				document.location = "delete/${participant.pid}/" + $("#deleteId").text();
				$("#deleteId").text("");
				$(this).dialog("close");
			},
			Cancel : function() {
				$(this).dialog("close");
			}
		}
	});
</script>
