package com.m3.csalgorithms.vendingmachine;

import com.m3.csalgorithms.vendingmachine.model.Product;

public enum VendingMessage {
    GREETING("Hi, please add coins (penny, nickel, dime, quarter), and make your selection ...", false),
    INSUFFICIENT("Please check the price and add more coins for", true),
    SOLDOUT("Please select another item as there is no remaining", true),
    COMPLETE("Thanks for your purchase. Please collect your change and", true);

    private final String _message;
    private final boolean _appendProductName;

    private VendingMessage(String msg, boolean moretocome) {
        _message = msg;
        _appendProductName = moretocome;
    }

    public String rawMessage() { return _message; }
    public String fullMessage(Product product) {
        if (!_appendProductName) return _message;
        StringBuilder sb = new StringBuilder(_message);
        sb.append(" ");
        sb.append(product.camelCaseName());
        return sb.toString();
    }
}
