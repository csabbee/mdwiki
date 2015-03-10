package io.github.kicsikrumpli.service.strategy.builder;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyString;
import io.github.kicsikrumpli.service.strategy.resolver.HomeDirectoryResolver;

import java.nio.file.Path;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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
    private static final String RESOLVED = "resolved";
    @InjectMocks
    private PathBuilder underTest;
    @Mock
    private HomeDirectoryResolver mockHomeDirResolver;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        given(mockHomeDirResolver.resolveHome(anyString())).willReturn(RESOLVED);
    }
    
    @Test
    public void testBuildShouldResolveHomeInFirstPathElement() {
        // GIVEN in setUp
        
        // WHEN
        Path path = underTest.withPathElement(FIRST_ELEMENT).build();
        
        // THEN
        assertThat(path.getName(0).toString(), is(RESOLVED));
    }
    
    @Test
    public void testBuildShouldNotResolveHomeInSubsequentElements() {
        // GIVEN in setUp
        
        // WHEN
        Path path = underTest
                .withPathElement(FIRST_ELEMENT)
                .withPathElement(SECOND_ELEMENT)
                .withPathElement(THIRD_ELEMENT)
                .build();
        
        // THEN
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
