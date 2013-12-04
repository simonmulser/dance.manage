<%@ page contentType="application/json;" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.joda.org/joda/time/tags" prefix="joda"%>

{
<c:forEach items="${user.courseParticipants}" var="courseParticipant"
	varStatus="loop">
	            {
              title: '${courseParticipant.key.course.name}',
              start: new Date(<joda:format
		value="${courseParticipant.key.course.time}" style="LL" />)
            },
</c:forEach>
}
