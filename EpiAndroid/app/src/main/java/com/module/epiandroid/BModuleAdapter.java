package com.module.epiandroid;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.epiandroid.R;

import java.util.List;


public class BModuleAdapter extends BaseAdapter implements View.OnClickListener {
    Context context;
    private List<BModule> modules;
    private static LayoutInflater inflater = null;
    private String token;
    private AsyncModule asynModule;

    public BModuleAdapter(Context context, List<BModule> modules, String t) {
        this.context = context;
        this.modules = modules;
        token = t;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return this.modules.size();
    }

    @Override
    public Object getItem(int position) {
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
            vi = inflater.inflate(R.layout.row_bmodule, null);

        TextView title = (TextView) vi.findViewById(R.id.title);
        title.setText(modules.get(position).getTitle());
        title.setTextColor(Color.parseColor("#00CCFF"));

        TextView begin_end = (TextView) vi.findViewById(R.id.begin_end);
        begin_end.setText("Début : " + modules.get(position).getBegin() + " - Fin : " + modules.get(position).getEnd());

        TextView credit = (TextView) vi.findViewById(R.id.credit);
        credit.setText("Credits : " + modules.get(position).getCredits());

        TextView end_sub = (TextView) vi.findViewById(R.id.end_sub);
        end_sub.setText("Fin inscription : " + modules.get(position).getEnd_register());


        Button b = (Button) vi.findViewById(R.id.button_on_list);
        b.setTag(modules.get(position));
        b.setOnClickListener(this);
        if (modules.get(position).getStatus().equals("notregistered") && modules.get(position).getOpen().equals("1")) {
            b.setText("Subscribe");
            b.setVisibility(View.VISIBLE);
        }
        else if (modules.get(position).getStatus().equals("ongoing") && modules.get(position).getOpen().equals("1")) {
            b.setText("Unsubscribe");
            b.setVisibility(View.VISIBLE);
        }
        else
            b.setVisibility(View.GONE);
        return vi;
    }

    @Override
    public void onClick(View v) {
        Button b = (Button) v.findViewById(R.id.button_on_list);
        String buttonText = b.getText().toString();
        BModule module = (BModule) b.getTag();

        if (buttonText.equals("Subscribe")) {
            asynModule = new AsyncModule(token, module, true, b);
            asynModule.execute();
        }
        else if (buttonText.equals("Unsubscribe")) {
            asynModule = new AsyncModule(token, module, false, b);
            asynModule.execute();
        }
    }
}
