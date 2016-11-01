package nl.yait.core.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.jackson.Jackson;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("OptionalGetWithoutIsPresent")
public final class NameTest {

    private static final Name A_NAME = new Name.Builder()
            .name("myFieldName").build();
    private static final Name A_NAME_WITH_NAMESPACE = new Name.Builder()
            .name("myFieldName")
            .namespace(Namespace.of(Namespaces.YAIT_CORE.getNamespace())).build();

    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

    @Rule
    public final ExpectedException expectedException = ExpectedException.none();

    @Test
    public void nonEmpty() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("name");
        expectedException.expectMessage("should not be empty");

        new Name.Builder().name("").build();
    }

    @Test
    public void maxLength() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("name");
        expectedException.expectMessage("should not be longer than");

        new Name.Builder().name(
                "a123456789a123456789a123456789a123456789a123456789a123456789a123456789").build();
    }

    @Test
    public void validCharacters() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("name");
        expectedException.expectMessage("consist only of");

        new Name.Builder().name("name with spaces").build();
    }

    @Test
    public void startWithCharacter() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("name");
        expectedException.expectMessage("should start with");

        new Name.Builder().name("01234abc").build();
    }

    @Test
    public void serializesToJSON() throws Exception {
        String expected = MAPPER.writeValueAsString(
                MAPPER.readValue(fixture("fixtures/name.json"), Name.class));
        String serialized = MAPPER.writeValueAsString(A_NAME);
        assertThat(serialized).isEqualTo(expected);
    }

    @Test
    public void deSerializesFromJSON() throws Exception {
        Name deserialized = MAPPER.readValue(
                fixture("fixtures/name.json"), Name.class);
        assertThat(deserialized).isEqualTo(A_NAME);
    }

    @Test
    public void of() throws Exception {
        assertThat(Name.of("foo")).isNotNull();
    }

    @Test
    public void ofWithNamespace() throws Exception {
        assertThat(Name.of("foo", Namespaces.YAIT_CORE).getNamespace().get())
                .isEqualTo(Namespaces.YAIT_CORE);
        assertThat(A_NAME).isNotEqualTo(A_NAME_WITH_NAMESPACE);
    }

    @Test
    public void serializesWithNamespace() throws Exception {
        String expected = MAPPER.writeValueAsString(
                MAPPER.readValue(fixture("fixtures/nameWithNamespace.json"), Name.class));
        String serialized = MAPPER.writeValueAsString(A_NAME_WITH_NAMESPACE);
        assertThat(serialized).isEqualTo(expected);
    }

    @Test
    public void deSerializesWithNamespace() throws Exception {
        Name deserialized = MAPPER.readValue(
                fixture("fixtures/nameWithNamespace.json"), Name.class);
        assertThat(deserialized).isEqualTo(A_NAME_WITH_NAMESPACE);
    }

    @Test
    public void compareTo() throws Exception {
        assertThat(A_NAME.compareTo(A_NAME) == 0);
        assertThat(A_NAME.compareTo(Name.of("other")) != 0);
        assertThat(A_NAME.compareTo(null) == 1);

        assertThat(A_NAME.compareTo(A_NAME_WITH_NAMESPACE) == -1);
        assertThat(A_NAME_WITH_NAMESPACE.compareTo(A_NAME) == 1);
        assertThat(A_NAME_WITH_NAMESPACE.compareTo(A_NAME_WITH_NAMESPACE) == 0);
    }
}
