package at.danceandfun.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

import at.danceandfun.enumeration.PatternConstants;

public class PatternTester {

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

        assertThat("Max ".matches(PatternConstants.CHARACTER_PATTERN),
                is(false));
        assertThat("Max-".matches(PatternConstants.CHARACTER_PATTERN),
                is(false));
        assertThat("Max -".matches(PatternConstants.CHARACTER_PATTERN),
                is(false));
        assertThat(
                "Max - Patrick ".matches(PatternConstants.CHARACTER_PATTERN),
                is(false));
        assertThat("Max-Patrick-".matches(PatternConstants.CHARACTER_PATTERN),
                is(false));
        assertThat("Max1234".matches(PatternConstants.CHARACTER_PATTERN),
                is(false));
        assertThat("Max!?".matches(PatternConstants.CHARACTER_PATTERN),
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

}
