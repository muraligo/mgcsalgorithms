package com.m3.csalgorithms.vendingmachine.model;

public enum Product {
    COKE(25),
    PEPSI(35),
    SODA(45);

    private final int _value;

    private Product(int thevalue) {
        _value = thevalue;
    }

    public int productValue() { return _value; }

    public String camelCaseName() {
        String nm = name();
        return nm.substring(0, 1) + nm.substring(1).toLowerCase();
    }
}
