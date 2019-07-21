package com.m3.csalgorithms.vendingmachine;

import java.util.ArrayList;
import java.util.List;

import com.m3.csalgorithms.vendingmachine.model.Coin;
import com.m3.csalgorithms.vendingmachine.model.Inventory;
import com.m3.csalgorithms.vendingmachine.model.ItemAndChange;
import com.m3.csalgorithms.vendingmachine.model.Product;

public class VendingMachine {
    private final Inventory<Coin> _cashInventory = new Inventory<Coin>();
    private final Inventory<Product> _itemInventory = new Inventory<Product>();
    private long _totalSales = 0;
    private int _currentValue = 0;

    public static void main(String[] args) {
    }

    VendingMachine(List<Coin> coins, List<Product> products) {
        initialize(coins, products);
    }

    void reset() {
        refund();
        _currentValue = 0;
    }

    public void outputMessage(String message) {
        System.out.println(message);
    }

    public void addCoin(Coin thecoin) {
        _currentValue += thecoin.coinValue();
        _cashInventory.add(thecoin);
    }

    public ItemAndChange vendProduct(Product theproduct) {
        String fullmessage = null;
        ItemAndChange result = null;
        if (! _itemInventory.has(theproduct)) {
            fullmessage = VendingMessage.SOLDOUT.fullMessage(theproduct);
            outputMessage(fullmessage);
            return new ItemAndChange(fullmessage);
        }
        int productvalue = theproduct.productValue();
        if (productvalue > _currentValue) {
            fullmessage = VendingMessage.INSUFFICIENT.fullMessage(theproduct);
            outputMessage(fullmessage);
            return new ItemAndChange(fullmessage);
        }
//        System.out.println("Current value before is [" + _currentValue + "]");
//        System.out.println("Product value is [" + productvalue + "]");
        _currentValue -= productvalue;
//        System.out.println("Current value after is [" + _currentValue + "]");
        _totalSales += productvalue;
        fullmessage = VendingMessage.COMPLETE.fullMessage(theproduct);
        result = new ItemAndChange(_itemInventory.remove(theproduct), fullmessage).addChange(refund());
        outputMessage(fullmessage);
        return result;
    }

    public List<Coin> refund() {
        List<Coin> result = new ArrayList<Coin>();
        Coin spitres = null;
        while (_currentValue >= 25) {
            spitres = spitCoin(Coin.QUARTER);
            if (spitres == null)
                break;
            result.add(spitres);
        }
        while (_currentValue >= 10) {
            spitres = spitCoin(Coin.DIME);
            if (spitres == null)
                break;
            result.add(spitres);
        }
        while (_currentValue >= 5) {
            spitres = spitCoin(Coin.NICKEL);
            if (spitres == null)
                break;
            result.add(spitres);
        }
        while (_currentValue >= 1) {
            spitres = spitCoin(Coin.PENNY);
            if (spitres == null)
                break;
            result.add(spitres);
        }
        return result;
    }

    long getTotalSales() { return _totalSales; }
    // TODO getCashInventory should provide a list
    // TODO getItemInventory should provide a list

    private Coin spitCoin(Coin thecoin) {
        Coin result = _cashInventory.remove(thecoin);
        if (result != null) {
            _currentValue -= thecoin.coinValue();
        }
        return result;
    }

    private void initialize(List<Coin> coins, List<Product> products) {
        coins.forEach(c -> _cashInventory.add(c));
        products.forEach(p -> _itemInventory.add(p));
    }

    public void clear() {
        _cashInventory.clear();
        _itemInventory.clear();
        _totalSales = 0;
        _currentValue = 0;
    }

}
