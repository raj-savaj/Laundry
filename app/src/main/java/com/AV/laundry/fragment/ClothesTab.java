package com.AV.laundry.fragment;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.app.DatePickerDialog.OnDateSetListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.AV.laundry.Model.Clothes;
import com.AV.laundry.Adapter.ClothesAdapter;
import com.AV.laundry.DBHelper;
import com.AV.laundry.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class ClothesTab extends Fragment
{
    EditText Clothes,Date;
    AppCompatAutoCompleteTextView Name;
    Button add;
    RecyclerView recyclerView;
    private DatePickerDialog fromDatePickerDialog;
    private SimpleDateFormat dateFormatter;
    List<com.AV.laundry.Model.Clothes> list;
    ClothesAdapter adapter;
    private DBHelper mydb;
    private View view;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v= inflater.inflate(R.layout.fragment_clothes_tab, container, false);
        view=v;

        add=v.findViewById(R.id.btn_add);
        recyclerView=(RecyclerView)v.findViewById(R.id.recycler_view_clths);
        recyclerView.setLayoutManager(new LinearLayoutManager(v.getContext()));
        Name=v.findViewById(R.id.editText_nm);
        Clothes =(EditText)v.findViewById(R.id.editText_clths);
        Date=(EditText)v.findViewById(R.id.editText_dt);

        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        java.util.Date date = Calendar.getInstance().getTime();
        Date.setText(dateFormatter.format(date));
        Date.setEnabled(false);
      /*  fromDatePickerDialog = new DatePickerDialog(view.getContext(), new OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                Date.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fromDatePickerDialog.show();
            }
        });*/
        list=new ArrayList<>();
        mydb =new DBHelper(v.getContext());
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = Name.getText().toString().trim();
                String clths = Clothes.getText().toString().trim();
                String date = Date.getText().toString().trim();

                boolean check=true;
                if(name.equals("") )
                {
                    Name.setError("Please Fill Name");
                    check=false;
                }
                if(clths.equals(""))
                {
                    Clothes.setError("Please Fill Cloths ");
                    check=false;
                }
                if(date.equals(""))
                {
                    Date.setError("Please Select Date");
                    check=false;
                }

                if(check)
                {
                    mydb.insertDataClothes(name, clths, date);
                    Name.setText("");
                    Clothes.setText("");
                    Toast.makeText(v.getContext(), " Added Successfully", Toast.LENGTH_SHORT).show();
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

    private void SelectData()
    {
        Cursor cursorClothes = mydb.getClths();
        cursorClothes.moveToFirst();
        while (cursorClothes.isAfterLast() == false) {
            Clothes c = new Clothes();
            c.setName(cursorClothes.getString(1));
            c.setClothes(cursorClothes.getString(2));
            c.setDate(cursorClothes.getString(3));
            list.add(c);
            cursorClothes.moveToNext();
        }
        cursorClothes.close();
        adapter = new ClothesAdapter(list);
        recyclerView.setAdapter(adapter);
    }

}

