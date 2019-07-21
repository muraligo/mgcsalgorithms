package com.m3.csalgorithms.vendingmachine.model;

import java.util.ArrayList;
import java.util.List;

public class ItemAndChange {
    private final String _message;
    private final Product _product;
    private final List<Coin> _change = new ArrayList<Coin>();

    public ItemAndChange(String themessage) {
        _product = null;
        _message = themessage;
    }

    public ItemAndChange(Product theproduct, String themessage) {
        _product = theproduct;
        _message = themessage;
    }

    public ItemAndChange addChange(List<Coin> coins) {
        _change.addAll(coins);
        return this;
    }

    public List<Coin> getChange() { return _change; }
    public Product getProduct() { return _product; }
    public String getMessage() { return _message; }
}
