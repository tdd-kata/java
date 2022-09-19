package com.markruler.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SecurityUtils {

    private SecurityUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * @see <a href="https://find-sec-bugs.github.io/bugs.htm#WEAK_MESSAGE_DIGEST_MD5">MD2, MD4 and MD5 are weak hash functions</a>
     */
    public static byte[] hash(final String password)
            throws NoSuchAlgorithmException {
        MessageDigest md5Digest = MessageDigest.getInstance("MD5");
        md5Digest.update(password.getBytes());
        return md5Digest.digest();
    }
}
