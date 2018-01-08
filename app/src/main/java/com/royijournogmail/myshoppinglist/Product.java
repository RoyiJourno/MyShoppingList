package com.royijournogmail.myshoppinglist;

/**
 * Created by ortal on 27/12/2017.
 */

public class Product {
    String p_name;
    String p_photoUrl;
    String p_description;
    String p_amount;
    int p_purchased;

    public Product(String p_name,String p_photoUrl , String p_description) {
        this(p_name , p_photoUrl, p_description,"0",0);

    }

    public Product(String p_name,String p_photoUrl , String p_description,String amount) {
        this(p_name , p_photoUrl, p_description,amount,0);
    }

    public Product(String p_name,String p_photoUrl , String p_description,String amount,int p_purchased) {
        this.p_name = p_name;
        this.p_photoUrl = p_photoUrl;
        this.p_description = p_description;
        this.p_amount=amount;
        this.p_purchased=p_purchased;
    }

    public void  updatePurchased()
    {
        if (this.p_purchased==1)
            this.p_purchased=0;
        else
            this.p_purchased=1;
    }


}

