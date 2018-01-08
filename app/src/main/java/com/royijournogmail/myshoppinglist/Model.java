package com.royijournogmail.myshoppinglist;

/**
 * Created by ortal on 05/01/2018.
 */

public class Model {

    private int number;
    private String product;
    private String productDesc;
    private int purchased;




    public int getPurchased(){return purchased;}

    public void setPurchased(int p){this.purchased=p;}

    public void updatePurchased ()
    {
        if (this.purchased==1)
            this.purchased=0;
        else
            this.purchased=1;
    }

    public String getproductDesc() {
        return productDesc;
    }

    public void setproductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }
}