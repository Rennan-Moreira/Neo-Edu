package com.example.rennan.neoedu;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CustomList2 extends ArrayAdapter<String>{

    private final Activity context;
    private final int[] num;
    private final String[] nome;
    private final int[] media;
    public CustomList2(Activity context, int[] num,
                       String[] nome, int[] media) {
        super(context, R.layout.list_item2, nome);
        this.context = context;
        this.num = num;
        this.nome = nome;
        this.media = media;
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.list_item2, null, true);
        TextView txtNum = (TextView) rowView.findViewById(R.id.txtNum);
        TextView txtNome = (TextView) rowView.findViewById(R.id.txtNomeEs);
        TextView txtMedia = (TextView) rowView.findViewById(R.id.txtMedia);
        txtNum.setText(num[position]+"");
        txtNome.setText(nome[position]);
        txtMedia.setText(media[position]+"");
        return rowView;
    }
}