package at.danceandfun.util;

public class PatternConstants {

    public static final String CHARACTER_PATTERN = "^\\p{L}+[\\.-[ ]\\p{L}]*$";
    public static final String CHARACTER_NUMBER_PATTERN = "^[\\p{L}\\p{N}]+[\\.-[ ]/\\p{L}}\\p{N}]*$";
    public static final String CHARACTER_PATTERN_CONTACT = "^\\p{L}*[-|[ ]]?\\p{L}+[ ]\\p{L}*[-|[ ]]?\\p{L}+$";
    public static final String ADDRESS_NUMBER_PATTERN = "^\\p{N}*(\\p{L}|-\\p{N}+)?";
    public static final String EMAIL_PATTERN = "^[a-z0-9-]+[\\.|_]?[\\._a-z0-9-]+@[a-z0-9-]+\\.[a-z]{2,4}$";
    public static final String CITY_PATTERN = "^\\p{L}+[\\.-[ ]\\p{L}]*$";
    public static final String TELEPHONE_PATTERN = "^((\\+|00)[1-9])?\\p{N}*";
    public static final String SVNR_PATTERN = "^[\\p{N}\\p{L}]+((\\.|-|[ ])?[\\p{N}\\p{L}]+)*";
    

    private PatternConstants() {

    }

}
