package com.AV.laundry.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.AV.laundry.DBHelper;
import com.AV.laundry.Model.Laundry;
import com.AV.laundry.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Param on 1/30/2019.
 */

public class LaundryAdapter extends RecyclerView.Adapter<LaundryAdapter.MyHolder> {

    List<Laundry>list=new ArrayList<>();
    DBHelper mydb;
    public LaundryAdapter(List<Laundry> list){this.list=list;}

    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.laundry_item,viewGroup,false);
        mydb=new DBHelper(viewGroup.getContext());
        return new MyHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull final MyHolder holder,final int i){
        final Laundry laundry=list.get(i);
        holder.Name.setText(laundry.getName());
        holder.Email.setText(laundry.getEmail());
        holder.MobileNo.setText(laundry.getMobileNo());
        holder.Password.setText(laundry.getPassword());
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mydb.delete(laundry.getId());
                list.remove(i);
                notifyDataSetChanged();
            }
        });

    }
    @Override
    public int getItemCount(){return list.size();}

    public static class MyHolder extends RecyclerView.ViewHolder{
        TextView Name,Email,MobileNo,Password,delete;
        Context v;
        public MyHolder(@NonNull View itemview){
            super(itemview);
            v=itemview.getContext();
            Name=itemview.findViewById(R.id.textView_unm);
            Email=itemview.findViewById(R.id.textView_email);
            MobileNo=itemview.findViewById(R.id.textView_mno);
            Password=itemview.findViewById(R.id.textView_pwd);
            delete=itemview.findViewById(R.id.textView_delete);
        }
    }
}



