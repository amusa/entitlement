package com.cosm.common.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * Created by maliska on 8/7/16.
 */
public class Sha256Encoder {

    public String encode(String text) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(text.getBytes(StandardCharsets.UTF_8));

        byte authBytes[] = md.digest();

        final String encoded = Base64.getEncoder().encodeToString(authBytes);
        return encoded;
    }
}
