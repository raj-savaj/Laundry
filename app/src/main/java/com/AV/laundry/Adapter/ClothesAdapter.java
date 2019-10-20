package com.AV.laundry.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.AV.laundry.Model.Clothes;
import com.AV.laundry.DBHelper;
import com.AV.laundry.R;

import java.util.ArrayList;
import java.util.List;

public class ClothesAdapter extends RecyclerView.Adapter<ClothesAdapter.MyHolder> {

    List<Clothes> list = new ArrayList<>();
    DBHelper mydb;

    public ClothesAdapter(List<Clothes> list) { this.list = (List<Clothes>) list;
    }

    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_clothes, viewGroup, false);
        mydb = new DBHelper(viewGroup.getContext());
        return new ClothesAdapter.MyHolder(view);

    }

    @Override
    public void onBindViewHolder( final ClothesAdapter.MyHolder holder,final int i ) {
        final Clothes m = list.get(i);
        holder.Name.setText(m.getName());
        holder.Clothes.setText(m.getClothes());
        holder.Date.setText(m.getDate());
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mydb.delete(m.getId());
                list.remove(i);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() { return list.size();
    }


    public static class MyHolder extends RecyclerView.ViewHolder {
        TextView Name, Clothes, Date, delete;
        Context v;

        public MyHolder(@NonNull View itemview) {
            super(itemview);
            v = itemview.getContext();
            Name = itemview.findViewById(R.id.text_name_item);
            Clothes = itemview.findViewById(R.id.text_cloths_item);
            Date = itemview.findViewById(R.id.text_date_item);
            delete = itemview.findViewById(R.id.text_delete_item);
        }

    }
}
