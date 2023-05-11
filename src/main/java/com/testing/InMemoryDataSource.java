package com.testing;

import java.io.IOException;
import java.util.List;

public class InMemoryDataSource implements DataSource {
    private final List<String> data;

    public InMemoryDataSource(List<String> data) {
        this.data = data;
    }

    @Override
    public List<String> readData() throws IOException {
        return data;
    }
}
