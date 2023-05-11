package com.testing;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class FileDataSource implements DataSource {
    private final String fileName;

    public FileDataSource(String fileName) {
        this.fileName = fileName;
    }

    public List<String> readData() throws IOException {
        ArrayList<String> words = new ArrayList<>();
        Scanner inputFile = null;
        String nextLine;

        inputFile = new Scanner(Paths.get(fileName));
        while (inputFile.hasNext()) {
            nextLine = inputFile.nextLine();
            if (nextLine.isEmpty()) {
                nextLine = " ";
            }
            List<String> wordsInLine = Arrays.asList(nextLine.split("[\\s(),.!?:;@|-]+"));

            for (String word : wordsInLine) {
                word = word.toLowerCase();
                if (word.length() > 30) {
                    word = word.substring(0, 30);
                }
                boolean unique = true;
                if (words.contains(word) || word.isEmpty()) {
                    unique = false;
                }
                for (int i = 0; i < word.length(); i++) {
                    for (int j = i + 1; j < word.length(); j++) {
                        if (word.charAt(i) == word.charAt(j)) {
                            unique = false;
                            break;
                        }

                    }
                }
                if (unique) {
                    words.add(word);
                }
            }
        }
        return words;
    }
}
