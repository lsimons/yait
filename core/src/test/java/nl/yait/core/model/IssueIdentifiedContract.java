package nl.yait.core.model;

public class IssueIdentifiedContract extends IdentifiedContract {
    private static final ID ID = new ID.Builder()
            .value("1").build();

    @Override
    protected final Identified getInstance() {
        return new Issue.Builder().id(ID).build();
    }
}
