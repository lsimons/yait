package nl.yait.core.model;

public class FieldNamedContract extends NamedContract {
    @Override
    protected final Field getInstance() {
        return Field.of("myFieldName", "Some Value");
    }
}
