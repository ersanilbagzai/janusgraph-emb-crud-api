package com.techframer.janusgraph.api.crud.utils;

import java.util.UUID;

public class UUIDUtils {

    private UUIDUtils() {

    }

    public static String get() {
        return UUID.randomUUID().toString();
    }
}
