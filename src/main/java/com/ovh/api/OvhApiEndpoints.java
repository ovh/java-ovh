package com.ovh.api;

/**
 * @author Florian Pradines <florian.pradines@gmail.com>
 */
public enum OvhApiEndpoints {
    OVH_EU("https://eu.api.ovh.com/1.0"),
    OVH_CA("https://ca.api.ovh.com/1.0"),
    KIMSUFI_EU("https://eu.api.kimsufi.com/1.0"),
    KIMSUFI_CA("https://ca.api.kimsufi.com/1.0"),
    SOYOUSTART_EU("https://eu.api.soyoustart.com/1.0"),
    SOYOUSTART_CA("https://ca.api.soyoustart.com/1.0"),
    RUNABOVE("https://api.runabove.com/1.0"),
    RUNAVOVE_CA("https://api.runabove.com/1.0");

    private final String url;

    OvhApiEndpoints(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public static OvhApiEndpoints fromString(String name) {
        for (OvhApiEndpoints value: values()) {
            if (value.toString().equalsIgnoreCase(name)) {
                return value;
            }
        }

        return null;
    }
}
