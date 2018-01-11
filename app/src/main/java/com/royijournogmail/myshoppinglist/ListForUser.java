package com.royijournogmail.myshoppinglist;

import java.util.ArrayList;

/**
 * Created by ortal on 04/01/2018.
 */

public class ListForUser {
    String name;
    ArrayList<Product> listOfProduct;

    public ListForUser(String name)
    {
        this.name=name;
        this.listOfProduct= new ArrayList<Product>();
    }

    public void updateName(String name)
    {
        this.name=name;

    }

    public void updateProdToList(Product p){
        this.listOfProduct.add(p);
    }

    public int getPurchasedInList(int index)
    {
        return listOfProduct.get(index).p_purchased;
    }



    public void updatePurchasedInList(int index)
    {
        listOfProduct.get(index).updatePurchased();
    }
}


