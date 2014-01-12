<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.joda.org/joda/time/tags" prefix="joda"%>
<%@taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="dmtags"%>

<dmtags:base title="${i18nNavParents}" activesection="parents">
	<dmtags:span width="12">

		<spring:message var="i18nWidgetParents" code="widget.parents" />
		<spring:message var="i18nNavParents" code="nav.parents" />

		<dmtags:widget title="${i18nWidgetParents}" icon="icon-user" id="add"
			retractable="true" retractedPerDefault="true">
			<spring:message code="help.parent" />
			<br />
			<spring:message code="help.required" />
			<form:form method="post" action="parent/add" commandName="parent"
				class="form-horizontal">

				<dmtags:personForm />

				<dmtags:addressForm />

				<div class="form-actions">
					<input type="submit" value="<spring:message code="label.save"/>"
						class="btn btn-primary" />
				</div>

			</form:form>
		</dmtags:widget>

		<c:if test="${!empty parentList}">
			<spring:message var="i18nOverview" code="widget.overview" />
			<dmtags:widget title="${i18nOverview}" style="table" icon="icon-list">
				<display:table name="parentList" id="row"
					class="table table-striped table-bordered displaytag" pagesize="15"
					requestURI="/admin/parent" defaultsort="1">
					<display:column sortable="true" titleKey="label.name"
						class="colName">
						<c:out value="${row.firstname} ${row.lastname}" />
					</display:column>
					<display:column sortable="true" titleKey="label.email">
						<c:out value="${row.email}" />
					</display:column>
					<display:column sortable="true" titleKey="label.telephone">
						<c:out value="${row.telephone}" />
					</display:column>
					<display:column sortable="true" titleKey="label.street">
						<c:out value="${row.address.street} ${row.address.number}" />
						<c:if test="${!empty row.address.stair}">
							<c:out value="/${row.address.stair }" />
						</c:if>
						<c:if test="${!empty row.address.door}">
							<c:out value="/${row.address.door }" />
						</c:if>
					</display:column>
					<display:column sortable="true" titleKey="label.zip">
						<c:out value="${row.address.zip}" />
					</display:column>
					<display:column sortable="true" titleKey="label.city"
						class="colCity">
						<c:out value="${row.address.city}" />
					</display:column>
					<display:column titleKey="label.children">
						<c:if test="${!empty row.children}">
							<c:forEach items="${row.children}" var="child" varStatus="loop">
								${child.firstname}
								${!loop.last ? ', ' : ''}
						</c:forEach>
						</c:if>
					</display:column>
					<display:column>
						<c:set var="pid" value="${row.pid}" />
						<a href="parent/edit/${pid}#add"><spring:message code="label.edit" /></a>
						<br />
						<a href="parent/delete/${pid}" class="openDialog" id="${pid }"><spring:message
								code="label.delete" /></a>
						<div id="deleteId" style="display: none;"></div>
					</display:column>
				</display:table>
				<div id="dialog-confirm"
					title="<spring:message code="delete.title" />">
					<p>
						<span class="ui-icon ui-icon-alert"
							style="float: left; margin: 0 7px 20px 0;"></span>
						<spring:message code="delete.parent" />
					</p>
				</div>
			</dmtags:widget>
		</c:if>
	</dmtags:span>
</dmtags:base>
<script type="text/javascript">
	// this section is needed if the url contains an anchor hash to a widget which is retracted by default
	$(document).ready(
			function() {
				if (window.location.hash) {
					$(window.location.hash).children("[id*='widget-content-']")
							.removeAttr('style');
				}
			});

	$('i').tooltip();

	$(".openDialog").click(function() { //Löschen rückbestätigen
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
				document.location = "parent/delete/" + $("#deleteId").text();

				$(this).dialog("close");
			},
			Cancel : function() {
				$(this).dialog("close");
			}
		}
	});
</script>
