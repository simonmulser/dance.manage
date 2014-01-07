package at.danceandfun.util;

import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.Locale;
import java.util.regex.Pattern;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import at.danceandfun.entity.Course;
import at.danceandfun.exception.BusinessException;

public class Helpers {

    public static final String PASSWORD_FOR_DUMMY_ACCOUNTS = "ca92ddbb320e5ba3d78ff898445ab385298bb69fd7129e1c05bf30f48e407821ebc2ee3ae372047b";
    private static final Pattern NONLATIN = Pattern.compile("[^\\w-]");
    private static final Pattern WHITESPACE = Pattern.compile("[\\s]");

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

    public static String toSlug(String input) {
        String nowhitespace = WHITESPACE.matcher(input).replaceAll("-");
        String normalized = Normalizer.normalize(nowhitespace, Form.NFD);
        String slug = NONLATIN.matcher(normalized).replaceAll("");
        return slug.toLowerCase(Locale.ENGLISH);
    }

    public static Integer extractId(String slug) {
        if (slug == null) {
            throw new BusinessException("slug is null");
        }

        String[] pieces = slug.split("-");
        try {
            int id = Integer.parseInt(pieces[pieces.length - 1]);
            return id;
        } catch (NumberFormatException e) {
            throw new BusinessException("last part of slug is not an integer");
        }
    }
}
