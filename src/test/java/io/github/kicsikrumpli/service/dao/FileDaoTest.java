package io.github.kicsikrumpli.service.dao;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.BDDMockito.willThrow;
import io.github.kicsikrumpli.dao.FileDao;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;

/**
 * Test class for {@link FileDao}.
 * @author kicsikrumpli1
 *
 */
public class FileDaoTest {
	private static final String DEFAULT_LINE_SEPARATOR = "#";
	private static final String SECOND_LINE = "second-line";
	private static final String FIRST_LINE = "first-line";
	private static final String DUMMY_CONTENT = "first-line#second-line";
	@Spy
	@InjectMocks
	private FileDao underTest;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		underTest.setDefaultCharset(Charset.defaultCharset());
		underTest.setDefaultLineSeparator(DEFAULT_LINE_SEPARATOR);
	}
	
	@Test
	public void testReadFileShouldReturnAbsentWhenFileDoesNotExist() throws IOException {
		// GIVEN
		willThrow(new IOException()).given(underTest).readAllLines(Mockito.any(Path.class));
		
		// WHEN
		Optional<String> fileContent = underTest.readFile(null);
		
		// THEN
		assertThat(fileContent.isPresent(), is(false));
	}
	
	@Test
	public void testReadFileShouldConcatenateLinesWithSeparatorWhenFileExists() throws IOException {
		// GIVEN
		List<String> dummyContent = Lists.newArrayList(FIRST_LINE, SECOND_LINE);
		willReturn(dummyContent).given(underTest).readAllLines(Mockito.any(Path.class));
		
		// WHEN
		Optional<String> fileContent = underTest.readFile(null);
		
		// THEN
		assertThat(fileContent.get(), is(DUMMY_CONTENT));
	}
}
