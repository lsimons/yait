package nl.yait.core.model;

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
import java.util.Optional;
import java.util.regex.Pattern;

@Value.Immutable
@JsonSerialize(as = ImmutableName.class, using = Name.FieldNameJsonSerializer.class)
@JsonDeserialize(as = ImmutableName.class, using = Name.FieldNameJsonDeserializer.class)
public interface Name extends Comparable<Name> {
    Pattern NAME_RE = Pattern.compile("^[a-z](?:[a-z0-9_.-]+)$", Pattern.CASE_INSENSITIVE);
    int NAME_MAX_LENGTH = 64;

    static Name of(String name) {
        return ImmutableName.of(name, Optional.empty());
    }

    static Name of(String name, Namespace namespace) {
        return ImmutableName.of(name, Optional.of(namespace));
    }

    @Value.Parameter
    String getName();

    @Value.Parameter
    Optional<Namespace> getNamespace();

    @Value.Check
    default void check() {
        Preconditions.checkArgument(!getName().isEmpty(), "'name' should not be empty");
        Preconditions.checkArgument(getName().length() <= NAME_MAX_LENGTH,
                "'name' should not be longer than 64 characters.");
        Preconditions.checkArgument(NAME_RE.matcher(getName()).matches(),
                "'name' should start with a letter and consist only of letters, numbers, and"
                        + " special characters _ - and .");
    }

    default int compareTo(@Nullable Name other) {
        if (other == null) {
            return 1;
        }
        final Optional<Namespace> namespace = getNamespace();
        final Optional<Namespace> otherNamespace = other.getNamespace();
        if (namespace.isPresent()) {
            if (otherNamespace.isPresent()) {
                int namespaceCompare = namespace.get().compareTo(otherNamespace.get());
                if (namespaceCompare != 0) {
                    return namespaceCompare;
                }
                return getName().compareTo(other.getName());
            } else {
                return 1;
            }
        } else {
            if (otherNamespace.isPresent()) {
                return -1;
            } else {
                return getName().compareTo(other.getName());
            }
        }
    }

    class Builder extends ImmutableName.Builder {}

    class FieldNameJsonSerializer extends JsonSerializer<Name> {

        @Override
        public void serialize(final Name value, final JsonGenerator generator,
                final SerializerProvider serializers) throws IOException {
            String fullName = value.getName();
            final Optional<Namespace> namespace = value.getNamespace();
            if (namespace.isPresent()) {
                fullName = "{" + namespace.get().getNamespace() + "}" + fullName;
            }
            generator.writeString(fullName);
        }
    }

    class FieldNameJsonDeserializer extends JsonDeserializer<Name> {
        @Override
        public Name deserialize(final JsonParser parser, final DeserializationContext context)
                throws IOException {
            final String fullName = parser.readValueAs(String.class);
            if (fullName.isEmpty() || !fullName.startsWith("{")) {
                return Name.of(fullName);
            } else {
                int endNamespace = fullName.indexOf("}");
                if (endNamespace == -1) {
                    throw new IllegalArgumentException("Cannot parse field name,"
                            + " namespace prefix has { but not }");
                }
                final String namespaceString = fullName.substring(1, endNamespace);
                final String name = fullName.substring(endNamespace + 1);
                final Namespace namespace = Namespace.of(namespaceString);
                return Name.of(name, namespace);
            }
        }
    }
}
