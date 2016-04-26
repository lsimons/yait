package nl.yait.server.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
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
        Long longValue = Long.parseLong(getValue());
        return longValue;
    }

    default boolean isLong() {
        try {
            asLong();
            return true;
        } catch(NumberFormatException e) {
            return false;
        }
    }

    class Builder extends ImmutableID.Builder {}
}
