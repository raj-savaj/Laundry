package com.AV.laundry.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.AV.laundry.DBHelper;
import com.AV.laundry.R;
import com.AV.laundry.Model.member;
import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joshi on 2/18/2019.
 */

public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.MyHolder> {

    List<member> list = new ArrayList<>();
    DBHelper mydb;

    public MemberAdapter(List<member> list) {
        this.list = (List<member>) list;
    }



    @Override
    public MemberAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_member, viewGroup, false);
        mydb = new DBHelper(viewGroup.getContext());
        return new MemberAdapter.MyHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final MemberAdapter.MyHolder holder, final int i ) {
        final member m = list.get(i);
        holder.Name.setText(m.getName());
        holder.Address.setText(m.getAddress());
        holder.MobileNo.setText(m.getMobileNo());
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final NiftyDialogBuilder dialogBuilder=NiftyDialogBuilder.getInstance(holder.itemView.getContext());
                dialogBuilder
                        .withTitle("Delete Member")                                  //.withTitle(null)  no title
                        .withTitleColor("#FFFFFF")                                  //def
                        .withDividerColor("#04c10d")                                //def
                        .withMessage("Are You Sure Want To Remove Member Account?")                     //.withMessage(null)  no Msg
                        .withMessageColor("#FFFFFFFF")                              //def  | withMessageColor(int resid)
                        .withDialogColor("#65f974")
                        .isCancelableOnTouchOutside(true)                           //def    | isCancelable(true)
                        .withDuration(700)                                          //def
                        .withEffect(Effectstype.SlideBottom)                                         //def Effectstype.Slidetop
                        .withButton1Text("OK")                                      //def gone
                        .withButton2Text("Cancel")      //.setCustomView(View or ResId,context)
                        .setButton1Click(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mydb.deleteMember(m.getId());
                                list.remove(i);
                                notifyDataSetChanged();
                                dialogBuilder.dismiss();
                            }
                        })
                        .setButton2Click(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(v.getContext(), "Account Will Be Safe", Toast.LENGTH_SHORT).show();
                                dialogBuilder.dismiss();
                            }
                        })
                        .show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        TextView Name, Address, MobileNo, delete;
        Context v;

        public MyHolder(@NonNull View itemview) {
            super(itemview);
            v = itemview.getContext();
            Name = itemview.findViewById(R.id.text_name_item);
            Address = itemview.findViewById(R.id.text_adr_item);
            MobileNo = itemview.findViewById(R.id.text_mno_item);
            delete = itemview.findViewById(R.id.text_delete_item);
        }

    }
}