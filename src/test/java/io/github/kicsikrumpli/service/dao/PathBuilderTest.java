package io.github.kicsikrumpli.service.dao;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import io.github.kicsikrumpli.service.HomeDirectoryResolver;
import io.github.kicsikrumpli.service.PathBuilder;

import java.nio.file.Path;

import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

/**
 * Test class for {@link PathBuilder}.
 * @author kicsikrumpli1
 *
 */
public class PathBuilderTest {
	private static final String BEGINS_WITH_TILDE = "^~";
	private static final String TILDE = "~";
	private static final String EMPTY = "";
	private static final String HOME = "HOME";
	@InjectMocks
	private PathBuilder underTest;
	@Mock
	private HomeDirectoryResolver mockResolver;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		BDDMockito.given(mockResolver.resolveHome(Mockito.anyString())).willAnswer(new Answer<String>() {
			@Override
			public String answer(InvocationOnMock invocation) throws Throwable {
				String param = invocation.getArgumentAt(0, String.class);
				return param.replaceAll(BEGINS_WITH_TILDE, HOME);
			}
		});
	}
	
	@Test
	public void testBuildShouldReturnEmptyPathWhenNoPathElementPresent() {
		// GIVEN
		
		// WHEN
		Path path = underTest.build();

		// THEN
		assertThat(path.getName(0).toString(), is(EMPTY));
	}
	
	@Test
	public void testBuildShoudResolveHomeWhenFirstElementHasHomeSymbol() {
		// GIVEN
		underTest.withPathElement(TILDE);
		
		// WHEN
		Path path = underTest.build();
		
		// THEN
		assertThat(path.toString(), is(HOME));
	}
	
	@Test
	public void testBuildShouldNotResolveHomeWhenSecondElementHasHomeSymbol() {
		// GIVEN
		underTest.withPathElement("first");
		underTest.withPathElement(TILDE);
		
		// WHEN
		Path path = underTest.build();
		
		// THEN
		assertThat(path.endsWith(TILDE), is(true));
	}
}
