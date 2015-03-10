package io.github.kicsikrumpli.service.strategy.resolver;

import static org.mockito.BDDMockito.given;
import io.github.kicsikrumpli.service.strategy.resolver.HomeDirectoryResolver;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

/**
 * Test class for {@link HomeDirectoryResolver}.
 * @author kicsikrumpli1
 *
 */
public class HomeDirectoryResolverTest {
    @Spy
    @InjectMocks
    private HomeDirectoryResolver underTest;
    
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void testResolveShouldRemoveLeadingTildeWhenUserHomeSystemPropertyNotSet() {
        // GIVEN
        given(underTest.getUserHome()).willThrow(new IllegalArgumentException());
        
        // WHEN
        String resolvedPath = underTest.resolveHome("~dummy-path");
        
        // THEN
        MatcherAssert.assertThat(resolvedPath, Matchers.is("dummy-path"));
    }
    
    @Test
    public void testResolveShouldReplaceLeadingTildeWhenUserHomeSystemPropertySet() {
        // GIVEN
        given(underTest.getUserHome()).willReturn("/user/home");
        
        // WHEN
        String resolvedPath = underTest.resolveHome("~/dummy-path");
        
        // THEN
        MatcherAssert.assertThat(resolvedPath, Matchers.is("/user/home/dummy-path"));
    }
    
    @Test
    public void testResolveShouldLeaveNonLeadingTildeUnchanged() {
        // GIVEN
        given(underTest.getUserHome()).willReturn("/user/home");
        
        // WHEN
        String resolvedPath = underTest.resolveHome("/dummy~path");
        
        // THEN
        MatcherAssert.assertThat(resolvedPath, Matchers.is("/dummy~path"));
    }
}
