package com.example.clinicsystem;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import org.w3c.dom.Text;
import java.util.List;
public class ListAdapter extends ArrayAdapter {
    private Activity mContext;
    List<patient2> patient2List;
    public ListAdapter(Activity mContext, List<patient2> patient2List){
        super(mContext,R.layout.list_item,patient2List);
        this.mContext = mContext;
        this.patient2List = patient2List;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull
    ViewGroup parent) {
        LayoutInflater inflater = mContext.getLayoutInflater();
        View lisViewtItem = inflater.inflate(R.layout.list_item,null,true);
        TextView txt1 = lisViewtItem.findViewById(R.id.atxt1);
        TextView txt2 = lisViewtItem.findViewById(R.id.atxt2);
        TextView txt3 = lisViewtItem.findViewById(R.id.atxt3);
        patient2 patient2 = patient2List.get(position);
        Log.d("Debug", "Name: " + patient2.getName1());
        Log.d("Debug", "Date: " + patient2.getDate());
        Log.d("Debug", "Time: " + patient2.getTime1());
        txt1.setText(patient2.getName1());
        txt2.setText(patient2.getDate());
        txt3.setText(patient2.getTime1());
        return lisViewtItem;
    }
}
