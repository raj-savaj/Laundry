package com.AV.laundry.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.AV.laundry.DBHelper;
import com.AV.laundry.Model.Clothes;
import com.AV.laundry.R;
import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;

import java.util.List;

public class clothsearchAdapater extends RecyclerView.Adapter<clothsearchAdapater.Viewholder>  {

    List<Clothes> clist;
    DBHelper mydb;
    public clothsearchAdapater(List<Clothes> list) {
        this.clist = list;
    }


    @Override
    public Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cloth_search, parent, false);
        mydb=new DBHelper(view.getContext());
        return new clothsearchAdapater.Viewholder(view);
    }

    @Override
    public void onBindViewHolder(final Viewholder holder, int position) {
        final Clothes c=clist.get(position);
        if(c.getStatus()==0)
        {
            holder.status.setImageDrawable(holder.itemView.getResources().getDrawable(R.drawable.ic_hourglass));
            holder.status.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final NiftyDialogBuilder dialogBuilder=NiftyDialogBuilder.getInstance(holder.itemView.getContext());
                    dialogBuilder
                            .withTitle("Payment Confirmation")                                  //.withTitle(null)  no title
                            .withTitleColor("#FFFFFF")                                  //def
                            .withDividerColor("#04c10d")                                //def
                            .withMessage("Are You Sure Want To Confirm Payment ?")                     //.withMessage(null)  no Msg
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
                                    String status=mydb.AcceptPayment(c.getId(),c.getPrice());
                                    if(status.equals("OK"))
                                    {
                                        Toast.makeText(v.getContext(), "Payment Successfully Accepted", Toast.LENGTH_SHORT).show();
                                        holder.status.setImageDrawable(holder.itemView.getResources().getDrawable(R.drawable.ic_sucecess));
                                    }
                                    else {
                                        Toast.makeText(v.getContext(), "error : "+status, Toast.LENGTH_SHORT).show();
                                    }

                                    dialogBuilder.dismiss();
                                }
                            })
                            .setButton2Click(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Toast.makeText(v.getContext(), "Payment Not Accepted", Toast.LENGTH_SHORT).show();
                                    dialogBuilder.dismiss();
                                }
                            })
                            .show();
                }
            });
        }
        holder.date.setText(c.getDate());
        holder.qty.setText(c.getClothes());
        holder.price.setText(""+c.getPrice());
        holder.totprice.setText(""+Integer.parseInt(c.getClothes())*c.getPrice());
    }

    @Override
    public int getItemCount() {
        return clist.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        TextView date,price,qty,totprice;
        ImageView status;
        public Viewholder(View v) {
            super(v);
            date=(TextView)v.findViewById(R.id.date);
            price=(TextView)v.findViewById(R.id.price);
            qty=(TextView)v.findViewById(R.id.cloth);
            totprice=(TextView)v.findViewById(R.id.totprice);
            status=(ImageView)v.findViewById(R.id.status);
        }
    }
}
