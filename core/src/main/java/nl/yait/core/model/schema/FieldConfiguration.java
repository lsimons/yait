package nl.yait.core.model.schema;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.neovisionaries.i18n.LanguageCode;
import nl.yait.core.model.Identified;
import nl.yait.core.model.Name;
import org.immutables.value.Value;

import javax.annotation.Nullable;
import java.util.Map;

@Value.Immutable
@JsonSerialize(as = ImmutableFieldConfiguration.class)
@JsonDeserialize(as = ImmutableFieldConfiguration.class)
public interface FieldConfiguration extends Identified, Comparable<FieldConfiguration> {

    Name getFieldName();

    Map<LanguageCode, String> getDescriptions();

    Position getPosition();

    default boolean isRequired() {
        return false;
    }

    default int compareTo(@Nullable FieldConfiguration other) {
        if (other == null) {
            return 1;
        }
        int positionCompare = getPosition().compareTo(other.getPosition());
        if (positionCompare != 0) {
            return positionCompare;
        }
        return getFieldName().compareTo(other.getFieldName());
    }

    class Builder extends ImmutableFieldConfiguration.Builder {}
}
