package nl.yait.core.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.jackson.Jackson;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.assertj.core.api.Assertions.assertThat;

public final class FieldNameTest {

    private static final FieldName A_FIELD_NAME = new FieldName.Builder()
            .name("myFieldName").build();

    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

    @Rule
    public final ExpectedException expectedException = ExpectedException.none();

    @Test
    public void nonEmpty() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("name");
        expectedException.expectMessage("should not be empty");

        new FieldName.Builder().name("").build();
    }

    @Test
    public void maxLength() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("name");
        expectedException.expectMessage("should not be longer than");

        new FieldName.Builder().name(
                "a123456789a123456789a123456789a123456789a123456789a123456789a123456789").build();
    }

    @Test
    public void validCharacters() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("name");
        expectedException.expectMessage("consist only of");

        new FieldName.Builder().name("name with spaces").build();
    }

    @Test
    public void startWithCharacter() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("name");
        expectedException.expectMessage("should start with");

        new FieldName.Builder().name("01234abc").build();
    }

    @Test
    public void serializesToJSON() throws Exception {
        String expected = MAPPER.writeValueAsString(
                MAPPER.readValue(fixture("fixtures/fieldName.json"), FieldName.class));
        String serialized = MAPPER.writeValueAsString(A_FIELD_NAME);
        assertThat(serialized).isEqualTo(expected);
    }

    @Test
    public void deSerializesFromJSON() throws Exception {
        FieldName expected = MAPPER.readValue(fixture("fixtures/fieldName.json"), FieldName.class);
        assertThat(expected).isEqualTo(A_FIELD_NAME);
    }

    @Test
    public void of() throws Exception {
        assertThat(FieldName.of("foo")).isNotNull();
    }

    @Test
    public void compareTo() throws Exception {
        assertThat(A_FIELD_NAME.compareTo(A_FIELD_NAME) == 0);
        assertThat(A_FIELD_NAME.compareTo(FieldName.of("other")) != 0);
        assertThat(A_FIELD_NAME.compareTo(null) == 1);
    }
}
