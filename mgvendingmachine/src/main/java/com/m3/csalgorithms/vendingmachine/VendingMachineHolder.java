package com.m3.csalgorithms.vendingmachine;

import java.util.List;

import com.m3.csalgorithms.vendingmachine.model.Coin;
import com.m3.csalgorithms.vendingmachine.model.Product;

public class VendingMachineHolder {
    private static VendingMachine _machine;

    static void createVendingMachine(List<Coin> coins, List<Product> products) {
        _machine = new VendingMachine(coins, products);
    }

    public static VendingMachine getVendingMachine() { return _machine; }
}
