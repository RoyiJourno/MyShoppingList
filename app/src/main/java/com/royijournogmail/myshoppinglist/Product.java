package com.royijournogmail.myshoppinglist;

/**
 * Created by ortal on 27/12/2017.
 */

public class Product {
    String p_name;
    String p_photoUrl;
    String p_description;
    String p_amount;
    String p_purchased;

    public Product(String p_name,String p_photoUrl , String p_description) {
        this.p_name = p_name;
        this.p_photoUrl = p_photoUrl;
        this.p_description = p_description;
        this.p_amount="0";
        this.p_purchased="false";
    }

    public Product(String p_name,String p_photoUrl , String p_description,String amount) {
        this.p_name = p_name;
        this.p_photoUrl = p_photoUrl;
        this.p_description = p_description;
        this.p_amount=amount;
        this.p_purchased="false";
    }

    public Product(String p_name,String p_photoUrl , String p_description,String amount,String p_purchased) {
        this.p_name = p_name;
        this.p_photoUrl = p_photoUrl;
        this.p_description = p_description;
        this.p_amount=amount;
        this.p_purchased=p_purchased;
    }

    @Override
    public String toString() {
        return "Name: "+ this.p_name + "  |  Description:" + this.p_description ;
    }
}

