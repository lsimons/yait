package nl.yait.server.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.jackson.Jackson;
import org.junit.Test;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.assertj.core.api.Assertions.assertThat;

public class SayingTest {

    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

    @Test
    public void serializesToJSON() throws Exception {
        Saying saying = new Saying(1, "Hi!");
        String expected = MAPPER.writeValueAsString(
                MAPPER.readValue(fixture("fixtures/saying.json"), Saying.class));
        String serialized = MAPPER.writeValueAsString(saying);
        assertThat(serialized).isEqualTo(expected);
    }

    @Test
    public void deSerializesFromJSON() throws Exception {
        Saying saying = new Saying(1, "Hi!");
        Saying expected = MAPPER.readValue(fixture("fixtures/saying.json"), Saying.class);
        assertThat(expected).isEqualTo(saying);
    }
}
