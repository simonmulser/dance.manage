<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.joda.org/joda/time/tags" prefix="joda"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="dmtags"%>

<spring:message var="i18nTitle" code="nav.accesscheck" />
<dmtags:scaffold title="${i18nTitle}">
    <dmtags:upperNavigation />
    <dmtags:center>
        <dmtags:span width="12">
            <br />
            <br />
            <spring:message var="i18nAccessCheck" code="widget.access" />
            <c:if test="${statuscode eq 0 }">
	            <dmtags:widget icon="icon-ok-sign" title="${i18nAccessCheck}">
	            	<spring:message code="access.ok" /><br/><br/>
	            	<a href="<c:url value='/login'/>"><spring:message code="login.signin"/></a>
	            </dmtags:widget>
            </c:if>
            <c:if test="${statuscode eq 1 }">
	            <dmtags:widget icon="icon-warning-sign" title="${i18nAccessCheck}">
	            	<spring:message code="access.justactivated" /><br/><br/>
	            	<a href="<c:url value='/login'/>"><spring:message code="login.signin"/></a>
	            </dmtags:widget>
            </c:if>
            <c:if test="${statuscode eq 2 }">
	            <dmtags:widget icon="icon-remove-sign" title="${i18nAccessCheck}">
	            	<spring:message code="access.nouser" />
	            </dmtags:widget>
            </c:if>
        </dmtags:span>
    </dmtags:center>
</dmtags:scaffold>