package com.m3.csalgorithms.vendingmachine.model;

public enum Coin {
    PENNY(1),
    NICKEL(5),
    DIME(10),
    QUARTER(25);

    private final int _value;

    private Coin(int thevalue) {
        _value = thevalue;
    }

    public int coinValue() { return _value; }
}
