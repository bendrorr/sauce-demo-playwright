package com.automation.framework.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Optional;
import java.util.function.Supplier;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NullUtil {

    public static <T> T getOrNull(Supplier<T> supplier) {
        try {
            return supplier.get();
        } catch (Exception exception) {
            return null;
        }

    }

    public static <T> Optional<T> getNonNull(Supplier<T> supplier) {
        try {
            return Optional.of(supplier.get());
        } catch (Exception exception) {
            return Optional.empty();
        }

    }

    public static <T> T getOrDefault(Supplier<T> supplier, T defaultValue) {
        try {
            return supplier.get();
        } catch (Exception exception) {
            return defaultValue;
        }

    }
}
