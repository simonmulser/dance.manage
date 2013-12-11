package at.danceandfun.util;

public class PatternConstants {

    public static final String CHARACTER_PATTERN = "^\\p{L}+[-[ ]\\p{L}]*$";
    public static final String CHARACTER_NUMBER_PATTERN = "^[\\p{L}\\p{N}]+[\\.-[ ]/\\p{L}}\\p{N}]*$";
    public static final String CHARACTER_PATTERN_CONTACT = "(^$)|(^\\p{L}{3,}(-\\p{L}+)?[ ]\\p{L}{3,}(-\\p{L}+)?$)";
    public static final String ADDRESS_NUMBER_PATTERN = "^\\p{N}+(\\p{L}|-\\p{N}+)?$";
    public static final String EMAIL_PATTERN = "^[\\p{L}\\p{N}]+([\\.|_]?[\\p{L}\\p{N}]+)*@[\\p{L}\\p{N}-]+\\.[\\p{L}]{2,4}$";
    public static final String CITY_PATTERN = "^\\p{L}+[\\.-[ ]\\p{L}]*$";
    public static final String TELEPHONE_PATTERN = "^((\\+|00)[1-9])?\\p{N}*$";
    // public static final String SVNR_PATTERN =
    // "^[\\p{N}\\p{L}]+((\\.|-|[ ])?[\\p{N}\\p{L}]+)*";
    public static final String SVNR_PATTERN = "^\\p{N}*$";

    private PatternConstants() {

    }

}
