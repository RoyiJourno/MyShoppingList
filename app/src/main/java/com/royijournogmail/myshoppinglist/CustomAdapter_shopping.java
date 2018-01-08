package com.royijournogmail.myshoppinglist;

/**
 * Created by ortal on 05/01/2018.
 */

import android.content.Context;
import android.graphics.Color;
import android.text.style.BackgroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CustomAdapter_shopping  extends BaseAdapter {

    private Context context;



    public CustomAdapter_shopping(Context context) {

        this.context = context;
    }

    @Override
    public int getViewTypeCount() {
        return getCount();
    }
    @Override
    public int getItemViewType(int position) {

        return position;
    }

    @Override
    public int getCount() {
        return ShowShoppingList.modelArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return ShowShoppingList.modelArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder(); LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.shopping_item, null, true);


            holder.tvProduct = (TextView) convertView.findViewById(R.id.animal);
            holder.tvnumber = (TextView) convertView.findViewById(R.id.number);
            holder.tvDesc = (TextView) convertView.findViewById(R.id.desc);
            //holder.takeIt = (Button) convertView.findViewById(R.id.takeIt);

            convertView.setTag(holder);
        }else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (ViewHolder)convertView.getTag();
        }

        holder.tvProduct.setText(ShowShoppingList.modelArrayList.get(position).getProduct());
        holder.tvnumber.setText(String.valueOf(ShowShoppingList.modelArrayList.get(position).getNumber()));
        holder.tvDesc.setText(ShowShoppingList.modelArrayList.get(position).getproductDesc());


       /* //mark selected item in current list.
        holder.takeIt.setTag(R.integer.btn_plus_view,convertView);
        holder.takeIt.setTag(R.integer.btn_plus_pos,position);
        holder.takeIt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });*/

        if (ShowShoppingList.modelArrayList.get(position).getPurchased()==1)
        {
            convertView.setBackgroundColor(Color.parseColor("#9fe7ff"));
        }



        return convertView;
    }

    private class ViewHolder {

        private TextView tvProduct, tvnumber;
        private  TextView tvDesc;
     //   private Button takeIt;

    }

}
