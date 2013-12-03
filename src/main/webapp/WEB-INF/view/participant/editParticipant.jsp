<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.joda.org/joda/time/tags" prefix="joda"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="dmtags"%>

<dmtags:base title="nav.editProfile" activesection="participants">

	<dmtags:widget title="widget.accountInfo" icon="icon-user">
		<form:form method="post" action="edit"
			commandName="participant" class="form-horizontal">

			<dmtags:personForm />

            <dmtags:participantForm />
			
			<dmtags:addressForm />
			
			<div class="form-actions">
				<input type="submit" value="<spring:message code="label.save"/>"
					class="btn btn-primary" />
			</div>

		</form:form>
	</dmtags:widget>

</dmtags:base>
