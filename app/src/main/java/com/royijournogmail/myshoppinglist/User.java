package com.royijournogmail.myshoppinglist;

/**
 * Created by ortal on 27/12/2017.
 */

public class User {
    String name;
    Product[] listOfProduct;
    public User(String name,Product[] listOfProduct) {
        this.name = name;
        this.listOfProduct = listOfProduct;
    }

    public void updateUser(Product[] listOfProduct){
        this.listOfProduct = listOfProduct;
    }
}
