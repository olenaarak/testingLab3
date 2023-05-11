package com.testing;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UniqueWordsFinder {
    private final DataSource dataSource;

    public UniqueWordsFinder(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    public List<String> findUniqueWords() throws IOException {
        List<String> words = dataSource.readData();

        List<String> uniqueWords = new ArrayList<>();
        for (String word : words) {
            boolean unique = true;
            for (int i = 0; i < word.length(); i++) {
                for (int j = i + 1; j < word.length(); j++) {
                    if (word.charAt(i) == word.charAt(j)) {
                        unique = false;
                        break;
                    }
                }
            }
            if (unique) {
                uniqueWords.add(word);
            }
        }
        return uniqueWords;
    }
}

