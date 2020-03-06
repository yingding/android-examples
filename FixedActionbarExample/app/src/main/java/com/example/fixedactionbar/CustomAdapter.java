package com.example.fixedactionbar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {
    private Context mContext;
    private ArrayList<String> list;

    public CustomAdapter(Context context, ArrayList<String> list) {
        this.mContext = context;
        this.list = list;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row, parent, false);
        return new CustomViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.title.setText(list.get(position));
        if (position % 2 == 0) {
            holder.image.setImageResource(R.drawable.city1);
        } else {
            holder.image.setImageResource(R.drawable.city2);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class CustomViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private ImageView image;
        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            image = itemView.findViewById(R.id.imageView);
        }
    }
}
