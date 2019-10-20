package com.AV.laundry;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    private DBHelper mydb;
    private Button reg;

    // UI references.
    private EditText mPasswordView,mEmailView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mydb = new DBHelper(this);

        mEmailView = (EditText) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);

        reg=(Button) findViewById(R.id.registration);
        reg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent i =new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);
            }
        }
        );

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=mEmailView.getText().toString().trim();
                String pass=mPasswordView.getText().toString().trim();
                boolean check=true;
                if(email.equals(""))
                {
                    mEmailView.setError("Please Enter Email Address");
                    check=false;
                }
                if(pass.equals(""))
                {
                    mPasswordView.setError("Please Enter Password");
                    check=false;
                }
                if(check)
                {
                    if (mydb.checkUser(email,pass)) {
                        SharedPreferences sp = getApplicationContext().getSharedPreferences("pref", MODE_PRIVATE);
                        SharedPreferences.Editor ed = sp.edit();
                        ed.putString("email", mEmailView.getText().toString());
                        ed.putString("pass", mPasswordView.getText().toString());
                        ed.commit();
                        Toast.makeText(getApplicationContext(),"Login Successfully",Toast.LENGTH_LONG) .show();
                        Intent i = new Intent(getApplicationContext(), Tab.class);
                        startActivity(i);

                    }
                    else {
                        Toast.makeText(getApplicationContext(),"Login Failed",Toast.LENGTH_LONG).show();
                    }
                }

            }
        });
    }
}

