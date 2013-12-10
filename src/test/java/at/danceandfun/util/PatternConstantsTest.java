package at.danceandfun.util;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

public class PatternConstantsTest {

    @Test
    public void testNamePattern() {
        assertThat("Max".matches(PatternConstants.CHARACTER_PATTERN),
                is(true));
        assertThat("Günther".matches(PatternConstants.CHARACTER_PATTERN),
                is(true));
        assertThat("Eva-Maria".matches(PatternConstants.CHARACTER_PATTERN),
                is(true));
        assertThat("Karl Heinz".matches(PatternConstants.CHARACTER_PATTERN),
                is(true));
        assertThat("Max1234".matches(PatternConstants.CHARACTER_PATTERN),
                is(false));
        assertThat("Max!?".matches(PatternConstants.CHARACTER_PATTERN),
                is(false));
        assertThat(".Max".matches(PatternConstants.CHARACTER_PATTERN),
                is(false));
        assertThat("-Max".matches(PatternConstants.CHARACTER_PATTERN),
                is(false));
    }

    @Test
    public void testContactPersonPattern() {
        assertThat(
                "Max Mustermann"
                        .matches(PatternConstants.CHARACTER_PATTERN_CONTACT),
                is(true));
        assertThat(
                "Max-Patrick Mustermann"
                        .matches(PatternConstants.CHARACTER_PATTERN_CONTACT),
                is(true));
        assertThat(
                "Max Mustermann-Friedrich"
                        .matches(PatternConstants.CHARACTER_PATTERN_CONTACT),
                is(true));
        assertThat(
                "Max Patrick Mustermann-Friedrich"
                        .matches(PatternConstants.CHARACTER_PATTERN_CONTACT),
                is(true));

        assertThat("Max ".matches(PatternConstants.CHARACTER_PATTERN_CONTACT),
                is(false));
        assertThat("Max-".matches(PatternConstants.CHARACTER_PATTERN_CONTACT),
                is(false));
        assertThat("Max -".matches(PatternConstants.CHARACTER_PATTERN_CONTACT),
                is(false));
        assertThat(
                "Max - Patrick "
                        .matches(PatternConstants.CHARACTER_PATTERN_CONTACT),
                is(false));
        assertThat(
                "Max-Patrick-"
                        .matches(PatternConstants.CHARACTER_PATTERN_CONTACT),
                is(false));
        assertThat(
                "Max1234".matches(PatternConstants.CHARACTER_PATTERN_CONTACT),
                is(false));
        assertThat("Max!?".matches(PatternConstants.CHARACTER_PATTERN_CONTACT),
                is(false));
    }

    @Test
    public void testEmailPattern() {
        assertThat(
                "max.mustermann@mail.com"
                        .matches(PatternConstants.EMAIL_PATTERN),
                is(true));
        assertThat(
                "max_mustermann@mail.com"
                        .matches(PatternConstants.EMAIL_PATTERN),
                is(true));
        assertThat(
                "maxmustermann@mail.at".matches(PatternConstants.EMAIL_PATTERN),
                is(true));
        assertThat(
                "max.karl.mustermann@mail.at"
                        .matches(PatternConstants.EMAIL_PATTERN),
                is(true));
        assertThat(
                "max_karl_mustermann@mail.at"
                        .matches(PatternConstants.EMAIL_PATTERN),
                is(true));
        assertThat(
                ".maxmustermann@mail.com"
                        .matches(PatternConstants.EMAIL_PATTERN),
                is(false));
        assertThat(
                "_maxmustermann@mail.com"
                        .matches(PatternConstants.EMAIL_PATTERN),
                is(false));
        assertThat(
                "max.mustermann@.com".matches(PatternConstants.EMAIL_PATTERN),
                is(false));
        assertThat(
                "max.mustermann@mail".matches(PatternConstants.EMAIL_PATTERN),
                is(false));
        assertThat(
                "max.mustermann@mail.mustermann"
                        .matches(PatternConstants.EMAIL_PATTERN),
                is(false));
    }

    @Test
    public void testCityPattern() {
        assertThat("Wien".matches(PatternConstants.CITY_PATTERN), is(true));
        assertThat("München".matches(PatternConstants.CITY_PATTERN), is(true));
        assertThat("St. Peter".matches(PatternConstants.CITY_PATTERN), is(true));
        assertThat("Hall in Tirol".matches(PatternConstants.CITY_PATTERN),
                is(true));
        assertThat("New York-City".matches(PatternConstants.CITY_PATTERN),
                is(true));
        assertThat("-New York-City".matches(PatternConstants.CITY_PATTERN),
                is(false));
        assertThat(".New York-City".matches(PatternConstants.CITY_PATTERN),
                is(false));
    }

    @Test
    public void testAddressNumberPattern() {
        assertThat("10".matches(PatternConstants.ADDRESS_NUMBER_PATTERN),
                is(true));
        assertThat("10A".matches(PatternConstants.ADDRESS_NUMBER_PATTERN),
                is(true));
        assertThat("10-12".matches(PatternConstants.ADDRESS_NUMBER_PATTERN),
                is(true));
        assertThat("10A-12".matches(PatternConstants.ADDRESS_NUMBER_PATTERN),
                is(false));
        assertThat("10-12-".matches(PatternConstants.ADDRESS_NUMBER_PATTERN),
                is(false));
        assertThat("10-12A".matches(PatternConstants.ADDRESS_NUMBER_PATTERN),
                is(false));
        assertThat("10ABC".matches(PatternConstants.ADDRESS_NUMBER_PATTERN),
                is(false));
    }
    
    @Test
    public void testTelephonePattern() {
        assertThat("06801111111".matches(PatternConstants.TELEPHONE_PATTERN),
                is(true));
        assertThat("00436801111111".matches(PatternConstants.TELEPHONE_PATTERN),
                is(true));
        assertThat("+436801111111".matches(PatternConstants.TELEPHONE_PATTERN),
                is(true));
        assertThat("+ABC1111111".matches(PatternConstants.TELEPHONE_PATTERN),
                is(false));
        assertThat("0680AAAAAAA".matches(PatternConstants.TELEPHONE_PATTERN),
                is(false));
        assertThat(
                "+0043680244".matches(PatternConstants.TELEPHONE_PATTERN),
                is(false));
    }
    
    @Test
    public void testSVNRPattern() {
        assertThat("1234 1234".matches(PatternConstants.SVNR_PATTERN), is(true));
        assertThat("1234-1234".matches(PatternConstants.SVNR_PATTERN), is(true));
        assertThat("1234-AB34".matches(PatternConstants.SVNR_PATTERN), is(true));
        assertThat("1234-1234-1234".matches(PatternConstants.SVNR_PATTERN),
                is(true));

    }
}
