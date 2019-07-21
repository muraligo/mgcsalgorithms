package com.m3.csalgorithms.vendingmachine;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.m3.csalgorithms.vendingmachine.model.Coin;
import com.m3.csalgorithms.vendingmachine.model.ItemAndChange;
import com.m3.csalgorithms.vendingmachine.model.Product;

class VendingMachineTest {
    private VendingMachine _machine;

    @BeforeEach
    void setUp() throws Exception {
    	List<Coin> coins = Arrays.asList(new Coin[] {Coin.QUARTER, Coin.QUARTER, Coin.QUARTER, 
    	        Coin.QUARTER, Coin.QUARTER, Coin.QUARTER, Coin.QUARTER, Coin.QUARTER, 
    	        Coin.DIME, Coin.DIME, Coin.DIME, Coin.DIME, Coin.DIME, Coin.DIME, Coin.DIME, 
    	        Coin.DIME, Coin.DIME, Coin.DIME, Coin.NICKEL, Coin.NICKEL, Coin.NICKEL, Coin.NICKEL, 
    	        Coin.NICKEL, Coin.NICKEL, Coin.NICKEL, Coin.NICKEL, Coin.NICKEL, Coin.NICKEL, 
    	        Coin.NICKEL, Coin.NICKEL, Coin.NICKEL, Coin.NICKEL, Coin.NICKEL, Coin.NICKEL, 
    	        Coin.PENNY, Coin.PENNY, Coin.PENNY, Coin.PENNY, Coin.PENNY, Coin.PENNY, Coin.PENNY, Coin.PENNY});
    	List<Product> products = Arrays.asList(new Product[] {Product.COKE, Product.COKE, Product.COKE, Product.COKE, 
    	        Product.PEPSI, Product.PEPSI, Product.PEPSI, Product.PEPSI, Product.SODA, Product.SODA, Product.SODA, 
    	        Product.SODA, Product.SODA, Product.SODA, Product.SODA, Product.SODA, Product.SODA, Product.SODA});
        _machine = new VendingMachine(coins, products);
    }

    @AfterEach
    void tearDown() throws Exception {
        _machine.clear();
    }

    @Test
    void testBuyItemWithExactMoneySucceeds() {
        String expectedMessage = VendingMessage.COMPLETE.rawMessage() + " " + Product.COKE.camelCaseName();
        _machine.addCoin(Coin.QUARTER);
        ItemAndChange itmnchg = _machine.vendProduct(Product.COKE);
        assertEquals(Product.COKE, itmnchg.getProduct());
        List<Coin> change = itmnchg.getChange();
        assertTrue(change.isEmpty());
        assertEquals(expectedMessage, itmnchg.getMessage());
    }

    @Test
    void testBuyItemWithMoreMoneySucceeds() {
        String expectedMessage = VendingMessage.COMPLETE.rawMessage() + " " + Product.PEPSI.camelCaseName();
        _machine.addCoin(Coin.QUARTER);
        _machine.addCoin(Coin.QUARTER);
        ItemAndChange itmnchg = _machine.vendProduct(Product.PEPSI);
        assertEquals(Product.PEPSI, itmnchg.getProduct());
        List<Coin> change = itmnchg.getChange();
        assertFalse(change.isEmpty());
        assertEquals(Coin.DIME, change.get(0));
        assertEquals(Coin.NICKEL, change.get(1));
        assertEquals(expectedMessage, itmnchg.getMessage());
    }

    @Test
    void testBuyItemWithLessMoneyFails() {
        String expectedMessage = VendingMessage.INSUFFICIENT.rawMessage() + " " + Product.PEPSI.camelCaseName();
        _machine.addCoin(Coin.QUARTER);
        ItemAndChange itmnchg = _machine.vendProduct(Product.PEPSI);
        assertNull(itmnchg.getProduct());
        List<Coin> change = itmnchg.getChange();
        assertTrue(change.isEmpty());
        assertEquals(expectedMessage, itmnchg.getMessage());
    }

    @Test
    void testBuyItemSoldOutFails() {
        String expectedMessage = VendingMessage.SOLDOUT.rawMessage() + " " + Product.COKE.camelCaseName();
        _machine.addCoin(Coin.QUARTER);
        _machine.vendProduct(Product.COKE);
        _machine.addCoin(Coin.QUARTER);
        _machine.vendProduct(Product.COKE);
        _machine.addCoin(Coin.QUARTER);
        _machine.vendProduct(Product.COKE);
        _machine.addCoin(Coin.QUARTER);
        _machine.vendProduct(Product.COKE);
        _machine.addCoin(Coin.QUARTER);
        ItemAndChange itmnchg = _machine.vendProduct(Product.COKE);
        assertNull(itmnchg.getProduct());
        List<Coin> change = itmnchg.getChange();
        assertTrue(change.isEmpty());
        assertEquals(expectedMessage, itmnchg.getMessage());
    }

    @Test
    void testRefundSucceeds() {
        _machine.addCoin(Coin.QUARTER);
        _machine.addCoin(Coin.DIME);
        _machine.addCoin(Coin.PENNY);
        _machine.addCoin(Coin.QUARTER);
        _machine.addCoin(Coin.DIME);
        _machine.addCoin(Coin.NICKEL);
        _machine.addCoin(Coin.PENNY);
        _machine.addCoin(Coin.DIME);
        _machine.addCoin(Coin.NICKEL);
        _machine.addCoin(Coin.PENNY);
        _machine.addCoin(Coin.PENNY);
        List<Coin> change = _machine.refund();
        assertFalse(change.isEmpty());
        long value = getTotal(change);
        assertEquals(94, value);
    }

    private long getTotal(List<Coin> change) {
        long total = 0;
        for (Coin c : change) {
            total = total + c.coinValue();
        }
        return total;
    } 
}
