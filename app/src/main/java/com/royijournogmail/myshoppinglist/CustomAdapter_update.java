package com.royijournogmail.myshoppinglist;

/**
 * Created by ortal on 05/01/2018.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class CustomAdapter_update  extends BaseAdapter {

    private Context context;


    public CustomAdapter_update(Context context) {

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
        return updateList.modelArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return updateList.modelArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position,  View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.update_item, null, true);


            holder.tvProduct = (TextView) convertView.findViewById(R.id.animal);
            holder.btn_delete = (Button) convertView.findViewById(R.id.delete);

            convertView.setTag(holder);
        } else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tvProduct.setText(updateList.modelArrayList.get(position).getProduct());


        holder.btn_delete.setTag(R.integer.btn_plus_view, convertView);
        holder.btn_delete.setTag(R.integer.btn_plus_pos, position);
        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ///delete product

                View tempview = (View) holder.btn_delete.getTag(R.integer.btn_plus_view);
                final TextView tv = (TextView) tempview.findViewById(R.id.animal);

                FirebaseAuth userInfo = FirebaseAuth.getInstance();
                final String u_id = userInfo.getUid();

                final FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference ref = database.getReference().child(u_id);
                //  DatabaseReference userToAddRef =database.getReference(u_id).child("listOfProduct").child;
                //  userToAddRef.removeValue();
                final String[] index = {"-1"};
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Iterable<DataSnapshot> children = dataSnapshot.child("listOfProduct").getChildren();
                        //int count = 0;
                        //find the product to delete
                        for (DataSnapshot child : children) {
                            String name = child.child("p_name").getValue().toString();
                            if (name.equals(tv.getText().toString())) {
                                index[0] = child.getKey() ;
                                break;
                            }
                        }
                        if (index[0] != "-1") {
                            DatabaseReference userToAddRef = database.getReference(u_id).child("listOfProduct").child(index[0]);
                            userToAddRef.removeValue();

                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

            }
        });
        return convertView;
    }



    private class ViewHolder {

        protected Button btn_delete;
        private TextView tvProduct;

    }

}