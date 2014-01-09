<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.joda.org/joda/time/tags" prefix="joda"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="dmtags"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<spring:message var="i18nTitle" code="nav.rating" />
<dmtags:base title="${i18nTitle}" activesection="rating">
	<spring:message var="i18nRating" code="widget.rating" />
	<dmtags:widget title="${i18nRating}" style="noTable" icon="icon-thumbs-up">
		<form:form method="post" action="<c:url value='/participant/rating/add/${participant.pid}' />"
				commandName="rating" class="form-horizontal">
			<div class="control-group">
				<form:label path="courseRating" class="control-label">
					<spring:message code="label.courseRating" />
			                </form:label>
				<div class="span6">
					<form:radiobutton path="courseRating" class="star" name="star1"/>
				</div>
			</div>
			<div class="control-group">
				<form:label path="teacherRating" class="control-label">
					<spring:message code="label.teacherRating" />
			                </form:label>
				<div class="span6">
					<form:radiobutton path="teacherRating" class="star" name="star2"/>
				</div>
			</div>
			<div class="control-group">
				<form:label path="serviceRating" class="control-label">
					<spring:message code="label.serviceRating" />
			                </form:label>
				<div class="span6">
					<form:radiobutton path="serviceRating" class="star" name="star3"/>
				</div>
			</div>
			<div class="control-group">
				<form:label path="proCritique" class="control-label">
					<spring:message code="label.proCritique" />
			                </form:label>
				<div class="span6">
					<form:textarea path="proCritique"/>
				</div>
			</div>
			<div class="control-group">
				<form:label path="contraCritique" class="control-label">
					<spring:message code="label.contraCritique" />
			                </form:label>
				<div class="span6">
					<form:textarea path="contraCritique"/>
				</div>
			</div>
			<div class="form-actions">
				<input type="submit" value="<spring:message code="label.save"/>"
					class="btn btn-primary" />
			</div>
		</form:form>
	</dmtags:widget>
</dmtags:base>
