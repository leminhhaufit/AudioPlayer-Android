package com.example.leminhhau_17084751;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.leminhhau_17084751.R;

public class NamelistAdapter extends RecyclerView.Adapter<NamelistAdapter.NameViewHolder> {
    private ArrayList<Music> nameArrayList;
    private Context context;
    public static  String path="";

    public static String getPath() {
        return path;
    }

    public NamelistAdapter(ArrayList<Music> nameArrayList, Context context) {
        this.nameArrayList = nameArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public NamelistAdapter.NameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item,parent,false);


        return new NameViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NamelistAdapter.NameViewHolder holder, int position) {
        Music music = nameArrayList.get(position);
        holder.nameitemview.setText(music.getTitle());
        holder.nameitemview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"Chon nhac thanh cong + "+music.getPath(),Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return nameArrayList.size();
    }

    public class NameViewHolder extends RecyclerView.ViewHolder {
        public TextView nameitemview;
        NamelistAdapter namelistAdapter;

        public NameViewHolder(@NonNull View itemView) {
            super(itemView);
            nameitemview = itemView.findViewById(R.id.lbten);



        }


    }
}
