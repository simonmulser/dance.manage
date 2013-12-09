package at.danceandfun.util;

import static org.junit.Assert.*;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.junit.Test;

import at.danceandfun.dao.CourseDaoTest;
import at.danceandfun.entity.Course;

public class HelpersTest {

	@Test
	public void testGetCurrentWeekWeekdayRepresentation() {
		LocalDate today = new LocalDate();
		LocalDate result = Helpers.getCurrentWeekWeekdayRepresentation(today
				.getDayOfWeek());
		assertEquals(today, result);
	}

	@Test
	public void testGetCourseStartDateTimeCurrentWeekRepresentation() {
		Course course = CourseDaoTest.getValidCourse();
		DateTime result = Helpers
				.getCourseStartDateTimeCurrentWeekRepresentation(course);
		assertEquals(course.getTime().getHourOfDay(), result.getHourOfDay());
		assertEquals(course.getTime().getMinuteOfHour(),
				result.getMinuteOfHour());
		assertEquals(course.getTime().getSecondOfMinute(),
				result.getSecondOfMinute());
		assertEquals(course.getTime().getMillisOfSecond(),
				result.getMillisOfSecond());
		assertEquals((int) course.getWeekday().getValue() + 1,
				result.getDayOfWeek());

		LocalDate now = new LocalDate();
		LocalDate weekDayOfThisWeek = now.withDayOfWeek(course.getWeekday()
				.getValue() + 1);
		assertEquals(weekDayOfThisWeek.getDayOfWeek(), result.getDayOfWeek());
		assertEquals(weekDayOfThisWeek.getMonthOfYear(),
				result.getMonthOfYear());
		assertEquals(weekDayOfThisWeek.getWeekOfWeekyear(),
				result.getWeekOfWeekyear());
		assertEquals(weekDayOfThisWeek.getYear(), result.getYear());
	}
	
	@Test
	public void testGetCourseSEndDateTimeCurrentWeekRepresentation() {	    
	}
}
