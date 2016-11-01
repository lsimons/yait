package nl.yait.core.model;

import java.util.Collections;
import java.util.List;

public final class Namespaces {
    public static final Namespace YAIT_CORE = Namespace.of("urn:x-yait:core", "yait");

    private static final List<Namespace> KNOWN_NAMESPACES = Collections.singletonList(YAIT_CORE);

    private Namespaces() {}

    public static Namespace withWellKnownPrefix(final Namespace namespace) {
        for (Namespace knownNamespace : KNOWN_NAMESPACES) {
            if (knownNamespace.getNamespace().equals(namespace.getNamespace())) {
                return knownNamespace;
            }
        }
        return namespace;
    }
}
