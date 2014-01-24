<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.joda.org/joda/time/tags" prefix="joda"%>
<%@taglib uri="http://displaytag.sf.net" prefix="display"%>

<%@taglib tagdir="/WEB-INF/tags" prefix="dmtags"%>

<spring:message var="i18nNavTitle" code="nav.appointments" />
<dmtags:base title="${i18nNavTitle}" activesection="courses">
	<dmtags:span width="6">
		<spring:message var="i18nWidgetTitle" code="widget.appointments" />
		<dmtags:widget icon="icon-calendar" title="${i18nWidgetTitle}">
			<form:form method="post" action="" commandName="wrapper">
				<c:forEach items="${wrapper.appointments}" var="appointment" varStatus="loop">
					<div class="alert alert-info inputField">
						<a class="button close"><i class="icon-remove"></i></a>
						<form:input path="appointments[${loop.index}].enabled" type="hidden" />
						<form:input path="appointments[${loop.index}].number" type="hidden" />
						<form:input path="appointments[${loop.index}].apid" type="hidden" />
						<span class="display-number">${appointment.number}</span>.&nbsp;
						<spring:message code="label.appointment" />
						&nbsp;&nbsp;
						<form:input path="appointments[${loop.index}].appointmentDate" class="datepicker" />
						&nbsp;&nbsp;
						<form:errors path="appointments[${loop.index}].appointmentDate" cssClass="error" />
					</div>
				</c:forEach>
				<c:if test="${wrapper.appointments.size() == 0}">
					<div class="alert alert-info inputField" style="">
						<a class="button close"><i class="icon-remove"></i></a> <input id="appointments0.enabled" name="appointments[0].enabled" type="hidden" value="true"> <input id="appointments0.number"
							name="appointments[0].number" type="hidden" value="1"> <span class="display-number">1</span>.&nbsp;
						<spring:message code="label.appointment" />
						&nbsp;&nbsp; <input id="appointments0.appointmentDate" name="appointments[0].appointmentDate" type="text" value="" class="datepicker">
					</div>
				</c:if>
				<div class="form-actions">
					<input type="submit" value="<spring:message code="label.save"/>" class="btn btn-primary" />
					<button id="add" class="btn btn-info">
						<spring:message code="label.addAppointment" />
					</button>
					<a href="/dancemanage/admin/course" class="btn"><spring:message code="label.cancel" /> </a>

				</div>
			</form:form>
			<div id="prototyp" class="alert alert-info inputField" style="display: none;">
				<a class="button close"><i class="icon-remove"></i></a> <input id="appointments-1.enabled" name="appointments[-1].enabled" type="hidden" value="true"> <input id="appointments-1.number"
					name="appointments[-1].number" type="hidden" value="-1"> <span class="display-number">-1</span>.&nbsp;
				<spring:message code="label.appointment" />
				&nbsp;&nbsp; <input id="appointments-1.appointmentDate" name="appointments[-1].appointmentDate" type="text" value=""> &nbsp;&nbsp;
			</div>
		</dmtags:widget>
	</dmtags:span>
	<dmtags:span width="6">
		<spring:message var="i18nInfo" code="widget.courseInformation" />
		<dmtags:widget icon="icon-info-sign" title="${i18nInfo}" style="table">
			<table class="table table-striped table-bordered">
				<tbody>
					<tr>
						<td><spring:message code="label.name" /></td>
						<td>${course.name}</td>
					</tr>
					<tr>
						<td><spring:message code="label.at" /></td>
                        <td>
                            <spring:message code="${course.weekday.i18nIdentifier}" />,&nbsp;
                            <joda:format value="${course.time}" pattern="HH:mm" />
                        </td>
                    </tr>
					<tr>
						<td><spring:message code="label.courselevel" /></td>
						<td><spring:message code="${course.level.i18nIdentifier}"/></td>
					</tr>
					<tr>
						<td><spring:message code="label.amountParticipants" /></td>
						<td>${course.courseParticipants.size()}</td>
					</tr>
					<tr>
						<td><spring:message code="label.teacher" /></td>
						<td>${course.teacher.lastname}&nbsp;${course.teacher.firstname}</td>
					</tr>
				</tbody>
			</table>
		</dmtags:widget>
	</dmtags:span>
</dmtags:base>
<script src="<c:url value="/js/searchBoxAutoComplete.js" />"></script>
<script type="text/javascript">
	$(document)
			.ready(

					function() {
						$(".datepicker")
								.datepicker(
										{
											showOn : "button",
											buttonImage : "/dancemanage/css/ui/images/calendar.gif",
											buttonImageOnly : true,
											dateFormat : "dd.mm.yy"
										});

						$(".close").click(
								function() {
									$(this).next('[name$="enabled"]').prop(
											'value', false);
									$(this).parent().slideUp(function() {
										sort();
									});
								});

						$("#add")
								.click(
										function(e) {
											e.preventDefault();

											var clone = $("#prototyp").clone(
													true);
											var index = $(
													'#wrapper .inputField:visible')
													.size();
											console.log(index);
											var id = "appointments" + index;
											var name = "appointments[" + index
													+ "]";
											clone
													.children(
															'[name$="enabled"]')
													.attr("id", id + ".enabled")
													.attr("name",
															name + ".enabled");
											clone.children('[name$="number"]')
													.attr("id", id + ".number")
													.attr("name",
															name + ".number")
													.val(index + 1);
											clone.children(".display-number")
													.text(index + 1);
											clone
													.children(
															'[name$="appointmentDate"]')
													.attr(
															"id",
															id
																	+ ".appointmentDate")
													.attr(
															"name",
															name
																	+ ".appointmentDate")
													.addClass("datepicker");
											clone.removeAttr("id");

											$(".form-actions").before(clone);

											clone
													.slideDown(function() {

														$(".datepicker")
																.datepicker(
																		{
																			showOn : "button",
																			buttonImage : "/dancemanage/css/ui/images/calendar.gif",
																			buttonImageOnly : true,
																			dateFormat : "dd.mm.yy"
																		});
													});

										});

						function compareDiv(a, b) {
							if (!$(a).children(".datepicker").val()) {
								return 1;
							}
							if (!$(b).children(".datepicker").val()) {
								return -1;
							}

							var valueA = $.datepicker.parseDate("dd.mm.yy",
									$(a).children(".datepicker").val());
							var valueB = $.datepicker.parseDate("dd.mm.yy",
									$(b).children(".datepicker").val());
							return valueA > valueB ? 1 : -1;
						}

						function sort() {
							var a = $('#wrapper .inputField:visible').sort(
									compareDiv);
							$.each(a, function(key, value) {
								$(value).children('[name$="number"]').val(
										key + 1);
								$(value).children(".display-number").text(
										key + 1);
								$(".form-actions").before(value);
							});
						}

						$('[name$="appointmentDate"]').change(function() {
							sort();
						});

						//TODO retrieve data ordered!
						sort();

					});
</script>
