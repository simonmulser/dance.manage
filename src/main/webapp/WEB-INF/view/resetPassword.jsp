<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.joda.org/joda/time/tags" prefix="joda"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="dmtags"%>

<spring:message var="i18nTitle" code="nav.resetPassword" />
<dmtags:scaffold title="${i18nTitle}">
    <dmtags:upperNavigation />
    <dmtags:center>
        <dmtags:span width="6">
            <br />
            <br />
            <spring:message var="i18nPasswordReset" code="widget.passwordReset" />
            <dmtags:widget icon="icon-edit" title="${i18nPasswordReset}">
            <c:choose>
            	<c:when test="${resetCode eq 2 }">
	            		<i class="icon-ok-sign"></i>&nbsp;&nbsp;<spring:message code="passwordReset.ok" /><br/><br/>
	            		<a href="<c:url value='/login'/>"><spring:message code="login.signin"/></a>
	            </c:when>
	            <c:otherwise>
	        		<c:if test="${resetCode eq 1 }">
	            			<i class="icon-remove-sign"></i>&nbsp;&nbsp;<spring:message code="passwordReset.nouser" /><br/><br/>
	            	</c:if>
	            	<form:form method="post" action="resetPassword" commandName="usernameBean" commclass="form-horizontal">
						<div class="control-group">
								<form:label path="username" class="control-label">
									<spring:message code="login.username" />
                				</form:label>
								<form:input path="username" /> <br/>
									
								<input type="submit" value="<spring:message code="label.reset"/>" class="btn btn-primary" />
						</div>
							
					</form:form>
					<a href="<c:url value='/login'/>"><spring:message code="login.signin"/></a>
            	</c:otherwise>
            </c:choose>
            </dmtags:widget>	
        </dmtags:span>
    </dmtags:center>
</dmtags:scaffold>