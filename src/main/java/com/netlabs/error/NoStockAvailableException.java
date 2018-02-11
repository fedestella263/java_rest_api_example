package com.netlabs.error;

@SuppressWarnings("serial")
public class NoStockAvailableException  extends RuntimeException {

    public NoStockAvailableException(Integer stock) {
        super("The available stock is " + stock);
    }
}