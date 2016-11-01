package nl.yait.core.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.jackson.Jackson;
import org.junit.Test;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.assertj.core.api.Assertions.assertThat;

public class SayingTest {

    private static final Saying SAYING = new Saying.Builder()
            .id(1).content("Hello, Stranger!").build();

    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

    @Test
    public final void serializesToJSON() throws Exception {
        String expected = MAPPER.writeValueAsString(
                MAPPER.readValue(fixture("fixtures/saying.json"), Saying.class));
        String serialized = MAPPER.writeValueAsString(SAYING);
        assertThat(serialized).isEqualTo(expected);
    }

    @Test
    public final void deSerializesFromJSON() throws Exception {
        Saying deserialized = MAPPER.readValue(fixture("fixtures/saying.json"), Saying.class);
        assertThat(deserialized).isEqualTo(SAYING);
    }
}
