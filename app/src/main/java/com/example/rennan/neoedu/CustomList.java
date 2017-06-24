package com.example.rennan.neoedu;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomList extends ArrayAdapter<String>{

    private final Activity context;
    private final String[] web;
    private final String[] sub;
    public CustomList(Activity context,
                      String[] web, String[] sub) {
        super(context, R.layout.list_item, web);
        this.context = context;
        this.web = web;
        this.sub = sub;
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.list_item, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.txtTi);
        TextView txtSub = (TextView) rowView.findViewById(R.id.txtCo);
        txtTitle.setText(web[position]);
        txtSub.setText(sub[position]);
        return rowView;
    }
}