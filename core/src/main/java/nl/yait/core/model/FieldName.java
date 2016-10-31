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
import java.util.regex.Pattern;

@Value.Immutable
@JsonSerialize(as = ImmutableFieldName.class, using = FieldName.FieldNameJsonSerializer.class)
@JsonDeserialize(as = ImmutableFieldName.class, using = FieldName.FieldNameJsonDeserializer.class)
public interface FieldName extends Comparable<FieldName> {
    Pattern NAME_RE = Pattern.compile("^[a-z](?:[a-z0-9_.-]+)$", Pattern.CASE_INSENSITIVE);
    int NAME_MAX_LENGTH = 64;

    static FieldName of(String name) {
        return ImmutableFieldName.of(name);
    }

    @Value.Parameter
    String getName();

    @Value.Check
    default void check() {
        Preconditions.checkArgument(!getName().isEmpty(), "'name' should not be empty");
        Preconditions.checkArgument(getName().length() <= NAME_MAX_LENGTH,
                "'name' should not be longer than 64 characters.");
        Preconditions.checkArgument(NAME_RE.matcher(getName()).matches(),
                "'name' should start with a letter and consist only of letters, numbers, and"
                        + " special characters _ - and .");
    }

    default int compareTo(@Nullable FieldName other) {
        if (other == null) {
            return 1;
        }
        return getName().compareTo(other.getName());
    }

    class Builder extends ImmutableFieldName.Builder {}

    class FieldNameJsonSerializer extends JsonSerializer<FieldName> {

        @Override
        public void serialize(final FieldName value, final JsonGenerator generator,
                final SerializerProvider serializers) throws IOException {
            generator.writeString(value.getName());
        }
    }

    class FieldNameJsonDeserializer extends JsonDeserializer<FieldName> {
        @Override
        public FieldName deserialize(final JsonParser parser, final DeserializationContext context)
                throws IOException {
            final String name = parser.readValueAs(String.class);
            return FieldName.of(name);
        }
    }
}
