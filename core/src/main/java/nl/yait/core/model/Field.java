package nl.yait.core.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

import javax.annotation.Nullable;

@Value.Immutable
@JsonSerialize(as = ImmutableField.class)
@JsonDeserialize(as = ImmutableField.class)
public interface Field extends Comparable<Field> {
    static Field of(FieldName name, Object value) {
        return ImmutableField.of(name, value);
    }

    static Field of(String name, Object value) {
        return ImmutableField.of(FieldName.of(name), value);
    }

    static Field of(Namespace namespace, String name, Object value) {
        return ImmutableField.of(FieldName.of(name, namespace), value);
    }

    @Value.Parameter
    FieldName getName();

    @Value.Parameter
    Object getValue();

    default int compareTo(@Nullable Field other) {
        if (other == null) {
            return 1;
        }
        int nameCompare = getName().compareTo(other.getName());
        if (nameCompare != 0) {
            return nameCompare;
        }

        Object value = getValue();
        Object otherValue = other.getValue();
        if (value instanceof Comparable && value.getClass().isInstance(otherValue)) {
            Comparable comparableValue = (Comparable) value;
            //noinspection unchecked
            return comparableValue.compareTo(otherValue);
        }
        return 0;
    }

    class Builder extends ImmutableField.Builder {}
}
