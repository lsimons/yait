package nl.yait.core.model;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public abstract class IdentifiedContract {

    @Test
    public final void returnsANonNullID() {
        assertNotNull("id member is not null", getInstance().getId());
    }

    @Test
    public final void supportsAtLeastOneExternalForm() {
        ID id = getInstance().getId();
        assertTrue("id member has an external form", id.isLong() || id.isURI() || id.isURL());
    }

    protected abstract Identified getInstance();

}
