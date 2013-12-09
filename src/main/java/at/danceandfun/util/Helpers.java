package at.danceandfun.util;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import at.danceandfun.entity.Course;

public class Helpers {

    public static LocalDate getCurrentWeekWeekdayRepresentation(int weekDay) {
        LocalDate now = new LocalDate();
        return now.withDayOfWeek(weekDay);
    }

    public static DateTime getCourseStartDateTimeCurrentWeekRepresentation(
            Course course) {
        DateTime result = getCurrentWeekWeekdayRepresentation(
                course.getWeekday().getValue() + 1).toDateTimeAtStartOfDay();
        result = result.withHourOfDay(course.getTime().getHourOfDay());
        result = result.withMinuteOfHour(course.getTime().getMinuteOfHour());
        result = result
                .withSecondOfMinute(course.getTime().getSecondOfMinute());
        result = result
                .withMillisOfSecond(course.getTime().getMillisOfSecond());
        return result;
    }

    public static DateTime getCourseEndDateTimeCurrentWeekRepresentation(
            Course course) {
        DateTime result = getCourseStartDateTimeCurrentWeekRepresentation(course);
        result = result.plusMinutes(course.getDuration().getValue());
        return result;
    }
}
