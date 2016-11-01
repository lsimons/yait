package nl.yait.core.model;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public abstract class NamedContract {

    @Test
    public final void returnsANonNullName() {
        assertNotNull("name member is not null", getInstance().getName());
    }

    protected abstract Named getInstance();

}
