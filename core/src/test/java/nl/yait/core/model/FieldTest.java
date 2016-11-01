package nl.yait.core.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.jackson.Jackson;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.assertj.core.api.Assertions.assertThat;

public final class FieldTest {

    private static final FieldName A_FIELD_NAME = FieldName.of("myFieldName");
    private static final Object A_VALUE = "Some Value";
    private static final Field A_FIELD = new Field.Builder()
            .name(A_FIELD_NAME).value(A_VALUE).build();
    private static final LinkedHashMap<String, Object> COMPLEX_VALUE = new LinkedHashMap<>();
    private static final Field A_FIELD_WITH_COMPLEX_VALUE = new Field.Builder()
            .name(A_FIELD_NAME).value(COMPLEX_VALUE).build();
    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

    static {
        final List<String> anArray = Arrays.asList("an", "array");
        COMPLEX_VALUE.put("aProperty", anArray);
    }

    @Rule
    public final ExpectedException expectedException = ExpectedException.none();

    @Test
    public void serializesToJSON() throws Exception {
        String expected = MAPPER.writeValueAsString(
                MAPPER.readValue(fixture("fixtures/field.json"), Field.class));
        String serialized = MAPPER.writeValueAsString(A_FIELD);
        assertThat(serialized).isEqualTo(expected);
    }

    @Test
    public void deSerializesFromJSON() throws Exception {
        Field deserialized = MAPPER.readValue(fixture("fixtures/field.json"), Field.class);
        assertThat(deserialized).isEqualTo(A_FIELD);
    }

    @Test
    public void serializesToJSONWithComplexValue() throws Exception {
        String expected = MAPPER.writeValueAsString(
                MAPPER.readValue(fixture("fixtures/fieldWithComplexValue.json"), Field.class));
        String serialized = MAPPER.writeValueAsString(A_FIELD_WITH_COMPLEX_VALUE);
        assertThat(serialized).isEqualTo(expected);
    }

    @Test
    public void deSerializesFromJSONWithComplexValue() throws Exception {
        Field deserialized = MAPPER.readValue(
                fixture("fixtures/fieldWithComplexValue.json"), Field.class);
        assertThat(deserialized.getName()).isEqualTo(A_FIELD_WITH_COMPLEX_VALUE.getName());
        Map map = (Map)deserialized.getValue();
        List someArray = (List) map.get("aProperty");
        String someString = (String) someArray.get(0);
        assertThat(someString).isEqualTo("an");
    }

    @Test
    public void of() {
        assertThat(Field.of(A_FIELD_NAME, A_VALUE)).isNotNull();
    }

    @Test
    public void ofString() {
        assertThat(Field.of(A_FIELD_NAME.getName(), A_VALUE)).isNotNull();
    }

    @Test
    public void compareTo() throws Exception {
        assertThat(A_FIELD.compareTo(A_FIELD) == 0);
        assertThat(A_FIELD.compareTo(Field.of("other", "value")) != 0);
        assertThat(A_FIELD.compareTo(null) == 1);

        assertThat(A_FIELD.compareTo(Field.of("myFieldName", "zValue")) == 1);
        assertThat(A_FIELD.compareTo(Field.of("myFieldName", "aValue")) == -1);
        assertThat(A_FIELD.compareTo(Field.of("myFieldName", new Object())) == 0);
        assertThat(Field.of("myFieldName", new Object()).compareTo(A_FIELD) == 0);
    }
}
