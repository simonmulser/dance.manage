package at.danceandfun.service;

import org.junit.Test;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

public class PasswordEncodeHelper {

    private PasswordEncoder passwordEncoder = new StandardPasswordEncoder();

    @Test
    public void generatePassword() {
        System.out.println(passwordEncoder.encode("abc"));
    }

}
