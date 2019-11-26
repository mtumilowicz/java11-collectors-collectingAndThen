import com.google.common.collect.ImmutableList;
import org.junit.Test;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class CollectingAndThenTest {
    @Test
    public void toGuavaCollections() {
        ImmutableList<Integer> collect = Stream.of(1)
                .collect(Collectors.collectingAndThen(Collectors.toList(), ImmutableList::copyOf));

        assertNotNull(collect);
    }

    @Test
    public void findMaxOfPositiveIntegersStream_maxNotExists() {
        Integer max = Stream.of(-1)
                .filter(x -> x >= 0)
                .collect(Collectors.collectingAndThen(
                        /*
                        without <Integer> - compilation error
                        
                        Error:(28, 36) java: method collectingAndThen in class java.util.stream.Collectors 
                        cannot be applied to given types;
                          required: java.util.stream.Collector<T,A,R>,java.util.function.Function<R,RR>
                          found: java.util.stream.Collector<T,capture#1 of ?,java.util.Optional<T>>,(x)->x.orElse(-1)
                          reason: inferred type does not conform to equality constraint(s)
                            inferred: T
                            equality constraints(s): T
                         */
                        Collectors.maxBy(Comparator.<Integer>naturalOrder()),
                        x -> x.orElse(-1)));

        assertThat(max, is(-1));
    }

    @Test
    public void findMaxOfPositiveIntegersStream_maxExists() {
        Integer max = Stream.of(1, 2, 3)
                .filter(x -> x >= 0)
                .collect(Collectors.collectingAndThen(
                        Collectors.maxBy(Comparator.<Integer>naturalOrder()),
                        x -> x.orElse(-1)));

        assertThat(max, is(3));
    }

    @Test
    public void checkIfListAfterCollectionIsNotEmpty_nonEmpty() {
        assertFalse(Stream.of(1)
                .collect(Collectors.collectingAndThen(
                        Collectors.toList(),
                        List::isEmpty)));
    }

    @Test
    public void checkIfListAfterCollectionIsNotEmpty_empty() {
        assertTrue(Stream.of(1)
                .filter(integer -> integer > 10)
                .collect(Collectors.collectingAndThen(
                        Collectors.toList(),
                        List::isEmpty)));
    }
}
