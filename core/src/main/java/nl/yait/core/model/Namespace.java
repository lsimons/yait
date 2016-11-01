package nl.yait.core.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.base.Preconditions;
import org.immutables.value.Value;

import javax.annotation.Nullable;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

@Value.Immutable
@JsonSerialize(as = ImmutableNamespace.class, using = Namespace.NamespaceJsonSerializer.class)
@JsonDeserialize(as = ImmutableNamespace.class, using = Namespace.NamespaceJsonDeserializer.class)
public interface Namespace {

    static Namespace of(String namespace) {
        return ImmutableNamespace.of(namespace, Optional.empty());
    }

    static Namespace of(String namespace, String defaultPrefix) {
        return ImmutableNamespace.of(namespace, Optional.of(defaultPrefix));
    }

    static Namespace of(URI uri) {
        return of(uri.toString());
    }

    @Value.Parameter
    String getNamespace();

    @Value.Parameter
    Optional<String> getDefaultPrefix();

    @Value.Check
    default void check() {
        Preconditions.checkArgument(!getNamespace().isEmpty(), "'namespace' should not be empty");
        Preconditions.checkArgument(isURI(), "'namespace' should be a valid URI");
    }

    default URI asURI() throws URISyntaxException {
        String namespace = getNamespace();
        return new URI(namespace);
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

    default int compareTo(@Nullable Namespace other) {
        if (other == null) {
            return 1;
        }
        return getNamespace().compareTo(other.getNamespace());
    }

    class Builder extends ImmutableNamespace.Builder {}

    class NamespaceJsonSerializer extends JsonSerializer<Namespace> {

        @Override
        public void serialize(final Namespace value, final JsonGenerator generator,
                final SerializerProvider serializers) throws IOException {
            generator.writeString(value.getNamespace());
        }
    }

    class NamespaceJsonDeserializer extends JsonDeserializer<Namespace> {
        @Override
        public Namespace deserialize(final JsonParser parser, final DeserializationContext context)
                throws IOException {
            final String namespace = parser.readValueAs(String.class);
            return Namespace.of(namespace);
        }
    }

}
