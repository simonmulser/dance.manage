<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="dmtags"%>
<spring:message var="i18nStatistics" code="nav.statistics" />
<dmtags:base title="${i18nStatistics}" activesection="statistics">

	<dmtags:span width="6">
		<spring:message var="i18nList" code="widget.participantsPerStyle" />
		<dmtags:widget title="${i18nList}" icon="icon-bar-chart">
			<canvas id="canvasStyle" height="450" width="450"></canvas>
		</dmtags:widget>
	</dmtags:span>

	<dmtags:span width="6">
		<spring:message var="i18nList" code="widget.participantsPerLevel" />
		<dmtags:widget title="${i18nList}" icon="icon-bar-chart">
			<canvas id="canvasLevel" height="450" width="450"></canvas>
			<table style="margin: 0 auto;">
				<tr>
					<td><div id="level2012" class="levelLegend"></div></td>
					<td>2012</td>
					<td><div id="level2013" class="levelLegend"></div></td>
					<td>2013</td>
				</tr>
			</table>
		</dmtags:widget>
	</dmtags:span>
	<dmtags:span width="12">
		<spring:message var="i18nList" code="widget.statistic" />
		<dmtags:widget title="${i18nList}" style="table" icon="icon-bar-chart"
			pdfLink="statistics/viewCourseStatisticsPdf">
			<display:table name="statistics" id="row"
				class="table table-striped table-bordered displaytag" pagesize="15"
				requestURI="/admin/statistics" defaultsort="1">
				<display:column sortable="true" titleKey="label.coursename">
					<c:out value="${row.name}" />
				</display:column>
				<display:column sortable="true" titleKey="label.teacher">
					<c:out value="${row.teacher.firstname} ${row.teacher.lastname}" />
				</display:column>
				<display:column sortable="true" titleKey="label.amountParticipants">
					<c:out value="${row.courseParticipants.size()}" />
				</display:column>
				<display:column sortable="true" titleKey="label.year">
					<c:out value="${row.year}" />
				</display:column>
			</display:table>
		</dmtags:widget>

	</dmtags:span>
</dmtags:base>

<script src="<c:url value="/js/searchBoxAutoComplete.js" />"></script>
<script>
	function rand(min, max) {
		return parseInt(Math.random() * (max - min + 1), 10) + min;
	}

	function get_random_color() {
		var h = rand(30, 30);
		var s = rand(40, 80);
		var l = rand(40, 80);
		return 'hsl(' + h + ',' + s + '%,' + l + '%)';
	}

	var pieData = new Array();
	<c:forEach items="${participantsPerStyleList}" var="participant">
	var participant = "${participant}";
	var participantSplit = participant.split(",");
	pieData.push({
		value : parseInt(participantSplit[1]),
		color : get_random_color(),
		label : participantSplit[0],
		labelColor : 'black',
		labelFontSize : '1.4em',
		labelAlign : 'center'

	});
	</c:forEach>

	var myChart = new Chart(document.getElementById("canvasStyle").getContext(
			"2d"));
	var myPie = myChart.Pie(pieData, {
		animationSteps : 100,
		animationEasing : 'easeOutBounce'
	});
	var courseLevels = new Array();
	<c:forEach items="${courseLevels}" var="level">
	courseLevels.push(String("${level}"));
	</c:forEach>
	var count1 = new Array();
	<c:forEach items="${participantsPerLevelActualYear}" var="count1">
	count1.push(parseInt("${count1}"));
	</c:forEach>

	var barChartData = {
		labels : courseLevels,
		datasets : [ {
			fillColor : "rgba(220,220,220,0.5)",
			strokeColor : "rgba(220,220,220,1)",
			data : <c:out value="${participantsPerLevelLastYear}" />
		}, {
			fillColor : "rgb(255, 159, 64)",
			strokeColor : "rgba(220,220,220,1)",
			data : <c:out value="${participantsPerLevelActualYear}" />
		} ]

	};
	var steps = 5;
	var max = parseInt("${maxScale}");
	var options = {
		barDatasetSpacing : 10,
		scaleGridLineColor : "rgba(0,0,0,.10)",
		scaleOverride : true,
		scaleSteps : steps,
		scaleStepWidth : Math.ceil(max / steps),
		scaleStartValue : 0

	};

	var myLine = new Chart(document.getElementById("canvasLevel").getContext(
			"2d")).Bar(barChartData, options);
</script>
