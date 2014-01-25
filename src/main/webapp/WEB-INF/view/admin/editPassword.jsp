<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.joda.org/joda/time/tags" prefix="joda"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="dmtags"%>

<spring:message var="i18nEditPassword" code="nav.changePassword" />

<dmtags:base title="${i18nEditPassword}" activesection="dashboard">
	<spring:message var="i18npassword" code="widget.password" />
<dmtags:span width="12">
	<dmtags:widget title="${i18npassword}" icon="icon-cogs">
		<form:form method="post" action="editPassword" commandName="password"
			class="form-horizontal">
			
			<dmtags:newPasswordForm />

			<div class="form-actions">
				<input type="submit" value="<spring:message code="label.save"/>"
					class="btn btn-primary" />
			</div>

		</form:form>
	</dmtags:widget>
</dmtags:span>
</dmtags:base>
