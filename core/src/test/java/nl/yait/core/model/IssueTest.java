package nl.yait.core.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.jackson.Jackson;
import org.junit.Test;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.assertj.core.api.Assertions.assertThat;

public final class IssueTest {
    private static final ID ID = new ID.Builder()
            .value("1").build();
    private static final Issue ISSUE = new Issue.Builder()
            .id(ID).build();

    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

    @Test
    public void serializesToJSON() throws Exception {
        String expected = MAPPER.writeValueAsString(
                MAPPER.readValue(fixture("fixtures/issue.json"), Issue.class));
        String serialized = MAPPER.writeValueAsString(ISSUE);
        assertThat(serialized).isEqualTo(expected);
    }

    @Test
    public void deSerializesFromJSON() throws Exception {
        Issue expected = MAPPER.readValue(fixture("fixtures/issue.json"), Issue.class);
        assertThat(expected).isEqualTo(ISSUE);
    }
}
