package com.AV.laundry.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.AV.laundry.Adapter.ClothesAdapter;
import com.AV.laundry.Adapter.InfoAdapater;
import com.AV.laundry.DBHelper;
import com.AV.laundry.LoginActivity;
import com.AV.laundry.Model.Clothes;
import com.AV.laundry.R;
import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Info extends Fragment {
    DBHelper dbHelper;
    RecyclerView recyclerView;
    TextView name,mem,amount;
    InfoAdapater infoAdapater;
    Button pass,delete;
    View v;
    String mno;
    int id;
    List<com.AV.laundry.Model.Info> infoList;
    public Info() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v= inflater.inflate(R.layout.fragment_info, container, false);
        dbHelper=new DBHelper(v.getContext());
        recyclerView=v.findViewById(R.id.rcv);
        recyclerView.setLayoutManager(new LinearLayoutManager(v.getContext()));
        name=v.findViewById(R.id.name);
        mem=v.findViewById(R.id.totmem);
        amount=v.findViewById(R.id.totamount);
        pass=v.findViewById(R.id.chngpass);
        delete=v.findViewById(R.id.delete);

        Map<String,String> map=dbHelper.getMemeber();
        mem.setText("Total Member "+map.get("totmem"));
        amount.setText("Total Paid Amount "+map.get("totamount"));
        SharedPreferences sharedpreferences = v.getContext().getSharedPreferences("session", Context.MODE_PRIVATE);
        mno=sharedpreferences.getString("mno","");
        id=sharedpreferences.getInt("id",0);
        name.setText(sharedpreferences.getString("name",""));

        pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showpassdialog();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteaccount();
            }
        });

        return v;
    }

    private void showpassdialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Change Password");
        View viewInflated = LayoutInflater.from(getContext()).inflate(R.layout.text_inpu_mobile, (ViewGroup) getView(), false);
        final EditText input = (EditText) viewInflated.findViewById(R.id.input);
        builder.setView(viewInflated);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if(input.getText().toString().equals(mno))
                {
                    changepass();
                }
                else {
                    Toast.makeText(v.getContext(), "Invalid Mobile Number", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void changepass() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Change Password");
        View viewInflated = LayoutInflater.from(getContext()).inflate(R.layout.text_inpu_mobile, (ViewGroup) getView(), false);
        final EditText input = (EditText) viewInflated.findViewById(R.id.input);
        input.setHint("Enter Your Password");
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        builder.setView(viewInflated);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if(!input.getText().toString().equals(""))
                {
                    dbHelper.passchange(input.getText().toString(),id);
                    Toast.makeText(v.getContext(), "Succesfully Update", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(v.getContext(), "Please Enter Password", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }



    public void initdata()
    {
        infoList=new ArrayList<>();
        Cursor cinfo =dbHelper.getMemberInfo();
        if(cinfo!=null)
        {
            cinfo.moveToFirst();
            while (cinfo.isAfterLast() == false) {
                com.AV.laundry.Model.Info c = new com.AV.laundry.Model.Info();
                c.setName(cinfo.getString(cinfo.getColumnIndex("Name")));
                c.setMno(cinfo.getString(cinfo.getColumnIndex("MobileNo")));
                c.setRqty(cinfo.getString(cinfo.getColumnIndex("Unpaid")));
                c.setPaid(cinfo.getString(cinfo.getColumnIndex("Paid")));
                infoList.add(c);
                cinfo.moveToNext();
            }
            cinfo.close();
        }
        infoAdapater = new InfoAdapater(infoList);
        recyclerView.setAdapter(infoAdapater);
    }

    public void deleteaccount()
    {
        final NiftyDialogBuilder dialogBuilder=NiftyDialogBuilder.getInstance(v.getContext());
        dialogBuilder
                .withTitle("Account Delete")                                  //.withTitle(null)  no title
                .withTitleColor("#FFFFFF")                                  //def
                .withDividerColor("#04c10d")                                //def
                .withMessage("Are You Sure Want To Delete Account ?")                     //.withMessage(null)  no Msg
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
                        dbHelper.deleteaccount(id);
                        Intent i=new Intent(v.getContext(), LoginActivity.class);
                        v.getContext().startActivity(i);
                        getActivity().finish();
                    }
                })
                .setButton2Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogBuilder.dismiss();
                    }
                })
                .show();
    }
}
