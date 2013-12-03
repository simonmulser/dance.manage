package at.danceandfun.enumeration;

public class PatternConstants {

    public static final String CHARACTER_PATTERN = "^\\p{L}*[-|[ ]]?\\p{L}+$";
    public static final String CHARACTER_PATTERN_CONTACT = "^\\p{L}*[-|[ ]]?\\p{L}+[ ]\\p{L}*[-|[ ]]?\\p{L}+$";
    public static final String EMAIL_PATTERN = "^[a-z0-9-]+[\\.|_]?[\\._a-z0-9-]+@[a-z0-9-]+\\.[a-z]{2,4}$";
    public static final String CITY_PATTERN = "^\\p{L}*([\\.|-]?[ ]?\\p{L}+)*$";
    
    private PatternConstants() {

    }

}
