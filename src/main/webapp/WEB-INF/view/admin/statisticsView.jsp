<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="dmtags"%>

<dmtags:base title="nav.statistics" activesection="statistics">
	<dmtags:widget title="widget.list" style="table" icon="icon-list">
	<display:table name="statistics" id="row" class="table table-striped table-bordered displaytag" pagesize="15" requestURI="/admin/statistics" defaultsort="1">
		<display:column sortable="true" titleKey="label.coursename"><c:out value="${row.name}"/></display:column>
		<display:column sortable="true" titleKey="label.teacher"><c:out value="${row.teacher.firstname} ${row.teacher.lastname}" /></display:column>
		<display:column sortable="true" titleKey="label.amountParticipants"><c:out value="${row.courseParticipants.size()}" /></display:column>
		<display:column sortable="true" titleKey="label.year"><c:out value="${row.year}" /></display:column>
	</display:table>
	
	</dmtags:widget>
	
</dmtags:base>