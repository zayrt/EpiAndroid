package com.example.epiandroid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zellou_i on 1/24/15.
 */
public class MarkAdapter extends BaseAdapter {
    Context context;
    private List<Mark> marks;
    private static LayoutInflater inflater = null;

    public MarkAdapter (Context context, ArrayList<Mark> _marks) {
        this.context = context;
        this.marks = _marks;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return this.marks.size();
    }

    @Override
    public Mark getItem(int position) {
        // TODO Auto-generated method stub
        return this.marks.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View vi = convertView;
        if (vi == null)
            vi = inflater.inflate(R.layout.row, null);
        TextView text = (TextView) vi.findViewById(R.id.header);
        text.setText(marks.get(position).getTitle());
        TextView text2 = (TextView) vi.findViewById(R.id.text);
        text2.setText(" : " +  marks.get(position).getFinal_note());
        return vi;
    }
}
