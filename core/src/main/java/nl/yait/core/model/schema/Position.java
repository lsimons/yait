package nl.yait.core.model.schema;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.base.Preconditions;
import org.immutables.value.Value;

import javax.annotation.Nullable;

@Value.Immutable
@JsonSerialize(as = ImmutablePosition.class)
@JsonDeserialize(as = ImmutablePosition.class)
public interface Position extends Comparable<Position> {
    static Position of(int value) {
        return ImmutablePosition.of(value);
    }

    @Value.Parameter
    int getValue();

    @Value.Check
    default void check() {
        Preconditions.checkArgument(getValue() >= 0, "'value' should be 0 or larger");
    }

    default int compareTo(@Nullable Position other) {
        if (other == null) {
            return 1;
        }
        return Integer.compare(getValue(), other.getValue());
    }

    class Builder extends ImmutablePosition.Builder {}
}
