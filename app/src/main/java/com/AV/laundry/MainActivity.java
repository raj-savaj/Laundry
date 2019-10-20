package com.AV.laundry;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.AV.laundry.Model.Laundry;

public class MainActivity extends AppCompatActivity
{
    EditText UserName,Email,MobileNo,PassWord;
    Button add;
    private DBHelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        add=(Button) findViewById(R.id.btn_add);
        mydb = new DBHelper(this);
        UserName=(EditText)findViewById(R.id.editText_unm);
        Email=(EditText)findViewById(R.id.editText_email);
        MobileNo=(EditText)findViewById(R.id.editText_mno);
        PassWord=(EditText)findViewById(R.id.editText_pwd);
        clear();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String Name=UserName.getText().toString().trim();
                String email=Email.getText().toString().trim();
                String mno=MobileNo.getText().toString().trim();
                String pwd=PassWord.getText().toString().trim();
                boolean check=true;
                if(Name.length()<2)
                {
                    UserName.setError("Please Enter Username");
                    check=false;
                }
                else
                {
                    if(!Name.matches("[a-zA-Z]+"))
                    {
                        UserName.setError("Please Enter Only Character");
                        check=false;
                    }
                }
                if(email.length()<2)
                {
                    Email.setError("Please Enter Email");
                    check=false;
                }
                else
                {
                    if(!email.matches("^\\S+@\\S+\\.\\S+$"))
                    {
                        Email.setError("Please Enter Correct Email");
                        check=false;
                    }
                }
                if(mno.length()<2)
                {
                    MobileNo.setError("Please Enter Mobile No");
                    check=false;
                }
                else
                {
                    if(!mno.matches("\\d{10}"))
                    {
                        MobileNo.setError("Please Enter Only 10 Digit");
                        check=false;
                    }
                }
                if(pwd.length()<4 || pwd.length() > 7)
                {
                    PassWord.setError("Please Enter Passweord 4 to 6 letter");
                    check=false;
                }
                if(check)
                {
                    mydb.insertData(Name,email,mno,pwd);
                    Laundry laundry=new Laundry();
                    laundry.setName(Name);
                    laundry.setEmail(email);
                    laundry.setMobileNo(mno);
                    laundry.setPassword(pwd);
                    Toast.makeText(MainActivity.this, "Account Created Successfully", Toast.LENGTH_SHORT).show();
                    Intent i= new Intent(getApplicationContext(),LoginActivity.class);
                    startActivity(i);
                }
            }
        });
    }
    public void clear()
    {
        UserName.setText(null);
        Email.setText(null);
        MobileNo.setText(null);
        PassWord.setText(null);

    }
}
