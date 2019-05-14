// Biblio: I used this medium article https://medium.com/mindorks/custom-array-adapters-made-easy-b6c4930560dd

package com.example.snapalarm;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class CustomAdapter extends ArrayAdapter<AlarmModel> {

    private Context mContext;
    private List<AlarmModel> AlarmModelsList = new ArrayList<>();

    public CustomAdapter(@NonNull Context context, @LayoutRes ArrayList<AlarmModel> list) {
        super(context, 0 , list);
        mContext = context;
        AlarmModelsList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.list_item,parent,false);

        AlarmModel currentAlarmModel = AlarmModelsList.get(position);

        TextView time = listItem.findViewById(R.id.textView_time);
        time.setText(currentAlarmModel.getTimeString());

        TextView days = listItem.findViewById(R.id.textView_days);
        days.setText(currentAlarmModel.getDays());

        TextView name = listItem.findViewById(R.id.textView_name);
        name.setText(currentAlarmModel.getName());

        Switch release = listItem.findViewById(R.id.active_switch);
        release.setChecked(currentAlarmModel.getActiveStatus());

        return listItem;
    }
}
