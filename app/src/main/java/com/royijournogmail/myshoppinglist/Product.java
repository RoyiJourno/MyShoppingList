package com.royijournogmail.myshoppinglist;

/**
 * Created by ortal on 27/12/2017.
 */

public class Product {
    String p_id;
    String p_name;
    String p_amount;
    String p_photoUrl;
    String p_description;

    public Product(String p_id,String p_name, String p_amount,String p_photoUrl,String p_description) {
        this.p_id=p_id;
        this.p_name = p_name;
        this.p_amount= p_amount;
        this.p_photoUrl= p_photoUrl;
        this.p_description= p_description;
    }
}

