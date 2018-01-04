package com.ovh.api;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Florian Pradines <florian.pradines@gmail.com>
 */
public final class Utils {

    private Utils() {}

    public static File getConfigFile(String... names) {
        if (names == null || names.length == 0) {
            return null;
        }

        for (String name: names) {
            File file = new File(name);
            if (file.exists() && file.canRead()) {
                return file;
            }
        }

        return null;
    }

    public static String sha1Hex(String text) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        md.update(text.getBytes("iso-8859-1"), 0, text.length());

        StringBuilder sb = new StringBuilder();
        byte[] sha1hash = md.digest();
        for (byte sha1HashByte : sha1hash) {
            sb.append(Integer.toString((sha1HashByte & 0xff) + 0x100, 16).substring(1));
        }

        return sb.toString();
    }
}
