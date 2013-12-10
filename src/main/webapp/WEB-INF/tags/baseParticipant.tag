<%@tag description="dance.manage base template" language="java"
	pageEncoding="UTF-8"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="dmtags"%>
<%@attribute name="title" required="true" type="java.lang.String"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@attribute name="activesection" required="true"
	type="java.lang.String"%>

<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<dmtags:scaffold title="${title}">

<dmtags:navigationParticipant activesection="${activesection}"/>

	<dmtags:center>
		<jsp:doBody />
	</dmtags:center>

</dmtags:scaffold>
