package com.olenick.avatar.util;

import java.util.Arrays;
import java.util.List;

public class SafeArrays {
    @SafeVarargs
    @SuppressWarnings("varargs")
    public static <T> List<T> asList(T... elements) {
        if (elements == null) {
            return null;
        } else {
            return Arrays.asList(elements);
        }
    }
}
