package com.royijournogmail.myshoppinglist;

/**
 * Created by ortal on 05/01/2018.
 */

public class Model {

    private int number;
    private String product;
    private String productDesc;
    private String purchased;

    public String getPurchased(){return purchased;}

    public void setPurchased (String purchased)
    {
        this.purchased=purchased;
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