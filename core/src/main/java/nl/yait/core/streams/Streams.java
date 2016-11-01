package nl.yait.core.streams;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

public final class Streams {
    /**
     * Check whether the provided stream has any duplicate elements.
     *
     * @param stream the stream to check for duplicates
     * @param <T> any type
     * @return true if a duplicate was found, false otherwise
     */
    public static <T> boolean containsDuplicate(final Stream<T> stream) {
        Set<T> set = new HashSet<>();
        return !stream.allMatch(set::add);
    }

    /**
     * Check whether the provided stream has any duplicate elements.
     *
     * @param stream the stream to check for duplicates
     * @param <T> any type
     * @return true if a duplicate was found, false otherwise
     */
    public static <T> boolean containsDuplicate(final Stream<T> stream) {
        Set<T> set = new HashSet<>();
        return !stream.allMatch(set::add);
    }

    private Streams() {}
}
