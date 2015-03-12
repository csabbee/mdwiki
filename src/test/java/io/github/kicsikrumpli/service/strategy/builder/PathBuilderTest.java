package io.github.kicsikrumpli.service.strategy.builder;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.nio.file.Path;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

/**
 * Test class for {@link PathBuilder}.
 * @author daniel
 *
 */
public class PathBuilderTest {
    private static final String EMPTY = "";
    private static final String THIRD_ELEMENT = "third element";
    private static final String SECOND_ELEMENT = "second element";
    private static final String FIRST_ELEMENT = "firstElement";
    @InjectMocks
    private PathBuilder underTest;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void testBuildShouldCreatePathWhenSingleElementGiven() {
        // GIVEN in setUp
        
        // WHEN
        Path path = underTest.withPathElement(FIRST_ELEMENT).build();
        
        // THEN
        assertThat(path.getName(0).toString(), is(FIRST_ELEMENT));
    }
    
    @Test
    public void testBuildShouldCreatePathWhenMultipleElementsGiven() {
        // GIVEN in setUp
        
        // WHEN
        Path path = underTest.withPathElement(FIRST_ELEMENT)
                .withPathElement(SECOND_ELEMENT)
                .withPathElement(THIRD_ELEMENT)
                .build();
        
        // THEN
        assertThat(path.getName(0).toString(), is(FIRST_ELEMENT));
        assertThat(path.getName(1).toString(), is(SECOND_ELEMENT));
        assertThat(path.getName(2).toString(), is(THIRD_ELEMENT));
    }
    
    @Test
    public void testBuildShouldReturnEmptyPathWhenNoPathElementGiven() {
        // GIVEN in setUp
        
        // WHEN
        Path path = underTest.build();
        
        // THEN
        assertThat(path.getFileName().toString(), is(EMPTY));
    }
}
