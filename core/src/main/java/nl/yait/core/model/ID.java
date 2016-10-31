package nl.yait.core.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.base.Preconditions;
import org.immutables.value.Value;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

@Value.Immutable
@JsonSerialize(as = ImmutableID.class)
@JsonDeserialize(as = ImmutableID.class)
public interface ID {
    String getValue();

    @Value.Check
    default void check() {
        Preconditions.checkArgument(!getValue().isEmpty(), "'value' should not be empty");
    }

    default URI asURI() throws URISyntaxException {
        String value = getValue();
        return new URI(value);
    }

    default boolean isURI() {
        try {
            asURI();
            return true;
        } catch (URISyntaxException e) {
            return false;
        }
    }

    default URL asURL() throws MalformedURLException {
        String value = getValue();
        return new URL(value);
    }

    default boolean isURL() {
        try {
            asURL();
            return true;
        } catch (MalformedURLException e) {
            return false;
        }
    }

    default long asLong() throws NumberFormatException {
        return Long.parseLong(getValue());
    }

    default boolean isLong() {
        try {
            asLong();
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    class Builder extends ImmutableID.Builder {}
}
