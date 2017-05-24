package pl.com.c4m.android.flags;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class Flags {
    public static final int INVALID_DRAWABLE_ID = 0;
    private static final String RESOURCE_NAME = "drawable";
    private static final Set<String> availableCountryCodes = new HashSet<>(Arrays.asList(Locale.getISOCountries()));
    private static final Map<String, String> customMappings = new HashMap<>();

    static {
        customMappings.put("DO", "_do");
    }

    /**
     * @param context     application context used to obtain Android {@link Resources} instance
     * @param countryCode 2-letter country code as defined in ISO 3166
     * @return drawable resource ID or {@link #INVALID_DRAWABLE_ID} if drawable is not available
     */
    @DrawableRes
    public static int resourceFromCode(@NonNull Context context, @NonNull String countryCode) {
        checkNotNull(context, "context is null");
        checkNotNull(countryCode, "countryCode is null");

        if (isCodeAvailable(countryCode)) {
            String resourceIdentifierName = getIdentifierName(countryCode);
            return context.getResources().getIdentifier(resourceIdentifierName, RESOURCE_NAME, context.getPackageName());
        } else {
            return INVALID_DRAWABLE_ID;
        }
    }

    @SuppressWarnings("deprecation")
    @Nullable
    public static Drawable drawableFromCode(Context context, String countryCode) {
        checkNotNull(context, "context is null");
        checkNotNull(countryCode, "countryCode is null");

        int resourceId = resourceFromCode(context, countryCode);
        if (resourceId != INVALID_DRAWABLE_ID) {
            return context.getResources().getDrawable(resourceId);
        } else {
            return null;
        }
    }

    /**
     * Method returns an unmodifiable set of all 2-letter country codes.
     * The source of those is {@link Locale#getISOCountries()}.
     * Country codes are uppercase Strings.
     *
     * @return set of country codes
     */
    public static Set<String> getAvailableCountryCodes() {
        return Collections.unmodifiableSet(availableCountryCodes);
    }

    private static void checkNotNull(Object object, String message) {
        if (object == null) {
            throw new IllegalArgumentException(message);
        }
    }

    private static String getIdentifierName(String countryCode) {
        String countryCodeUpper = countryCode.toUpperCase(Locale.ENGLISH);
        String resourceIdentifierName;
        if (customMappings.containsKey(countryCodeUpper)) {
            resourceIdentifierName = customMappings.get(countryCodeUpper);
        } else {
            resourceIdentifierName = countryCode.toLowerCase(Locale.ENGLISH);
        }
        return resourceIdentifierName;
    }

    private static boolean isCodeAvailable(String countryCode) {
        return availableCountryCodes.contains(countryCode.toUpperCase(Locale.ENGLISH));
    }
}
