package com.m3.csalgorithms.vendingmachine.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class Inventory<T extends Enum<T>> {
    private final ConcurrentMap<String, List<T>> _items = new ConcurrentHashMap<String, List<T>>();

    public void add(T theitem) {
        synchronized (_items) {
            List<T> values = null;
            if (_items.containsKey(theitem.name())) {
                values = _items.get(theitem.name());
            } else {
                values = new ArrayList<T>();
                _items.put(theitem.name(), values);
            }
            values.add(theitem);
        }
    }

    public T remove(T theitem) {
        synchronized (_items) {
            if (_items.containsKey(theitem.name())) {
                List<T> values = _items.get(theitem.name());
                if (!values.isEmpty()) {
                    return values.remove(0);
                }
            }
            return null;
        }
    }

    public boolean has(T theitem) {
        return _items.containsKey(theitem.name()) && !_items.get(theitem.name()).isEmpty();
    }

    public void clear() {
        synchronized (_items) {
            _items.forEach((k, v) -> {
                List<T> itms = (List<T>)v;
                itms.clear();
            });
            _items.clear();
        }
    }
}
