package nl.yait.core.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

import javax.annotation.Nullable;
import java.util.SortedSet;

@Value.Immutable
@JsonSerialize(as = ImmutableIssue.class)
@JsonDeserialize(as = ImmutableIssue.class)
public interface Issue extends Identified, Comparable<Issue> {

    @Value.NaturalOrder
    SortedSet<Field> getFields();

    default int compareTo(@Nullable Issue other) {
        if (other == null) {
            return 1;
        }
        return getId().compareTo(other.getId());
    }

    class Builder extends ImmutableIssue.Builder {}
}
