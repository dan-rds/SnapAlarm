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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class CustomAdapter extends ArrayAdapter<ImagePojo> {

    private Context mContext;
    private List<ImagePojo> ImagePojosList = new ArrayList<>();

    public CustomAdapter(@NonNull Context context, @LayoutRes ArrayList<ImagePojo> list) {
        super(context, 0 , list);
        mContext = context;
        ImagePojosList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.list_item,parent,false);

        ImagePojo currentImagePojo = ImagePojosList.get(position);

        ImageView image = (ImageView)listItem.findViewById(R.id.imageView_poster);
        image.setImageBitmap(currentImagePojo.getmImageBitmap());

        TextView name = (TextView) listItem.findViewById(R.id.textView_id);
        name.setText(currentImagePojo.getmTitle());

        TextView release = (TextView) listItem.findViewById(R.id.textView_title);
        release.setText(currentImagePojo.getmID());

        return listItem;
    }
}
