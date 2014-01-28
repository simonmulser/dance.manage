<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.joda.org/joda/time/tags" prefix="joda"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="dmtags"%>

<spring:message var="i18nEditProfile" code="nav.editProfile" />

<dmtags:base title="${i18nEditProfile}" activesection="dashboard">
	<spring:message var="i18nAccountInfo" code="widget.accountInfo" />
<dmtags:span width="12">
	<dmtags:widget title="${i18nAccountInfo}" icon="icon-user">
		<form:form method="post" action="edit" commandName="admin"
			class="form-horizontal">

			<dmtags:personForm />

			<dmtags:addressForm />

			<div class="form-actions">
				<input type="submit" value="<spring:message code="label.save"/>"
					class="btn btn-primary" />
			</div>

		</form:form>
	</dmtags:widget>
</dmtags:span>
</dmtags:base>
