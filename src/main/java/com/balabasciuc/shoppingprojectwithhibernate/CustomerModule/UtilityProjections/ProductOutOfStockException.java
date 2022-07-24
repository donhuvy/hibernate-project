package com.balabasciuc.shoppingprojectwithhibernate.CustomerModule.UtilityProjections;

public class ProductOutOfStockException extends ProductException {

    public ProductOutOfStockException(String message) {
        super(message);
    }
}
