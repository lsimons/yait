package nl.yait.core.model;

import com.google.common.base.Preconditions;
import com.neovisionaries.i18n.LanguageCode;

import java.util.stream.IntStream;

@SuppressWarnings("NullableProblems")
public final class LocalizedString implements CharSequence {
    private String value;
    private LanguageCode language;

    public LocalizedString(final String value) {
        this(value, LanguageCode.undefined);
    }

    public LocalizedString(final String value, final LanguageCode language) {
        Preconditions.checkNotNull(value);
        Preconditions.checkNotNull(language);
        this.value = value;
        this.language = language;
    }

    public LanguageCode getLanguage() {
        return language;
    }

    @Override
    public int length() {
        return value.length();
    }

    @Override
    public char charAt(final int index) {
        return value.charAt(index);
    }

    @Override
    public CharSequence subSequence(final int start, final int end) {
        return value.subSequence(start, end);
    }

    @Override
    public IntStream chars() {
        return value.chars();
    }

    @Override
    public IntStream codePoints() {
        return value.codePoints();
    }

    @Override
    @SuppressWarnings("MagicNumber")
    public int hashCode() {
        int result = value.hashCode();
        result = 31 * result + language.hashCode();
        return result;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof LocalizedString)) {
            return false;
        }

        final LocalizedString that = (LocalizedString)other;

        return value.equals(that.value) && language == that.language;
    }

    @Override
    public String toString() {
        return value;
    }
}
