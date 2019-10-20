package com.AV.laundry.fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.AV.laundry.DBHelper;
import com.AV.laundry.Adapter.MemberAdapter;
import com.AV.laundry.R;
import com.AV.laundry.Model.member;

import java.util.ArrayList;
import java.util.List;


public class MemberTab extends Fragment
{
    EditText Name,Address,MobileNo;
    Button add;
    RecyclerView recyclerView;



    List<member> list;
    MemberAdapter adapter;
    private DBHelper mydb;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_member_tab, container, false);
        add=v.findViewById(R.id.btn_add);
        recyclerView=(RecyclerView)v.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(v.getContext()));
        list=new ArrayList<>();
        mydb = new DBHelper(v.getContext());
        Name=(EditText)v.findViewById(R.id.editText_nm);
        Address=(EditText)v.findViewById(R.id.editText_adr);
        MobileNo=(EditText)v.findViewById(R.id.editText_mno);

        list=new ArrayList<>();
        mydb = new DBHelper(v.getContext());


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                String name= Name.getText().toString().trim();
                String Adr= Address.getText().toString().trim();
                String mno=MobileNo.getText().toString().trim();
                boolean check=true;
                if(name.equals("") )
                {
                    Name.setError("Plz Enter Name");
                    check=false;
                }
                if(Adr.equals(""))
                {
                    Address.setError("plz Enter Address");
                    check=false;
                }
                if(mno.equals(""))
                {
                    MobileNo.setError("Plz Enter MobiileNo");
                    check=false;
                }
                if(check)
                {
                    mydb.insertDataMember(name,Adr,mno);
                    member m= new member();
                    m.setName(name);
                    m.setAddress(Adr);
                    m.setMobileNo(mno);
                    list.add(0,m);

                    adapter.notifyItemInserted(0);
                    recyclerView.smoothScrollToPosition(0);
                    Toast.makeText(v.getContext(), " Added Successfully", Toast.LENGTH_SHORT).show();
                    Name.setText("");
                    Address.setText("");
                    MobileNo.setText("");
                }
            }
        });
        SelectData();
        return  v;
    }

    private void SelectData()
    {
        Cursor cursorMember = mydb.getMember();
        cursorMember.moveToFirst();
        while (cursorMember.isAfterLast() == false) {
            member m = new member();
            m.setId(cursorMember.getInt(0));
            m.setName(cursorMember.getString(1));
            m.setAddress(cursorMember.getString(2));
            m.setMobileNo(cursorMember.getString(3));
            list.add(m);
            cursorMember.moveToNext();
        }
        cursorMember.close();

        adapter = new MemberAdapter(list);
        recyclerView.setAdapter(adapter);
    }
    public void clear()
    {
        Name.setText(null);
        //ContactsContract.CommonDataKinds.Email.setText(null);
        Address.setText(null);
        MobileNo.setText(null);

    }

}
