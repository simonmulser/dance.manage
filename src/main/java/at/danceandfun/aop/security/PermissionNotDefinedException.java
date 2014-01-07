package at.danceandfun.aop.security;

public class PermissionNotDefinedException extends RuntimeException {

    public PermissionNotDefinedException(String string) {
        super(string);
    }

}
