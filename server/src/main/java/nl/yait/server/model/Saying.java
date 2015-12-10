package nl.yait.server.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

@Value.Immutable
@JsonSerialize(as = ImmutableSaying.class)
@JsonDeserialize(as = ImmutableSaying.class)
public interface Saying {
    long getId();

    String getContent();

    class Builder extends ImmutableSaying.Builder {}
}
