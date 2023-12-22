package ru.artorium.storages.utils;

public class TextUtils {

    public static String getCollectionName(Class<?> clazz) {
        final StringBuilder builder = new StringBuilder();
        final String className = clazz.getSimpleName();

        for (int i = 0; i < className.length(); i++) {
            final char c = className.charAt(i);

            if (i == 0) {
                builder.append(c);
                continue;
            }

            if (Character.isUpperCase(c)) {
                builder.append("_");
            }

            builder.append(c);
        }

        return builder.toString().toLowerCase();
    }

}
