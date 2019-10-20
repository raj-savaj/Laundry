package com.AV.laundry.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.AV.laundry.Model.Info;
import com.AV.laundry.R;

import java.util.List;

public class InfoAdapater extends RecyclerView.Adapter<InfoAdapater.ViewHolder> {
    List<Info> infoList;

    public InfoAdapater(List<Info> infoList) {
        this.infoList = infoList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_info,parent,false);
        return new InfoAdapater.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Info i=infoList.get(position);
        holder.name.setText(i.getName());
        holder.mno.setText(i.getMno());
        holder.paid.setText(i.getPaid());
        holder.remain.setText(i.getRqty());
    }

    @Override
    public int getItemCount() {
        return infoList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name,mno,paid,remain;
        public ViewHolder(View v) {
            super(v);
            name=v.findViewById(R.id.name);
            mno=v.findViewById(R.id.mno);
            paid=v.findViewById(R.id.paid);
            remain=v.findViewById(R.id.remain);
        }
    }
}
