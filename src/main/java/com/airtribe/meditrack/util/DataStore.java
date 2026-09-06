package com.airtribe.meditrack.util;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Generic in-memory store keyed by an id extracted from each item via
 * {@code idExtractor}, e.g. {@code new DataStore<>(Doctor::getId)}.
 */
public class DataStore<T> {

    private final Map<Integer, T> items = new LinkedHashMap<>();
    private final Function<T, Integer> idExtractor;

    public DataStore(Function<T, Integer> idExtractor) {
        this.idExtractor = idExtractor;
    }

    public void add(T item) {
        items.put(idExtractor.apply(item), item);
    }

    public T getById(int id) {
        return items.get(id);
    }

    public boolean removeById(int id) {
        return items.remove(id) != null;
    }

    public List<T> getAll() {
        return new ArrayList<>(items.values());
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }
}
