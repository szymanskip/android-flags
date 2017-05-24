package pl.com.c4m.android.flags.app;

/**
 * @author Pawel Szymanski
 */

public class Country {
    private final String code;
    private final String displayName;

    public Country(String code, String displayName) {
        if (code == null) {
            throw new IllegalArgumentException("code is null");
        }
        if (displayName == null) {
            throw new IllegalArgumentException("displayName is null");
        }
        this.code = code;
        this.displayName = displayName;
    }

    public String getCode() {
        return code;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Country country = (Country) o;

        return code.equals(country.code) && displayName.equals(country.displayName);

    }

    @Override
    public int hashCode() {
        int result = code.hashCode();
        result = 31 * result + displayName.hashCode();
        return result;
    }
}
