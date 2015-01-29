package com.example.epiandroid;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ModuleAdapter extends BaseAdapter {
    Context context;
    private List<Module> modules;
    private static LayoutInflater inflater = null;

    public ModuleAdapter (Context context, List<Module> _modules) {
        this.context = context;
        this.modules = _modules;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return this.modules.size();
    }

    @Override
    public Module getItem(int position) {
        return this.modules.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (vi == null)
            vi = inflater.inflate(R.layout.row, null);
        
        String grade = modules.get(position).getGrade();
        if (grade.equals("Echec"))
            vi.setBackgroundColor(Color.parseColor("#F6CECE"));
        else if (grade.equals("-"))
            vi.setBackgroundColor(Color.WHITE);
        else
            vi.setBackgroundColor(Color.parseColor("#BCF5A9"));
        
        TextView text = (TextView) vi.findViewById(R.id.header);
        text.setText(modules.get(position).getTitle() + " : ");
        TextView text2 = (TextView) vi.findViewById(R.id.text);
        text2.setText(grade);
        TextView credit = (TextView) vi.findViewById(R.id.credit);
        credit.setText("Crédits : " + modules.get(position).getCredits());
        return vi;
    }

}

