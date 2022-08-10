package ro.ubb.utils;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class Utils {

    private final Integer LEN = 8;
    private final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    private SecureRandom rnd = new SecureRandom();

    public String randomString() {
        StringBuilder sb = new StringBuilder(LEN);
        for (int i = 0; i < LEN; i++)
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        return sb.toString();
    }
}
