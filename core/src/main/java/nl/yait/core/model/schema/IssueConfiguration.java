package nl.yait.core.model.schema;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.base.Preconditions;
import nl.yait.core.model.Named;
import nl.yait.core.streams.Streams;
import org.immutables.value.Value;

import java.util.SortedSet;

@Value.Immutable
@JsonSerialize(as = ImmutableIssueConfiguration.class)
@JsonDeserialize(as = ImmutableIssueConfiguration.class)
public interface IssueConfiguration extends Named {

    @Value.NaturalOrder
    SortedSet<FieldConfiguration> getFieldConfigurations();

    @Value.Check
    default void check() {
        final SortedSet<FieldConfiguration> fieldConfigurations = getFieldConfigurations();
        final boolean hasDuplicateFieldName = Streams.containsDuplicate(
                fieldConfigurations.stream().map(FieldConfiguration::getFieldName));
        Preconditions.checkArgument(!hasDuplicateFieldName, "field names should be unique");
    }

    class Builder extends ImmutableIssueConfiguration.Builder {}
}
