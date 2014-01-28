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
	<dmtags:span width="12">
	<spring:message var="i18nRating" code="widget.rating" />
	<dmtags:widget title="${i18nRating}" style="noTable"
		icon="icon-thumbs-up">
		<spring:message code="help.ratingAdmin" />
		<c:choose>
			<c:when test="${!empty rating.rid }">
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
						<form:errors path="answer" cssClass="error" />		
					</div>
					<div class="form-actions">
						<input type="submit" value="<spring:message code="label.save"/>"
							class="btn btn-primary" />
					</div>
				</form:form>
			</c:when>
			<c:otherwise></c:otherwise>
		</c:choose>
	</dmtags:widget>
	<spring:message var="i18nOverview" code="widget.overview" />
	<dmtags:widget title="${i18nOverview}" style="table" icon="icon-list">
		<display:table name="ratingList" id="rating"
			class="table table-striped table-bordered displaytag" pagesize="15"
			requestURI="/admin/rating" defaultsort="1">
			<display:column sortable="true" titleKey="label.course">
						${rating.course.name}
			</display:column>
			<display:column sortable="true" titleKey="label.ratingDate">
						<fmt:formatDate value="${rating.created}" pattern="dd.MM.yyy HH:mm" />
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
			<display:column titleKey="label.proCritique" class="feedbackCritique">
						${rating.proCritique}
					</display:column>
			<display:column titleKey="label.contraCritique" class="feedbackCritique">
						${rating.contraCritique}
					</display:column>
			<display:column titleKey="label.answer">
				<c:choose>
					<c:when test="${!empty rating.answer }">
								${rating.answer }
							</c:when>
					<c:otherwise>
						<span class="noAnswerYet"><spring:message code="label.noAnswerYet" /></span>
					</c:otherwise>
				</c:choose>
			</display:column>
			<display:column>
				<c:set var="rid" value="${rating.rid}" />
				<a href="rating/addAnswer/${rid}"><spring:message
						code="label.edit" /></a>
			</display:column>
		</display:table>
	</dmtags:widget>
	</dmtags:span>
</dmtags:base>

<script src="<c:url value="/js/searchBoxAutoComplete.js" />"></script>
<script type="text/javascript">
	$('i').tooltip();
</script>
