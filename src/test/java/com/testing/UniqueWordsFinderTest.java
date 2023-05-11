package com.testing;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.*;

public class UniqueWordsFinderTest {

    // для мок об’єкту задається принаймні 3 сценарії, один з яких буде стосуватись обробки виключень;
    // 1 сценарій
    @Test
    public void testFindUniqueWords2() throws IOException {
        DataSource mockDataSource = mock(DataSource.class);
        when(mockDataSource.readData()).thenReturn(Arrays.asList("hello", "world", "java", "unique", "words"));

        UniqueWordsFinder uniqueWordsFinder = new UniqueWordsFinder(mockDataSource);
        List<String> uniqueWords = uniqueWordsFinder.findUniqueWords();

        assertEquals(Arrays.asList("world", "words"), uniqueWords);
    }

    UniqueWordsFinder uniqueWordsFinder;
    DataSource dataSource;
    @BeforeEach
    public void setUp() {
        dataSource = mock(DataSource.class);
        uniqueWordsFinder = new UniqueWordsFinder(dataSource);
    }

    // 2 сценарій
    @Test
    public void testFindUniqueWords1() throws IOException {
        List<String> words = Arrays.asList("hello", "world", "java");
        when(dataSource.readData()).thenReturn(words);

        List<String> uniqueWords = uniqueWordsFinder.findUniqueWords();
        assertEquals(Arrays.asList("world"), uniqueWords);
    }

    // 3 сценарій
    @Test
    public void testFindUniqueWordsUniqueWords() throws IOException {
        DataSource mockDataSource = mock(DataSource.class);
        when(mockDataSource.readData()).thenReturn(Arrays.asList("hello", "world", "java"));

        UniqueWordsFinder uniqueWordsFinder = new UniqueWordsFinder(mockDataSource);
        List<String> uniqueWords = uniqueWordsFinder.findUniqueWords();

        assertEquals(1, uniqueWords.size());
    }

    //сценарій за якого мок об’єкт має згенерувати виключення.
    @Test
    public void testFindUniqueWordsThrowsIOException() throws IOException {
        DataSource mockDataSource = mock(DataSource.class);
        when(mockDataSource.readData()).thenThrow(new IOException("Error reading data"));

        UniqueWordsFinder uniqueWordsFinder = new UniqueWordsFinder(mockDataSource);

        try {
            uniqueWordsFinder.findUniqueWords();
            fail("Expected an IOException to be thrown");
        } catch (IOException e) {
            assertEquals("Error reading data", e.getMessage());
        }
    }

    //  має бути реалізована перевірка того, що метод було викликано певну кількість разів;
    @Test
    public void testFindUniqueWordsReadDataCalledOnce() throws IOException {
        DataSource mockDataSource = mock(DataSource.class);
        when(mockDataSource.readData()).thenReturn(Arrays.asList("hello", "world", "java", "unique", "words"));

        UniqueWordsFinder uniqueWordsFinder = new UniqueWordsFinder(mockDataSource);
        uniqueWordsFinder.findUniqueWords();

        verify(mockDataSource, times(1)).readData();
    }


    //продемонстрована ініціалізація за допомогою анотацій (@Mock @InjectMocks);
    @Mock
    DataSource dataSource2;
    @InjectMocks
    UniqueWordsFinder uniqueWordsFinder2;
    @BeforeEach
    void initMocks(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testWithMock() throws IOException {
        when(dataSource2.readData()).thenReturn(Arrays.asList("hello", "world", "java", "unique", "words"));
        List<String> uniqueWords = uniqueWordsFinder2.findUniqueWords();
        assertEquals(Arrays.asList("world", "words"), uniqueWords);
    }


    // використаний частковий мок об’єкт @Spy.
    @Spy
    private DataSource dataSourceSpy;

    @Test
    public void testFindUniqueWords() throws IOException {

        //перевизначаємо метод readData() за допомогою doReturn()
        doReturn(Arrays.asList("hello", "world", "world")).when(dataSourceSpy).readData();

        UniqueWordsFinder uniqueWordsFinderSpy = new UniqueWordsFinder(dataSourceSpy);
        List<String> uniqueWords = uniqueWordsFinderSpy.findUniqueWords();

        assertEquals(2, uniqueWords.size());
        assertTrue(uniqueWords.contains("world"));
    }

}
