package com.AV.laundry.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.AV.laundry.Model.Account;
import com.AV.laundry.DBHelper;
import com.AV.laundry.R;

import java.util.ArrayList;
import java.util.List;

public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.MyHolder>
{
    List<Account> list = new ArrayList<>();
    DBHelper mydb;

    public AccountAdapter(List<Account> list) { this.list = (List<Account>) list;
    }

    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_account, viewGroup, false);
        mydb = new DBHelper(viewGroup.getContext());
        return new AccountAdapter.MyHolder(view);

    }

    @Override
    public void onBindViewHolder( final AccountAdapter.MyHolder holder,final int i ) {
        final Account account = list.get(i);
        //final com.rj.registration.Account ac= list.get(i);
        holder.Name.setText(account.getName());
        holder.StartDate.setText(account.getStartDate());
        holder.EndDate.setText(account.getEndDate());
       holder.BasePrice.setText(account.getBasePrice());
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mydb.delete(account.getId());
                list.remove(i);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() { return list.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        TextView Name, StartDate, EndDate, BasePrice,delete;
        Context v;

        public MyHolder(@NonNull View itemview) {
            super(itemview);
            v = itemview.getContext();
            Name = itemview.findViewById(R.id.text_name_item);
            StartDate = itemview.findViewById(R.id.text_sd_item);
            EndDate = itemview.findViewById(R.id.text_ed_item);
            BasePrice = itemview.findViewById(R.id.text_bp_item);
            delete = itemview.findViewById(R.id.text_delete_item);
        }

    }



}
