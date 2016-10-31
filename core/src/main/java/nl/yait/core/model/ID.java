package nl.yait.core.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.base.Preconditions;
import org.immutables.value.Value;

import javax.annotation.Nullable;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

@Value.Immutable
@JsonSerialize(as = ImmutableID.class)
@JsonDeserialize(as = ImmutableID.class)
public interface ID extends Comparable<ID> {
    static ID of(String value) {
        return ImmutableID.of(value);
    }

    @Value.Parameter
    String getValue();

    @Value.Check
    default void check() {
        Preconditions.checkArgument(!getValue().isEmpty(), "'value' should not be empty");
    }

    default URI asURI() throws URISyntaxException {
        String value = getValue();
        return new URI(value);
    }

    @JsonIgnore
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

    @JsonIgnore
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

    @JsonIgnore
    default boolean isLong() {
        try {
            asLong();
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    default int compareTo(@Nullable ID other) {
        if (other == null) {
            return 1;
        }
        return getValue().compareTo(other.getValue());
    }

    class Builder extends ImmutableID.Builder {}
}
