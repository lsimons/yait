package nl.yait.core.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.jackson.Jackson;
import org.junit.Test;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.assertj.core.api.Assertions.assertThat;

public final class IssueTest {
    private static final ID AN_ID = new ID.Builder()
            .value("1").build();
    private static final Issue AN_ISSUE = new Issue.Builder()
            .id(AN_ID).build();
    private static final Issue ISSUE_WITH_FIELDS = new Issue.Builder()
            .id(AN_ID)
            .addFields(Field.of("summary", "Example issue"))
            .addFields(Field.of("description", "Longer description of this example issue."))
            .build();
    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

    @Test
    public void serializesToJSON() throws Exception {
        String expected = MAPPER.writeValueAsString(
                MAPPER.readValue(fixture("fixtures/issue.json"), Issue.class));
        String serialized = MAPPER.writeValueAsString(AN_ISSUE);
        assertThat(serialized).isEqualTo(expected);
    }

    @Test
    public void deSerializesFromJSON() throws Exception {
        Issue deserialized = MAPPER.readValue(fixture("fixtures/issue.json"), Issue.class);
        assertThat(deserialized).isEqualTo(AN_ISSUE);
    }

    @Test
    public void serializesToJSONWithFields() throws Exception {
        String expected = MAPPER.writeValueAsString(
                MAPPER.readValue(fixture("fixtures/issueWithFields.json"), Issue.class));
        String serialized = MAPPER.writeValueAsString(ISSUE_WITH_FIELDS);
        assertThat(serialized).isEqualTo(expected);
    }

    @Test
    public void deSerializesFromJSONWithFields() throws Exception {
        Issue deserialized = MAPPER.readValue(
                fixture("fixtures/issueWithFields.json"), Issue.class);
        assertThat(deserialized).isEqualTo(ISSUE_WITH_FIELDS);
    }

    @Test
    public void compareTo() throws Exception {
        assertThat(AN_ISSUE.compareTo(AN_ISSUE) == 0);
        assertThat(AN_ISSUE.compareTo(ISSUE_WITH_FIELDS) == 0);
        assertThat(AN_ISSUE.compareTo(new Issue.Builder().id(ID.of("2")).build()) == -1);
        assertThat(new Issue.Builder().id(ID.of("2")).build().compareTo(AN_ISSUE) == 1);
        assertThat(AN_ISSUE.compareTo(null) == 1);
    }
}
