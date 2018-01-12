package com.royijournogmail.myshoppinglist;


import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class CustomAdapter_lists extends BaseAdapter {
    private Context context;



    public CustomAdapter_lists(Context context) {

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
        return previousLists.modelArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return previousLists.modelArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final CustomAdapter_lists.ViewHolder holder;

        if (convertView == null) {
            holder = new CustomAdapter_lists.ViewHolder(); LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item, null, true);


            holder.tvList = (TextView) convertView.findViewById(R.id.animal);
            holder.img=(ImageView) convertView.findViewById(R.id.imageicon);


            convertView.setTag(holder);
        }else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (CustomAdapter_lists.ViewHolder)convertView.getTag();
        }

        holder.tvList.setText(previousLists.modelArrayList.get(position).getNameList());


        //convertView.setBackgroundColor(Color.parseColor("#dafbf3"));
        if (previousLists.modelArrayList.get(position).getBuyAll()==1)
            holder.img.setImageResource(R.mipmap.goodicom);



        return convertView;
    }

    private class ViewHolder {

        private TextView tvList ;
        private ImageView img;

    }

}
