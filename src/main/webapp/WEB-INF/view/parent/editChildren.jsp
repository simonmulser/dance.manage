<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.joda.org/joda/time/tags" prefix="joda"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="dmtags"%>

<spring:message var="i18nTitle" code="nav.home" />
<spring:message var="i18nAddChild" code="widget.addChild" />
<spring:message var="i18nMyChildren" code="widget.myChildren" />

<dmtags:base title="${i18nTitle}" activesection="dashboard">
	<dmtags:span width="6">
		<dmtags:widget title="${i18nAddChild}" style="noTable" icon="icon-edit">
			<form method="POST" class="form-horizontal" action="">

				<div id="find_keyword" class="control-group">
					<label for="childQuery" class="control-label"><spring:message code="label.child" />*</label>
					<div class="ui-widget span3">
						<input id="childQuery" type="text" value="" class="ui-autocomplete-input" autocomplete="off" /> <span role="status" aria-live="polite" class="ui-helper-hidden-accessible"></span> <i
							title="<spring:message code='help.searchChild' />" class="inline-tooltip icon icon-question-sign"></i>
						<div id="showChild"></div>
						<input id="childId" value="" type="hidden" />
					</div>
				</div>
				<div id="find_keyword" class="control-group">
					<label for="childPassword" class="control-label"><spring:message code="label.child" />*</label>
					<div class="ui-widget span3">
						<input id="childPassword" type="password" value="" />
					</div>
				</div>
				<div class="form-actions">
					<input type="submit" value="<spring:message code="label.save"/>" class="btn btn-primary" />
					<button class="btn">
						<spring:message code="label.cancel" />
					</button>
				</div>
			</form>
		</dmtags:widget>
	</dmtags:span>
	<dmtags:span width="6">

		<dmtags:widget title="${i18nMyChildren}" style="noTable" icon="icon-user">
			<c:choose>
				<c:when test="${parent.children.size() gt 0}">
					<div class="widget-content">
						<table class="table table-striped table-bordered">
							<thead>
								<tr>
									<th><spring:message code="label.firstname" /></th>
									<th><spring:message code="label.lastname" /></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${parent.children}" var="child" varStatus="loop">
									<tr>
										<td>${child.firstname}</td>
										<td>${child.lastname}</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</c:when>
				<c:otherwise>
					<spring:message code="parent.noChildren" />
				</c:otherwise>
			</c:choose>
		</dmtags:widget>
	</dmtags:span>
</dmtags:base>
<script type="text/javascript">
	$(document).ready(function() {
		$('i').tooltip();
	});
</script>