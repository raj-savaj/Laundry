package com.AV.laundry.fragment;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.AV.laundry.Adapter.ClothesAdapter;
import com.AV.laundry.Adapter.clothsearchAdapater;
import com.AV.laundry.Model.Account;
import com.AV.laundry.Adapter.AccountAdapter;
import com.AV.laundry.DBHelper;
import com.AV.laundry.Model.Clothes;
import com.AV.laundry.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class AccountTab extends Fragment
{
    EditText StartDate,EndDate,BasePrice;
    TextView txttotdue,txttotpay;
    AppCompatAutoCompleteTextView Name;
    Button add;
    RecyclerView recyclerView;
    LinearLayout header;
    List<Account> list;
    List<Clothes> clothesList;
    private DBHelper mydb;
    View view;

    private DatePickerDialog sdatepick,edatepick;
    private SimpleDateFormat dateFormatter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_account_tab, container, false);
        this.view=v;
        add=v.findViewById(R.id.btn_add);
        recyclerView=(RecyclerView)v.findViewById(R.id.recycler_view_account);
        recyclerView.setLayoutManager(new LinearLayoutManager(v.getContext()));

        mydb = new DBHelper(v.getContext());
        Name=v.findViewById(R.id.editText_nm);
        StartDate =(EditText)v.findViewById(R.id.editText_sd);
        EndDate=(EditText)v.findViewById(R.id.editText_ed);
        BasePrice=(EditText)v.findViewById(R.id.editText_bp);
        header=(LinearLayout)v.findViewById(R.id.header);
        txttotdue=(TextView)v.findViewById(R.id.totdue);
        txttotpay=(TextView)v.findViewById(R.id.totpaid);
        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        Calendar newCalendar = Calendar.getInstance();

        sdatepick = new DatePickerDialog(view.getContext(), new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                StartDate.setText(dateFormatter.format(newDate.getTime()));
            }
        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        edatepick = new DatePickerDialog(view.getContext(), new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                EndDate.setText(dateFormatter.format(newDate.getTime()));
            }
        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));


        StartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sdatepick.show();
            }
        });
        EndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edatepick.show();
            }
        });

        list=new ArrayList<>();
        mydb = new DBHelper(v.getContext());
        clothesList=new ArrayList<>();
        final clothsearchAdapater adapter = new clothsearchAdapater(clothesList);
        recyclerView.setAdapter(adapter);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clothesList.clear();
                String name = Name.getText().toString().trim();
                String startdate = StartDate.getText().toString().trim();
                String enddate = EndDate.getText().toString().trim();
                String baseprice = BasePrice.getText().toString().trim();
                boolean check=true;

                if(name.equals(""))
                {
                    Name.setError("Please Enter Name");
                    check=false;
                }
                if(startdate.equals(""))
                {
                    StartDate.setError("Please Select Start Date");
                    check=false;
                }
                if(enddate.equals(""))
                {
                    EndDate.setError("Please Select End Date");
                    check=false;
                }
                if(baseprice.equals(""))
                {
                    BasePrice.setError("Please Enter Base Price ");
                    check=false;
                }

                if(check)
                {
                    Cursor cursordata =mydb.getSortCloth(name,startdate,enddate);
                    if(cursordata!=null)
                    {
                        if(cursordata.getCount()!=0)
                        {
                            header.setVisibility(View.VISIBLE);
                            int totpay=0,totdue=0;
                            int qty;
                            cursordata.moveToFirst();
                            while (!cursordata.isAfterLast()) {
                                Clothes clothes=new Clothes();
                                clothes.setDate( cursordata.getString(cursordata.getColumnIndex("Date")));
                                clothes.setId( cursordata.getInt(cursordata.getColumnIndex("cid")));
                                clothes.setClothes(cursordata.getString(cursordata.getColumnIndex("Clothes")));
                                clothes.setPrice(cursordata.getInt(cursordata.getColumnIndex("Price")));
                                clothes.setStatus(cursordata.getInt(cursordata.getColumnIndex("status")));
                                qty=Integer.parseInt(clothes.getClothes());
                                if(clothes.getPrice()==0)
                                {
                                    clothes.setPrice(Integer.parseInt(baseprice));
                                    totdue+=clothes.getPrice()*qty;
                                }
                                else {
                                    totpay+=clothes.getPrice()*qty;
                                }
                                clothesList.add(clothes);
                                cursordata.moveToNext();
                            }
                            adapter.notifyDataSetChanged();
                            txttotdue.setText(""+totdue);
                            txttotpay.setText(""+totpay);
                            cursordata.close();
                            Name.setText("");
                            StartDate.setText("");
                            EndDate.setText("");
                        }
                        else
                        {
                            header.setVisibility(View.GONE);
                            adapter.notifyDataSetChanged();
                            Toast.makeText(v.getContext(), "No Cloths Are Founded", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
        return  v;
    }

    public void getClintname()
    {
        ArrayList<String> fruits =new ArrayList<>();
        Cursor cursorName = mydb.getMembername();
        cursorName.moveToFirst();
        int count=0;
        while (cursorName.isAfterLast() == false) {
            fruits.add(cursorName.getString(0));
            cursorName.moveToNext();
            count++;
        }
        cursorName.close();
        ArrayAdapter<String> ad = new ArrayAdapter<String>
                (view.getContext(), android.R.layout.select_dialog_item, fruits);
        Name.setThreshold(1);
        Name.setAdapter(ad);
    }
}
