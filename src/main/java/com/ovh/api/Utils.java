package com.ovh.api;

import java.io.File;

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
}
