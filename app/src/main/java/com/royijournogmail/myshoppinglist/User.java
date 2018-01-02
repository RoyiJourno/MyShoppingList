package com.royijournogmail.myshoppinglist;

import java.util.ArrayList;

/**
 * Created by ortal on 27/12/2017.
 */

public class User {
    public  String name;
    ArrayList<Product> listOfProduct;
    public User(String name) {
        this.name = name;
        this.listOfProduct = new ArrayList<Product>();
    }

  /*  public void updateUser(Product[] listOfProduct){
        this.listOfProduct = listOfProduct;
    } */
}
